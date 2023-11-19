package net.barrage.school.java.ecatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@ToString
public class Product implements Serializable {
    private UUID id;
    private String name = "";
    private String description = "";
    private String image = "";
    private double price;
    private String merchant = "";
}
