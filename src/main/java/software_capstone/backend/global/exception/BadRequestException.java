package software_capstone.backend.global.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
