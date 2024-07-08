package com.microservice.inventoryService.service;

import com.microservice.inventoryService.dto.InventoryResponse;
import com.microservice.inventoryService.model.Inventory;
import com.microservice.inventoryService.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode){
        return  inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inventory -> mapObjectToInventoryResponse(inventory)).toList();
    }

    public InventoryResponse mapObjectToInventoryResponse(Inventory inventory){
        InventoryResponse inventoryResponse = new InventoryResponse();
        inventoryResponse.setSkuCode(inventory.getSkuCode());
        inventoryResponse.setInStock(inventory.getQuantity() > 0);
        return inventoryResponse;
    }

}
