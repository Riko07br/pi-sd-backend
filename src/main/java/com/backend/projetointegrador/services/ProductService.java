package com.backend.projetointegrador.services;

import com.backend.projetointegrador.domain.QueryParams.PaginationParams;
import com.backend.projetointegrador.domain.dtos.ProductRequestDTO;
import com.backend.projetointegrador.domain.dtos.ProductResponseDTO;
import com.backend.projetointegrador.domain.entities.Product;
import com.backend.projetointegrador.domain.mappers.ProductMapper;
import com.backend.projetointegrador.repositories.ProductRepository;
import com.backend.projetointegrador.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<ProductResponseDTO> findAll(PaginationParams paginationParams) {
        Pageable pageable = PageRequest.of(paginationParams.getPage(), paginationParams.getSize());
        return productRepository.findAll(pageable)
                .map(ProductMapper::toResponseDTO);
    }

    public ProductResponseDTO findById(Long id) {
        Product product = findEntityById(id);
        return ProductMapper.toResponseDTO(product);
    }

    public ProductResponseDTO create(ProductRequestDTO requestDTO) {
        Product product = new Product();
        addDTOValuesToEntity(product, requestDTO);
        product = productRepository.save(product);

        return ProductMapper.toResponseDTO(product);
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO requestDTO) {
        Product product = findEntityById(id);
        addDTOValuesToEntity(product, requestDTO);
        product = productRepository.save(product);

        return ProductMapper.toResponseDTO(product);
    }

    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(Product.class, id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    Product findEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Product.class, id));
    }

    private void addDTOValuesToEntity(Product product, ProductRequestDTO requestDTO) {
        product.setName(requestDTO.name());
        product.setDailyYield(requestDTO.dailyYield());
    }

}
