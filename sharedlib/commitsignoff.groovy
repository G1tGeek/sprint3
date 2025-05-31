def checkSignOff(Map args = [:]) {
    def allowedEmails = args.allowedEmails?.tokenize(',') ?: []

    def commitMessage = sh(script: "git log -1 --pretty=%B", returnStdout: true).trim()
    echo "Latest Commit Message:\n${commitMessage}"

    def signedOffLine = commitMessage.readLines().find { it.startsWith("Signed-off-by:") }

    if (!signedOffLine) {
        currentBuild.result = 'FAILURE'
        env.SIGN_OFF_STATUS = 'missing'
        error("❌ Commit is missing Signed-off-by line.")
    }

    echo "✅ Found: ${signedOffLine}"
    env.SIGNED_OFF_LINE = signedOffLine

    def emailRegex = ~/.*<(.+)>/
    def matcher = signedOffLine =~ emailRegex
    def email = matcher ? matcher[0][1] : null
    env.SIGNED_OFF_EMAIL = email

    if (!allowedEmails.contains(email)) {
        currentBuild.result = 'FAILURE'
        env.SIGN_OFF_STATUS = 'unauthorized'
        error("❌ Signed-off-by email '${email}' is not allowed.")
    }

    env.SIGN_OFF_STATUS = 'valid'
}


def notifySuccess(Map args = [:]) {
    slackSend(
        channel: args.channel,
        color: 'good',
        message: """\
✅ *Build #${env.BUILD_NUMBER} succeeded*
• Repository: ${args.gitUrl}
• ${args.signedOffLine}
• Job URL: ${env.BUILD_URL}
""",
        tokenCredentialId: args.slackCredentialId
    )

    emailext(
        to: args.emailTo,
        subject: "Build #${env.BUILD_NUMBER} Success - Valid Sign-off",
        body: """\
Hello,

The latest Jenkins build completed successfully.

• Job: ${env.JOB_NAME}
• Build Number: ${env.BUILD_NUMBER}
• Repository: ${args.gitUrl}
• Signed-off-by: ${args.signedOffLine}
• Job URL: ${env.BUILD_URL}

Regards,
Jenkins
""",
        mimeType: 'text/plain'
    )
}


def notifyFailure(Map args = [:]) {
    def baseMessage = """\
• Repository: ${args.gitUrl}
• Job URL: ${env.BUILD_URL}
"""

    if (args.signOffStatus == 'missing') {
        slackSend(
            channel: args.channel,
            color: 'danger',
            message: "❌ *Build #${env.BUILD_NUMBER} failed*\n• Reason: Missing Signed-off-by line\n${baseMessage}",
            tokenCredentialId: args.slackCredentialId
        )

        emailext(
            to: args.emailTo,
            subject: "Build #${env.BUILD_NUMBER} Failed - Missing Signed-off-by",
            body: """\
Hello,

The Jenkins build failed due to a missing Signed-off-by line.

• Job: ${env.JOB_NAME}
• Build Number: ${env.BUILD_NUMBER}
${baseMessage}

Please ensure all commits are signed off properly.

Regards,
Jenkins
""",
            mimeType: 'text/plain'
        )

    } else if (args.signOffStatus == 'unauthorized') {
        slackSend(
            channel: args.channel,
            color: 'warning',
            message: """\
⚠️ *Build #${env.BUILD_NUMBER} failed*
• Reason: Unauthorized Signed-off-by
• Found: ${args.signedOffLine}
• Allowed Emails: ${args.allowedEmails}
${baseMessage}
""",
            tokenCredentialId: args.slackCredentialId
        )

        emailext(
            to: args.emailTo,
            subject: "Build #${env.BUILD_NUMBER} Failed - Unauthorized Sign-off",
            body: """\
Hello,

The Jenkins build failed due to an unauthorized Signed-off-by email.

• Job: ${env.JOB_NAME}
• Build Number: ${env.BUILD_NUMBER}
• Found Signed-off-by: ${args.signedOffLine}
• Allowed Emails: ${args.allowedEmails}
${baseMessage}

Please ensure only authorized contributors sign off commits.

Regards,
Jenkins
""",
            mimeType: 'text/plain'
        )

    } else {
        slackSend(
            channel: args.channel,
            color: 'danger',
            message: "❌ *Build #${env.BUILD_NUMBER} failed due to an internal error.*\n${baseMessage}",
            tokenCredentialId: args.slackCredentialId
        )

        emailext(
            to: args.emailTo,
            subject: "Build #${env.BUILD_NUMBER} Failed - Internal Error",
            body: """\
Hello,

The Jenkins build failed due to an internal error unrelated to Signed-off-by validation.

• Job: ${env.JOB_NAME}
• Build Number: ${env.BUILD_NUMBER}
${baseMessage}

Regards,
Jenkins
""",
            mimeType: 'text/plain'
        )
    }
}
