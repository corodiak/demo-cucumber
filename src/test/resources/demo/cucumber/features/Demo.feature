@demo
Feature: Demo
  These tests are for easy demo and debugging of the lifecycle and logging.
  They do not require the starting of the RemoteWebDriver

  @demo1
  Scenario: This test should pass
    Given I set actual result to "true"
    When I set expected result to "true"
    Then I should be told "Yeah"

  @demo2
  Scenario: This test should fail
    Given I set actual result to "true"
    When I set expected result to "false"
    Then I should be told "Yeah"
    And This step should be skipped