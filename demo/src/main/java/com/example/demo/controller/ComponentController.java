package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Component;
import com.example.demo.entity.ProductComponent;
import com.example.demo.repository.ComponentRepository;
import com.example.demo.repository.ProductComponentRepository; // IMPORT NOU

import java.util.List;

@Controller
public class ComponentController {

    @Autowired
    private ComponentRepository componentRepo;
    
    @Autowired
    private ProductComponentRepository bomRepo; // INJECTARE NOUĂ

    // 1. LISTA
    @GetMapping("/components")
    public String listComponents(Model model) {
        model.addAttribute("components", componentRepo.findAll());
        return "components"; 
    }

    // 2. FORMULAR ADAUGARE
    @GetMapping("/components/new")
    public String showNewForm(Model model) {
        Component component = new Component();
        model.addAttribute("component", component);
        return "add_component"; 
    }

    // 3. FORMULAR EDITARE
    @GetMapping("/components/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Component component = componentRepo.findById(id).orElse(null);
        model.addAttribute("component", component);
        return "add_component";
    }

    // 4. SALVARE (Păstrăm logica de duplicate)
    @PostMapping("/components/save")
    public String saveComponent(@ModelAttribute("component") Component component, Model model) {
        Component existingByName = componentRepo.findByName(component.getName());
        if (existingByName != null) {
            boolean isNew = (component.getId() == null);
            boolean isDifferent = (component.getId() != null && !component.getId().equals(existingByName.getId()));
            if (isNew || isDifferent) {
                model.addAttribute("error", "O componentă cu acest NUME există deja!");
                return "add_component"; 
            }
        }

        if (component.getCode() != null && !component.getCode().isEmpty()) {
            Component existingByCode = componentRepo.findByCode(component.getCode());
            if (existingByCode != null) {
                boolean isNew = (component.getId() == null);
                boolean isDifferent = (component.getId() != null && !component.getId().equals(existingByCode.getId()));
                if (isNew || isDifferent) {
                    model.addAttribute("error", "O componentă cu acest COD există deja!");
                    return "add_component"; 
                }
            }
        }

        if (component.getActive() == null) {
            component.setActive(1);
        }
        componentRepo.save(component);
        return "redirect:/components";
    }

    // 5. STERGERE (ACTUALIZATĂ PENTRU CURĂȚARE BOM)
    @GetMapping("/components/delete/{id}")
    public String deleteComponent(@PathVariable("id") Integer id) {
        // A. Căutăm componenta
        Component component = componentRepo.findById(id).orElse(null);
        
        if (component != null) {
            // B. Găsim toate legăturile din tabelul de rețete unde apare această componentă
            List<ProductComponent> links = bomRepo.findByComponent(component);
            
            // C. Ștergem acele legături (curățăm rețetele produselor care foloseau componenta)
            if (!links.isEmpty()) {
                bomRepo.deleteAll(links);
            }
            
            // D. Ștergem componenta propriu-zisă
            componentRepo.delete(component);
        }
        
        return "redirect:/components";
    }
}