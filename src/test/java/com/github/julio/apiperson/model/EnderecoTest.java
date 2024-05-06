package com.github.julio.apiperson.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EnderecoTest {

	@Test
	@DisplayName("Endereco Constructor")
	public void contructorTest() {
		Endereco endereco = new Endereco("Rua",
				"N",
				"123456",
				"Cidade",
				"Estado");
		assertEquals("Rua", endereco.getLogradouro());
		assertEquals("N", endereco.getNumero());
		assertEquals("123456", endereco.getCep());
		assertEquals("Cidade", endereco.getCidade());
		assertEquals("Estado", endereco.getEstado());
	}

	@Test
	@DisplayName("Endereco Setter")
	public void setterTest() {
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua");
		endereco.setNumero("N");
		endereco.setCep("123");
		endereco.setCidade("Cidade");
		endereco.setEstado("Estado");

		assertEquals("Rua", endereco.getLogradouro());
		assertEquals("N", endereco.getNumero());
		assertEquals("123", endereco.getCep());
		assertEquals("Cidade", endereco.getCidade());
		assertEquals("Estado", endereco.getEstado());
	}
}
