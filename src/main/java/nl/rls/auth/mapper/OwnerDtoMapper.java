package nl.rls.auth.mapper;

import nl.rls.auth.domain.Owner;
import nl.rls.auth.domain.User;
import nl.rls.auth.rest.dto.OwnerDto;
import nl.rls.auth.rest.dto.UserDto;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.rest.dto.WagonInTrainDto;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

public class OwnerDtoMapper {
    public static OwnerDto map(Owner owner) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
                mapping(Owner.class, OwnerDto.class)
                        .fields("users", "users",
                                FieldsMappingOptions.customConverter("nl.rls.auth.mapper.converter.UserConverter"))
                ;
            }
        };
        mapper.addMapping(mappingBuilder);
        return mapper.map(owner, OwnerDto.class);
    }

}