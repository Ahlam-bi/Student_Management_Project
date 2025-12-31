package com.example.thymeleaf_project.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DossierAdministratif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroInscription;

    @Column(nullable = false)
    private LocalDate dateCreation;

    @OneToOne(mappedBy = "dossierAdministratif")
    private Eleve eleve;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDate.now();

        if (numeroInscription == null || numeroInscription.isEmpty()) {
            numeroInscription = "INS-" + System.currentTimeMillis();
        }
    }
}