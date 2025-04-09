## Demo Project to run Cucumber tests with Selenium RemoteWebDriver

### How to run locally

1. Start the Selenium Grid
    ```
    java -jar selenium-server-<VERSION>.jar standalone --selenium-manager true
    ```
2. Run the tests
   ```
   mvn clean test \
       -Dcucumber.filter.tags=@torun \
       -DhubHost=localhost \
       -DhubPort=4444
   ```
   `-Dcucumber.filter.tags=@torun` is used to run only the scenarios tagged with `@torun`. Omit or leave empty to run all scenarios.
   
   **Mandatory:** `-DhubHost=localhost` is used to specify the host where the Selenium Grid is running
   
   **Mandatory:** `-DhubPort=4444` is used to specify the port where the Selenium Grid is running.

### Jenkins setup
1. Install Jenkins, Java, Git and Maven on the machine
2. Install the following Jenkins plugins:
   - [Cucumber Reports](https://plugins.jenkins.io/cucumber-reports/)
   - [Pipeline: Declarative](https://plugins.jenkins.io/pipeline-model-definition/)
3. Setup the Git credentials in Jenkins
   - Create a ssh key on the machine
   - Add the public key to the Git repository/user
   - Add the private key to Jenkins
4. Setup Java and Maven in Jenkins
   - Go to Manage Jenkins > Global Tool Configuration
   - Add Java and Maven installations

### Creating a Jenkins pipeline
1. Check "This project is parameterized"
   - Add a String parameter
      - Name: `TAGS`
      - Default value: 
   - Add a String parameter
      - Name: `BRANCH`
      - Default value: `main`
2. Select "Pipeline script from SCM"
   - SCM: Git
     - URL: `<URL to the Git repository>`
     - Credentials: `<Git credentials>`
       - SSH Username with private key from the Jenkins machine linked to the git repository
     - Branch Specifier: `${BRANCH}`
       - The branch to checkout to get the pipeline script
   - Script Path: `<Path to the Jenkinsfile>`

### Remote Selenium grid setup
1. Download the Selenium server jar file from the [Selenium website](https://www.selenium.dev/downloads/) \
   `wget https://github.com/SeleniumHQ/selenium/releases/download/selenium-4.31.0/selenium-server-4.31.0.jar`
2. Download the Chrome driver \
   `apt-get install chromium-chromedriver`
3. Install Xvfb \
   `apt-get install xvfb`
4. Create a "node-config.toml" \
   ```toml
   [server]
   port = 4444

   [node]
   detect-drivers = false

   [[node.driver-configuration]]
   browser-name = "chrome"
   display-name = "<Some Name>"
   max-sessions = 4
   webdriver-executable = "/usr/bin/chromedriver"
   stereotype = '{"browserName": "chrome"}'
   ```
5. Start the Selenium server \
   `xvfb-run -a java -jar selenium-server-<VERSION>.jar standalone --config node-config.toml`

### TODO
 - [X] Test context
 - [X] Setup and teardown
 - [ ] Parallel test execution
 - [ ] Add support for Firefox
 - [ ] Automatic deflakes
 - [X] Support for running tests via Jenkins
   - [X] Checkout code from Git
   - [X] Run tests
   - [X] Generate and show reports
   - [X] Run tests on remote Selenium Grid
 - [X] Improved logging
 - [ ] Improved reporting
   - [X] Generate Cucumber reports
     - [ ] Attach screenshots
   - [X] Console output