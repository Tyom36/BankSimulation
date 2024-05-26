package rogue.tyom.sub.securityapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rogue.tyom.sub.securityapp.config.MapStructConfig;
import rogue.tyom.sub.securityapp.dto.AuthenticationDto;
import rogue.tyom.sub.securityapp.dto.BankAccountDto;
import rogue.tyom.sub.securityapp.dto.RegistrationPersonDto;
import rogue.tyom.sub.securityapp.models.BankAccount;
import rogue.tyom.sub.securityapp.models.Person;

@Mapper(config = MapStructConfig.class)
public interface PersonMapper {

    @Mapping(target = "bankAccount", expression = "java(mapBankAccount(registrationPersonDto.getBankAccountDto()))")
    Person mapPerson(RegistrationPersonDto registrationPersonDto);

    BankAccount mapBankAccount(BankAccountDto bankAccountDto);

    BankAccount mapAuthentication(AuthenticationDto authenticationDto);

}
