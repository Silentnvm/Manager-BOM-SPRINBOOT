package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.ProductComponent;
import com.example.demo.entity.Product;
import com.example.demo.entity.Component; // <--- ACEASTA ESTE LINIA CARE LIPSEȘTE

public interface ProductComponentRepository extends JpaRepository<ProductComponent, Long> {

    // Găsește toate componentele pentru un produs
    List<ProductComponent> findByProduct(Product product);
    
    // Găsește toate produsele în care este folosită o componentă
    List<ProductComponent> findByComponent(Component component);
}