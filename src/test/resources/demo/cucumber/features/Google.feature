@google
Feature: Search

  # Background is a common step that is executed before each scenario
  Background:
    Given I am on the Google search page
    And I dismiss the Google cookie popup
    # NOTE: There will be a captcha challenge
    And I do not see the Google cookie popup

  @g1
  Scenario: Searching for Cucumber
    When I search for "Cucumber framework" in Google
    Then I see "Cucumber" in page title
    And I see "https://cucumber.io/" in Google search results
