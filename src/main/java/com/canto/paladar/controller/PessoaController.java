package com.canto.paladar.controller;

import com.canto.paladar.dto.pessoa.PessoaRequest;
import com.canto.paladar.dto.pessoa.PessoaResponse;
import com.canto.paladar.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api/pessoas")
@RestController
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<PessoaResponse> save(@RequestBody @Valid final PessoaRequest request) {
        return ResponseEntity.status(CREATED).body(pessoaService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> findById(@PathVariable final Long id) {
        return ResponseEntity.ok(pessoaService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponse>> findAll() {
        return ResponseEntity.ok(pessoaService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponse> update(@PathVariable final Long id,
                                                 @RequestBody @Valid final PessoaRequest request) {
        return ResponseEntity.ok(pessoaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
