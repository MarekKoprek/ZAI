package com.example.fitshop.service;

import com.example.fitshop.converter.ProductToProductDTO;
import com.example.fitshop.dto.OpinionDTO;
import com.example.fitshop.dto.ProductDTO;
import com.example.fitshop.dto.ProductFilterDTO;
import com.example.fitshop.model.*;
import com.example.fitshop.repository.CartRepo;
import com.example.fitshop.repository.ClientOrderRepo;
import com.example.fitshop.repository.OpinionRepo;
import com.example.fitshop.repository.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductToProductDTO productToProductDTO;

    private final ProductRepo productRepo;
    private final OpinionRepo opinionRepo;
    private final AuthService authService;
    private final ClientOrderRepo clientOrderRepo;

    public Product getProductById(Long id){
        Product product = productRepo.findFirstById(id);
        if(product == null){
            throw new EntityNotFoundException("Brak produktu o takim id");
        }
        return product;
    }

    public List<ProductDTO> getProducts(Double minPrice, Double maxPrice, Double minRating){
        List<Product> products = productRepo.findAll();
        if(minPrice != null){
            log.info("minPrice: " + minPrice);
            products = products.stream()
                    .filter(product -> product.getPrice() >= minPrice)
                    .toList();
        }
        if(minRating != null){
            log.info("minRating: " + minRating);
            products = products.stream()
                    .filter(product -> getProductRating(product) >= minRating)
                    .toList();
        }
        if(maxPrice != null && maxPrice > 0){
            log.info("maxPrice: " + maxPrice);
            products = products.stream()
                    .filter(product -> product.getPrice() <= maxPrice)
                    .toList();
        }
        return products.stream()
                .map(productToProductDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsBySubCategoryId(Long subCategoryId, Double minPrice, Double maxPrice, Double minRating){
        List<Product> products = productRepo.findAllBySubCategoryId(subCategoryId);
        if(minPrice != null){
            log.info("minPrice: " + minPrice);
            products = products.stream()
                    .filter(product -> product.getPrice() >= minPrice)
                    .toList();
        }
        if(minRating != null){
            log.info("minRating: " + minRating);
            products = products.stream()
                    .filter(product -> getProductRating(product) >= minRating)
                    .toList();
        }
        if(maxPrice != null && maxPrice > 0){
            log.info("maxPrice: " + maxPrice);
            products = products.stream()
                    .filter(product -> product.getPrice() <= maxPrice)
                    .toList();
        }
        return products.stream()
                .map(productToProductDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByCategoryId(Long categoryId, ProductFilterDTO productFilterDTO){
        List<Product> products = productRepo.findAllBySubCategoryCategoryId(categoryId);
        products = products.stream()
                .filter(product -> product.getPrice() >= productFilterDTO.getMinPrice())
                .toList();
        products = products.stream()
                .filter(product -> getProductRating(product) >= productFilterDTO.getMinRating())
                .toList();
        if(productFilterDTO.getMaxPrice() > 0){
            products = products.stream()
                    .filter(product -> product.getPrice() <= productFilterDTO.getMaxPrice())
                    .toList();
        }
        return products.stream()
                .map(productToProductDTO::convert)
                .collect(Collectors.toList());
    }

    public void addProductOpinion(Long productId, OpinionDTO opinionDTO){
        AppUser user = authService.getAuthenticatedUser();
        Product product = getProductById(productId);
        List<ClientOrder> orders = clientOrderRepo.findAllByUser(user);
        boolean found = false;
        for (ClientOrder order : orders){
            for (Product product1 : order.getProducts()){
                if (product1.getId().equals(productId)){
                    found = true;
                }
            }
        }
        if(!found){
            throw new IllegalArgumentException("Brak produktu o takim id");
        }
        Opinion opinion = opinionRepo.findByProductAndUser(product, user).orElse(null);
        if(opinion == null){
            product.getOpinions().add(new Opinion(null, opinionDTO.getRating(), user, product));
            productRepo.save(product);
        }
        else{
            opinion.setRating(opinionDTO.getRating());
            opinionRepo.save(opinion);
        }
    }

    public double getProductRating(Product product){
        return product.getOpinions().stream().mapToInt(Opinion::getRating).average().orElse(0);
    }

    public Page<ProductDTO> getPagedProducts(Pageable pageable){
        Page<Product> products = productRepo.findAll(pageable);
        return products.map(productToProductDTO::convert);
    }
}
