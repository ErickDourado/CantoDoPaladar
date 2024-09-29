package com.canto.paladar.controller;

import com.canto.paladar.dto.pedido.PedidoRequest;
import com.canto.paladar.dto.pedido.PedidoResponse;
import com.canto.paladar.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api/pedidos")
@RestController
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping("/{pedidoId}")
    public ResponseEntity<PedidoResponse> save(@PathVariable final Long pedidoId,
                                               @RequestBody @Valid final PedidoRequest request) {
        return ResponseEntity.status(CREATED).body(pedidoService.save(request, pedidoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> findById(@PathVariable final Long id) {
        return ResponseEntity.ok(pedidoService.findById(id));
    }

    @GetMapping("/pessoa/{pedidoId}")
    public ResponseEntity<List<PedidoResponse>> findAllByPessoa(@PathVariable final Long pedidoId) {
        return ResponseEntity.ok(pedidoService.findAllByPessoa(pedidoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponse> update(@PathVariable final Long id,
                                                 @RequestBody @Valid final PedidoRequest request) {
        return ResponseEntity.ok(pedidoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
