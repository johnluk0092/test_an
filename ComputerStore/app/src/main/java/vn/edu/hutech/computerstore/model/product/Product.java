package vn.edu.hutech.computerstore.model.product;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Product implements Serializable {

    @DocumentId
    private String id;

    private String name;
    private String description;
    private long price;
    private String brand;
    private String warranty;
    private String imagePath;
    private String createDateTime;
    private List<String> categoryPaths;

}
