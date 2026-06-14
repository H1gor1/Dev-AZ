package com.higotlino.leilao.controller;

import com.higotlino.leilao.dto.ApiResponse;
import com.higotlino.leilao.dto.Leilao.CreateLeilaoRequest;
import com.higotlino.leilao.dto.Leilao.LeilaoMapper;
import com.higotlino.leilao.dto.Leilao.LeilaoResponse;
import com.higotlino.leilao.dto.Leilao.LeilaoUpdateRequest;
import com.higotlino.leilao.entity.Leilao;
import com.higotlino.leilao.service.LeilaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leilao")
public class LeilaoController {
    private final LeilaoService service;
    private final LeilaoMapper mapper;

    public LeilaoController(LeilaoService service, LeilaoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LeilaoResponse>>> getAll(@PageableDefault(size = 10) Pageable pageable){
        Page<LeilaoResponse> leiloes = service.paginate(pageable).map(mapper::toResponse);
        return ResponseEntity.ok(ApiResponse.ok("Leiloes encontrados", leiloes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LeilaoResponse>> getById(@PathVariable Long id){
        Leilao leilao = service.getById(id);
        if(leilao == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ApiResponse<>("OK", "Leilao encontrado", mapper.toResponse(leilao), null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LeilaoResponse>> create(@RequestBody @Validated CreateLeilaoRequest request){
        Leilao leilao = mapper.toEntity(request);
        Leilao saved = service.create(leilao, request.vendedorId());
        return ResponseEntity.ok(new ApiResponse<>("OK", "Leilao criado", mapper.toResponse(saved), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LeilaoResponse>> update(@PathVariable Long id, @RequestBody @Validated LeilaoUpdateRequest request){
        Leilao leilao = service.getById(id);
        if(leilao == null){
            return ResponseEntity.notFound().build();
        }
        mapper.updateEntity(request, leilao);
        Leilao saved = service.update(leilao);
        return ResponseEntity.ok(new ApiResponse<>("OK", "Leilao atualizado", mapper.toResponse(saved), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
