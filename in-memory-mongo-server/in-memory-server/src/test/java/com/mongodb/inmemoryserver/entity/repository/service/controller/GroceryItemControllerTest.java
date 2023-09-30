package com.mongodb.inmemoryserver.entity.repository.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.inmemoryserver.config.EnableMongoTestServer;
import com.mongodb.inmemoryserver.entity.GroceryItem;
import com.mongodb.inmemoryserver.entity.repository.GroceryItemRepository;
import com.mongodb.inmemoryserver.model.GroceryItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@EnableMongoTestServer
@EnableMongoRepositories(basePackages = "com.mongodb.inmemoryserver.entity.repository")
class GroceryItemControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Test
    void testAddGroceryItem() throws Exception {

        GroceryItemDto dto = getGroceryItem();

        this.mockMvc.perform(post("/api/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(dto.getName()))
                .andExpect(jsonPath("$.category").value(dto.getCategory()))
                .andExpect(jsonPath("$.quantity").value(dto.getQuantity()));
    }

    @Test
    void testDeleteGroceryItem() throws Exception {
        GroceryItem saved = this.groceryItemRepository.save(getGroceryItemEntity());

        String id = saved.getId();
        this.mockMvc.perform(delete("/api/item/{itemId}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getGroceryItemById() throws Exception {
        GroceryItem saved = this.groceryItemRepository.save(getGroceryItemEntity());

        String id = saved.getId();
        this.mockMvc.perform(get("/api/item/{itemId}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(saved.getName()))
                .andExpect(jsonPath("$.category").value(saved.getCategory()));
    }

    @Test
    void updateGroceryItem() throws Exception {
        GroceryItem item = this.groceryItemRepository.save(getGroceryItemEntity());

        item.setQuantity(10);
        GroceryItemDto itemDto = toDto(item);
        this.mockMvc.perform(put("/api/item", itemDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.name").value(item.getName()))
                .andExpect(jsonPath("$.category").value(item.getCategory()));
    }

    private static GroceryItemDto getGroceryItem() {
        GroceryItemDto dto = new GroceryItemDto();
        dto.setCategory("fruit");
        dto.setName("Grapes");
        dto.setQuantity(5);
        return dto;
    }

    private GroceryItem getGroceryItemEntity() {
        GroceryItem item = new GroceryItem();
        item.setQuantity(4);
        item.setName("milk");
        item.setCategory("dairy");
        return item;
    }

    public static GroceryItemDto toDto(GroceryItem item){
        GroceryItemDto dto= new GroceryItemDto();
        dto.id(item.getId());
        dto.setName(item.getName());
        dto.setCategory(item.getCategory());
        dto.setQuantity(item.getQuantity());
        return dto;
    }
}