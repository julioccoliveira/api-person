package com.github.julio.apiperson.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.julio.apiperson.dto.EnderecoDto;
import com.github.julio.apiperson.dto.PessoaDto;
import com.github.julio.apiperson.repository.PessoaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PessoaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup() {
		pessoaRepository.deleteAll();
		objectMapper.registerModule(new JavaTimeModule());
	}

	@AfterEach
	public void tearDown() throws InterruptedException {
		pessoaRepository.deleteAll();
	}

	@Test
	@DisplayName("/pessoa Should GET empty/null")
	public void testGetPessoa() throws Exception {
		mockMvc.perform(get("/pessoa"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0]").doesNotExist());
		mockMvc.perform(get("/pessoa")
						.contentType(MediaType.APPLICATION_JSON)
						.content("[]"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0]").doesNotExist());
		mockMvc.perform(get("/pessoa")
						.contentType(MediaType.APPLICATION_JSON)
						.content(""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0]").doesNotExist());

	}

	@Test
	@DisplayName("/pessoa Should GET 1 Pessoa")
	public void testGetPessoaOneValue() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String reqBody = objectMapper.writeValueAsString(List.of(mockPessoa1()));

		mockMvc.perform(post("/pessoa")
				.contentType(MediaType.APPLICATION_JSON)
				.content(reqBody));

		mockMvc.perform(get("/pessoa"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nomeCompleto").value(mockPessoa1().getNomeCompleto()))
				.andExpect(jsonPath("$[0].dataNascimento").value(new SimpleDateFormat("yyyy-MM-dd").format(mockPessoa1().getDataNascimento())))
				.andExpect(jsonPath("$[0].enderecos[0].logradouro").value(mockPessoa1().getEnderecos().get(0).getLogradouro()));
	}

	@Test
	@DisplayName("/pessoa Should GET 2 Pessoa")
	public void testGetPessoaMoreValues() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String reqBody = objectMapper.writeValueAsString(List.of(mockPessoa1(), mockPessoa2()));

		mockMvc.perform(post("/pessoa")
				.contentType(MediaType.APPLICATION_JSON)
				.content(reqBody));

		mockMvc.perform(get("/pessoa"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nomeCompleto").value(mockPessoa1().getNomeCompleto()))
				.andExpect(jsonPath("$[0].dataNascimento").value(new SimpleDateFormat("yyyy-MM-dd").format(mockPessoa1().getDataNascimento())))
				.andExpect(jsonPath("$[0].enderecos[0].logradouro").value(mockPessoa1().getEnderecos().get(0).getLogradouro()))
				.andExpect(jsonPath("$[1].nomeCompleto").value(mockPessoa2().getNomeCompleto()))
				.andExpect(jsonPath("$[1].dataNascimento").value(new SimpleDateFormat("yyyy-MM-dd").format(mockPessoa2().getDataNascimento())))
				.andExpect(jsonPath("$[1].enderecos[1].logradouro").value(mockPessoa2().getEnderecos().get(1).getLogradouro()));
	}

	@Test
	@DisplayName("/pessoa Should GET 404 Pessoa")
	public void testGetPessoa404() throws Exception {
		mockMvc.perform(get("/pessoa").param("id", "900"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Pessoa not found"));
	}

	@Test
	@DisplayName("/pessoa Should POST 1 Pessoa")
	public void testPostOnePessoa() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String reqBody = objectMapper.writeValueAsString(List.of(mockPessoa1()));

		mockMvc.perform(post("/pessoa")
						.contentType(MediaType.APPLICATION_JSON)
						.content(reqBody))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/pessoa").param("id", "1"))
				.andExpect(jsonPath("$[0].nomeCompleto").value(mockPessoa1().getNomeCompleto()))
				.andExpect(jsonPath("$[0].dataNascimento").value(LocalDate.parse("1990-01-01").toString()))
				.andExpect(jsonPath("$[0].enderecos[0].logradouro").value(mockPessoa1().getEnderecos().get(0).getLogradouro()));
	}

	@Test
	@DisplayName("/pessoa Shouldn't POST No Pessoa")
	public void testPostNoPessoa() throws Exception {

		mockMvc.perform(post("/pessoa")
						.contentType(MediaType.APPLICATION_JSON)
						.content("[]"))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Pessoa can't be empty"));
	}

	@Test
	@DisplayName("/pessoa Should PUT Pessoa")
	public void testPutPessoa() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		String reqBodyPost = objectMapper.writeValueAsString(List.of(mockPessoa1()));
		String reqBodyPut = objectMapper.writeValueAsString(List.of(mockPessoa2WithId()));

		mockMvc.perform(post("/pessoa")
				.contentType(MediaType.APPLICATION_JSON)
				.content(reqBodyPost));

		mockMvc.perform(put("/pessoa")
						.contentType(MediaType.APPLICATION_JSON)
						.content(reqBodyPut))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]").value("Updated Pessoa id " + mockPessoa2WithId().getId()));

		mockMvc.perform(get("/pessoa").param("id", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nomeCompleto").value(mockPessoa2WithId().getNomeCompleto()))
				.andExpect(jsonPath("$[0].dataNascimento").value(LocalDate.parse("1992-01-01").toString()))
				.andExpect(jsonPath("$[0].enderecos[0].logradouro").value(mockPessoa2WithId().getEnderecos().get(0).getLogradouro()));
	}

	@Test
	@DisplayName("/pessoa Shouldn't PUT Pessoa")
	public void testPutPessoaNotFound() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		String reqBodyPut = objectMapper.writeValueAsString(List.of(mockPessoa2WithId()));

		mockMvc.perform(put("/pessoa")
						.contentType(MediaType.APPLICATION_JSON)
						.content(reqBodyPut))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]").value("Can't find Pessoa id " + mockPessoa2WithId().getId()));
	}

	@Test
	@DisplayName("/pessoa Shouldn't PUT Pessoa - empty")
	public void testPutPessoaEmpty() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		String reqBodyPut = objectMapper.writeValueAsString(List.of(mockPessoa2WithId()));

		mockMvc.perform(put("/pessoa")
						.contentType(MediaType.APPLICATION_JSON)
						.content("[]"))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Pessoa can't be empty"));
	}
}
