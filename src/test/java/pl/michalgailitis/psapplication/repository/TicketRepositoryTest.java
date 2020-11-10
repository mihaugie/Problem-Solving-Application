package pl.michalgailitis.psapplication.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import pl.michalgailitis.psapplication.domain.*;
import pl.michalgailitis.psapplication.model.Role;
import pl.michalgailitis.psapplication.model.Status;
import pl.michalgailitis.psapplication.model.TicketType;

import java.util.ArrayList;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TicketRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Test
    void shouldFindOpenTicketByAuthorOrResponsible(){

        User user = new User("email@gmail.com", "aaa", "AdamB", Role.USER, new ArrayList<>(), new ArrayList<>());
        User savedUser = userRepository.save(user);

        Ticket ticket = new Ticket("title", "des", "solu", TicketType.BUG, null, savedUser, null, Status.OPEN, new ArrayList<>());
        ticketRepository.save(ticket);

        Set<Ticket> tickets = ticketRepository.find(Status.OPEN, "email@gmail.com");

        assertThat(tickets).hasSize(1);

        //MB: sortowanie
        ticketRepository.findAll(Sort.by("proposedSolution").ascending());
    }


}