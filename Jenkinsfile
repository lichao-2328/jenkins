pipeline {
    agent any
    
    tools {
        maven 'Maven'
    }
    
    stages {
        stage('拉取代码') {
            steps {
                echo '正在从 GitHub 拉取代码...'
                checkout scm
            }
        }
        
        stage('打包构建') {
    steps {
        echo '正在打包成 jar 文件...'
        sh 'mvn clean package -DskipTests -f ssm.com/pom.xml'
    }
}
        
        stage('保存产物') {
    steps {
        echo '保存 war 包...'
        archiveArtifacts artifacts: 'ssm.com/target/*.war'
    }
}
    
    post {
        success {
            echo '✅ 构建成功！jar 包已生成！'
        }
        failure {
            echo '❌ 构建失败！请检查代码！'
        }
    }
}