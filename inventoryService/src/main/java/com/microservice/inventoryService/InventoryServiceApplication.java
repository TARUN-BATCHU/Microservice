package com.microservice.inventoryService;

import com.microservice.inventoryService.model.Inventory;
import com.microservice.inventoryService.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
//		return args -> {
//			Inventory inventory1 = new Inventory();
//			inventory1.setSkuCode("Iphone");
//			inventory1.setQuantity(5);
//
//			Inventory inventory2 = new Inventory();
//			inventory2.setSkuCode("Samsung");
//			inventory2.setQuantity(0);
//
//			inventoryRepository.save(inventory1);
//			inventoryRepository.save(inventory2);
//		};
//	}

}
