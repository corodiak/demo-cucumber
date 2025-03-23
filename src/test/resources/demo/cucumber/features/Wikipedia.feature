@wikipedia
Feature: Wikipedia

  # Background is a common step that is executed before each scenario
  Background:
    Given I am on the Wikipedia search page

  @w1
  Scenario Outline: Learning about <Query>
    When I search for "<Query>" in Wikipedia
    And I wait for 1 second
    Then I see "<Title>" in page title
    And I see "<WebsiteInfobox>" in Website infobox

    Examples:
        | Query               | Title               | WebsiteInfobox |
        | Cucumber framework  | Cucumber (software) | cucumber.io    |
        | selenium (software) | Selenium (software) | selenium.dev   |
