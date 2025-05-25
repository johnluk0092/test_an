package vn.edu.hutech.computerstore.model.product;

import com.google.firebase.firestore.DocumentId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {
    public static Category ALL = new Category("all");

    @DocumentId
    @EqualsAndHashCode.Include
    private String id;

    private String name;
    private String parent;
    private String path;

    public Category(String id) {
        this.id = id;
    }

}
