package pl.michalgailitis.psapplication.controllers;

import pl.michalgailitis.psapplication.model.ticket.specifications.Status;

public interface WebConstants {

    String USERS_URL = "/users";
    String USERS_MODEL = "users";
    String UPDATE_URL = "/update";
    String UPDATE_FORM_URL = "/updateForm";
    String CREATE_URL = "/create";
    String NEWUSER_URL = "/newUser";
    String TICKETS_URL = "/tickets";
    String TICKETS_MODEL = "tickets";
    String USERS_LIST_MODEL = "usersList";
    Status USERSTATUS_OPEN = Status.OPEN;
    String USER_DASHBOARD_VIEW = "userDashboard";
    String USER_TO_UPDATE_VIEW = "userToUpdate";
    String ALL_TICKETS_VIEW = "allTickets";
    String TICKET_DETAILS_VIEW = "ticketDetails";
    String NEW_TICKET_VIEW = "newTicket";
    String TICKETS_VIEW = "ticketsView";
    String ROLES_MODEL = "roles";
    String CURRENT_USER_MODEL = "currentUser";
    String COMMENT_MODEL = "comment";
    String TICKET_FORM_MODEL = "ticketForm";
    String USER_FORM_MODEL = "userForm";
    String SELECTED_TICKET_MODEL = "selectedTicket";
    String USER_TICKETS_MODEL = "userTickets";
    String NO_OF_OPEN_TICKETS_MODEL = "noOfOpenTickets";
    String ADD_URL = "/add";
    String ALL_URL = "/all";
    String NEW_TICKET_MESSAGE = "New ticket was created!";
    String TICKET_CLOSED_MESSAGE = "Ticked was closed.";
}
