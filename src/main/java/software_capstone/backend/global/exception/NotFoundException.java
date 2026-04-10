package software_capstone.backend.global.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
