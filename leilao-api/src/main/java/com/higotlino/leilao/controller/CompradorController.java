package com.higotlino.leilao.controller;

import com.higotlino.leilao.dto.ApiResponse;
import com.higotlino.leilao.dto.Comprador.CompradorMapper;
import com.higotlino.leilao.dto.Comprador.CompradorResponse;
import com.higotlino.leilao.dto.Comprador.CreateCompradorRequest;
import com.higotlino.leilao.entity.Comprador;
import com.higotlino.leilao.service.CompradorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comprador")
public class CompradorController {

    private final CompradorService service;
    private final CompradorMapper mapper;

    public CompradorController(CompradorService service, CompradorMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompradorResponse>>> getAll(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<CompradorResponse> page = service.paginate(pageable).map(mapper::toResponse);
        return ResponseEntity.ok(ApiResponse.ok("Compradores encontrados", page));
    }

    @GetMapping("/leilao/{leilaoId}")
    public ResponseEntity<ApiResponse<List<CompradorResponse>>> getByLeilao(
            @PathVariable Long leilaoId) {
        List<CompradorResponse> list = service.getByLeilaoId(leilaoId)
                .stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(new ApiResponse<>("OK", "Compradores encontrados", list, null));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<ApiResponse<List<CompradorResponse>>> getByEmpresa(
            @PathVariable Long empresaId) {
        List<CompradorResponse> list = service.getByEmpresaId(empresaId)
                .stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(new ApiResponse<>("OK", "Compradores encontrados", list, null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CompradorResponse>> create(
            @RequestBody @Validated CreateCompradorRequest request) {
        Comprador saved = service.create(request.leilaoId(), request.empresaId());
        return ResponseEntity.ok(new ApiResponse<>("OK", "Comprador vinculado",
                mapper.toResponse(saved), null));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
            @RequestParam Long empresaId,
            @RequestParam Long leilaoId) {
        service.delete(empresaId, leilaoId);
        return ResponseEntity.noContent().build();
    }
}
