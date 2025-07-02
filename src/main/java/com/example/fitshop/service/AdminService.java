package com.example.fitshop.service;

import com.example.fitshop.converter.OrderToOrderDTO;
import com.example.fitshop.converter.ProductDTOToProduct;
import com.example.fitshop.converter.ProductToProductDTO;
import com.example.fitshop.converter.UserToUserDto;
import com.example.fitshop.dto.OrderDTO;
import com.example.fitshop.dto.ProductDTO;
import com.example.fitshop.dto.UserDto;
import com.example.fitshop.model.*;
import com.example.fitshop.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final ProductDTOToProduct productDTOToProduct;
    private final ProductToProductDTO productToProductDTO;
    private final UserToUserDto userToUserDto;

    private final ProductRepo productRepo;
    private final ProductImageRepo productImageRepo;
    private final AppUserRepo appUserRepo;
    private final ClientOrderRepo clientOrderRepo;
    private final SubCategoryRepo subCategoryRepo;
    private final OrderToOrderDTO orderToOrderDTO;

    public ProductDTO addProduct(ProductDTO productDTO) {
        log.info("Adding product " + productDTO);
        Product product;
        SubCategory subCategory = subCategoryRepo.findFirstById(productDTO.getSubCategory().getId());
        if (productDTO.getId() != null && productDTO.getId() > 0) {
            product = productRepo.findById(productDTO.getId()).get();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setQuantity(productDTO.getQuantity());
        } else {
            product = productDTOToProduct.convert(productDTO);
            product.setId(null);
            product.setOpinions(Collections.emptyList());
            product.setShippers(Collections.emptyList());
        }
        product.setSubCategory(subCategory);
        return productToProductDTO.convert(productRepo.save(product));
    }

    public ProductDTO uploadImages(MultipartFile[] files, Long productId) {
        log.info("Uploading images");
        Product product = productRepo.findById(productId).get();
        List<String> fileNames = saveFiles(files);
        for (String fileName : fileNames) {
            ProductImage productImage = new ProductImage();
            productImage.setProduct(product);
            productImage.setUrl(fileName);
            productImageRepo.save(productImage);
        }
        return productToProductDTO.convert(productRepo.save(product));
    }

    private Path getUploadDir() {
        return Path.of(System.getProperty("user.dir"))
                .getParent()
                .resolve("frontend")
                .resolve("src")
                .resolve("assets")
                .resolve("images");
    }

    private List<String> saveFiles(MultipartFile[] files) {
        List<String> savedFileNames = new ArrayList<>();

        log.info("Saving files");
        for (MultipartFile file : files) {
            log.info("Saving file {}", file.getOriginalFilename());
            if (!file.isEmpty()) {
                try {
                    String originalFilename = file.getOriginalFilename();
                    String extension = "";

                    int dotIndex = originalFilename != null ? originalFilename.lastIndexOf(".") : -1;
                    if (dotIndex != -1) {
                        extension = originalFilename.substring(dotIndex);
                    }

                    Path uploadDir = getUploadDir();
                    if (!Files.exists(uploadDir)) {
                        Files.createDirectories(uploadDir);
                    }

                    Path targetPath = getUploadDir().resolve(originalFilename);

                    Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                    savedFileNames.add(originalFilename);
                } catch (IOException e) {
                    log.error("Błąd zapisu pliku: " + file.getOriginalFilename(), e);
                    throw new RuntimeException("Błąd zapisu pliku: " + file.getOriginalFilename(), e);
                }
            }
        }
        return savedFileNames;
    }

    public List<UserDto> getUsers() {
        List<AppUser> users = appUserRepo.findAll();
        return users.stream().map(userToUserDto::convert).collect(Collectors.toList());
    }

    public List<OrderDTO> getOrders() {
        return clientOrderRepo.findAll().stream().map(orderToOrderDTO::convert).collect(Collectors.toList());
    }

    public void deleteUser(Long userId) {
        AppUser user = appUserRepo.findById(userId).get();
        if (user != null && user.getType().equals(UserType.ADMIN)) {
            return;
        }
        appUserRepo.deleteById(userId);
    }

    public void deleteProduct(Long productId) {
        productRepo.deleteById(productId);
    }

    public void deleteOrder(Long orderId) {
        clientOrderRepo.deleteById(orderId);
    }
}
