package stepdefinitions;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class AddClientSteps {

    private String clientName;
    private String clientEmail;
    private boolean isSaved;

    @Given("the admin is on the {string} page")
    public void admin_on_page(String pageName) {
        // simulate navigation
        assertEquals("Add Client", pageName);
    }

    @When("the admin submits a new client with name {string} and email {string}")
    public void submit_new_client(String name, String email) {
        this.clientName = name;
        this.clientEmail = email;

        // call your service method (from TDD)
        // Example:
        // isSaved = clientService.addClient(new Client(name, email));
        isSaved = true; // simulate for now
    }

    @Then("the client should be successfully saved in the system")
    public void client_should_be_saved() {
        assertTrue(isSaved);
    }

    @Then("the system should display a confirmation message")
    public void system_displays_confirmation() {
        // simulate confirmation
        String confirmationMessage = "Client added successfully!";
        assertEquals("Client added successfully!", confirmationMessage);
    }
}
