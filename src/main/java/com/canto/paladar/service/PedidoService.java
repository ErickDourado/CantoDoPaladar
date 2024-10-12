package com.canto.paladar.service;

import com.canto.paladar.dto.pedido.PedidoRequest;
import com.canto.paladar.dto.pedido.PedidoResponse;
import com.canto.paladar.dto.pessoa.PessoaResponse;
import com.canto.paladar.dto.produto.ProdutoResponse;
import com.canto.paladar.entity.Endereco;
import com.canto.paladar.entity.Pedido;
import com.canto.paladar.entity.Pessoa;
import com.canto.paladar.entity.Produto;
import com.canto.paladar.exception.ResourceNotFoundException;
import com.canto.paladar.mapper.PedidoMapper;
import com.canto.paladar.mapper.PessoaMapper;
import com.canto.paladar.mapper.ProdutoMapper;
import com.canto.paladar.repository.PedidoRepository;
import com.canto.paladar.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    private final PessoaService pessoaService;
    private final EnderecoService enderecoService;

    private final PedidoMapper pedidoMapper;
    private final PessoaMapper pessoaMapper;
    private final ProdutoMapper produtoMapper;

    @Transactional
    public PedidoResponse save(PedidoRequest request, Long pessoaId) {
        checkTotalSum(request);

        Set<Produto> produtosEntity = Set.copyOf(produtoRepository.findAllById(request.produtosId()));
        Set<ProdutoResponse> produtosResponse = produtoMapper.toResponseSet(produtosEntity);

        checkIfAnyProductNotFound(request, produtosResponse);
        checkSubtotalSum(request, produtosResponse);

        Pessoa pessoaEntity = pessoaService.find(pessoaId);
        PessoaResponse pessoaResponse = pessoaMapper.toResponse(pessoaEntity);

        Endereco enderecoEntity = enderecoService.find(request.enderecoId());

        checkAddress(pessoaEntity, enderecoEntity);

        log.info("Salvando pedido: {}, pessoa: {}", request, pessoaResponse);

        Pedido pedidoEntity = pedidoMapper.toEntity(request)
                .withPessoa(pessoaEntity)
                .withEndereco(enderecoEntity)
                .withProdutos(produtosEntity);

        produtosEntity.forEach(produto -> produto.getPedidos().add(pedidoEntity));

        PedidoResponse pedidoResponse = pedidoMapper.toResponse(pedidoRepository.save(pedidoEntity));

        log.info("Pedido salvo com sucesso: {}, pessoa: {}", pedidoResponse, pessoaResponse);
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

    // TODO: Refatorar, pois ainda não suporta o update de endereço e produtos
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
        entity.getProdutos().forEach(produto -> produto.getPedidos().remove(entity));
        pedidoRepository.deleteById(id);
        log.info("Pedido deletado com sucesso: {}", pedidoMapper.toResponse(entity));
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

    private void checkTotalSum(PedidoRequest request) {
        Double totalSoma = request.subtotal() + request.frete();
        if (!totalSoma.equals(request.total())) {
            log.info("Erro ao salvar pedido: {}. O valor total do pedido deve ser a soma do subtotal com o frete. Total {}, soma {}", request, request.total(), totalSoma);
            throw new DataIntegrityViolationException("O valor total do pedido deve ser a soma do subtotal com o frete. Total " + request.total() + ", soma " + totalSoma);
        }
    }

    private void checkIfAnyProductNotFound(PedidoRequest request, Set<ProdutoResponse> produtosResponse) {
        List<Long> produtosNaoEncontrados = request.produtosId().stream()
                .filter(id -> !produtosResponse.stream()
                        .map(ProdutoResponse::id)
                        .toList()
                        .contains(id))
                .toList();
        if (!produtosNaoEncontrados.isEmpty()) {
            log.info("Erro ao salvar pedido: {}. Produto(s) {} não encontrado(s).", request, produtosNaoEncontrados);
            throw new ResourceNotFoundException("Erro ao salvar pedido, produto(s) não encontrado(s): " + produtosNaoEncontrados);
        }
    }

    private void checkSubtotalSum(PedidoRequest request, Set<ProdutoResponse> produtosResponse) {
        Double subtotalSoma = produtosResponse.stream()
                .mapToDouble(ProdutoResponse::preco)
                .sum();
        if (!request.subtotal().equals(subtotalSoma)) {
            log.info("Erro ao salvar pedido: {}. O valor do subtotal está incorreto. Subtotal {}, soma {}", request, request.subtotal(), subtotalSoma);
            throw new DataIntegrityViolationException("O valor do subtotal está incorreto. Subtotal " + request.subtotal() + ", soma " + subtotalSoma);
        }
    }

    private void checkAddress(Pessoa pessoaEntity, Endereco enderecoEntity) {
        if (!pessoaEntity.getEnderecos().contains(enderecoEntity)) {
            log.info("Erro ao salvar pedido. O endereço {} não pertence a pessoa {}", pessoaEntity, enderecoEntity);
            throw new DataIntegrityViolationException("O endereço informado não pertence a pessoa");
        }
    }

}
