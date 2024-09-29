package com.canto.paladar.service;

import com.canto.paladar.dto.endereco.EnderecoRequest;
import com.canto.paladar.dto.endereco.EnderecoResponse;
import com.canto.paladar.dto.pessoa.PessoaResponse;
import com.canto.paladar.entity.Endereco;
import com.canto.paladar.entity.Pessoa;
import com.canto.paladar.exception.ResourceNotFoundException;
import com.canto.paladar.mapper.EnderecoMapper;
import com.canto.paladar.mapper.PessoaMapper;
import com.canto.paladar.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final PessoaService pessoaService;
    private final EnderecoMapper enderecoMapper;
    private final PessoaMapper pessoaMapper;

    @Transactional
    public EnderecoResponse save(EnderecoRequest request, Long pessoaId) {
        Pessoa pessoaEntity = pessoaService.find(pessoaId);
        PessoaResponse pessoaResponse = pessoaMapper.toResponse(pessoaEntity);
        log.info("Salvando endereco: {}, para a pessoa: {}", request, pessoaResponse);

        Endereco enderecoEntity = enderecoMapper.toEntity(request);
        enderecoEntity.setPessoa(pessoaEntity);
        EnderecoResponse enderecoResponse = enderecoMapper.toResponse(enderecoRepository.save(enderecoEntity));

        log.info("Endereco salvo com sucesso: {} para a pessoa: {}", enderecoResponse, pessoaResponse);
        return enderecoResponse;
    }

    @Transactional(readOnly = true)
    public EnderecoResponse findById(Long id) {
        Endereco entity = find(id);
        return enderecoMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<EnderecoResponse> findAllByPessoa(Long pessoaId) {
        Pessoa pessoaEntity = pessoaService.find(pessoaId);
        log.info("Buscando todos os endereços da pessoa {}", pessoaMapper.toResponse(pessoaEntity));
        List<Endereco> enderecoEntities = pessoaEntity.getEnderecos();
        return enderecoMapper.toResponseSet(enderecoEntities);
    }

    @Transactional
    public EnderecoResponse update(Long id, EnderecoRequest request) {
        Endereco entity = find(id);
        log.info("Atualizando endereço pelo id: {}, dados antigos {}, dados novos: {}", id, enderecoMapper.toResponse(entity), request);
        EnderecoResponse response = enderecoMapper.toResponse(enderecoRepository.save(enderecoMapper.toEntity(request, entity)));
        log.info("Endereço atualizado com sucesso: {}", response);
        return response;
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deletando endereço pelo id: {}", id);
        Endereco entity = find(id);
        entity.getPessoa().getEnderecos().remove(entity);
        EnderecoResponse response = enderecoMapper.toResponse(entity);
        enderecoRepository.deleteById(id);
        log.info("Endereço deletado com sucesso: {}", response);
    }

    public Endereco find(Long id) {
        log.info("Buscando endereço pelo id: {}", id);
        return enderecoRepository.findById(id)
                .map(endereco -> {
                    log.info("Endereço encontrado: {}", enderecoMapper.toResponse(endereco));
                    return endereco;
                })
                .orElseThrow(() -> {
                    log.info("Endereço não encontrado pelo id: {}", id);
                    return new ResourceNotFoundException("Endereço não encontrado pelo id: " + id);
                });
    }

}
