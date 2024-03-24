pipeline {
    agent any
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
        stage('docker login & ansible playbook for docker build and push') {
    steps {
        withCredentials([usernamePassword(credentialsId: 'DOCKER_HUB_LOGIN', passwordVariable: 'DOCKER_HUB_PASSWORD', usernameVariable: 'DOCKER_HUB_USERNAME')]) {
            script {
                def registryUrl = 'https://index.docker.io/v1/'
                def dockerLogin = "docker login -u ${DOCKER_HUB_USERNAME} --password-stdin ${registryUrl}"
                def ansiblePlaybook = "ansible-playbook -i localhost, deploy/ansible_dockerbuild_play2.yml"
                
                // Log in to Docker Hub
                sh "echo ${DOCKER_HUB_PASSWORD} | ${dockerLogin}"
                
                // Run Ansible playbook for Docker build and push
                sh ansiblePlaybook
            }
        }
    }
}
        stage('K8s Deploy-QA') {
            steps {
                sh 'ansible-playbook --inventory /etc/ansible/hosts deploy/ansible-pb-k8s-deploy.yml'
            }
        }
    }
    post {
        always {
            archiveArtifacts artifacts: 'target/*.war', followSymlinks: false
        }
    }
}
