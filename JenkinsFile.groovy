pipeline {
    agent any
    environment {
    registry = "mgara07"
    registryCredential = 'dockerHub'
    dockerImage = ''
  }
    stages {
         stage('clone and clean repo') {
            steps {
                git changelog: false, branch: 'master',  credentialsId: 'mgara07', poll: false, url: 'https://github.com/majallouz/PI-SoftIB.git'
                
            }
        }
        stage('Test') {
            steps { 
                bat 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
        stage('Sonar') {
            steps {
                bat 'mvn sonar:sonar'
            }
        }
        stage('Deploy') {
            steps {
                bat 'mvn package deploy '
            }
        }
       
        stage('Building image') {

        steps {

          script {

            dockerImage = docker.build registry + ":$BUILD_NUMBER"

          }

        }

      }

    
    stage('Deploy image') {

      steps {

        script {

          docker.withRegistry('', registryCredential) {

            dockerImage.push()

          }

        }

      }

    }
     
        stage('clean ws') {

            steps {
                    cleanWs()
            }

        }
    }
    post { 
        always {            
            emailext body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
                        recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                        subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}",
                        to: '$DEFAULT_RECIPIENTS'
        }
    } 
}
