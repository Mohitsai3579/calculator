pipeline {
    environment{
        registry = "byterider/cal"
        registryCredential = 'dockerhub'
        dockerImage = ''
    }
    agent any
// Stages
    stages {
        stage('SCM Checkout'){
            steps{
                git 'https://github.com/SEETHAMRAJU/calculator.git'
            }
        }
        stage('Clean'){
            steps{
                sh 'mvn clean'
            }
        }
        stage('Build'){
            steps{
                sh 'mvn install'
            }
        }
        stage('Test'){
            steps{
                sh 'mvn test'
            }
        }
        stage('Build Docker Image'){
            steps{
                script{
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"

                }
            }
        }
        stage('Deploy/Push Docker Image to hub'){
               steps{
                    script{
                        docker.withRegistry('',registryCredential){
                            dockerImage.push()
                        }
                    }
               }
        }
        stage('Remove docker images that we dont need'){
            steps{
                sh "docker rmi $registry:$BUILD_NUMBER"
                sh "docker image prune"
            }
        }
        stage('Deploy on Node'){
            steps{
                script{
                    step([
                                $class: "RundeckNotifier",
                                rundeckInstance: "myRundeck",
                                options: """ Build_Number=$BUILD_NUMBER""",
                                jobId: "b6b65fd4-0f56-4049-b608-837207c1a844",
                                shouldFailTheBuild: true,
                    ])
                }
            }
        }
    }
}
