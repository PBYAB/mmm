package pl.edu.pb.wi.mmm.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class ValidationException extends RuntimeException {
    private final Map<String, String> errors;
}