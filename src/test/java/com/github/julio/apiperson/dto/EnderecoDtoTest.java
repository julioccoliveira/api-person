package com.github.julio.apiperson.dto;

import com.github.julio.apiperson.model.Endereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EnderecoDtoTest {

	@Test
	@DisplayName("Should map EnderecoDto to Endereco")
	public void toEnderecoTest() {
		EnderecoDto enderecoDto = mockEndereco1();
		Endereco endereco = enderecoDto.toEndereco();

		assertEquals(enderecoDto.getLogradouro(), endereco.getLogradouro());
		assertEquals(enderecoDto.getNumero(), endereco.getNumero());
		assertEquals(enderecoDto.getCep(), endereco.getCep());
		assertEquals(enderecoDto.getCidade(), endereco.getCidade());
		assertEquals(enderecoDto.getEstado(), endereco.getEstado());
	}

	@Test
	@DisplayName("Constructor test")
	public void constructorTest() {
		Endereco endereco = mockEndereco1().toEndereco();
		EnderecoDto enderecoDto2 = new EnderecoDto(endereco);

		assertEquals(enderecoDto2.getLogradouro(), endereco.getLogradouro());
		assertEquals(enderecoDto2.getNumero(), endereco.getNumero());
		assertEquals(enderecoDto2.getCep(), endereco.getCep());
		assertEquals(enderecoDto2.getCidade(), endereco.getCidade());
		assertEquals(enderecoDto2.getEstado(), endereco.getEstado());
	}

	private EnderecoDto mockEndereco1() {
		EnderecoDto endereco1 = new EnderecoDto();
		endereco1.setLogradouro("Rua A");
		endereco1.setNumero("123");
		endereco1.setCep("12345-678");
		endereco1.setCidade("São Paulo");
		endereco1.setEstado("SP");

		return endereco1;
	}
}
