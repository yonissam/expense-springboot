pipeline {
    agent any
    tools{
        maven
    }
    stages{
        stage('Build Maven'){
            steps{
                sh 'mvn clean install'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t yoniss/expense .'
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                   withCredentials([string(credentialsId: 'DOCKER_HUB', variable: 'dockerhubpwd')]) {
                   sh 'docker login -u yoniss -p ${dockerhubpwd}'

}
                   sh 'docker push yoniss/expense'
                }
            }
        }
    }
}