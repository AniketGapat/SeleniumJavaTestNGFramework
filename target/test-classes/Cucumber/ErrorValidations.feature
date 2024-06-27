@tag
Feature: Error validation
  I want to use this template for my feature file


  @ErrorValidation
  Scenario Outline: Negative test of submitting the order
    Given I landed on Ecommerce page
    When Login with username <name> and password <password>
    Then "Incorrect email or password." message is displayed

     Examples: 
      | name  								|  password		    |
      | aniketgapat@test.com  |  Iamking@0      | 
