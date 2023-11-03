package pl.edu.pb.wi.mmm.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import pl.edu.pb.wi.mmm.exception.ValidationException;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValidationHandler {

    public void validateAndHandleErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            throw new ValidationException(errors);
        }
    }
}