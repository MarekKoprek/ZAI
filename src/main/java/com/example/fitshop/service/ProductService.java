package com.example.fitshop.service;

import com.example.fitshop.converter.ProductToProductDTO;
import com.example.fitshop.dto.OpinionDTO;
import com.example.fitshop.dto.ProductDTO;
import com.example.fitshop.dto.ProductFilterDTO;
import com.example.fitshop.model.AppUser;
import com.example.fitshop.model.Opinion;
import com.example.fitshop.model.Product;
import com.example.fitshop.repository.OpinionRepo;
import com.example.fitshop.repository.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public Product getProductById(Long id){
        Product product = productRepo.findFirstById(id);
        if(product == null){
            throw new EntityNotFoundException("Brak produktu o takim id");
        }
        return product;
    }

    public List<ProductDTO> getProducts(ProductFilterDTO productFilterDTO){
        List<Product> products = productRepo.findAll();
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

    public List<ProductDTO> getProductsBySubCategoryId(Long subCategoryId, ProductFilterDTO productFilterDTO){
        List<Product> products = productRepo.findAllBySubCategoryId(subCategoryId);
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

    public void addProductOpinion(Long productId, OpinionDTO opinionDTO, AppUser appUser){
        Product product = getProductById(productId);
        Opinion opinion = opinionRepo.findByProductAndUser(product, appUser).orElse(null);
        if(opinion == null){
            product.getOpinions().add(new Opinion(null, opinionDTO.getRating(), appUser, product));
        }
        else{
            opinion.setRating(opinionDTO.getRating());
            opinionRepo.save(opinion);
        }
    }

    public double getProductRating(Product product){
        return product.getOpinions().stream().mapToInt(Opinion::getRating).average().orElse(0);
    }
}
