package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    //Injeção do repository no service
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    //Retorna todos os cadastros dos pacientes do DB pelo DTO, formatando pelo mapper
    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    /*
    Adiciona um novo patient ao DB, realiza as conversões de DTO no entity, salva o novo cadastro
    e converte novamente a entity em DB
    */
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        //Realiza a validação se já há um email cadastrado no DB igual à requisição do cadastro
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "A patient with this email already exist " + patientRequestDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toDTO(newPatient);
    }

    //Realiza um update em um paciente pelo Id, ou retorna um erro caso não encontrado
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with id: " + id));

        //Realiza a validação se já há um email cadastrado no DB igual à requisição do update
        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException(
                    "A patient with this email already exist " + patientRequestDTO.getEmail());
        }

        //Setta os dados do request no Entity
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    //Serviço que chama a função do Repository para delatar um paciente pelo ID
    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
