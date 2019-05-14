pipeline {
  agent any

  tools {
    maven 'maven'
  }

  stages {
    stage('Build') {
      steps {
        bat 'mvn clean package -P prod'
        bat 'path'
      }
    }
  }

  post {
    always {
        echo 'post always'
    }
    success {
        echo 'post success'
    }
    cleanup {
        echo 'post cleanup'
    }
  }
}
