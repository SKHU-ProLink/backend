package software_capstone.backend.app.auth.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import software_capstone.backend.app.abocado.document.Difficulty;
import software_capstone.backend.global.document.BaseEntity;

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
    private boolean isProActive;

    private Difficulty difficulty;
}
