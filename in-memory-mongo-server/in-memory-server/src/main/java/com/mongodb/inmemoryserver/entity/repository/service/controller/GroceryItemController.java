package com.mongodb.inmemoryserver.entity.repository.service.controller;

import com.mongodb.inmemoryserver.api.ItemApi;
import com.mongodb.inmemoryserver.entity.GroceryItem;
import com.mongodb.inmemoryserver.entity.repository.service.GroceryItemService;
import com.mongodb.inmemoryserver.model.GroceryItemDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class GroceryItemController implements ItemApi {

    private final GroceryItemService groceryItemService;

    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<GroceryItemDto> addGroceryItem(GroceryItemDto groceryItemDto) {
        GroceryItem groceryItem = toEntity(groceryItemDto);
        GroceryItem saved = this.groceryItemService.addGroceryItem(groceryItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @Override
    public ResponseEntity<Void> deleteGroceryItem(String itemId) {
        try{
            this.groceryItemService.delete(itemId);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<GroceryItemDto> getGroceryItemById(String itemId) {
        Optional<GroceryItem> itemOptional = groceryItemService.getGroceryItemById(itemId);
        if (itemOptional.isPresent()) {
            GroceryItem item = itemOptional.get();
            GroceryItemDto dto = toDto(item);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<GroceryItemDto> updateGroceryItem(GroceryItemDto groceryItemDto) {
        GroceryItem updated = groceryItemService.updateGroceryItem(toEntity(groceryItemDto));
        if(updated == null){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(toDto(updated));
    }

    private GroceryItem toEntity(GroceryItemDto groceryItemDto) {
        return modelMapper.map(groceryItemDto, GroceryItem.class);
    }

    private GroceryItemDto toDto(GroceryItem groceryItem) {
        return modelMapper.map(groceryItem, GroceryItemDto.class);
    }
}
