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
                       sh 'mvn clean install -DskipTests'
                    }
                }
            }

             stage('Unit Test'){
                        steps {
                            sh 'mvn test'
                        }
                    }

                    stage('Integration Test'){
                                steps {
                                    sh 'mvn verify -DskipUnitTests'
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
                  dockerImage.push("$BUILD_NUMBER")
                  dockerImage.push('latest')
                }
                }
            }
        }

        stage('Deploy to Kubernetes') {
              steps {
                withKubeConfig([credentialsId: 'kubeconfig', serverUrl: 'https://192.168.0.38:6443']) {
                  sh 'kubectl apply -f expense-spring-deployment.yaml'
                }
              }
            }


    }
}