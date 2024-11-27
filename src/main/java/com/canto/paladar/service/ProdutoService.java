package com.canto.paladar.service;

import com.canto.paladar.dto.produto.ProdutoRequest;
import com.canto.paladar.dto.produto.ProdutoResponse;
import com.canto.paladar.entity.Produto;
import com.canto.paladar.exception.ResourceNotFoundException;
import com.canto.paladar.mapper.ProdutoMapper;
import com.canto.paladar.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper mapper;

    public ProdutoResponse save(ProdutoRequest request) {
        log.info("Salvando produto: {}", request);
        verifyIfNameAlreadyExists(request, null);
        Produto entity = produtoRepository.save(mapper.toEntity(request));
        ProdutoResponse response = mapper.toResponse(entity);
        log.info("Produto salvo com sucesso: {}", response);
        return response;
    }

    public ProdutoResponse findById(Long id) {
        log.info("Buscando produto pelo id: {}", id);
        Produto entity = find(id);
        ProdutoResponse response = mapper.toResponse(entity);
        log.info("Produto encontrado: {}", response);
        return response;
    }

    public List<ProdutoResponse> findAll() {
        log.info("Buscando todos os produtos");
        List<Produto> entities = produtoRepository.findAll();
        return mapper.toResponseList(entities);
    }

    public ProdutoResponse update(Long id, ProdutoRequest request) {
        verifyIfNameAlreadyExists(request, id);
        Produto entity = find(id);
        log.info("Atualizando produto pelo id: {}, dados antigos {}, dados novos: {}", id, mapper.toResponse(entity), request);
        ProdutoResponse response = mapper.toResponse(produtoRepository.save(mapper.toEntity(request, entity)));
        log.info("Produto atualizado com sucesso: {}", response);
        return response;
    }

    public void delete(Long id) {
        log.info("Deletando produto pelo id: {}", id);
        ProdutoResponse response = mapper.toResponse(find(id));
        produtoRepository.deleteById(id);
        log.info("Produto deletado com sucesso: {}", response);
    }

    private Produto find(Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> {
            log.info("Produto não encontrado pelo id: {}", id);
            return new ResourceNotFoundException("Produto não encontrado pelo id: " + id);
        });
    }

    private void verifyIfNameAlreadyExists(ProdutoRequest request, Long id) {
        if (Objects.isNull(id))
            log.info("Validando nome do produto antes de cadastrar os dados.");
        else
            log.info("Validando nome do produto antes de atualizar os dados.");

        produtoRepository.findByNome(request.nome())
                .filter(produto -> !produto.getId().equals(id))
                .ifPresent(produto -> {
                    log.info("Nome \"{}\" já existe!", produto.getNome());
                    throw new DataIntegrityViolationException("Nome \""+ produto.getNome() +"\" já existe!");
                });
    }

}
