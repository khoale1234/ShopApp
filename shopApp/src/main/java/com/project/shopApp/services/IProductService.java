package com.project.shopApp.services;

import com.project.shopApp.dtos.ProductDTO;
import com.project.shopApp.dtos.ProductImageDTO;
import com.project.shopApp.exceptions.DataNotFoundException;
import com.project.shopApp.exceptions.InvalidParaException;
import com.project.shopApp.models.product;
import com.project.shopApp.models.productImage;
import com.project.shopApp.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {
    public product createProduct(ProductDTO productDTO) throws DataNotFoundException;
    public product getProductById(Long id) throws DataNotFoundException;
    Page<ProductResponse> getAllProducts(String keyword,Long category_id,PageRequest pageRequest);
    product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException;
    void deleteProduct(Long id);
    boolean existsByName(String name);
    public productImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws DataNotFoundException, InvalidParaException;
    public List<product>findProductByIds(List<Long> productIds);
}
