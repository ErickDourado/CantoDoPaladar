package com.canto.paladar.controller;

import com.canto.paladar.dto.endereco.EnderecoRequest;
import com.canto.paladar.dto.endereco.EnderecoResponse;
import com.canto.paladar.service.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api/enderecos")
@RestController
public class EnderecoController {

    private final EnderecoService enderecoService;

    @PostMapping("/{pessoaId}")
    public ResponseEntity<EnderecoResponse> save(@PathVariable("pessoaId") final Long pessoaId,
                                                 @RequestBody @Valid final EnderecoRequest request) {
        return ResponseEntity.status(CREATED).body(enderecoService.save(request, pessoaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponse> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(enderecoService.findById(id));
    }

    @GetMapping("/pessoa/{pessoaId}")
    public ResponseEntity<List<EnderecoResponse>> findAllByPessoa(@PathVariable("pessoaId") final Long pessoaId) {
        return ResponseEntity.ok(enderecoService.findAllByPessoa(pessoaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponse> update(@PathVariable("id") final Long id,
                                                   @RequestBody @Valid final EnderecoRequest request) {
        return ResponseEntity.ok(enderecoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
