package nl.rls.auth.service;

import nl.rls.auth.domain.Owner;
import nl.rls.auth.domain.User;
import nl.rls.auth.mapper.OwnerDtoMapper;
import nl.rls.auth.mapper.UserDtoMapper;
import nl.rls.auth.repository.OwnerRepository;
import nl.rls.auth.repository.UserRepository;
import nl.rls.auth.rest.dto.OwnerDto;
import nl.rls.auth.rest.dto.UserDto;
import nl.rls.composer.domain.Company;
import nl.rls.composer.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class  OwnerService {
    private final OwnerRepository ownerRepository;

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public OwnerService(OwnerRepository ownerRepository, CompanyRepository companyRepository, UserRepository userRepository) {
        this.ownerRepository = ownerRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public OwnerDto createOwner(String companyCode, List<String> auth0Ids) {
        Company company = companyRepository.findByCode(companyCode)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find company with code %s", companyCode)));
        Owner owner = new Owner();
        owner.setCompany(company);
        for (String auth0Id : auth0Ids) {
            User user = new User(auth0Id);
            owner.addUser(user);
        }
        owner = ownerRepository.save(owner);
        return OwnerDtoMapper.map(owner);
    }

    public List<OwnerDto> getOwners() {
        return ownerRepository.findAll()
                .stream()
                .map(OwnerDtoMapper::map)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersByOwnerId(Integer ownerId) {
        return userRepository.findAllByOwnerId(ownerId)
                .stream()
                .map(UserDtoMapper::map)
                .collect(Collectors.toList());
    }

    public OwnerDto updateOwner(Integer ownerId, String companyCode, List<String> auth0Ids) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find user with id %d.", ownerId)));
        Company company = getCompany(companyCode);
        owner.setCompany(company);
        for (String auth0Id : auth0Ids) {
            User user = new User(auth0Id);
            owner.addUser(user);
        }
        owner = ownerRepository.save(owner);
        return OwnerDtoMapper.map(owner);
    }

    private Company getCompany(String companyCode) {
        return companyRepository.findByCode(companyCode)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find company with code %s", companyCode)));
    }
}
