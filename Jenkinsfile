pipeline {

    agent any



    environment {

        registry = "byterider/cal"

        registryCredential = 'dockerhub'   // set this in Jenkins credentials if you will push

        dockerImage = ''                   // will be set if Docker build runs

    }



    tools {

        // Must match the Maven installation name in Global Tool Configuration

        maven 'M3'

    }



    stages {

        stage('SCM Checkout') {

            steps {

                // lightweight checkout

                git url: 'https://github.com/Mohitsai3579/calculator.git'

            }

        }



        stage('Clean') {

            steps {

                script {

                    // use configured Maven installation so 'mvn' is found even if not on PATH

                    def mvnHome = tool name: 'M3', type: 'maven'

                    if (isUnix()) {

                        sh "${mvnHome}/bin/mvn clean"

                    } else {

                        bat "\"${mvnHome}\\bin\\mvn\" clean"

                    }

                }

            }

        }



        stage('Build') {

    steps {

        script {

            // 1. Get the Maven tool path (Make sure 'M3' matches Manage Jenkins -> Tools -> Maven)

            def mvnHome = tool name: 'M3', type: 'maven'

            

            // 2. Force Windows execution (bat) and point to mvn.cmd

            // We added '.cmd' because Windows can fail to execute the file without the extension

            bat "\"${mvnHome}\\bin\\mvn.cmd\" -B install"

        }

    }

}

        stage('Test') {

            steps {

                script {

                    def mvnHome = tool name: 'M3', type: 'maven'

                    if (isUnix()) {

                        sh "${mvnHome}/bin/mvn test"

                    } else {

                        bat "\"${mvnHome}\\bin\\mvn\" test"

                    }

                }

            }

        }



        stage('Build Docker Image') {

            steps {

                script {

                    if (fileExists('Dockerfile')) {

                        try {

                            if (isUnix()) {

                                // using docker pipeline plugin on unix nodes

                                dockerImage = docker.build("${registry}:${env.BUILD_NUMBER}")

                            } else {

                                // Windows: run docker build via bat (ensure docker CLI present)

                                bat "docker build -t ${registry}:${env.BUILD_NUMBER} ."

                                dockerImage = "${registry}:${env.BUILD_NUMBER}"

                            }

                            echo "Docker image prepared: ${dockerImage}"

                        } catch (err) {

                            // do not fail the whole pipeline if docker build not available

                            echo "Docker build skipped/failed: ${err}"

                            dockerImage = '' 

                        }

                    } else {

                        echo "No Dockerfile found — skipping docker build"

                    }

                }

            }

        }
        stage('Push Docker Image') {             when {                 expression { return env.dockerImage != null && env.dockerImage != '' }             }             steps {                 script {                     try {                         if (isUnix()) {                             // use pipeline docker credentials (requires docker pipeline plugin & credential)                             docker.withRegistry('', registryCredential) {                                 dockerImage.push()                                 echo "Docker image pushed: ${dockerImage}"                             }                         } else {                             // Windows: try a simple docker push (credentials must be available on agent or prior login)                             bat "docker push ${dockerImage}"                             echo "Docker image pushed: ${dockerImage}"                         }                     } catch (err) {                         echo "Docker push failed or skipped: ${err}"                         // don't fail entire build for optional push failure                     }                 }             }         }


        



        stage('Clean Docker Images') {

            steps {

                script {

                    if (fileExists('Dockerfile') && env.dockerImage) {

                        if (isUnix()) {

                            sh "docker rmi ${registry}:${env.BUILD_NUMBER} || true"

                            sh "docker image prune -f || true"

                        } else {

                            bat "docker rmi ${registry}:${env.BUILD_NUMBER} || exit 0"

                            bat "docker image prune -f || exit 0"

                        }

                    } else {

                        echo "Nothing to clean (no Dockerfile or no built image)."

                    }

                }

            }

        }



        stage('Deploy on Node') {

            steps {

                script {

                    // keep Rundeck notifier if you have it configured; wrap in try/catch to avoid pipeline failure

                    try {

                        step([

                            $class: "RundeckNotifier",

                            rundeckInstance: "myRundeck",

                            options: "Build_Number=${env.BUILD_NUMBER}",

                            jobId: "b6b65fd4-0f56-4049-b608-837207c1a844",

                            shouldFailTheBuild: true

                        ])

                    } catch (err) {

                        echo "Rundeck notify failed or plugin not configured: ${err}"

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

            // avoid mail step unless mail plugin configured; just print helpful info

            echo "Build failed: ${env.JOB_NAME} #${env.BUILD_NUMBER} - check console output."

        }

    }

}
