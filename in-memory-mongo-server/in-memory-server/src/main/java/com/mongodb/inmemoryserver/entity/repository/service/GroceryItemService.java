package com.mongodb.inmemoryserver.entity.repository.service;

import com.mongodb.inmemoryserver.entity.GroceryItem;
import com.mongodb.inmemoryserver.entity.repository.GroceryItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GroceryItemService {

    private final GroceryItemRepository groceryItemRepository;

    public GroceryItem addGroceryItem(GroceryItem groceryItem) {
        return this.groceryItemRepository.save(groceryItem);
    }

    public void delete(String itemId) {
        this.groceryItemRepository.deleteById(itemId);
    }

    public Optional<GroceryItem> getGroceryItemById(String itemId) {
        return this.groceryItemRepository.findById(itemId);
    }

    public GroceryItem updateGroceryItem(GroceryItem item) {
        Optional<GroceryItem> existing = this.groceryItemRepository.findById(item.getId());
        if(existing.isPresent()){
            GroceryItem existingItem = existing.get();
            existingItem.setCategory(item.getCategory());
            existingItem.setName(item.getName());
            existingItem.setQuantity(item.getQuantity());
            return this.groceryItemRepository.save(existingItem);
        }
        return null;
    }
}
