package vn.edu.hutech.computerstore.model.user;

import com.google.firebase.firestore.DocumentId;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class User {

    @DocumentId
    private String userId;

    private String name;
    private String birthday;
    private String phoneNumber;

    public User(String userId, String name, String birthday, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }

}
