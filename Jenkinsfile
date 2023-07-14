pipeline {
environment {
registry = "yoniss/expense-spring"
registryCredential = 'dockerhub_id'
dockerImage = ''
}
    agent any
    stages{
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
       stage('Deploy App to Kubernetes') {
             steps {
               container('kubectl') {
                 withCredentials([file(credentialsId: 'mykubeconfig', variable: 'KUBECONFIG')]) {
                   sh 'kubectl apply -f expense-spring-deployment.yaml'
                 }
               }
             }
           }

    }
}