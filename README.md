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

### TODO
 - [X] Test context
 - [X] Setup and teardown
 - [ ] Support for running tests via Jenkins
 - [X] Improved logging
 - [ ] Improved reporting
   - [X] Generate Cucumber reports
     - [ ] Attach screenshots
   - [X] Console output