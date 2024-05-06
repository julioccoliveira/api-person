package com.github.julio.apiperson.dto;

import com.github.julio.apiperson.model.Pessoa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PessoaDtoTest {

	@Test
	@DisplayName("Should map PessoaDto to Pessoa")
	public void testMap() throws ParseException {
		PessoaDto pessoaDto = mockPessoaDto1();
		Pessoa pessoa = pessoaDto.toEntity();

		assertEquals(pessoaDto.getId(), pessoa.getId());
		assertEquals(pessoaDto.getDataNascimento(), pessoa.getDataNascimento());
		assertEquals(pessoaDto.getNomeCompleto(), pessoa.getNomeCompleto());
		assertEquals(pessoaDto.getEnderecos().get(0).toEndereco().getLogradouro(), pessoa.getEnderecos().get(0).getLogradouro());
	}

	@Test
	@DisplayName("Constructor test")
	public void constructorTest() throws ParseException {
		Pessoa pessoa = mockPessoaDto1().toEntity();
		PessoaDto pessoaDto2 = new PessoaDto(pessoa);

		assertEquals(pessoaDto2.getId(), pessoa.getId());
		assertEquals(pessoaDto2.getDataNascimento(), pessoa.getDataNascimento());
		assertEquals(pessoaDto2.getNomeCompleto(), pessoa.getNomeCompleto());
		assertEquals(pessoaDto2.getEnderecos().get(0).toEndereco().getLogradouro(), pessoa.getEnderecos().get(0).getLogradouro());
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

	private EnderecoDto mockEndereco2() {
		EnderecoDto endereco2 = new EnderecoDto();
		endereco2.setLogradouro("Rua C");
		endereco2.setNumero("456");
		endereco2.setCep("12345-678");
		endereco2.setCidade("Rio de Janeiro");
		endereco2.setEstado("RJ");

		return endereco2;
	}

	private PessoaDto mockPessoaDto1() throws ParseException {
		PessoaDto pessoaDto1 = new PessoaDto();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dataNascimento = dateFormat.parse("1990-01-01");

		pessoaDto1.setId(1L);
		pessoaDto1.setNomeCompleto("Joao");
		pessoaDto1.setDataNascimento(dataNascimento);
		pessoaDto1.setEnderecos(List.of(mockEndereco1()));
		return pessoaDto1;
	}
}
