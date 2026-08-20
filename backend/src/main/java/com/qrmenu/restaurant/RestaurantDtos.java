package com.qrmenu.restaurant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.UUID;

public class RestaurantDtos {

    private RestaurantDtos() {
    }

    public record CreateRestaurantRequest(
            @NotBlank(message = "name is required")
            @Size(max = 255)
            String name
    ) {
    }

    public record RestaurantResponse(
            UUID id,
            String name,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt
    ) {
        public static RestaurantResponse from(Restaurant restaurant) {
            return new RestaurantResponse(
                    restaurant.getId(),
                    restaurant.getName(),
                    restaurant.getCreatedAt(),
                    restaurant.getUpdatedAt()
            );
        }
    }
}
