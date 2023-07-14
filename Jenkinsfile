pipeline {
    agent any
    stages{

       stage('Deploy App to Kubernetes') {
             steps {
                script{
                 withCredentials([file(credentialsId: 'mykubeconfig', variable: 'KUBECONFIG')]) {
                   sh 'kubectl apply -f expense-spring-deployment.yaml'
                 }
               }
             }
           }

    }
}