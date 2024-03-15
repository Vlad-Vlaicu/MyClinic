package com.pweb.MyClinic.repository;

import com.pweb.MyClinic.model.db.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
}