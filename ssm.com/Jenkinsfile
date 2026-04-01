pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {
        stage('拉取代码') {
            steps {
                git branch: 'main', url: 'https://github.com/lichao-2328/jenkins'
            }
        }

        stage('打包构建') {
            steps {
                echo '正在打包...'
                sh 'mvn clean package -DskipTests -f ssm.com/pom.xml'
            }
        }

        stage('保存产物') {
            steps {
                archiveArtifacts artifacts: 'ssm.com/target/*.war'
            }
        }

        stage('部署到 Tomcat') {
            steps {
                echo '正在部署...'
                sh '''
                    docker cp ssm.com/target/ssm.com-0.0.1-SNAPSHOT.war tomcat:/usr/local/tomcat/webapps/
                '''
            }
        }
    }

    post {
        success { echo '✅ 部署成功！' }
        failure { echo '❌ 失败！' }
    }
}