package nl.rls.ci.aa.dto;

import nl.rls.ci.aa.controller.OwnerController;
import nl.rls.ci.aa.controller.UserController;
import nl.rls.ci.aa.domain.AppUser;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class UserDtoMapper {

    public static UserDto map(AppUser user) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
                mapping(AppUser.class, UserDto.class)
                        .fields("role.name", "role");
            }
        };
        mapper.addMapping(mappingBuilder);
        UserDto userDto = mapper.map(user, UserDto.class);
        Link link = linkTo(methodOn(OwnerController.class).getOwner(user.getOwner().getId())).withRel("owner");
        userDto.add(link);
        userDto.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());
        return userDto;
    }

    public static AppUser map(UserPostDto dto) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        AppUser entity = mapper.map(dto, AppUser.class);
        return entity;
    }

}
