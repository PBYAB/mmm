package pl.edu.pb.wi.mmm.exception;

public class LoginAlreadyExists extends RuntimeException {

    public LoginAlreadyExists(String message) {
        super(message);
    }
}
