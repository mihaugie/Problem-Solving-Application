package pl.michalgailitis.psapplication.controllers;

import pl.michalgailitis.psapplication.model.ticket.specifications.Status;

public interface WebConstants {

    String USERS_URL = "/users";
    String CREATE_URL = "/create";
    String NEWUSER_URL = "/newUser";
    Status USERSTATUS_OPEN = Status.OPEN;
}
