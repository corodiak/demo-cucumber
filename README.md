## Demo Project to run Cucumber tests with Selenium RemoteWebDriver

### How to run

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

### TODO
 - [ ] Support for running tests via Jenkins
 - [ ] Improved logging
 - [ ] Improved reporting
   - [ ] Generate Cucumber reports
   - [ ] Console output