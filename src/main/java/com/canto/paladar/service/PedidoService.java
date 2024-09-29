package com.canto.paladar.service;

import com.canto.paladar.dto.pedido.PedidoRequest;
import com.canto.paladar.dto.pedido.PedidoResponse;
import com.canto.paladar.dto.pessoa.PessoaResponse;
import com.canto.paladar.entity.Pedido;
import com.canto.paladar.entity.Pessoa;
import com.canto.paladar.exception.ResourceNotFoundException;
import com.canto.paladar.mapper.PedidoMapper;
import com.canto.paladar.mapper.PessoaMapper;
import com.canto.paladar.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PessoaService pessoaService;
    private final PedidoMapper pedidoMapper;
    private final PessoaMapper pessoaMapper;

    @Transactional
    public PedidoResponse save(PedidoRequest request, Long pessoaId) {
        Pessoa pessoaEntity = pessoaService.find(pessoaId);
        PessoaResponse pessoaResponse = pessoaMapper.toResponse(pessoaEntity);
        log.info("Salvando pedido: {}, para a pessoa: {}", request, pessoaResponse);

        Pedido pedidoEntity = pedidoMapper.toEntity(request);
        pedidoEntity.setPessoa(pessoaEntity);
        PedidoResponse pedidoResponse = pedidoMapper.toResponse(pedidoRepository.save(pedidoEntity));

        log.info("Pedido salvo com sucesso: {} para a pessoa: {}", pedidoResponse, pessoaResponse);
        return pedidoResponse;
    }

    @Transactional(readOnly = true)
    public PedidoResponse findById(Long id) {
        Pedido entity = find(id);
        return pedidoMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> findAllByPessoa(Long pessoaId) {
        Pessoa pessoaEntity = pessoaService.find(pessoaId);
        log.info("Buscando todos os pedidos da pessoa {}", pessoaMapper.toResponse(pessoaEntity));
        List<Pedido> pedidoEntities = pessoaEntity.getPedidos();
        return pedidoMapper.toResponseList(pedidoEntities);
    }

    @Transactional
    public PedidoResponse update(Long id, PedidoRequest request) {
        Pedido entity = find(id);
        log.info("Atualizando pedido pelo id: {}, dados antigos {}, dados novos: {}", id, pedidoMapper.toResponse(entity), request);
        PedidoResponse response = pedidoMapper.toResponse(pedidoRepository.save(pedidoMapper.toEntity(request, entity)));
        log.info("Pedido atualizado com sucesso: {}", response);
        return response;
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deletando pedido pelo id: {}", id);
        Pedido entity = find(id);
        entity.getPessoa().getPedidos().remove(entity);
        PedidoResponse response = pedidoMapper.toResponse(entity);
        pedidoRepository.deleteById(id);
        log.info("Pedido deletado com sucesso: {}", response);
    }

    public Pedido find(Long id) {
        log.info("Buscando pedido pelo id: {}", id);
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    log.info("Pedido encontrado: {}", pedidoMapper.toResponse(pedido));
                    return pedido;
                })
                .orElseThrow(() -> {
                    log.info("Pedido não encontrado pelo id: {}", id);
                    return new ResourceNotFoundException("Pedido não encontrado pelo id: " + id);
                });
    }

}
