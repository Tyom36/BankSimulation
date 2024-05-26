package rogue.tyom.sub.securityapp.mappers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rogue.tyom.sub.securityapp.dto.BankAccountDto;
import rogue.tyom.sub.securityapp.dto.PersonDto;
import rogue.tyom.sub.securityapp.models.BankAccount;
import rogue.tyom.sub.securityapp.models.Person;

@Component
@AllArgsConstructor
public class BankAccountMapper {

    private final ModelMapper modelMapper;

    public BankAccount convertToPerson(BankAccountDto bankAccountDto) {
        return modelMapper.map(bankAccountDto, BankAccount.class);
    }
}
