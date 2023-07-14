pipeline {
environment {
registry = "yoniss/spring-expense"
registryCredential = 'dockerhub_id'
dockerImage = ''
}
    agent any
    stages{
    stage('Build maven'){
                steps{
                    script{
                       sh 'mvn clean install'
                    }
                }
            }
        stage('Build docker image'){
            steps{
                script{
                   dockerImage = docker.build registry
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


    }
}