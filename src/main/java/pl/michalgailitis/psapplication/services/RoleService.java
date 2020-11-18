package pl.michalgailitis.psapplication.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.michalgailitis.psapplication.model.user.specifications.Role;
import pl.michalgailitis.psapplication.repository.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getRoles() {
        return roleRepository.getRolesList();
    }
}
