@youtube
Feature: YouTube

  # Background is a common step that is executed before each scenario
  Background:
    Given I am on the YouTube start page
    And I dismiss the YouTube cookie popup
    And I do not see the YouTube cookie popup

  @youtube1
  Scenario: Searching for Cucumber
    When I search for "cucumber.io" in YouTube
    And I wait for 1 second
    Then I see "cucumber.io - YouTube" in page title
    And I see "BDD with Cucumber" in YouTube search results
