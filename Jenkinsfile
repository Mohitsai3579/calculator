pipeline {
    environment {
        registry = "byterider/cal"
        registryCredential = 'dockerhub'
        dockerImage = ''
    }
    agent any

    tools {
        maven 'M3'   // name of the Maven installation you added in Global Tool Configuration
    }

    stages {
        stage('SCM Checkout') {
            steps {
                git 'https://github.com/Mohitsai3579/calculator.git'
            }
        }

        stage('Clean') {
            steps {
                script {
                    def mvnHome = tool name: 'M3', type: 'maven'
                    if (isUnix()) {
                        sh "${mvnHome}/bin/mvn clean"
                    } else {
                        // wrap path in quotes in case there are spaces
                        bat "\"${mvnHome}\\bin\\mvn\" clean"
                    }
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    def mvnHome = tool name: 'M3', type: 'maven'
                    if (isUnix()) {
                        sh "${mvnHome}/bin/mvn -B install"
                    } else {
                        bat "\"${mvnHome}\\bin\\mvn\" -B install"
                    }
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

        // Docker stages: be careful — docker build/push on Windows requires Docker available
        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("${registry}:${env.BUILD_NUMBER}")
                }
            }
        }

        stage('Push Docker Image') {
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
                    if (isUnix()) {
                        sh "docker rmi ${registry}:${env.BUILD_NUMBER} || true"
                        sh "docker image prune -f"
                    } else {
                        bat "docker rmi ${registry}:${env.BUILD_NUMBER} || exit 0"
                        bat "docker image prune -f"
                    }
                }
            }
        }

        stage('Deploy on Node') {
            steps {
                script {
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
}
