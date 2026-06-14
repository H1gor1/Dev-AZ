package com.higotlino.leilao.controller;

import com.higotlino.leilao.dto.ApiResponse;
import com.higotlino.leilao.dto.Empresa.EmpresaMapper;
import com.higotlino.leilao.dto.Empresa.EmpresaRequest;
import com.higotlino.leilao.dto.Empresa.EmpresaResponse;
import com.higotlino.leilao.dto.Empresa.EmpresaUpdateRequest;
import com.higotlino.leilao.entity.Empresa;
import com.higotlino.leilao.service.EmpresaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    private final EmpresaService service;
    private final EmpresaMapper mapper;

    public EmpresaController(EmpresaService service, EmpresaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmpresaResponse>>> getAll(@PageableDefault(size = 10) Pageable pageable){
        Page<EmpresaResponse> empresas = service.paginate(pageable).map(mapper::toResponse);
        return ResponseEntity.ok(ApiResponse.ok( "Empresas encontradas", empresas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpresaResponse>> getById(@PathVariable Long id){
        Empresa empresa = service.getById(id);
        if(empresa == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ApiResponse<>("OK", "Empresa encontrada", mapper.toResponse(empresa), null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmpresaResponse>> create(@RequestBody @Validated EmpresaRequest request){
        Empresa empresa = mapper.toEntity(request);
        Empresa saved = service.create(empresa);
        return ResponseEntity.ok(new ApiResponse<>("OK", "Empresa criada", mapper.toResponse(saved), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpresaResponse>> update(@PathVariable Long id, @RequestBody @Validated EmpresaUpdateRequest request){
        Empresa empresa = service.getById(id);
        if(empresa == null){
            return ResponseEntity.notFound().build();
        }
        mapper.updateEntity(request, empresa);
        Empresa saved = service.update(empresa);
        return ResponseEntity.ok(new ApiResponse<>("OK", "Empresa atualizada", mapper.toResponse(saved), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
