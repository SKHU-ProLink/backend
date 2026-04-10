package software_capstone.backend.global.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
