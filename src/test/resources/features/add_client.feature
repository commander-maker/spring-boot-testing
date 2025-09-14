Feature: Add New Client

  Scenario: Successfully adding a new client
    Given the admin is on the "Add Client" page
    When the admin submits a new client with name "John Doe" and email "john@example.com"
    Then the client should be successfully saved in the system
    And the system should display a confirmation message
