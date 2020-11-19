package pl.michalgailitis.psapplication.model.ticket.specifications;

import java.util.Arrays;
import java.util.List;

public enum TicketType {
    PROBLEM,
    BUG,
    TODO,
    IDEA;

    public static List<TicketType> allTypes(){
        return Arrays.asList(TicketType.values());
    }
}
