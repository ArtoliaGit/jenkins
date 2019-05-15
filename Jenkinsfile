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
        bat 'java -jar ./target/examination-0.0.1-SNAPSHOT.jar'
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
