package com.example.demo.entity;

import jakarta.persistence.*; // Importuri pentru baza de date

@Entity
@Table(name = "product_components")
public class ProductComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // LEGATURA CU PRODUSUL (Many-to-One)
    // Un produs poate avea multe componente in reteta
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // LEGATURA CU COMPONENTA (Many-to-One)
    // O componenta poate fi folosita in multe produse
    @ManyToOne
    @JoinColumn(name = "component_id", nullable = false)
    private Component component;

    // CANTITATEA NECESARA
    private Double quantity;

    // CONSTRUCTOR GOL (Obligatoriu)
    public ProductComponent() {}

    // GETTERS SI SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Component getComponent() { return component; }
    public void setComponent(Component component) { this.component = component; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
}