package com.higotlino.leilao.service;

import com.higotlino.leilao.business.EmpresaBO;
import com.higotlino.leilao.dto.ApiResponse;
import com.higotlino.leilao.dto.Empresa.EmpresaMapper;
import com.higotlino.leilao.dto.Empresa.EmpresaRequest;
import com.higotlino.leilao.dto.Empresa.EmpresaResponse;
import com.higotlino.leilao.dto.Empresa.EmpresaUpdateRequest;
import com.higotlino.leilao.entity.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaService {

    private final EmpresaBO empresaBO;
    private final EmpresaMapper mapper;

    public EmpresaService(EmpresaBO empresaBO, EmpresaMapper mapper) {
        this.empresaBO = empresaBO;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmpresaResponse>>> getAll(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String razaoSocial,
            @RequestParam(required = false) String cnpj,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String usuario) {

        Page<EmpresaResponse> empresas = empresaBO
                .paginate(razaoSocial, cnpj, email, usuario, pageable)
                .map(mapper::toResponse);
        return ResponseEntity.ok(ApiResponse.ok("Empresas encontradas", empresas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpresaResponse>> getById(@PathVariable Long id){
        Empresa empresa = empresaBO.getById(id);
        return ResponseEntity.ok(ApiResponse.ok("Empresa encontrada", mapper.toResponse(empresa)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmpresaResponse>> create(@RequestBody @Validated EmpresaRequest request){
        Empresa empresa = mapper.toEntity(request);
        Empresa saved = empresaBO.create(empresa);
        return ResponseEntity.ok(ApiResponse.ok("Empresa criada", mapper.toResponse(saved)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpresaResponse>> update(@PathVariable Long id, @RequestBody @Validated EmpresaUpdateRequest request){
        Empresa empresa = empresaBO.getById(id);
        mapper.updateEntity(request, empresa);
        Empresa saved = empresaBO.update(empresa);
        return ResponseEntity.ok(ApiResponse.ok("Empresa atualizada", mapper.toResponse(saved)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        empresaBO.delete(id);
        return ResponseEntity.noContent().build();
    }
}
