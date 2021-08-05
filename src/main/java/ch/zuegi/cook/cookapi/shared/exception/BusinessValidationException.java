package ch.zuegi.cook.cookapi.shared.exception;

public class BusinessValidationException extends RuntimeException {
    public BusinessValidationException(String s) {
        super(s);
    }
}
