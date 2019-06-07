pipeline {

  agent  any
  options {
        /* buildDiscarder(logRotator(numToKeepStr: '3')),*/
        timeout(time: 120, unit: 'MINUTES')
    }
  tools {
    maven "Maven_3.3.9"
  }

  stages {

    stage('Check Scm Changelog') {
        steps {
          script {
            def changeLogSets = currentBuild.changeSets
            for (int i = 0; i < changeLogSets.size(); i++) {
                def entries = changeLogSets[i].items
                for (int j = 0; j < entries.length; j++) {
                    def entry = entries[j]
                    if(entry.author.toString().contains('Jenkins') || entry.msg.contains('maven-release-plugin')){
                      echo "Les Commit effectués par le user jenkins sont toujours ignorés. C'est le cas des releases effectuées depuis la chaine d'integration avec le user jenkins"
                        currentBuild.result = 'ABORTED'
                        error('Aucun besoin de builder de façon cyclique les les commits de Jenkins')
                        return
                    }else{
                      echo "ID Commit : ${entry.commitId} \nAuteur : ${entry.author} \nDate : ${new Date(entry.timestamp)} \nMessage: ${entry.msg}"
                      def files = new ArrayList(entry.affectedFiles)
                      for (int k = 0; k < files.size(); k++) {
                          def file = files[k]
                          echo "  ${file.editType.name} ${file.path}"
                      }
                    }
                }
            }
          }
        }
    }

    stage('Clean Package') {
      steps {
        sh 'mvn clean package'
      }

    }

    stage('Units Tests') {
      steps {
        sh 'mvn clean test -Dmaven.test.skip=false'
      }
      post {
        success {
          junit 'target/surefire-reports/**/*.xml'
        }

      }

    }

    stage('SonarQube Scan') {
      steps{
        script{
            withSonarQubeEnv('SonarQubeServer') {
            sh 'mvn sonar:sonar -X'
           }
       }
     }
     }

   /* Modifier le profil selon vos profils projet */

    stage('Deploy ITG') {
      steps {
        sh 'mvn clean install -P ITG -Dskip.deploy=false'
      }
    }

    stage('Integration Tests') {
      steps {
        echo 'Jeux Campagne tests d\'intégration'
      }
    }

    stage('Deploy REC') {
      steps {
        sh 'mvn clean install -P REC -Dskip.deploy=false'
      }
    }

    stage('Functionnals Tests Phases') {
      steps {
        parallel(
          "NR Tests": {
            echo 'Pas de tests pour le moment'

          },
          "Functionnals Tests": {
            echo 'Pas de tests pour le moment'

          }
        )
      }
    }

    stage("SonarQube Quality Gate") {
      steps{
          script{
            timeout(time: 10, unit: 'MINUTES') {
               def qg = waitForQualityGate()
               if (qg.status != 'OK') {
                 error "Pipeline aborted due to quality gate failure: ${qg.status}"
               }
            }

          }
        }
    }

    stage('Deploy PPROD') {
      steps {
        echo 'deployer sur environnement de préproduction'
      }
    }


    stage('Launch Qualys Scan') {
      steps {
        sh 'mvn qualys:scan'
      }
  }

/*
Lancer un waitUntil et recuperer des valeurs depuis les logs pour s'assurer que le statut du scan est bien terminé. Après seulement passer à la prochaine action.
En même temps prevoir un try catch pour eviter de faire echouer le build.
*/
  stage('Check Qualys Scan') {
      steps {
          script{
            timeout(time:90, unit: 'MINUTES'){
              waitUntil {
                sleep time: 7, unit: 'MINUTES'
                try{
                  sh 'mvn qualys:check'
                  def result = manager.logContains(".*SCAN-FINISHED*.")

                  if(result)
                    return true
                }catch(exc){
                  echo 'Une exception a été rencontrée... Retry en cours'
                }
                return false
              }
            }
          }
      }
  }

 stage('Qualys Download Report') {
       steps {
           sh 'mvn qualys:report'
           sleep time: 1, unit: 'MINUTES'
           sh 'mvn qualys:download'
       }
       post{
        success {
          archiveArtifacts artifacts: 'target/qualys/*.pdf'
          emailext attachmentsPattern: 'target/qualys/*.pdf',
            body: 'Rapport Qualys joint au mail.',
            subject: '[QUALYS] Rapport Vulnerabilité',
            to: 'groupe_DSI/DIF@orange-sonatel.com'
        }
       }
    }

  stage('Analyse Qualys Report') {
      steps {
       script{
         try{
            sh 'mvn qualys:analyse-prepare'
          }catch(exc){
            echo 'Une exception a été rencontrée pendant qualys:analyse-prepare'
          }
         sleep time: 1, unit: 'MINUTES'
         sh 'mvn qualys:analyse-perform'

          }
       }
   }



    /*
  D'après les bonnes pratiques coté release avec pipeline, il faudra sortir la release sur un autre job different de la partie CI
  */

    stage('Release On Nexus') {
     when {
      branch 'release'
     }
      /*
        ici vous devez créer un nouveau job classique pour la release de votre projet. Celui ci sera appelé de cette façon depuis un pipeline :
      */
      steps {
        build job: 'Obelix-Release'
      }
    }
  }

  post {

   changed {
      emailext attachLog: true, body: '$DEFAULT_CONTENT', subject: '$DEFAULT_SUBJECT',  to: 'diomyero.sow@orange-sonatel.com'
   }
    always{
        sh 'mvn clean'
        /* cleanWs deleteDirs: true, notFailBuild: true    */
    }
    failure {
            emailext attachLog: true, body: '$DEFAULT_CONTENT', subject: '$DEFAULT_SUBJECT',  to: 'diomyero.sow@orange-sonatel.com'
        }

  }
}
