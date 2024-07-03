package com.project.shopApp.services;

import com.project.shopApp.dtos.ProductDTO;
import com.project.shopApp.dtos.ProductImageDTO;
import com.project.shopApp.exceptions.DataNotFoundException;
import com.project.shopApp.exceptions.InvalidParaException;
import com.project.shopApp.models.category;
import com.project.shopApp.models.product;
import com.project.shopApp.models.productImage;
import com.project.shopApp.repositories.CategoryRepository;
import com.project.shopApp.repositories.ProductImageRepository;
import com.project.shopApp.repositories.ProductRepository;
import com.project.shopApp.response.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    @Override
    @Transactional
    public product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        category existCategory= categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(()->
                new DataNotFoundException("Can not find category with id: "+productDTO.getCategoryId()));
        product newProduct= product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .category(existCategory)
                .description(productDTO.getDescription())
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public product getProductById(Long id) throws DataNotFoundException {

        return productRepository.findById(id).orElseThrow(()-> new DataNotFoundException("can not find product with id: "+id));
    }

    @Override
    public Page<ProductResponse> getAllProducts(String keyword,Long category_id,PageRequest pageRequest) {
        // lay danh sach san pham theo page va limit
        Page<product> productsPage;
        productsPage=productRepository.searchProduct(category_id,keyword,pageRequest);

        return productsPage.map(ProductResponse::fromProduct);
    }
    @Transactional
    @Override
    public product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException {
        product existProduct= getProductById(id);
        if(existProduct!=null){
            category existCategory= categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(()->
                    new DataNotFoundException("Can not find category with id: "+productDTO.getCategoryId()));
            existProduct.setName(productDTO.getName());
            existProduct.setPrice(productDTO.getPrice());
            existProduct.setThumbnail(productDTO.getThumbnail());
            existProduct.setDescription(productDTO.getDescription());
            existProduct.setCategory(existCategory);
            return productRepository.save(existProduct);
        }
        return null;
    }
    @Transactional
    @Override
    public void deleteProduct(Long id) {
        Optional<product> product= productRepository.findById(id);
        product.ifPresent(productRepository::delete);

    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
    @Override
    public productImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws DataNotFoundException, InvalidParaException {
        product existProduct= productRepository.findById(productImageDTO.getProductId()).orElseThrow(()->
                new DataNotFoundException("Can not find product with id: "+productImageDTO.getProductId()));
        productImage newProductImage= productImage.builder()
                .product(existProduct)
                .imageUrl(productImageDTO.getImageUrl()).build();
        //Ko cho insert qua 5 anh cho 1 san pham
        int size= productImageRepository.findByProductId(existProduct.getId()).size();
        if(size>=productImage.MAXIMUM_IMAGES_PER_PRODUCT){
            throw new InvalidParaException("Number of images must be less than or equal to "+productImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }

        return productImageRepository.save(newProductImage);
    }

    @Override
    public List<product> findProductByIds(List<Long> productIds) {
        return productRepository.findProductsByIds(productIds);
    }
}
