package com.eemeli.orderservice.repository;

import com.eemeli.orderservice.model.product.BeerProduct;
import com.eemeli.orderservice.model.product.BreadProduct;
import com.eemeli.orderservice.model.product.Product;
import com.eemeli.orderservice.model.product.VegetableProduct;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Repository
public class ProductRepository {
    private final HashMap<String, Product> productByName = new HashMap<>() {{
        put("Bread 1d", new BreadProduct("Bread", 100, LocalDate.now().minusDays(1)));
        put("Bread 3d", new BreadProduct("Bread", 100, LocalDate.now().minusDays(3)));
        put("Bread 6d", new BreadProduct("Bread", 100, LocalDate.now().minusDays(6)));
        put("Bread 7d", new BreadProduct("Bread", 100, LocalDate.now().minusDays(7)));
        put("Vegetables", new VegetableProduct("Vegetables", 100, 100));
        put("Belgian beer", new BeerProduct("Belgian beer", 50));
        put("Dutch beer", new BeerProduct("Dutch beer", 50));
        put("German beer", new BeerProduct("German beer", 50));
    }};

    public @NotNull List<Product> getAllProducts() {
        return productByName.values().stream().toList();
    }

    public @Nullable Product getProductByName(@NotNull String name) {
        return productByName.get(name);
    }

}
