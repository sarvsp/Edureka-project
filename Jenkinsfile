pipeline {
    agent {
        label 'dockerworkernode'
    }
    environment {
        BRANCH_NAME = 'master'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                    branches: [[name: "${BRANCH_NAME}"]],
                    userRemoteConfigs: [[url: 'https://github.com/sarvsp/Edureka-project.git']]])
            }
        }
        stage('Compile') {
            steps {
                echo 'compiling...'
                sh '/opt/apache-maven-3.8.8/bin/mvn compile' // Example Maven command
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                sh '/opt/apache-maven-3.8.8/bin/mvn test' // Example Maven command
            }
        }
        stage('Build') {
            steps {
                echo 'Building...'
                sh '/opt/apache-maven-3.8.8/bin/mvn package' // Example Maven command
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker-build --no-cache -t edurekafinalproject:latest .'
                }
            }
        }
    }
}
