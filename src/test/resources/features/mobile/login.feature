@login @mobile @regression
Feature: Mobile User Login

  Background:
    Given user launches the mobile app

  @MOB-001
  Scenario: Valid Login
    Given user enters the username as "standard_user"
    And user enters the password as "secret_sauce"
    When user taps the login button
    Then user verifies the mobile home screen