def installPythonPackages() {
    sh '''
        set -e

        check_package() {
            dpkg -s "$1" >/dev/null 2>&1
        }

        MISSING=0

        for pkg in python3 python3-pip python3-venv; do
            if check_package "$pkg"; then
                echo "$pkg is already installed."
            else
                echo "$pkg is NOT installed."
                MISSING=1
            fi
        done

        if [ "$MISSING" -eq 1 ]; then
            echo "Installing missing packages..."
            sudo apt-get update
            sudo apt-get install -y python3 python3-pip python3-venv
        else
            echo "All required packages are already installed."
        fi
    '''
}

def auditDependencies(String projectDir, String venvName, String outputFile) {
    dir(projectDir) {
        sh """
            set -e
            python3 -m venv ${venvName}
            . ${venvName}/bin/activate
            pip install --upgrade pip pip-audit
            pip freeze > requirements.txt
            pip-audit -r requirements.txt > ${outputFile} || true
            deactivate
        """

        // Check if audit report contains vulnerabilities
        def result = sh(script: "grep -q 'Vulnerability:' ${outputFile}", returnStatus: true)
        if (result == 0) {
            currentBuild.result = 'UNSTABLE'
            echo "⚠️ Vulnerabilities found in ${projectDir}."
        } else {
            echo "✅ No vulnerabilities found in ${projectDir}."
        }

        // Archive the audit report
        archiveArtifacts artifacts: outputFile, allowEmptyArchive: true
    }
}

def sendSlack(String status, env) {
    def color = status == 'success' ? 'good' : (status == 'unstable' ? '#FFA500' : 'danger')
    def emoji = status == 'success' ? '✅' : (status == 'unstable' ? '⚠️' : '❌')
    def title = status == 'success' ? 'succeeded' : (status == 'unstable' ? 'has vulnerabilities' : 'failed')

    slackSend(
        channel: env.SLACK_CHANNEL,
        color: color,
        message: """\
${emoji} *Build #${env.BUILD_NUMBER} ${title}*
• Job: ${env.JOB_NAME}
• URL: ${env.BUILD_URL}
""",
        tokenCredentialId: env.SLACK_CREDENTIAL_ID
    )
}

def sendEmail(String subjectStatus, env) {
    emailext(
        to: env.EMAIL_TO,
        subject: "Jenkins Build #${env.BUILD_NUMBER} - ${subjectStatus}",
        body: """\
Hello Yuvraj,

Your Jenkins build status: ${subjectStatus}

• Job: ${env.JOB_NAME}
• Build Number: ${env.BUILD_NUMBER}
• URL: ${env.BUILD_URL}

Please review the audit reports (if any).

Regards,  
Jenkins
""",
        attachmentsPattern: '**/pip_audit_*.txt',
        mimeType: 'text/plain'
    )
}
