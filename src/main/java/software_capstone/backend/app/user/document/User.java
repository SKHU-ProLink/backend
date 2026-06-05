package software_capstone.backend.app.user.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import software_capstone.backend.app.auth.document.OAuthProvider;
import software_capstone.backend.global.document.BaseEntity;
import software_capstone.backend.global.exception.BadRequestException;
import software_capstone.backend.global.exception.ErrorMessage;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User extends BaseEntity {

    private OAuthProvider oauthProvider;
    private String oauthId;
    private String name;
    private Role role;
    private Difficulty difficulty;
    private LocalDateTime onBoardedAt;

    public boolean isOnboarded() {
        return onBoardedAt != null;
    }

    public void completeOnboarding(Difficulty difficulty) {
        if (isOnboarded()) {
            throw new BadRequestException(ErrorMessage.USER_ALREADY_ONBOARDED);
        }
        this.difficulty = difficulty;
        this.onBoardedAt = LocalDateTime.now();
    }
}
