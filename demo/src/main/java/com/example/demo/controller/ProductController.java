package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductComponent;
import com.example.demo.entity.Component;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ComponentRepository;
import com.example.demo.repository.ProductComponentRepository;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepo;
    
    @Autowired
    private ComponentRepository componentRepo;
    
    @Autowired
    private ProductComponentRepository bomRepo;

    // 1. LISTA PRODUSE
    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productRepo.findAll());
        return "products"; 
    }

    // 2. FORMULAR ADAUGARE
    @GetMapping("/products/new")
    public String showNewProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "add_product"; 
    }

    // 3. FORMULAR EDITARE (Integer id)
    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Product product = productRepo.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "add_product";
    }

    // 4. SALVARE
    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute("product") Product product, Model model) {
        Product existingProduct = productRepo.findByName(product.getName());

        if (existingProduct != null) {
            boolean isNewProduct = (product.getId() == null);
            boolean isDifferentProduct = (product.getId() != null && !product.getId().equals(existingProduct.getId()));

            if (isNewProduct || isDifferentProduct) {
                model.addAttribute("error", "Acest produs exista deja. Selectati o alta denumire.");
                return "add_product"; 
            }
        }

        if (product.getActive() == null) {
            product.setActive(1);
        }
        productRepo.save(product);
        return "redirect:/products";
    }

    // 5. STERGERE (ACTUALIZAT PENTRU A EVITA ERORILE DE BOM)
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        // Căutăm produsul mai întâi
        Product product = productRepo.findById(id).orElse(null);
        
        if (product != null) {
            // Pasul A: Găsim toate legăturile din rețetar (BOM) pentru acest produs
            List<ProductComponent> connections = bomRepo.findByProduct(product);
            
            // Pasul B: Ștergem aceste legături mai întâi
            if (!connections.isEmpty()) {
                bomRepo.deleteAll(connections);
            }
            
            // Pasul C: Acum putem șterge produsul în siguranță
            productRepo.delete(product);
        }
        
        return "redirect:/products";
    }

    // --- ZONA BOM (RETETAR INDIVIDUAL) ---

    @GetMapping("/products/bom/{id}")
    public String showProductBom(@PathVariable("id") Integer id, Model model) {
        Product product = productRepo.findById(id).get();
        List<ProductComponent> existingBom = bomRepo.findByProduct(product);
        List<Component> allComponents = componentRepo.findAll();

        model.addAttribute("product", product);
        model.addAttribute("bomList", existingBom);
        model.addAttribute("allComponents", allComponents);
        model.addAttribute("newLink", new ProductComponent());

        return "product_bom";
    }

    @PostMapping("/products/bom/add")
    public String addComponentToProduct(
            @RequestParam("productId") Integer productId,
            @RequestParam("componentId") Integer componentId,
            @RequestParam("quantity") Double quantity) {
        
        Product product = productRepo.findById(productId).get();
        Component component = componentRepo.findById(componentId).get();

        ProductComponent link = new ProductComponent();
        link.setProduct(product);
        link.setComponent(component);
        link.setQuantity(quantity);

        bomRepo.save(link);
        return "redirect:/products/bom/" + productId;
    }

    @GetMapping("/products/bom/remove/{id}")
    public String removeComponentFromBom(@PathVariable("id") Long id) {
        ProductComponent link = bomRepo.findById(id).get();
        Integer productId = link.getProduct().getId();
        bomRepo.deleteById(id);
        return "redirect:/products/bom/" + productId;
    }

    @GetMapping("/bom/all")
    public String showAllBoms(Model model) {
        List<ProductComponent> allLinks = bomRepo.findAll();
        model.addAttribute("allLinks", allLinks);
        return "all_bom"; 
    }
}