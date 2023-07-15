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
                   dockerImage = docker.build "${IMAGE_NAME}"
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

        stage('Updating kubernetes deployment file') {
        steps{
        script {
                sh """#!/bin/bash
                cat expense-spring-deployment.yaml | grep image
                sed -i  's|${APP_NAME}.*|${APP_NAME}:${IMAGE_TAG}|' expense-spring-deployment.yaml
                cat expense-spring-deployment.yaml | grep image
                """
        }
        }
        }




    }
}