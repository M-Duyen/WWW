package iuh.fit.ongk_detusinh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    private String id;
    private String name;
    private String author;
    private double price;
    private Date publication;
    private String imageUrl;
    private Category category;

}
