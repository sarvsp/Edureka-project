pipeline {
    agent {
        label 'dockerslave'
    }
    environment {
        BRANCH_NAME = 'master'
    }
    stages {
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
                    sh 'ansible-playbook dockerplaybook.yml  
                }
            }
        }  
    }
}
