def checkoutFromGit(String url, String branch = 'main', String credentialsId = 'yuvraj-snaatak-git') {
    checkout([
        $class: 'GitSCM',
        branches: [[name: "*/${branch}"]],
        userRemoteConfigs: [[url: url, credentialsId: credentialsId]]
    ])
}

def runSonarAnalysis(String projectKey, String sonarUrl, String sonarTokenId) {
    withSonarQubeEnv('Yuvraj-sonar') {
        withCredentials([string(credentialsId: sonarTokenId, variable: 'SONAR_TOKEN')]) {
            sh """
                mvn clean verify -DskipTests sonar:sonar \\
                  -Dsonar.projectKey=${projectKey} \\
                  -Dsonar.host.url=${sonarUrl} \\
                  -Dsonar.login=${SONAR_TOKEN}
            """
        }
    }
}

def notifySlackSuccess(String channel, String gitUrl, String buildUrl, String tokenId) {
    slackSend(
        channel: channel,
        color: 'good',
        message: """\
✅ *SonarQube Analysis Succeeded*
• Repository: ${gitUrl}
• Job URL: ${buildUrl}
""",
        tokenCredentialId: tokenId
    )
}

def notifySlackFailure(String channel, String gitUrl, String buildUrl, String tokenId) {
    slackSend(
        channel: channel,
        color: 'danger',
        message: """\
❌ *SonarQube Analysis Failed*
• Repository: ${gitUrl}
• Job URL: ${buildUrl}
""",
        tokenCredentialId: tokenId
    )
}

def sendSonarEmail(String emailTo, String jobName, String buildNumber, String buildUrl, String sonarUrl, String projectKey) {
    emailext(
        to: emailTo,
        subject: "SonarQube Report - Jenkins Build #${buildNumber}",
        body: """\
Hello Yuvraj,

The SonarQube analysis for your Jenkins job is complete.

• Job: ${jobName}
• Build Number: ${buildNumber}
• Build URL: ${buildUrl}
• SonarQube Dashboard: ${sonarUrl}/dashboard?id=${projectKey}

Regards,
Jenkins
""",
        mimeType: 'text/plain'
    )
}
