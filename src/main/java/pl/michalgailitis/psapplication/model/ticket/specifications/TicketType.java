package pl.michalgailitis.psapplication.model.ticket.specifications;

//MB dodac metode statyczna ktora zwraca listę typow i usunąc repository - to samo dla ROLERepository
// z tej metody korzystac w serwisie

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
