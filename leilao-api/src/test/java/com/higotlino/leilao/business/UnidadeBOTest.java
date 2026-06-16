package com.higotlino.leilao.business;

import com.higotlino.leilao.entity.Lote;
import com.higotlino.leilao.entity.Unidade;
import com.higotlino.leilao.exception.BusinessRuleException;
import com.higotlino.leilao.exception.ResourceNotFoundException;
import com.higotlino.leilao.repository.UnidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UnidadeBOTest {

    @Mock
    private UnidadeRepository unidadeRepository;

    @InjectMocks
    private UnidadeBO unidadeBO;

    private Unidade unidade;

    @BeforeEach
    void setUp() {
        unidade = new Unidade();
        unidade.setNome("unidade");
    }

    @Test
    @DisplayName("Deve criar unidade com sucesso")
    void createShouldSucceed() {
        when(unidadeRepository.save(any(Unidade.class))).thenAnswer(inv -> inv.getArgument(0));

        Unidade saved = unidadeBO.create(unidade);

        assertNotNull(saved);
        assertEquals("unidade", saved.getNome());
        verify(unidadeRepository).save(unidade);
    }

    @Test
    @DisplayName("Deve atualizar unidade com sucesso")
    void updateShouldSucceed() {
        when(unidadeRepository.save(any(Unidade.class))).thenAnswer(inv -> inv.getArgument(0));

        unidade.setId(1L);
        Unidade updated = unidadeBO.update(unidade);

        assertNotNull(updated);
        assertEquals(1L, updated.getId());
        verify(unidadeRepository).save(unidade);
    }

    @Test
    @DisplayName("Deve retornar unidade ao buscar por id existente")
    void getByIdShouldReturnUnidade() {
        Unidade persisted = new Unidade();
        persisted.setId(1L);
        persisted.setNome("unidade");
        when(unidadeRepository.findById(1L)).thenReturn(Optional.of(persisted));

        Unidade found = unidadeBO.getById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
        assertEquals("unidade", found.getNome());
    }

    @Test
    @DisplayName("Deve lancar ResourceNotFoundException ao buscar unidade por id inexistente")
    void getByIdShouldThrowWhenNotFound() {
        when(unidadeRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> unidadeBO.getById(99L)
        );

        assertEquals("Unidade não encontrada", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lancar ResourceNotFoundException ao deletar unidade inexistente")
    void deleteShouldThrowWhenNotFound() {
        when(unidadeRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> unidadeBO.delete(99L)
        );

        assertEquals("Unidade não encontrada", ex.getMessage());
        verify(unidadeRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve lancar BusinessRuleException ao deletar unidade com lotes vinculados")
    void deleteShouldThrowWhenHasLotes() {
        Unidade persisted = new Unidade();
        persisted.setId(1L);
        persisted.setNome("unidade");
        persisted.setLotes(List.of(new Lote())); // tem lotes vinculados
        when(unidadeRepository.findById(1L)).thenReturn(Optional.of(persisted));

        BusinessRuleException ex = assertThrows(
                BusinessRuleException.class,
                () -> unidadeBO.delete(1L)
        );

        assertEquals("Não é possível excluir a unidade pois há lotes vinculados a ela", ex.getMessage());
        verify(unidadeRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve deletar unidade sem lotes com sucesso")
    void deleteShouldSucceedWhenNoLotes() {
        Unidade persisted = new Unidade();
        persisted.setId(1L);
        persisted.setNome("unidade");
        persisted.setLotes(List.of()); // lista vazia
        when(unidadeRepository.findById(1L)).thenReturn(Optional.of(persisted));

        unidadeBO.delete(1L);

        verify(unidadeRepository).findById(1L);
        verify(unidadeRepository).delete(persisted);
    }
}
