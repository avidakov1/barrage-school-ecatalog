package net.barrage.school.java.ecatalog.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

@Data
@Accessors(chain = true)
@ToString
public class Product implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private String image;
    private double price;
    private String merchant;
}
