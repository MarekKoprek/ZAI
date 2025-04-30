package com.example.fitshop.service;

import com.example.fitshop.converter.ProductToProductDTO;
import com.example.fitshop.dto.OpinionDTO;
import com.example.fitshop.dto.ProductDTO;
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

    public List<ProductDTO> getProducts(){
        return productRepo.findAll().stream()
                .map(productToProductDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsBySubCategoryId(Long subCategoryId){
        return productRepo.findAllBySubCategoryId(subCategoryId).stream()
                .map(productToProductDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByCategoryId(Long categoryId){
        return productRepo.findAllBySubCategoryCategoryId(categoryId).stream()
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
}
