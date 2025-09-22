pipeline {
    agent any  // Run on any available Jenkins agent

    environment {
        MVN_HOME = "/usr/bin" // Updated Maven path
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Youssef-Mastoura/jenkinstest.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the Spring Boot project...'
                sh "${MVN_HOME}/mvn clean compile"
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit tests...'
                sh "${MVN_HOME}/mvn test"
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging the application...'
                sh "${MVN_HOME}/mvn package"
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
