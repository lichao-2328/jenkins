pipeline {
    agent any
    stages {
        stage('拉取代码') {
            steps {
                echo '正在拉取代码...'
                checkout scm
            }
        }
        stage('构建') {
            steps {
                echo '正在构建...'
                // 比如 Node.js 项目：
                // sh 'npm install'
                // sh 'npm run build'
            }
        }
        stage('测试') {
            steps {
                echo '正在运行测试...'
                // sh 'npm test'
            }
        }
        stage('部署') {
            steps {
                echo '正在部署...'
            }
        }
    }
}