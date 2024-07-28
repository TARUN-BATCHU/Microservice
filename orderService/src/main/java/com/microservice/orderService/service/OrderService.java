package com.microservice.orderService.service;

import com.microservice.orderService.dto.InventoryResponse;
import com.microservice.orderService.dto.OrderLineItemsDto;
import com.microservice.orderService.dto.OrderRequest;
import com.microservice.orderService.model.Order;
import com.microservice.orderService.model.OrderLineItems;
import com.microservice.orderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDto().stream().map(this::mapObjectToDto).toList();
        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = orderLineItemsList.stream().map(orderLineItems -> orderLineItems.getSkuCode()).toList();

        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                                            .uri("http://inventoryService/api/inventory",
                                                    uriBuilder -> uriBuilder.queryParam("skuCodes",skuCodes).build())
                                             .retrieve()
                                              .bodyToMono(InventoryResponse[].class)
                                             .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(inventoryResponse -> inventoryResponse.isInStock());
        if(allProductsInStock) {
            orderRepository.save(order);
        }else{
            throw new IllegalArgumentException("Product has no stock");
        }
        return "Ordered";
    }

    public OrderLineItems mapObjectToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setId(orderLineItemsDto.getId());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
