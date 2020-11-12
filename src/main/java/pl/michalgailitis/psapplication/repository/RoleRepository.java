package pl.michalgailitis.psapplication.repository;

import org.springframework.stereotype.Repository;
import pl.michalgailitis.psapplication.model.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class RoleRepository {

    public static final List<Role> ROLE_LIST = new ArrayList<Role>(Arrays.asList(Role.values()));

    public List<Role> getRolesList() {
        return ROLE_LIST;
    }
}
