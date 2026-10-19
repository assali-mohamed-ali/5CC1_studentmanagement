pipeline {
  agent any
  tools { jdk 'JDK17'; maven 'M2_HOME' }
  triggers { pollSCM('H/5 * * * *') }
  environment {
    IMAGE_NAME = 'dalyasseli/student-management'
    IMAGE_TAG = "${env.BUILD_NUMBER}"
  }
  stages {
    stage('Commit') {
      steps {
        git branch: 'main', url: 'https://github.com/assali-mohamed-ali/student-management.git'
        sh "git log -1 --pretty=format:'%h - %an : %s'"
      }
    }
    stage('Build') {
      steps { sh 'mvn -B clean package -DskipTests' }
    }
    stage('Test unitaire') {
      steps { sh 'mvn -B test' }
      post { always { junit 'target/surefire-reports/*.xml' } }
    }
    stage('Docker Build') {
      steps { sh 'docker build -t $IMAGE_NAME:$IMAGE_TAG -t $IMAGE_NAME:latest .' }
    }
    stage('Docker Push') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhubcredentials', usernameVariable: 'DH_USER', passwordVariable: 'DH_PASS')]) {
          sh 'echo "$DH_PASS" | docker login -u "$DH_USER" --password-stdin'
          sh 'docker push $IMAGE_NAME:$IMAGE_TAG'
          sh 'docker push $IMAGE_NAME:latest'
        }
      }
    }
  }
  post {
    success {
      archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
      echo 'Pipeline reussi : le jar est archive et l image est publiee sur Docker Hub.'
    }
    failure { echo 'Pipeline en echec : consulter la Console Output et Test Result.' }
    always { sh 'docker logout || true' }
  }
}
