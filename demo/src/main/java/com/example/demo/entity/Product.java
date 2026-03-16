package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products") // Numele tabelei in baza veche
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproduct") // <--- NUMELE COLOANEI DIN BAZA VECHE
    private Integer id;         // <--- FOLOSIM INTEGER, NU LONG

    @Column(name = "product_name") // Asigura-te ca numele corespund
    private String name;

    private String description;
    private String category;
    private Double price;
    private Integer stock;
    
    // Coloana 'active' in baza veche era probabil INT sau TINYINT
    private Integer active; 

    public Product() {}

    // Getters si Setters actualizati pentru Integer
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    
    public Integer getActive() { return active; }
    public void setActive(Integer active) { this.active = active; }
}