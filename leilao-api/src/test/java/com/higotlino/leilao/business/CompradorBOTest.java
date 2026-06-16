package com.higotlino.leilao.business;

import com.higotlino.leilao.entity.Comprador;
import com.higotlino.leilao.entity.Empresa;
import com.higotlino.leilao.entity.Leilao;
import com.higotlino.leilao.exception.ResourceNotFoundException;
import com.higotlino.leilao.repository.CompradorRepository;
import com.higotlino.leilao.repository.EmpresaRepository;
import com.higotlino.leilao.repository.LeilaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CompradorBOTest {

    @Mock
    private CompradorRepository compradorRepository;

    @Mock
    private LeilaoRepository leilaoRepository;

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private CompradorBO compradorBO;

    private Empresa empresa;
    private Leilao leilao;

    @BeforeEach
    void setUp() {
        empresa = Empresa.builder()
                .id(1L)
                .razaoSocial("Comprador Teste")
                .cnpj("12345678000199")
                .email("comprador@teste.com")
                .usuario("comprador")
                .password("senha123")
                .build();

        Empresa vendedor = Empresa.builder()
                .id(2L)
                .razaoSocial("Vendedor")
                .cnpj("99999999000199")
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
    }

    @Test
    @DisplayName("Deve criar vinculo comprador-leilao com sucesso")
    void createShouldSucceed() {
        when(leilaoRepository.findById(1L)).thenReturn(Optional.of(leilao));
        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));
        when(compradorRepository.save(any(Comprador.class))).thenAnswer(inv -> inv.getArgument(0));

        Comprador saved = compradorBO.create(1L, 1L);

        assertNotNull(saved);
        assertEquals(leilao, saved.getLeilao());
        assertEquals(empresa, saved.getEmpresa());
        verify(leilaoRepository).findById(1L);
        verify(empresaRepository).findById(1L);
        verify(compradorRepository).save(any(Comprador.class));
    }

    @Test
    @DisplayName("Deve lancar ResourceNotFoundException quando leilao nao existe")
    void createShouldThrowWhenLeilaoNotFound() {
        when(leilaoRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> compradorBO.create(99L, 1L)
        );

        assertEquals("Leilao nao encontrado", ex.getMessage());
        verify(compradorRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lancar ResourceNotFoundException quando empresa nao existe")
    void createShouldThrowWhenEmpresaNotFound() {
        when(leilaoRepository.findById(1L)).thenReturn(Optional.of(leilao));
        when(empresaRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> compradorBO.create(1L, 99L)
        );

        assertEquals("Empresa nao encontrada", ex.getMessage());
        verify(compradorRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lancar DataIntegrityViolationException quando empresa ja vinculada ao mesmo leilao")
    void createShouldThrowWhenDuplicate() {
        when(leilaoRepository.findById(1L)).thenReturn(Optional.of(leilao));
        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));

        when(compradorRepository.save(any(Comprador.class)))
                .thenThrow(new DataIntegrityViolationException("duplicate key"));

        assertThrows(DataIntegrityViolationException.class,
                () -> compradorBO.create(1L, 1L));

        verify(compradorRepository).save(any(Comprador.class));
    }

    @Test
    @DisplayName("Deve retornar compradores por leilaoId")
    void getByLeilaoIdShouldReturnList() {
        when(leilaoRepository.findById(1L)).thenReturn(Optional.of(leilao));
        Comprador c = Comprador.builder().empresa(empresa).leilao(leilao).build();
        when(compradorRepository.findByLeilaoId(1L)).thenReturn(List.of(c));

        List<Comprador> result = compradorBO.getByLeilaoId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(empresa, result.get(0).getEmpresa());
    }

    @Test
    @DisplayName("Deve lancar ResourceNotFoundException quando leilao nao existe no getByLeilaoId")
    void getByLeilaoIdShouldThrowWhenLeilaoNotFound() {
        when(leilaoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> compradorBO.getByLeilaoId(99L));
    }

    @Test
    @DisplayName("Deve retornar compradores por empresaId")
    void getByEmpresaIdShouldReturnList() {
        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));
        Comprador c = Comprador.builder().empresa(empresa).leilao(leilao).build();
        when(compradorRepository.findByEmpresaId(1L)).thenReturn(List.of(c));

        List<Comprador> result = compradorBO.getByEmpresaId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Deve lancar ResourceNotFoundException quando empresa nao existe no getByEmpresaId")
    void getByEmpresaIdShouldThrowWhenEmpresaNotFound() {
        when(empresaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> compradorBO.getByEmpresaId(99L));
    }
}
