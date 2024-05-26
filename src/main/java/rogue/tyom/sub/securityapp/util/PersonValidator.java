package rogue.tyom.sub.securityapp.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class PersonValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        List<FieldError> fieldErrors = errors.getFieldErrors();

        StringBuilder builder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            builder.append(error.getField()).append(" - ")
                    .append(error.getDefaultMessage()).append("; ");
        }
        if (!builder.isEmpty()) {
            throw new PersonException(builder.toString().trim());
        }

    }
}
