package com.project.shopApp.controllers;

import com.github.javafaker.Faker;
import com.project.shopApp.components.LocalizationUtils;
import com.project.shopApp.dtos.ProductDTO;
import com.project.shopApp.dtos.ProductImageDTO;
import com.project.shopApp.exceptions.DataNotFoundException;
import com.project.shopApp.models.product;
import com.project.shopApp.models.productImage;
import com.project.shopApp.response.ProductListResponse;
import com.project.shopApp.response.ProductResponse;
import com.project.shopApp.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;
    private final LocalizationUtils localizationUtils;
    @PostMapping(value = "")
    public  ResponseEntity<?> creatProduct(@Valid @RequestBody ProductDTO productDTO
            , BindingResult result
            ) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            //luu vao doi tuong product trong db
            product newProduct = productService.createProduct(productDTO);
            return ResponseEntity.ok(newProduct);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping(value = "upload/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@PathVariable("id")Long id, @ModelAttribute("files") List<MultipartFile> files){
       try {
           files=files == null? new ArrayList<MultipartFile>():files;
           if(files.size()>productImage.MAXIMUM_IMAGES_PER_PRODUCT){
               return ResponseEntity.badRequest().body("you can only upload maximum 5 images");
           }
           List<productImage> productImages = new ArrayList<>();
           for (MultipartFile file : files) {
               if (file.getSize() == 0) {
                   continue;
               }
               if (file.getSize() > 10 * 1024 * 1024) {
                   return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large, maximum is 10MB");
               }
               String contentType = file.getContentType();
               if (contentType == null || !contentType.startsWith("image/")) {
                   return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                           .body("File is not an image");
               }
               product esixtingProduct = productService.getProductById(id);
               //luu file va cap nhat thumbnail trong DTO
               String fileName = storeFile(file);
               //luu vao bang product_images
               productImage productImage = productService.createProductImage(esixtingProduct.getId(),
                       ProductImageDTO.builder()
                               .productId(esixtingProduct.getId())
                               .imageUrl(fileName).build());
               productImages.add(productImage);
           }
           return ResponseEntity.ok(productImages);
       }
       catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
    private String storeFile( MultipartFile file) throws IOException {
        if(!isImageValid(file) || file.getOriginalFilename()==null){
            throw new IOException("Invalid image file format");
        }
        String fileName= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // them UUID vao truoc ten file de tao ra unique file name
        String uniqueFileName = UUID.randomUUID().toString()+"_"+fileName;
        // tao thu muc dich
        java.nio.file.Path uploadDir= Paths.get("uploads");
        if(!Files.exists(uploadDir)){
            Files.createDirectory(uploadDir);
        }
        //tao duong dan toi thu muc dich
        java.nio.file.Path destination=Paths.get(uploadDir.toString(),uniqueFileName);
        //sao chep file vao thu muc dich
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
    private  boolean isImageValid(MultipartFile file){
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
    @GetMapping("/images/{imagesName}")
    public ResponseEntity<?> getProductImages(@PathVariable String imagesName) throws MalformedURLException {
        try {
            Path imagePath=Paths.get("uploads/"+imagesName);
            UrlResource urlResource = new UrlResource(imagePath.toUri());
            if(urlResource.exists()){
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(urlResource);
            }

            else {
               return ResponseEntity.ok()
                       .contentType(MediaType.IMAGE_JPEG)
                       .body(new UrlResource(Paths.get("uploads/img.png").toUri()));
            }
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("")
    public ResponseEntity<ProductListResponse> getAllProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0",name = "category_id") long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int limit
    ) {
        PageRequest pageRequest= PageRequest.of(page,limit, Sort.by("id").descending());
        Page<ProductResponse> productPage= productService.getAllProducts(keyword,categoryId,pageRequest);
        int totalPage = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse
                .builder()
                .products(products)
                .totalPages(totalPage).build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long product_id) throws DataNotFoundException {
            product esixtingProduct = productService.getProductById(product_id);
        System.out.println(esixtingProduct);
            return ResponseEntity.ok(ProductResponse.fromProduct(esixtingProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id){
        try {
            productService.deleteProduct(id);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Product"+id+" deleted successfully");
    }
    @PostMapping("generateFakeProducts")
    public  ResponseEntity <String> generateFakeProducts(){
        Faker faker = new Faker();
        for (int i=0; i <1000000;i++){
            String productName = faker.company().name();
            if(productService.existsByName(productName)){
                continue;
            }
            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .price((float)faker.number().numberBetween(10,90000000))
                    .description(faker.lorem().sentence())
                    .categoryId((long)faker.number().numberBetween(2,5))
                    .thumbnail("")
                    .build();

           try {
               productService.createProduct(productDTO);
           }
           catch (Exception e){
               return ResponseEntity.badRequest().body(e.getMessage());
           }
        }
        return ResponseEntity.status(HttpStatus.OK).body("Fake product generated successfully");
    }
    @PutMapping("{id}")
    public  ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO productDTO){
        try {
            product updatedProduct= productService.updateProduct(id,productDTO);
            return ResponseEntity.ok(updatedProduct);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/by-ids")
    public ResponseEntity<?> getProductByIds(@RequestParam("ids") String ids){
//        System.out.println("asdasdasda");
        try {
            List<Long> productIds= Arrays.stream(ids.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            List<product> products= productService.findProductByIds(productIds);
            return ResponseEntity.ok(products);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
