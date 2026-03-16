package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "components")
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcomponent") // <--- NUMELE COLOANEI DIN BAZA VECHE
    private Integer id;           // <--- INTEGER

    @Column(name = "code")
    private String code;

    @Column(name = "component_name") // Verificam daca asa e in baza
    private String name;

    @Column(name = "unit_cost")
    private Double unitCost;

    private Integer stock;
    
    // Adaugam campurile lipsa care erau obligatorii in baza veche
    private String unit; 
    private Integer active;

    public Component() {}

    // Getters si Setters (Integer)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getUnitCost() { return unitCost; }
    public void setUnitCost(Double unitCost) { this.unitCost = unitCost; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Integer getActive() { return active; }
    public void setActive(Integer active) { this.active = active; }
}