package rogue.tyom.sub.securityapp.mappers;

import org.mapstruct.Mapper;
import rogue.tyom.sub.securityapp.config.MapStructConfig;
import rogue.tyom.sub.securityapp.dto.PersonDto;
import rogue.tyom.sub.securityapp.models.Person;

@Mapper(config = MapStructConfig.class)
public interface PersonDtoToPersonMapper {

    Person map(PersonDto personDto);

}
