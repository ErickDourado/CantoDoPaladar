package com.canto.paladar.service;

import com.canto.paladar.dto.pessoa.PessoaRequest;
import com.canto.paladar.dto.pessoa.PessoaResponse;
import com.canto.paladar.entity.Pessoa;
import com.canto.paladar.exception.ResourceNotFoundException;
import com.canto.paladar.mapper.PessoaMapper;
import com.canto.paladar.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper mapper;

    public PessoaResponse save(PessoaRequest request) {
        log.info("Salvando pessoa: {}", request);
        verifyIfEmailOrCellPhoneAlreadyExists(request, null);
        Pessoa entity = pessoaRepository.save(mapper.toEntity(request));
        PessoaResponse response = mapper.toResponse(entity);
        log.info("Pessoa salva com sucesso: {}", response);
        return response;
    }

    public PessoaResponse findById(Long id) {
        log.info("Buscando pessoa pelo id: {}", id);
        Pessoa entity = find(id);
        PessoaResponse response = mapper.toResponse(entity);
        log.info("Pessoa encontrada: {}", response);
        return response;
    }

    public List<PessoaResponse> findAll() {
        log.info("Buscando todas as pessoas");
        List<Pessoa> entities = pessoaRepository.findAll();
        return mapper.toResponseList(entities);
    }

    public PessoaResponse update(Long id, PessoaRequest request) {
        verifyIfEmailOrCellPhoneAlreadyExists(request, id);
        Pessoa entity = find(id);
        log.info("Atualizando pessoa pelo id: {}, dados antigos {}, dados novos: {}", id, mapper.toResponse(entity), request);
        PessoaResponse response = mapper.toResponse(pessoaRepository.save(mapper.toEntity(request, entity)));
        log.info("Pessoa atualizada com sucesso: {}", response);
        return response;
    }

    public void delete(Long id) {
        log.info("Deletando pessoa pelo id: {}", id);
        PessoaResponse response = mapper.toResponse(find(id));
        pessoaRepository.deleteById(id);
        log.info("Pessoa deletada com sucesso: {}", response);
    }

    private Pessoa find(Long id) {
        return pessoaRepository.findById(id).orElseThrow(() -> {
            log.info("Pessoa não encontrada pelo id: {}", id);
            return new ResourceNotFoundException("Pessoa não encontrada pelo id: " + id);
        });
    }

    private void verifyIfEmailOrCellPhoneAlreadyExists(PessoaRequest request, Long id) {
        if (Objects.isNull(id))
            log.info("Validando email e celular da pessoa antes de cadastrar os dados.");
        else
            log.info("Validando email e celular da pessoa antes de atualizar os dados.");

        pessoaRepository.findByEmail(request.email())
                .filter(user -> !user.getId().equals(id))
                .ifPresentOrElse(user -> {
                    log.info("Email \"{}\" já existe!", user.getEmail());
                    throw new DataIntegrityViolationException("Email \""+ user.getEmail() +"\" já existe!");
                    }, () -> pessoaRepository.findByCelular(request.celular())
                        .filter(user -> !user.getId().equals(id))
                        .ifPresent(user -> {
                            log.info("Celular \"{}\" já existe!", user.getCelular());
                            throw new DataIntegrityViolationException("Celular \""+ user.getCelular() +"\" já existe!");
                        }));
    }

}
