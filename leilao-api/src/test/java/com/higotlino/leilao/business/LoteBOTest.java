package com.higotlino.leilao.business;

import com.higotlino.leilao.entity.Empresa;
import com.higotlino.leilao.entity.Leilao;
import com.higotlino.leilao.entity.Lote;
import com.higotlino.leilao.entity.Unidade;
import com.higotlino.leilao.exception.ResourceNotFoundException;
import com.higotlino.leilao.repository.LeilaoRepository;
import com.higotlino.leilao.repository.LoteRepository;
import com.higotlino.leilao.repository.UnidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class LoteBOTest {

    @Mock
    private LoteRepository loteRepository;

    @Mock
    private UnidadeRepository unidadeRepository;

    @Mock
    private LeilaoRepository leilaoRepository;

    @InjectMocks
    private LoteBO loteBO;

    private Lote lote;
    private Unidade unidade;
    private Leilao leilao;

    @BeforeEach
    void setUp() {
        unidade = new Unidade();
        unidade.setId(1L);
        unidade.setNome("unidade");

        Empresa vendedor = Empresa.builder()
                .id(1L)
                .razaoSocial("Vendedor")
                .cnpj("12345678000199")
                .email("vendedor@teste.com")
                .usuario("vendedor")
                .password("senha123")
                .build();

        leilao = Leilao.builder()
                .id(1L)
                .codigo(1)
                .descricao("Leilao teste")
                .vendedor(vendedor)
                .inicioPrevisto(LocalDateTime.now().plusDays(7))
                .build();

        lote = Lote.builder()
                .numeroLote(1)
                .descricao("Lote teste")
                .quantidade(100.0)
                .valorInicial(50.0)
                .build();
    }

    @Test
    @DisplayName("Deve criar lote com sucesso associando unidade e leilao")
    void createShouldSucceed() {
        when(unidadeRepository.findById(1L)).thenReturn(Optional.of(unidade));
        when(leilaoRepository.findById(1L)).thenReturn(Optional.of(leilao));
        when(loteRepository.save(any(Lote.class))).thenAnswer(inv -> inv.getArgument(0));

        Lote saved = loteBO.create(lote, 1L, 1L);

        assertNotNull(saved);
        assertEquals(unidade, saved.getUnidade());
        assertEquals(leilao, saved.getLeilao());
        verify(unidadeRepository).findById(1L);
        verify(leilaoRepository).findById(1L);
        verify(loteRepository).save(lote);
    }

    @Test
    @DisplayName("Deve lancar ResourceNotFoundException quando unidade nao existe")
    void createShouldThrowWhenUnidadeNotFound() {
        when(unidadeRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> loteBO.create(lote, 1L, 1L)
        );

        assertEquals("Unidade nao encontrada", ex.getMessage());
        verify(unidadeRepository).findById(1L);
        verify(loteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lancar ResourceNotFoundException quando leilao nao existe")
    void createShouldThrowWhenLeilaoNotFound() {
        when(unidadeRepository.findById(1L)).thenReturn(Optional.of(unidade));
        when(leilaoRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> loteBO.create(lote, 1L, 1L)
        );

        assertEquals("Leilao nao encontrado", ex.getMessage());
        verify(unidadeRepository).findById(1L);
        verify(leilaoRepository).findById(1L);
        verify(loteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve retornar lote ao buscar por id existente")
    void getByIdShouldReturnLote() {
        Lote persisted = Lote.builder()
                .id(1L).numeroLote(1).descricao("Lote teste")
                .quantidade(100.0).valorInicial(50.0)
                .unidade(unidade).leilao(leilao)
                .build();
        when(loteRepository.findById(1L)).thenReturn(Optional.of(persisted));

        Lote found = loteBO.getById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
        assertEquals("Lote teste", found.getDescricao());
    }

    @Test
    @DisplayName("Deve lancar ResourceNotFoundException ao buscar lote por id inexistente")
    void getByIdShouldThrowWhenNotFound() {
        when(loteRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> loteBO.getById(99L)
        );

        assertEquals("Lote não encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lancar ResourceNotFoundException ao deletar lote inexistente")
    void deleteShouldThrowWhenNotFound() {
        when(loteRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> loteBO.delete(99L)
        );

        assertEquals("Lote nao encontrado", ex.getMessage());
        verify(loteRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve deletar lote existente com sucesso")
    void deleteShouldSucceed() {
        Lote persisted = Lote.builder()
                .id(1L).numeroLote(1).descricao("Lote teste")
                .quantidade(100.0).valorInicial(50.0)
                .build();
        when(loteRepository.findById(1L)).thenReturn(Optional.of(persisted));

        loteBO.delete(1L);

        verify(loteRepository).findById(1L);
        verify(loteRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve atualizar lote mantendo unidade quando unidadeId e nulo")
    void updateShouldKeepUnidadeWhenNull() {
        Lote existing = Lote.builder()
                .id(1L).numeroLote(1).descricao("Lote original")
                .quantidade(100.0).valorInicial(50.0)
                .unidade(unidade).leilao(leilao)
                .build();
        when(loteRepository.save(any(Lote.class))).thenAnswer(inv -> inv.getArgument(0));

        Lote updated = loteBO.update(existing, null);

        assertNotNull(updated);
        assertEquals(unidade, updated.getUnidade());
        verify(unidadeRepository, never()).findById(any());
        verify(loteRepository).save(existing);
    }

    @Test
    @DisplayName("Deve atualizar lote trocando unidade quando unidadeId informado")
    void updateShouldChangeUnidadeWhenIdProvided() {
        Unidade novaUnidade = new Unidade();
        novaUnidade.setId(2L);
        novaUnidade.setNome("nova");

        Lote existing = Lote.builder()
                .id(1L).numeroLote(1).descricao("Lote original")
                .quantidade(100.0).valorInicial(50.0)
                .unidade(unidade).leilao(leilao)
                .build();
        when(unidadeRepository.findById(2L)).thenReturn(Optional.of(novaUnidade));
        when(loteRepository.save(any(Lote.class))).thenAnswer(inv -> inv.getArgument(0));

        Lote updated = loteBO.update(existing, 2L);

        assertNotNull(updated);
        assertEquals(novaUnidade, updated.getUnidade());
        verify(unidadeRepository).findById(2L);
        verify(loteRepository).save(existing);
    }

    @Test
    @DisplayName("Deve lancar ResourceNotFoundException no update quando unidadeId nao existe")
    void updateShouldThrowWhenUnidadeNotFound() {
        Lote existing = Lote.builder()
                .id(1L).numeroLote(1).descricao("Lote original")
                .quantidade(100.0).valorInicial(50.0)
                .build();
        when(unidadeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> loteBO.update(existing, 99L));

        verify(loteRepository, never()).save(any());
    }
}
