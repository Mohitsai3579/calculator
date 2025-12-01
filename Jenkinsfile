pipeline {
    agent any

    environment {
        registry = "byterider/cal"
        registryCredential = 'dockerhub'
        dockerImage = ''
    }

    tools {
        // Name must match the Maven installation name you create in Global Tool Configuration
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
                    if (isUnix()) {
                        sh 'mvn clean'
                    } else {
                        bat 'mvn clean'
                    }
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn -B install'
                    } else {
                        bat 'mvn -B install'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn test'
                    } else {
                        bat 'mvn test'
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // only attempt docker build if there's a Dockerfile
                    if (fileExists('Dockerfile')) {
                        dockerImage = docker.build("${registry}:${env.BUILD_NUMBER}")
                    } else {
                        echo "No Dockerfile found — skipping docker build"
                    }
                }
            }
        }

        stage('Push Docker Image') {
            when {
                expression { return dockerImage != '' && dockerImage != null }
            }
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Clean Docker Images') {
            steps {
                script {
                    if (fileExists('Dockerfile')) {
                        if (isUnix()) {
                            sh "docker rmi ${registry}:${env.BUILD_NUMBER} || true"
                            sh "docker image prune -f || true"
                        } else {
                            bat "docker rmi ${registry}:${env.BUILD_NUMBER} || exit 0"
                            bat "docker image prune -f || exit 0"
                        }
                    } else {
                        echo "No Dockerfile — nothing to clean"
                    }
                }
            }
        }

        stage('Deploy on Node') {
            steps {
                script {
                    // Rundeck notifier — keep as-is, ensure Rundeck plugin & instance configured
                    step([
                        $class: "RundeckNotifier",
                        rundeckInstance: "myRundeck",
                        options: "Build_Number=${env.BUILD_NUMBER}",
                        jobId: "b6b65fd4-0f56-4049-b608-837207c1a844",
                        shouldFailTheBuild: true
                    ])
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline finished with status: ${currentBuild.currentResult}"
        }
        failure {
            mail to: 'you@example.com', subject: "Build failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}", body: "See Jenkins console output."
        }
    }
}
