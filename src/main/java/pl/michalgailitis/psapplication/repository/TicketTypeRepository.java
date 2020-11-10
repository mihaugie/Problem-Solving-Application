package pl.michalgailitis.psapplication.repository;

import org.springframework.stereotype.Repository;
import pl.michalgailitis.psapplication.model.TicketType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class TicketTypeRepository {

    public static final List<TicketType> TICKET_TYPE_LIST = new ArrayList<TicketType>(Arrays.asList(TicketType.values()));

    public List<TicketType> getTicketTypeList() {
        return TICKET_TYPE_LIST;
    }
}
