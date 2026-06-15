package com.higotlino.leilao.controller;

import com.higotlino.leilao.dto.ApiResponse;
import com.higotlino.leilao.dto.Lote.CreateLoteRequest;
import com.higotlino.leilao.dto.Lote.LoteMapper;
import com.higotlino.leilao.dto.Lote.LoteResponse;
import com.higotlino.leilao.dto.Lote.UpdateLoteRequest;
import com.higotlino.leilao.entity.Lote;
import com.higotlino.leilao.service.LoteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lote")
public class LoteController {

    private final LoteService service;
    private final LoteMapper mapper;

    public LoteController(LoteService service, LoteMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LoteResponse>>> getAll(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<LoteResponse> page = service.paginate(pageable).map(mapper::toResponse);
        return ResponseEntity.ok(ApiResponse.ok("Lotes encontrados", page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LoteResponse>> getById(@PathVariable Long id) {
        Lote lote = service.getById(id);
        return ResponseEntity.ok(ApiResponse.ok("Lote encontrado",
                mapper.toResponse(lote)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LoteResponse>> create(
            @RequestBody @Validated CreateLoteRequest request) {
        Lote lote = mapper.toEntity(request);
        Lote saved = service.create(lote, request.getUnidadeId(), request.getLeilaoId());
        return ResponseEntity.ok(ApiResponse.ok("Lote criado",
                mapper.toResponse(saved)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LoteResponse>> update(
            @PathVariable Long id,
            @RequestBody @Validated UpdateLoteRequest request) {
        Lote lote = service.getById(id);
        mapper.updateEntity(request, lote);
        Lote saved = service.update(lote, request.getUnidadeId());
        return ResponseEntity.ok(ApiResponse.ok("Lote atualizado",
                mapper.toResponse(saved)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
