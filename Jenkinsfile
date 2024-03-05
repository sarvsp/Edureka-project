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
                    // Build Docker image
                    def customImage = docker.build('edurekafinalproject1:latest', '--no-cache .')
                }
            }
        }
        stage('Run Docker Container') {
            steps {
                script {
                    // Run Docker container from the built image
                    def customContainer = customImage.run('-p 8080:80')
                }
            }
        }    
        // Add more stages if needed
    }
    post {
        always {
            // Clean up: Stop and remove the Docker container
            script {
                customContainer.stop()
                customContainer.remove(force: true)
            }
        }
    }
}
