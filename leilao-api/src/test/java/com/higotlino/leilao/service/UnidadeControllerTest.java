package com.higotlino.leilao.service;

import com.higotlino.leilao.business.UnidadeBO;
import com.higotlino.leilao.dto.Unidade.CreateUnidadeRequest;
import com.higotlino.leilao.dto.Unidade.UnidadeMapper;
import com.higotlino.leilao.dto.Unidade.UnidadeResponse;
import com.higotlino.leilao.entity.Unidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UnidadeService.class)
class UnidadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UnidadeBO unidadeBO;

    @MockBean
    private UnidadeMapper mapper;

    @Test
    @DisplayName("POST /unidades com nome vazio deve retornar 400")
    void createWithEmptyNomeShouldReturn400() throws Exception {
        String json = "{\"nome\": \"\"}";

        mockMvc.perform(post("/unidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Erro de validacao"))
                .andExpect(jsonPath("$.details.nome").isNotEmpty());

        verify(unidadeBO, never()).create(any());
    }

    @Test
    @DisplayName("POST /unidades com nome valido deve retornar 201 e criar unidade")
    void createWithValidNomeShouldReturn200() throws Exception {
        Unidade entity = new Unidade();
        entity.setId(1L);
        entity.setNome("Nova unidade");

        UnidadeResponse response = new UnidadeResponse();
        response.setId(1L);
        response.setNome("Nova unidade");

        when(mapper.toEntity(any(CreateUnidadeRequest.class))).thenReturn(entity);
        when(unidadeBO.create(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        String json = "{\"nome\": \"Nova unidade\"}";

        mockMvc.perform(post("/unidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Unidade criada"))
                .andExpect(jsonPath("$.data.nome").value("Nova unidade"));

        verify(unidadeBO).create(entity);
    }
}
