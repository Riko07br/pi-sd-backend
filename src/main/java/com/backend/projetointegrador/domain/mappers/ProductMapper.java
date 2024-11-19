package com.backend.projetointegrador.domain.mappers;

import com.backend.projetointegrador.domain.dtos.ProductResponseDTO;
import com.backend.projetointegrador.domain.entities.Product;

public class ProductMapper {
    public static ProductResponseDTO toResponseDTO(Product product) {
        return new ProductResponseDTO(product.getId(),
                product.getName(),
                product.getDailyYield(),
                product.getDescription()
        );
    }
}
