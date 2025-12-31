package com.example.thymeleaf_project.repositories;

import com.example.thymeleaf_project.entities.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EleveRepository extends JpaRepository<Eleve, Long> {
    List<Eleve> findByFiliereId(Long filiereId);
}