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
        stage('docker build ') {
	         steps {
              echo "docker building image..."
              sh 'cd $WORKSPACE'
	          sh 'docker build --file Dockerfile --tag durgarani/abc_technologies:$BUILD_NUMBER .'
           }	
        }
        stage('push docker image') {
	        steps {
              echo "pushing image to docker hub..."
	          withDockerRegistry(credentialsId: 'DOCKER_HUB_LOGIN', url: 'https://index.docker.io/v1/') {
              sh 'docker push docker.io/durgarani/abc_technologies:$BUILD_NUMBER'
                }       
	     }
	  }
        stage('docker deploy') {
          steps {
            echo "deploying to docker container..."
            sh 'docker stop abc-container || true' 
            sh 'docker rm -f abc-container || true' 
            sh 'docker run -d -P --name abc-container durgarani/abc_technologies:$BUILD_NUMBER'
            sh 'docker ps -a'
          }
        }
    }

    post {
      always {
        junit stdioRetention: '', testResults: 'target/surefire-reports/*.xml'
         }
       }

       post {
         always {
           archiveArtifacts artifacts: 'target/*.war', followSymlinks: false
          }
       }
   }
