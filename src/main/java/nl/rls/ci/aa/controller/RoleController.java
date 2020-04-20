package nl.rls.ci.aa.controller;

import nl.rls.ci.aa.domain.Role;
import nl.rls.ci.aa.dto.RoleDto;
import nl.rls.ci.aa.dto.RoleDtoMapper;
import nl.rls.ci.aa.repository.RoleRepository;
import nl.rls.ci.url.BaseURL;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/roles")
public class RoleController {
    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAll() {
        Iterable<Role> roleList = roleRepository.findAll();
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (Role role : roleList) {
            roleDtoList.add(RoleDtoMapper.map(role));
        }
        return ResponseEntity.ok(roleDtoList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RoleDto> getRole(@PathVariable int id) {
        Optional<Role> role = roleRepository.findById(id);
        return ResponseEntity.ok(RoleDtoMapper.map(role.get()));
    }

}
