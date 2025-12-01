pipeline {
    agent any

    environment {
        // set to your Docker Hub repo; change username if needed
        registry = "mohit3579/calculator-app"
        registryCredential = 'dockerhub'   // Jenkins credential id (username/password)
        dockerImage = ''                   // will be set after Jib build
    }

    tools {
        // Ensure you have a Maven installation named "M3"
        maven 'M3'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/Mohitsai3579/calculator.git'
            }
        }

        stage('Maven Build (package)') {
            steps {
                script {
                    def mvnHome = tool name: 'M3', type: 'maven'
                    if (isUnix()) {
                        sh "${mvnHome}/bin/mvn -B -DskipTests=true clean package"
                    } else {
                        // Use mvn.cmd on Windows to avoid 'sh' requirement
                        bat "\"${mvnHome}\\bin\\mvn.cmd\" -B -DskipTests=true clean package"
                    }
                }
            }
        }

        stage('Build & Push Image (Jib)') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${registryCredential}",
                                                 usernameVariable: 'DOCKERHUB_USER',
                                                 passwordVariable: 'DOCKERHUB_PASS')]) {
                    script {
                        def imageTag = "${registry}:${env.BUILD_NUMBER}"
                        def mvnHome = tool name: 'M3', type: 'maven'

                        if (isUnix()) {
                            sh """
                              ${mvnHome}/bin/mvn -B -DskipTests=true \
                                -Dimage=${imageTag} \
                                -Djib.to.auth.username=${DOCKERHUB_USER} \
                                -Djib.to.auth.password=${DOCKERHUB_PASS} \
                                com.google.cloud.tools:jib-maven-plugin:build
                            """
                        } else {
                            // Windows - use mvn.cmd; escape quotes for bat
                            bat "\"${mvnHome}\\bin\\mvn.cmd\" -B -DskipTests=true -Dimage=${imageTag} -Djib.to.auth.username=%DOCKERHUB_USER% -Djib.to.auth.password=%DOCKERHUB_PASS% com.google.cloud.tools:jib-maven-plugin:build"
                        }

                        // record built image tag for later stages / logs
                        env.dockerImage = imageTag
                        echo "Built and pushed image: ${env.dockerImage}"
                    }
                }
            }
        }

        stage('Post') {
            steps {
                echo "Image available at: ${registry}:${env.BUILD_NUMBER}"
            }
        }

        stage('Optional: Deploy / Notify') {
            steps {
                script {
                    // keep Rundeck call wrapped in try/catch if you still want it;
                    // it will not fail pipeline if plugin is missing.
                    try {
                        step([
                          $class: "RundeckNotifier",
                          rundeckInstance: "myRundeck",
                          options: "Build_Number=${env.BUILD_NUMBER}",
                          jobId: "b6b65fd4-0f56-4049-b608-837207c1a844",
                          shouldFailTheBuild: true
                        ])
                    } catch (err) {
                        echo "Rundeck notify skipped (plugin missing or misconfigured): ${err}"
                    }
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline finished with status: ${currentBuild.currentResult}"
        }
        failure {
            echo "Build failed: ${env.JOB_NAME} #${env.BUILD_NUMBER} - check console output."
        }
    }
}
