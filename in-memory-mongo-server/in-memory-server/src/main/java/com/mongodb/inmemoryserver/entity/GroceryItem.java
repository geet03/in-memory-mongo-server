package com.mongodb.inmemoryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("grocer_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroceryItem {
    @Id
    private String id;

    private String name;
    private int quantity;
    private String category;
}
