package com.github.julio.apiperson.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PessoaTest {

	@Test
	@DisplayName("Pessoa Constructor")
	public void constructorTest() {
		Date date = new Date();
		ArrayList<Endereco> enderecos = new ArrayList<>();
		enderecos.add(enderecoMock1());
		Pessoa pessoa = new Pessoa(
				1L,
				"Joao",
				date,
				enderecos);
		assertEquals(1L, pessoa.getId());
		assertEquals("Joao", pessoa.getNomeCompleto());
		assertEquals(date, pessoa.getDataNascimento());
		assertEquals(enderecos, pessoa.getEnderecos());
	}

	@Test
	@DisplayName("Pessoa Setter")
	public void setterTest() {
		Date date = new Date();
		ArrayList<Endereco> enderecos = new ArrayList<>();
		enderecos.add(enderecoMock1());
		Pessoa pessoa = new Pessoa();
		pessoa.setId(1L);
		pessoa.setNomeCompleto("Joao");
		pessoa.setDataNascimento(date);
		pessoa.setEnderecos(enderecos);

		assertEquals(1L, pessoa.getId());
		assertEquals("Joao", pessoa.getNomeCompleto());
		assertEquals(date, pessoa.getDataNascimento());
		assertEquals(enderecos, pessoa.getEnderecos());
	}

	@Test
	@DisplayName("Pessoa should add Endereco")
	public void shouldAddEndereco() {
		ArrayList<Endereco> endereco = new ArrayList<>();
		endereco.add(enderecoMock2());
		Pessoa pessoa = pessoaMock();
		pessoa.addEnderecos(endereco);

		assertEquals(2, pessoa.getEnderecos().size());
	}

	@Test
	@DisplayName("Pessoa shouldn't add Endereco")
	public void shouldNotAddEndereco() {
		ArrayList<Endereco> endereco = new ArrayList<>();
		endereco.add(enderecoMock1());
		Pessoa pessoa = pessoaMock();
		pessoa.addEnderecos(endereco);

		assertEquals(1, pessoa.getEnderecos().size());
	}

	private Endereco enderecoMock1() {
		return new Endereco("Rua",
				"N",
				"123456",
				"Cidade",
				"Estado");
	}

	private Endereco enderecoMock2() {
		return new Endereco("Rua 2",
				"N1",
				"654321",
				"Cidade",
				"Estado");
	}

	private Pessoa pessoaMock() {
		Date date = new Date();
		ArrayList<Endereco> enderecos = new ArrayList<>();
		enderecos.add(enderecoMock1());
		return new Pessoa(
				1L,
				"Joao",
				date,
				enderecos);
	}
}
