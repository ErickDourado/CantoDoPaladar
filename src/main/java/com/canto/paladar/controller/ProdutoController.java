package com.canto.paladar.controller;

import com.canto.paladar.dto.produto.ProdutoRequest;
import com.canto.paladar.dto.produto.ProdutoResponse;
import com.canto.paladar.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api/produtos")
@RestController
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponse> save(@RequestBody @Valid final ProdutoRequest request) {
        return ResponseEntity.status(CREATED).body(produtoService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable final Long id) {
        return ResponseEntity.ok(produtoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> findAll() {
        return ResponseEntity.ok(produtoService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> update(@PathVariable final Long id,
                                                  @RequestBody @Valid final ProdutoRequest request) {
        return ResponseEntity.ok(produtoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
