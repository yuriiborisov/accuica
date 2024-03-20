package code.accessor.implementations.service;

import code.accessor.core.Role4Access;
import code.accessor.implementations.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class ViewService {
    private final RoleRepository roleRepository;
//    public Role4Access getRolesForView(){
//        roleRepository.findAll();
//    }

    public ViewService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
