pipeline {
    agent any

    tools {
        jdk 'jdk-11'
        maven 'maven-3.9.12'
    }

    parameters {
        string(name: 'SGW_MAJOR_VERSION', defaultValue: '2.2.0', description: 'The major version for naikeri-signaling-gateway-core')
    }

    stages {
        stage('Set Version') {
            steps {
                echo "Setting version to ${params.SGW_MAJOR_VERSION}-${BUILD_NUMBER}"
                sh "mvn versions:set -DnewVersion=${params.SGW_MAJOR_VERSION}-${BUILD_NUMBER} -DgenerateBackupPoms=false"
            }
        }

        stage('Build') {
            steps {
                script {
                    currentBuild.displayName = "#${params.SGW_MAJOR_VERSION}-${BUILD_NUMBER}"
                    currentBuild.description = "naikeri-signaling-gateway-core"
                }
                sh "mvn clean install"
            }
        }

        stage('Save Artifacts') {
            steps {
                archiveArtifacts artifacts: "target/naikeri-signaling-gateway-core-${params.SGW_MAJOR_VERSION}-${BUILD_NUMBER}.jar", followSymlinks: false, onlyIfSuccessful: true
            }
        }

        stage('Push to jFrog') {
            when { anyOf { branch 'master'; branch 'release' } }
            steps {
                sh "mvn deploy -DskipTests"
            }
        }
    }

    post {
        success { echo "Successfully built naikeri-signaling-gateway-core ${params.SGW_MAJOR_VERSION}-${BUILD_NUMBER}" }
        failure { echo "Building naikeri-signaling-gateway-core failed." }
    }
}
