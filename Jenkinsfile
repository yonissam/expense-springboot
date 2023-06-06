pipeline {
environment {
registry = "yoniss/expense"
registryCredential = 'dockerhub_id'
dockerImage = ''
}
    agent any
    stages{
        stage('Build Maven'){
            steps{
                sh 'mvn clean install'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                   dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                   docker.withRegistry( '', registryCredential ) {
                  dockerImage.push()
                }
                }
            }
        }
        stage('Deploy') {
                steps{
                script{
                                   docker.image('yoniss/expense').withRun('-d --name expense -p 8085:8085')
                                }
                }
                }
        stage('Cleaning up') {
        steps{
        sh "docker rmi $registry:$BUILD_NUMBER"
        }
        }
    }
}