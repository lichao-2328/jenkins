pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {
        stage('拉取代码') {
            steps {
                git 'https://github.com/lichao-2328/jenkins'
            }
        }

        stage('打包构建') {
            steps {
                echo '正在打包成 war 文件...'
                sh 'mvn clean package -DskipTests -f ssm.com/pom.xml'
            }
        }

        stage('保存产物') {
            steps {
                echo '保存 war 包...'
                archiveArtifacts artifacts: 'ssm.com/target/*.war'
            }
        }
    }

    post {
        success {
            echo '✅ 构建成功！'
        }
        failure {
            echo '❌ 构建失败！'
        }
    }
}