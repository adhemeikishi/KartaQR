package com.qrmenu.admin;

import com.qrmenu.restaurant.Restaurant;
import com.qrmenu.restaurant.RestaurantDtos.CreateRestaurantRequest;
import com.qrmenu.restaurant.RestaurantDtos.RestaurantResponse;
import com.qrmenu.restaurant.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/restaurants")
public class RestaurantAdminController {

    private final RestaurantService restaurantService;

    public RestaurantAdminController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> create(@Valid @RequestBody CreateRestaurantRequest request) {
        Restaurant restaurant = restaurantService.create(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(RestaurantResponse.from(restaurant));
    }

    @GetMapping
    public List<RestaurantResponse> findAll() {
        return restaurantService.findAll().stream().map(RestaurantResponse::from).toList();
    }

    @GetMapping("/{id}")
    public RestaurantResponse findById(@PathVariable UUID id) {
        return RestaurantResponse.from(restaurantService.getOrThrow(id));
    }
}
