pipeline {
    agent any

    stages {
        stage('Preparation') {
            steps {
               git branch: 'SPECCHIO_Master', url: 'https://github.com/SPECCHIODB/SPECCHIO.git'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean build'
                sh './gradlew izPackCreateInstaller'
            }
        }
        stage('Upload Archive') {
            steps {
                archiveArtifacts artifacts: 'src/client/build/libs/specchio-client.jar', fingerprint: true
                archiveArtifacts artifacts: 'src/client/build/distributions/*.jar', fingerprint: true
            }
        }
    }
}