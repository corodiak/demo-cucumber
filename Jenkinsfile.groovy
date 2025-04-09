pipeline {
    agent any

    // When creating the pipeline, add the parameter 'TAGS' and 'BRANCH'

    stages {
        stage('Checkout') {
            steps {
                // Get some code from a GitHub repository
                checkout scmGit(branches: [[name: '*/${BRANCH}']], extensions: [], userRemoteConfigs: [[credentialsId: 'demo_cucumber', url: 'https://github.com/corodiak/demo-cucumber.git']])
            }
        }

        stage('Run Tests') {
            steps {
                echo("Running tests...")
                catchError(buildResult: 'UNSTABLE') {
                    withMaven(globalMavenSettingsConfig: '', jdk: '', maven: 'M3', options: [junitPublisher(disabled: true), jacocoPublisher(disabled: true)]) {
                        sh """
                            mvn \\
                            -Dcucumber.filter.tags='${TAGS}' \\
                            -DhubHost='192.168.178.58' \\
                            -DhubPort='4444' \\
                            clean test
                        """
                    }
                }
            }
        }

        stage('Generate reports') {
            steps {
                echo("Generating test report...")
                script {
                    try {
                        cucumber failedFeaturesNumber: -1, failedScenariosNumber: -1, failedStepsNumber: -1, fileIncludePattern: '*report*.json', hideEmptyHooks: true, jsonReportDirectory: 'target', mergeFeaturesById: true, pendingStepsNumber: -1, skippedStepsNumber: -1, sortingMethod: 'ALPHABETICAL', undefinedStepsNumber: -1
                    } catch (e) {
                        print e
                    }
                }
            }
        }
    }
}
