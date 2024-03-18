package com.pweb.MyClinic.service.company;

import com.pweb.MyClinic.model.db.Product;
import com.pweb.MyClinic.repository.ProductInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductInfoRepository repository;

    public void saveProduct(Product productInfo) {
        repository.save(productInfo);
    }

    public Product getProduct(Long productId) {
        return repository.getProductInfoById(productId);
    }
}