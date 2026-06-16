package com.higotlino.leilao.business;

import com.higotlino.leilao.entity.Empresa;
import com.higotlino.leilao.entity.Leilao;
import com.higotlino.leilao.repository.EmpresaRepository;
import com.higotlino.leilao.repository.LeilaoRepository;
import com.higotlino.leilao.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeilaoBOTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private LeilaoRepository leilaoRepository;

    @InjectMocks
    private LeilaoBO leilaoBO;

    private Leilao leilao;
    private Empresa vendedor;

    @BeforeEach
    void setUp() {
        vendedor = Empresa.builder()
                .id(1L)
                .razaoSocial("Vendedor Teste")
                .cnpj("12345678000199")
                .email("vendedor@teste.com")
                .usuario("vendedor")
                .password("senha123")
                .build();

        leilao = Leilao.builder()
                .codigo(123456)
                .descricao("Leilao teste")
                .inicioPrevisto(LocalDateTime.now().plusDays(7))
                .build();
    }

    @Test
    @DisplayName("Deve criar leilao com vendedor valido")
    void createShouldSucceedWithValidVendedor() {
        when(empresaRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(leilaoRepository.save(any(Leilao.class))).thenAnswer(inv -> inv.getArgument(0));

        Leilao saved = leilaoBO.create(leilao, 1L);

        assertNotNull(saved);
        assertEquals(vendedor, saved.getVendedor());
        verify(empresaRepository).findById(1L);
        verify(leilaoRepository).save(leilao);
    }

    @Test
    @DisplayName("Deve lancar ResourceNotFoundException quando vendedor nao existe")
    void createShouldThrowWhenVendedorNotFound() {
        when(empresaRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> leilaoBO.create(leilao, 99L)
        );

        assertEquals("Vendedor não encontrado", ex.getMessage());
        verify(empresaRepository).findById(99L);
        verify(leilaoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lancar ResourceNotFoundException ao buscar leilao por id inexistente")
    void getByIdShouldThrowWhenNotFound() {
        when(leilaoRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> leilaoBO.getById(99L)
        );

        assertEquals("Leilao não encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("Deve retornar leilao ao buscar por id existente")
    void getByIdShouldReturnLeilao() {
        Leilao persisted = Leilao.builder()
                .id(1L)
                .codigo(123456)
                .descricao("Leilao teste")
                .vendedor(vendedor)
                .inicioPrevisto(LocalDateTime.now().plusDays(7))
                .build();
        when(leilaoRepository.findById(1L)).thenReturn(Optional.of(persisted));

        Leilao found = leilaoBO.getById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
        assertEquals("Leilao teste", found.getDescricao());
    }
}