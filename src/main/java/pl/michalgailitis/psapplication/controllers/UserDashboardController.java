package pl.michalgailitis.psapplication.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.michalgailitis.psapplication.model.Status;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.services.TicketService;
import pl.michalgailitis.psapplication.services.users.UserInfoService;
import pl.michalgailitis.psapplication.services.users.UserService;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserDashboardController {

    private final UserInfoService userInfoService;
    private final TicketService ticketService;
    private final UserService userService;

    //MB stałe wyrzucić do AppConsts w ramach pakietu - klasa finalna z polami statycznymi
    //LUB
    //Interfejs bo nie trzeba pisać public static final

    private static final Status USERDASHBOARDSTATUS = Status.OPEN;


//    @RequestMapping(value = "/loginPage")
//    public String homePage() {
//        return "loginView";
//    }


    @GetMapping
    public String userDashboard(final ModelMap modelMap) throws Exception {

//DONE: MB zrobić tak metody zeby modelMap tylko raz przekazywał dane

        String currentUserName = userInfoService.getCurrentUserName();
        User currentUser = userService.getUserById(currentUserName);

        List<Ticket> ticketByAuthor = ticketService.getTicketByAuthor(currentUser);

        Set<Ticket> ticketByAuthorOrResponsible = ticketService.getTicketForUserDashboard(USERDASHBOARDSTATUS, currentUser.getEmail());

        int noOfOpenTicketsForLoggedUser = ticketByAuthorOrResponsible.size();

        modelMap.addAllAttributes(Map.of("username", currentUser, "byauthor", ticketByAuthor, "usertickets", ticketByAuthorOrResponsible, "noofopentickets", noOfOpenTicketsForLoggedUser));

        return "userdashboard";


    }

}


