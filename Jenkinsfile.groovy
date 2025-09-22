pipeline {
    agent any  // Run on any available Jenkins agent

    environment {
        MVN_HOME = "/usr/bin"  // Updated Maven path
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code from GitHub...'
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
                echo 'Running unit tests against MySQL...'
                // Ensure MySQL is running and accessible
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
                echo 'Archiving the JAR artifact...'
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
