package com.pweb.MyClinic.repository;

import com.pweb.MyClinic.model.db.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInfoRepository extends JpaRepository<Product, Long> {

    Product getProductInfoById(Long id);
}