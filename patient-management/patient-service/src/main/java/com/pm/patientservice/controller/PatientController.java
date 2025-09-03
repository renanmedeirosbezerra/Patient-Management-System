package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//Essa anotação informa que todos os request vão vir da forma http://localhost:4000/patients
@RequestMapping("/patients")
public class PatientController {
    //Todas as funções do PatientController é dependente do PatientService
    private final PatientService patientService;

    //Injeção do Service no Controller
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    //Método GET que recebe a lista de pacientes, insere no corpo da resposta e envia uma mensagem de sucesso 200
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    //Método que recebe um DTO, valida os atributos ou retorna erros, e transforma o json request em um DTO request
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);

        return ResponseEntity.ok().body(patientResponseDTO);
    }
}
