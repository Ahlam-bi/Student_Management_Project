package com.example.thymeleaf_project.controllers;

import com.example.thymeleaf_project.entities.Filiere;
import com.example.thymeleaf_project.services.CoursService;
import com.example.thymeleaf_project.services.EleveService;
import com.example.thymeleaf_project.services.FiliereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/filieres")
public class FiliereController {

    @Autowired
    private FiliereService filiereService;

    @Autowired
    private EleveService eleveService;

    @Autowired
    private CoursService coursService;

    @GetMapping
    public String listFilieres(Model model) {
        model.addAttribute("filieres", filiereService.getAllFilieres());
        return "filieres/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("filiere", new Filiere());
        return "filieres/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Filiere filiere = filiereService.getFiliereById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid filiere Id:" + id));
        model.addAttribute("filiere", filiere);
        return "filieres/form";
    }

    @PostMapping("/save")
    public String saveFiliere(@ModelAttribute Filiere filiere) {
        // Spring remplit automatiquement l'objet depuis le formulaire
        filiereService.saveFiliere(filiere);
        return "redirect:/filieres";
    }

    @GetMapping("/delete/{id}")
    public String deleteFiliere(@PathVariable Long id) {
        filiereService.deleteFiliere(id);
        return "redirect:/filieres";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Filiere filiere = filiereService.getFiliereById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid filiere Id:" + id));
        model.addAttribute("filiere", filiere);
        model.addAttribute("eleves", eleveService.getElevesByFiliere(id));
        model.addAttribute("cours", coursService.getCoursByFiliere(id));
        return "filieres/details";
    }
}
