package com.mongodb.inmemoryserver.entity.repository;

import com.mongodb.inmemoryserver.entity.GroceryItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroceryItemRepository extends MongoRepository<GroceryItem, String> {
}
