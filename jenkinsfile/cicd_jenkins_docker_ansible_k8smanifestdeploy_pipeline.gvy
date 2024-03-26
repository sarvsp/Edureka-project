pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    stages {
      stage('compile') {
	        steps {
		            git url: 'https://github.com/sarvsp/Edureka-project.git'
		            sh script: '/opt/apache-maven-3.8.8/bin/mvn compile'
          }
      }
      
      stage('unit-test') {
	        steps {
	                sh script: '/opt/apache-maven-3.8.8/bin/mvn test'
            }
	        post {
            success {
                    junit 'target/surefire-reports/*.xml'
            }
            }			
      }
      
      stage('package') {
	        steps {
		            sh script: '/opt/apache-maven-3.8.8/bin/mvn package'	
            }		
      }
      
      stage('ansible-dockerbuild-push') {
	        steps {
                    echo "building image and pushing to dockerhub..."
	                withDockerRegistry(credentialsId: 'DOCKER_HUB_LOGIN', url: 'https://index.docker.io/v1/') {
                    sh 'ansible-playbook -i localhost, deploy/dockerbuildimage_push.yml'

                    }
	           
	        }
	  }
      stage('ansible-k8sdeploy-qa') {
	    steps {
		    sh 'ansible-playbook --inventory /etc/ansible/hosts deploy/ansibleplaybook-k8smanifestdeploy.yml'
	    }
      }
    }
}		
