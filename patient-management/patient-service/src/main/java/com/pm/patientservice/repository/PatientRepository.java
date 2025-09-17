package com.pm.patientservice.repository;

import com.pm.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    //retorna true ou false se já existe um email cadastrado no DB
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID id);
}
