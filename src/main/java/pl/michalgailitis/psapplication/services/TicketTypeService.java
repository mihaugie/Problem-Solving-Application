package pl.michalgailitis.psapplication.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.michalgailitis.psapplication.model.TicketType;
import pl.michalgailitis.psapplication.repository.TicketTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;

    public List<TicketType> getTicketTypes(){
        return ticketTypeRepository.getTicketTypeList();
    };

}
