package rogue.tyom.sub.securityapp.mappers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rogue.tyom.sub.securityapp.dto.PersonDto;
import rogue.tyom.sub.securityapp.models.Person;

@Component
@AllArgsConstructor
public class PersonMapper {

    private final ModelMapper modelMapper;

    public Person convertToPerson(PersonDto personDto) {
        return modelMapper.map(personDto, Person.class);
    }
}
