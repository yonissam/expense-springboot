pipeline {
    agent any
    stages{

       stage('Deploy App to Kubernetes') {
             steps {
                {
                 withCredentials([file(credentialsId: 'mykubeconfig', variable: 'KUBECONFIG')]) {
                   sh 'kubectl apply -f expense-spring-deployment.yaml'
                 }
               }
             }
           }

    }
}