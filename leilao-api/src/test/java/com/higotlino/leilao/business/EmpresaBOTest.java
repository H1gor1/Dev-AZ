package com.higotlino.leilao.business;

import com.higotlino.leilao.entity.Empresa;
import com.higotlino.leilao.repository.EmpresaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class EmpresaBOTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmpresaBO empresaBO;

    private Empresa empresa;

    @BeforeEach
    void setUp() {
        empresa = Empresa.builder()
                .razaoSocial("Empresa Teste")
                .cnpj("12345678000199")
                .email("teste@empresa.com")
                .usuario("teste")
                .password("senha123")
                .logradouro("Rua Teste")
                .municipio("Cidade Teste")
                .numero("100")
                .complemento("Sala 1")
                .bairro("Centro")
                .cep("12345678")
                .telefone("11999999999")
                .site("https://empresa.com")
                .build();
    }

    @Test
    @DisplayName("CREATE Deve lancar DataIntegrityViolationException quando CNPJ ja existe")
    void createShouldThrowWhenCnpjExists() {

        when(passwordEncoder.encode(any())).thenReturn("hash123");
        when(empresaRepository.existsEmpresaByCnpj("12345678000199")).thenReturn(true);

        DataIntegrityViolationException ex = assertThrows(
                DataIntegrityViolationException.class,
                () -> empresaBO.create(empresa)
        );

        assertEquals("Empresa ja cadastrada com este CNPJ", ex.getMessage());

        verify(empresaRepository, never()).save(any());
    }

    @Test
    @DisplayName("CREATE Deve lancar DataIntegrityViolationException quando usuario ja existe")
    void createShouldThrowWhenUsuarioExists() {

        when(passwordEncoder.encode(any())).thenReturn("hash123");
        when(empresaRepository.existsEmpresaByCnpj("12345678000199")).thenReturn(false);
        when(empresaRepository.existsEmpresaByUsuario("teste")).thenReturn(true);

        DataIntegrityViolationException ex = assertThrows(
                DataIntegrityViolationException.class,
                () -> empresaBO.create(empresa)
        );

        assertEquals("Empresa ja cadastrada com este usuario", ex.getMessage());

        verify(empresaRepository, never()).save(any());
    }

    @Test
    @DisplayName("CREATE Deve criar empresa com sucesso e codificar senha")
    void createShouldSucceedAndEncodePassword() {
        when(passwordEncoder.encode("senha123")).thenReturn("hash123");
        when(empresaRepository.existsEmpresaByCnpj("12345678000199")).thenReturn(false);
        when(empresaRepository.existsEmpresaByUsuario("teste")).thenReturn(false);

        when(empresaRepository.save(any(Empresa.class))).thenAnswer(inv -> inv.getArgument(0));

        Empresa saved = empresaBO.create(empresa);

        assertNotNull(saved);
        assertEquals("hash123", saved.getPassword());
        verify(passwordEncoder).encode("senha123");
        verify(empresaRepository).save(empresa);
    }

    @Test
    @DisplayName("UPDATE Deve atualizar empresa com sucesso.")
    void updateShouldSucceed() {
        when(empresaRepository.existsEmpresaByCnpj("12345678000199")).thenReturn(false);
        when(empresaRepository.existsEmpresaByUsuario("teste")).thenReturn(false);

        when(empresaRepository.save(any(Empresa.class))).thenAnswer(inv -> inv.getArgument(0));

        Empresa saved = empresaBO.update(empresa);

        assertNotNull(saved);
        verify(empresaRepository).save(empresa);
    }

    @Test
    @DisplayName("UPDATE Deve lancar DataIntegrityViolationException quando CNPJ ja existe")
    void updateShouldThrowWhenCnpjExists() {
        when(empresaRepository.existsEmpresaByCnpj("12345678000199")).thenReturn(true);
        DataIntegrityViolationException ex = assertThrows(
                DataIntegrityViolationException.class,
                () -> empresaBO.update(empresa)
        );

        assertEquals("Empresa ja cadastrada com este CNPJ", ex.getMessage());

        verify(empresaRepository, never()).save(any());
    }

    @Test
    @DisplayName("UPDATE Deve lancar DataIntegrityViolationException quando usuario ja existe")
    void updateShouldThrowWhenUserExists() {
        when(empresaRepository.existsEmpresaByCnpj("12345678000199")).thenReturn(false);
        when(empresaRepository.existsEmpresaByUsuario("teste")).thenReturn(true);
        DataIntegrityViolationException ex = assertThrows(
                DataIntegrityViolationException.class,
                () -> empresaBO.update(empresa)
        );

        assertEquals("Empresa ja cadastrada com este usuario", ex.getMessage());

        verify(empresaRepository, never()).save(any());
    }


}
