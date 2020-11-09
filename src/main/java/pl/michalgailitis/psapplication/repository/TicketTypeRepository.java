package pl.michalgailitis.psapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pl.michalgailitis.psapplication.domain.TicketType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TicketTypeRepository {

    public static final List<TicketType> TICKET_TYPE_LIST = new ArrayList<TicketType>(Arrays.asList(TicketType.values()));

    public List<TicketType> getTicketTypeList() {
        return TICKET_TYPE_LIST;
    }
}
