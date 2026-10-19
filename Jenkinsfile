pipeline {
  agent any
  tools { jdk 'JDK17'; maven 'M2_HOME' }
  triggers { pollSCM('H/5 * * * *') }
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
  }
  post {
    success {
      archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
      echo 'Pipeline reussi : le jar est archive.'
    }
    failure { echo 'Pipeline en echec : consulter la Console Output et Test Result.' }
  }
}
