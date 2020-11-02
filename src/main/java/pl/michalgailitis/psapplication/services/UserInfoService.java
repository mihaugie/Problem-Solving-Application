package pl.michalgailitis.psapplication.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {


    public String getCurrentUserName() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return username;
        } else {
            String username = principal.toString();
            return username;
        }

    }

}
