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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper mapper;

    @Transactional
    public PessoaResponse save(PessoaRequest request) {
        log.info("Salvando pessoa: {}", request);
        verifyIfEmailOrCellPhoneAlreadyExists(request, null);
        Pessoa entity = pessoaRepository.save(mapper.toEntity(request));
        PessoaResponse response = mapper.toResponse(entity);
        log.info("Pessoa salva com sucesso: {}", response);
        return response;
    }

    @Transactional(readOnly = true)
    public PessoaResponse findById(Long id) {
        Pessoa entity = find(id);
        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<PessoaResponse> findAll() {
        log.info("Buscando todas as pessoas");
        List<Pessoa> entities = pessoaRepository.findAll();
        return mapper.toResponseList(entities);
    }

    @Transactional
    public PessoaResponse update(Long id, PessoaRequest request) {
        verifyIfEmailOrCellPhoneAlreadyExists(request, id);
        Pessoa entity = find(id);
        log.info("Atualizando pessoa pelo id: {}, dados antigos {}, dados novos: {}", id, mapper.toResponse(entity), request);
        PessoaResponse response = mapper.toResponse(pessoaRepository.save(mapper.toEntity(request, entity)));
        log.info("Pessoa atualizada com sucesso: {}", response);
        return response;
    }

    @Transactional
    public void delete(Long id) {
        PessoaResponse response = mapper.toResponse(find(id));
        log.info("Deletando pessoa pelo id: {}", id);
        pessoaRepository.deleteById(id);
        log.info("Pessoa deletada com sucesso: {}", response);
    }

    public Pessoa find(Long id) {
        log.info("Buscando pessoa pelo id: {}", id);
        return pessoaRepository.findById(id)
                .map(pessoa -> {
                    log.info("Pessoa encontrada: {}", mapper.toResponse(pessoa));
                    return pessoa;
                })
                .orElseThrow(() -> {
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
