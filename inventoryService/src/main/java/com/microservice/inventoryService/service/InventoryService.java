package com.microservice.inventoryService.service;

import com.microservice.inventoryService.dto.InventoryResponse;
import com.microservice.inventoryService.model.Inventory;
import com.microservice.inventoryService.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode){
        log.info("wait started");
        Thread.sleep(10000);
        log.info("wait ended");
        return  inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inventory -> mapObjectToInventoryResponse(inventory)).toList();
    }

    public InventoryResponse mapObjectToInventoryResponse(Inventory inventory){
        InventoryResponse inventoryResponse = new InventoryResponse();
        inventoryResponse.setSkuCode(inventory.getSkuCode());
        inventoryResponse.setInStock(inventory.getQuantity() > 0);
        return inventoryResponse;
    }

}
