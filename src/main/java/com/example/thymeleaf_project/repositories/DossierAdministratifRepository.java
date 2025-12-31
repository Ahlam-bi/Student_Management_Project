package com.example.thymeleaf_project.repositories;

import com.example.thymeleaf_project.entities.DossierAdministratif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DossierAdministratifRepository extends JpaRepository<DossierAdministratif, Long> {
}