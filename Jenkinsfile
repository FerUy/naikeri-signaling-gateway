pipeline {
    agent any

    tools {
        jdk 'JDK 11'
		maven 'Maven_3.8.5'
	}

    options {
    	//buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '30', numToKeepStr: '10'))
    }

    parameters {
        string(name: 'SGW_MAJOR_VERSION', defaultValue: '2.1.1', description: 'The major version for naikeri-signaling-gateway-core')
    }

	stages {
        stage("Set Version") {
          steps {
			echo "Setting version to ${params.SGW_MAJOR_VERSION}-${BUILD_NUMBER} ..."
            sh "mvn versions:set -DnewVersion=${params.SGW_MAJOR_VERSION}-${BUILD_NUMBER}"
            echo "Setting version to ${params.SGW_MAJOR_VERSION}-${BUILD_NUMBER} completed"
          }
        }

		stage("Build") {
			steps {
				echo "Building application..."
				script {
           			currentBuild.displayName = "#${params.SGW_MAJOR_VERSION}-${BUILD_NUMBER}"
           			currentBuild.description = "naikeri-signalling-gateway-core"
       	        }
		  	    sh "mvn clean install -DskipTests"

			    echo "Maven build completed."
			}
		}

		stage("Release") {
            steps {
                withAnt(installation: 'Ant_1.10.12') {
        			echo "Building a released version"
                    dir('release') {
                        sh "ant -f build.xml -Dsgw.release.version=${params.SGW_MAJOR_VERSION}-${BUILD_NUMBER}"
         			}
        		}
        	}
        }

        stage('Save Artifacts') {
            steps {
                echo "Archiving Naikeri-Signaling-Gateway-Core-${params.SGW_MAJOR_VERSION}-${BUILD_NUMBER}"
                archiveArtifacts artifacts: "release/Naikeri-Signaling-Gateway-Core-*.zip", followSymlinks: false, onlyIfSuccessful: true
            }
        }

        stage('Push to jFrog') {
            when {anyOf {branch 'master'; branch 'release'}}
                 steps {
                 sh 'mvn deploy -DskipTests'
           }
        }
    }

	post {
		success {
			echo "SUCCESSFULLY built naikeri-signalling-gateway-core"
		}
		failure {
			echo "Build of naikeri-signalling-gateway-core FAILED."
		}
		always {
             sh 'rm -rf release/checkout'
             sh 'rm -rf release/target'
        }
	}
}
