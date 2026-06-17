package com.higotlino.leilao.service;

import com.higotlino.leilao.dto.ApiResponse;
import com.higotlino.leilao.dto.Leilao.CreateLeilaoRequest;
import com.higotlino.leilao.dto.Leilao.LeilaoMapper;
import com.higotlino.leilao.dto.Leilao.LeilaoResponse;
import com.higotlino.leilao.dto.Leilao.LeilaoUpdateRequest;
import com.higotlino.leilao.entity.Leilao;
import com.higotlino.leilao.business.LeilaoBO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/leilao")
public class LeilaoService {
    private final LeilaoBO leilaoBO;
    private final LeilaoMapper mapper;

    public LeilaoService(LeilaoBO leilaoBO, LeilaoMapper mapper) {
        this.leilaoBO = leilaoBO;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LeilaoResponse>>> getAll(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) Integer codigo,
            @RequestParam(required = false) String descricao
    ){
        Page<LeilaoResponse> leiloes = leilaoBO.paginate(codigo, descricao, pageable).map(mapper::toResponse);
        return ResponseEntity.ok(ApiResponse.ok("Leiloes encontrados", leiloes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LeilaoResponse>> getById(@PathVariable Long id){
        Leilao leilao = leilaoBO.getById(id);
        return ResponseEntity.ok(ApiResponse.ok("Leilao encontrado", mapper.toResponse(leilao)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LeilaoResponse>> create(@RequestBody @Validated CreateLeilaoRequest request){
        Leilao leilao = mapper.toEntity(request);
        Leilao saved = leilaoBO.create(leilao, request.getVendedorId());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(ApiResponse.ok("Leilao criado", mapper.toResponse(saved)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LeilaoResponse>> update(@PathVariable Long id, @RequestBody @Validated LeilaoUpdateRequest request){
        Leilao leilao = leilaoBO.getById(id);
        mapper.updateEntity(request, leilao);
        Leilao saved = leilaoBO.update(leilao);
        return ResponseEntity.ok(ApiResponse.ok("Leilao atualizado", mapper.toResponse(saved)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        leilaoBO.delete(id);
        return ResponseEntity.noContent().build();
    }


}
