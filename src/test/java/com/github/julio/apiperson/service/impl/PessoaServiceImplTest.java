package com.github.julio.apiperson.service.impl;

import com.github.julio.apiperson.model.Pessoa;
import com.github.julio.apiperson.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PessoaServiceImplTest {

	@Mock
	private PessoaRepository pessoaRepository;

	@InjectMocks
	private PessoaServiceImpl pessoaService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("impl insert Pessoa")
	public void testInsertPessoa() {
		Pessoa pessoa = new Pessoa();
		pessoa.setNomeCompleto("Fulano");

		pessoaService.insert(pessoa);

		verify(pessoaRepository, times(1)).save(pessoa);
	}

	@Test
	@DisplayName("impl update Pessoa")
	public void testUpdatePessoa() {
		Long id = 1L;
		Pessoa pessoa = new Pessoa();
		pessoa.setId(id);
		pessoa.setNomeCompleto("Fulano");

		when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));

		Pessoa updatedPessoa = new Pessoa();
		updatedPessoa.setNomeCompleto("Beltrano");

		pessoaService.update(id, updatedPessoa);

		assertEquals(updatedPessoa.getNomeCompleto(), pessoa.getNomeCompleto());
		verify(pessoaRepository, times(1)).save(pessoa);
	}

	@Test
	@DisplayName("impl findPessoaById")
	public void testFindPessoaById() {
		Long id = 1L;
		Pessoa pessoa = new Pessoa();
		pessoa.setId(id);
		pessoa.setNomeCompleto("Fulano");

		when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));

		Pessoa foundPessoa = pessoaService.findById(id);

		assertEquals(pessoa, foundPessoa);
	}

	@Test
	@DisplayName("impl findAllPessoas")
	public void testFindAllPessoas() {
		List<Pessoa> pessoas = new ArrayList<>();
		pessoas.add(new Pessoa(1L, "Fulano", null, new ArrayList<>()));
		pessoas.add(new Pessoa(2L, "Beltrano", null, new ArrayList<>()));

		when(pessoaRepository.findAll()).thenReturn(pessoas);

		Iterable<Pessoa> foundPessoas = pessoaService.findAll();

		assertEquals(pessoas, foundPessoas);
	}
}
