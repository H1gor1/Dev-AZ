package com.higotlino.leilao.controller;

import com.higotlino.leilao.dto.ApiResponse;
import com.higotlino.leilao.dto.Unidade.CreateUnidadeRequest;
import com.higotlino.leilao.dto.Unidade.UnidadeMapper;
import com.higotlino.leilao.dto.Unidade.UnidadeResponse;
import com.higotlino.leilao.dto.Unidade.UpdateUnidadeRequest;
import com.higotlino.leilao.entity.Unidade;
import com.higotlino.leilao.service.UnidadeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidade")
public class UnidadeController {

    private final UnidadeService service;
    private final UnidadeMapper mapper;

    public UnidadeController(UnidadeService service, UnidadeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UnidadeResponse>>> getAll(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<UnidadeResponse> page = service.paginate(pageable).map(mapper::toResponse);
        return ResponseEntity.ok(ApiResponse.ok("Unidades encontradas", page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UnidadeResponse>> getById(@PathVariable Long id) {
        Unidade unidade = service.getById(id);
        return ResponseEntity.ok(ApiResponse.ok("Unidade encontrada",
                mapper.toResponse(unidade)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UnidadeResponse>> create(
            @RequestBody @Validated CreateUnidadeRequest request) {
        Unidade unidade = mapper.toEntity(request);
        Unidade saved = service.create(unidade);
        return ResponseEntity.ok(ApiResponse.ok("Unidade criada",
                mapper.toResponse(saved)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UnidadeResponse>> update(
            @PathVariable Long id,
            @RequestBody @Validated UpdateUnidadeRequest request) {
        Unidade unidade = service.getById(id);
        mapper.updateEntity(request, unidade);
        Unidade saved = service.update(unidade);
        return ResponseEntity.ok(ApiResponse.ok("Unidade atualizada",
                mapper.toResponse(saved)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
