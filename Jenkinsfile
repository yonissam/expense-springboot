pipeline {
environment {
DOCKERHUB_USERNAME = "yoniss"
APP_NAME = "spring-expense"
IMAGE_TAG = "${BUILD_NUMBER}"
IMAGE_NAME = "${DOCKERHUB_USERNAME}" + "/" + "${APP_NAME}"
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

             stage('Unit Test Maven'){
                        steps {
                            sh 'mvn test'
                        }
                    }

                    stage('Integration Test Maven'){
                                steps {
                                    sh 'mvn verify -DskipUnitTests'
                                }
                            }

        stage('Build docker image'){
            steps{
                script{
                   dockerImage = docker.build "${IMAGE_NAME}"
                }
            }
        }
        stage('Push image to Docker Hub Registry'){
            steps{
                script{
                   docker.withRegistry( '', registryCredential ) {
                  dockerImage.push("$BUILD_NUMBER")
                  dockerImage.push('latest')
                }
                }
            }
        }

        stage('Delete Docker Images from local docker repo') {
        steps{
          script{
                sh 'docker rmi ${IMAGE_NAME}:${IMAGE_TAG}'
                sh 'docker rmi ${IMAGE_NAME}:latest'
          }
        }
        }

        stage('Trigger config change pipeline to jenkins'){
           steps{
               script{
                    sh "curl -v -k --user yoniss:11dee7cae1810803b7e2aa51d53ed660c1 -X POST -H 'cache-control: no-cache' -H 'content-type: application/x-www-form-urlencoded' --data 'IMAGE_TAG=${IMAGE_TAG}' 'http://192.168.0.139:8080/job/expense-spring-argo/buildWithParameters?token=gitops-config'"
               }
           }
        }


    }
}