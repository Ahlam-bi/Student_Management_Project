package com.example.thymeleaf_project.services;

import com.example.thymeleaf_project.entities.DossierAdministratif;
import com.example.thymeleaf_project.entities.Eleve;
import com.example.thymeleaf_project.repositories.EleveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EleveService {

    @Autowired
    private EleveRepository eleveRepository;

    public List<Eleve> getAllEleves() {
        return eleveRepository.findAll();
    }

    public Optional<Eleve> getEleveById(Long id) {
        return eleveRepository.findById(id);
    }

    public List<Eleve> getElevesByFiliere(Long filiereId) {
        return eleveRepository.findByFiliereId(filiereId);
    }

    public Eleve saveEleve(Eleve eleve) {
        // Créer automatiquement le dossier administratif si absent
        if (eleve.getDossierAdministratif() == null) {
            DossierAdministratif dossier = new DossierAdministratif();
            dossier.setDateCreation(LocalDate.now());
            eleve.setDossierAdministratif(dossier);
        }

        // Sauvegarder l'élève
        Eleve savedEleve = eleveRepository.save(eleve);

        // Générer le numéro d'inscription
        if (savedEleve.getDossierAdministratif().getNumeroInscription() == null) {
            String numero = genererNumeroInscription(savedEleve);
            savedEleve.getDossierAdministratif().setNumeroInscription(numero);
            savedEleve = eleveRepository.save(savedEleve);
        }

        return savedEleve;
    }

    private String genererNumeroInscription(Eleve eleve) {
        String codeFiliere = eleve.getFiliere() != null ?
                eleve.getFiliere().getCode() : "XXX";
        int annee = LocalDate.now().getYear();
        Long id = eleve.getId();

        return String.format("%s-%d-%d", codeFiliere, annee, id);
    }

    public void deleteEleve(Long id) {
        eleveRepository.deleteById(id);
    }
}