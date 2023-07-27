properties([gitLabConnection(gitLabConnection: 'Gitlab TechVG', jobCredentialId: ''), [$class: 'GitlabLogoProperty', repositoryName: ''], parameters([choice(choices: ['qa', 'preprod'], name: 'BUILD_ENV'),
choice(choices: ['develop', 'release'], name: 'BRANCH_NAME')])])

node {

    stage('Checkout Git') {
      git url: 'https://gitlab.com/techvg/covid/covidcare-backend.git', branch:"${params.BRANCH_NAME}"
    }
	
	stage('check java') {
        bat "java -version"
    }

    stage('clean') {
        bat "./mvnw.cmd -ntp clean -P-webpack"
    }
    stage('nohttp') {
        bat "./mvnw.cmd -ntp checkstyle:check"
    }

    stage('install tools') {
        bat "./mvnw.cmd -ntp com.github.eirslett:frontend-maven-plugin:install-node-and-npm -DnodeVersion=v12.16.1 -DnpmVersion=6.14.5"
    }

    stage('npm install') {
        bat "./mvnw.cmd -ntp com.github.eirslett:frontend-maven-plugin:npm"
    }

    stage('packaging') {
        bat "./mvnw.cmd -ntp verify -P-webpack -P${params.BUILD_ENV} -DskipTests"
        archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    }
	
	stage('UnDeploy') {
         if(params.BUILD_ENV ==~ 'qa'){
		 catchError {
			  echo 'UnDeploy from qa environment'
			  bat """
				FOR /F "tokens=5" %%T IN ('netstat -a -n -o ^| findstr 8181 ') DO (
				SET /A ProcessId=%%T) &GOTO SkipLine                                                   
				:SkipLine                                                                              
				echo ProcessId to kill = %ProcessId%
				taskkill /f /pid %ProcessId%
			  """
			}
           }
           if(params.BUILD_ENV ==~ 'preprod'){
				echo 'UnDeploy from preprod environment'
			catchError {				
				bat """
				FOR /F "tokens=5" %%T IN ('netstat -a -n -o ^| findstr 9191 ') DO (
				SET /A ProcessId=%%T) &GOTO SkipLine                                                   
				:SkipLine                                                                              
				echo ProcessId to kill = %ProcessId%
				taskkill /f /pid %ProcessId%
			  """
			  }
          }

	}
	
	stage('deploy') {
            bat "start cmd.exe /C start.bat ${params.BUILD_ENV}"
	}
}