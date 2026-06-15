package com.higotlino.leilao.service;

import com.higotlino.leilao.business.CompradorBO;
import com.higotlino.leilao.dto.ApiResponse;
import com.higotlino.leilao.dto.Comprador.CompradorMapper;
import com.higotlino.leilao.dto.Comprador.CompradorResponse;
import com.higotlino.leilao.dto.Comprador.CreateCompradorRequest;
import com.higotlino.leilao.entity.Comprador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comprador")
public class CompradorService {

    private final CompradorBO compradorBO;
    private final CompradorMapper mapper;

    public CompradorService(CompradorBO compradorBO, CompradorMapper mapper) {
        this.compradorBO = compradorBO;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompradorResponse>>> getAll(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<CompradorResponse> page = compradorBO.paginate(pageable).map(mapper::toResponse);
        return ResponseEntity.ok(ApiResponse.ok("Compradores encontrados", page));
    }

    @GetMapping("/leilao/{leilaoId}")
    public ResponseEntity<ApiResponse<List<CompradorResponse>>> getByLeilao(
            @PathVariable Long leilaoId) {
        List<CompradorResponse> list = compradorBO.getByLeilaoId(leilaoId)
                .stream().map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok("Compradores encontrados", list));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<ApiResponse<List<CompradorResponse>>> getByEmpresa(
            @PathVariable Long empresaId) {
        List<CompradorResponse> list = compradorBO.getByEmpresaId(empresaId)
                .stream().map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok("Compradores encontrados", list));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CompradorResponse>> create(
            @RequestBody @Validated CreateCompradorRequest request) {
        Comprador saved = compradorBO.create(request.getLeilaoId(), request.getEmpresaId());
        return ResponseEntity.ok(ApiResponse.ok("Comprador vinculado",
                mapper.toResponse(saved)));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
            @RequestParam Long empresaId,
            @RequestParam Long leilaoId) {
        compradorBO.delete(empresaId, leilaoId);
        return ResponseEntity.noContent().build();
    }
}
