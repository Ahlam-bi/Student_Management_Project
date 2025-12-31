package com.example.thymeleaf_project.controllers;

import com.example.thymeleaf_project.entities.Eleve;
import com.example.thymeleaf_project.services.CoursService;
import com.example.thymeleaf_project.services.EleveService;
import com.example.thymeleaf_project.services.FiliereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/eleves")
public class EleveController {

    @Autowired
    private EleveService eleveService;

    @Autowired
    private FiliereService filiereService;

    @Autowired
    private CoursService coursService;

    @GetMapping
    public String listEleves(Model model) {
        model.addAttribute("eleves", eleveService.getAllEleves());
        return "eleves/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("eleve", new Eleve());
        model.addAttribute("filieres", filiereService.getAllFilieres());
        return "eleves/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Eleve eleve = eleveService.getEleveById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid eleve Id:" + id));
        model.addAttribute("eleve", eleve);
        model.addAttribute("filieres", filiereService.getAllFilieres());
        return "eleves/form";
    }

    @PostMapping("/save")
    public String saveEleve(@ModelAttribute Eleve eleve, @RequestParam(required = false) Long filiereId) {
        if (filiereId != null) {
            eleve.setFiliere(filiereService.getFiliereById(filiereId).orElse(null));
        }
        eleveService.saveEleve(eleve);
        return "redirect:/eleves";
    }

    @GetMapping("/delete/{id}")
    public String deleteEleve(@PathVariable Long id) {
        eleveService.deleteEleve(id);
        return "redirect:/eleves";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Eleve eleve = eleveService.getEleveById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid eleve Id:" + id));
        model.addAttribute("eleve", eleve);
        return "eleves/details";
    }

    @GetMapping("/inscription/{id}")
    public String showInscriptionForm(@PathVariable Long id, Model model) {
        Eleve eleve = eleveService.getEleveById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid eleve Id:" + id));

        if (eleve.getFiliere() != null) {
            model.addAttribute("coursDisponibles",
                    coursService.getCoursByFiliere(eleve.getFiliere().getId()));
        } else {
            model.addAttribute("coursDisponibles", coursService.getAllCours());
        }

        model.addAttribute("eleve", eleve);
        return "eleves/inscription";
    }

    @PostMapping("/inscription/{id}")
    public String inscrireCours(@PathVariable Long id, @RequestParam List<Long> coursIds) {
        Eleve eleve = eleveService.getEleveById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid eleve Id:" + id));

        eleve.getCours().clear();
        for (Long coursId : coursIds) {
            coursService.getCoursById(coursId).ifPresent(cours -> eleve.getCours().add(cours));
        }

        eleveService.saveEleve(eleve);
        return "redirect:/eleves/details/" + id;
    }

}