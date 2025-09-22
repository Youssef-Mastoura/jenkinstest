pipeline {
    agent any  

    environment {
        MVN_HOME = "/usr/bin" 
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
                sh "${MVN_HOME}/mvn clean compile -DskipTests -Dmaven.test.skip=true"
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging the application...'
                sh "${MVN_HOME}/mvn package -DskipTests -Dmaven.test.skip=true"
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