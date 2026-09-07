@login @web @regression
Feature: User Login

  Background:
    Given user launches the web app
    When user verifies the landing page

  @TES-001
  Scenario: Valid Login
    Given user clicks the "Signup / Login" button
    And user should be navigated to login page
    And user enters the valid email as "testuser-01@gmail.com"
    And user enters the valid password as "test123"

  @TES-002
  Scenario: Invalid Login
    Given user clicks the "Signup / Login" button
    And user should be navigated to login page
    And user enters the valid email as "testuser-01@gmail.com"
    And user enters the valid password as "test123"
