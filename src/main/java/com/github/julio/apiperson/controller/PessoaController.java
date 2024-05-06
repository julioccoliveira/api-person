package com.github.julio.apiperson.controller;


import com.github.julio.apiperson.dto.PessoaDto;
import com.github.julio.apiperson.model.Endereco;
import com.github.julio.apiperson.model.Pessoa;
import com.github.julio.apiperson.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;

	@PostMapping
	public ResponseEntity<?> insertPessoas(@RequestBody ArrayList<PessoaDto> pessoas) {
		if (pessoas.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pessoa can't be empty");
		}
		pessoas.forEach(pessoa -> pessoaService.insert(pessoa.toEntity()));

		return ResponseEntity.status(HttpStatus.CREATED).body(pessoas);
	}

	@PutMapping
	public ResponseEntity<?> updatePessoas(@RequestBody ArrayList<PessoaDto> pessoasDto) {
		if (pessoasDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pessoa can't be empty");
		}

		ArrayList<String> responses = new ArrayList<>();
		ArrayList<Pessoa> pessoas = new ArrayList<>();
		for (PessoaDto pessoaDto : pessoasDto) {
			pessoas.add(pessoaDto.toEntity());
		}

		for (Pessoa pessoa : pessoas) {
			Pessoa personToUpdate = pessoaService.findById(pessoa.getId());
			if (personToUpdate == null) {
				responses.add("Can't find Pessoa id " + pessoa.getId());
			} else {
				personToUpdate.setNomeCompleto(pessoa.getNomeCompleto());

				personToUpdate.setEnderecos(pessoa.getEnderecos());
				personToUpdate.setDataNascimento(pessoa.getDataNascimento());
				pessoaService.update(pessoa.getId(), personToUpdate);
				responses.add("Updated Pessoa id " + pessoa.getId());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(responses);
	}

	@GetMapping
	public ResponseEntity<?> getPessoas(@RequestParam(value = "id", required = false) ArrayList<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return ResponseEntity.ok(pessoaService.findAll());
		}

		ArrayList<PessoaDto> response = new ArrayList<>();
		for (Long id : ids) {
			Pessoa pessoa = pessoaService.findById(id);
			if (pessoa != null) {
				PessoaDto preResponse = new PessoaDto(pessoa);
				response.add(preResponse);
			}
		}
		if (response.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa not found");
		}
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/endereco-principal")
	public ResponseEntity<?> setEnderecoPrincipal(
			@RequestParam(value = "pessoaId") Long id,
			@RequestParam(value = "indexEndereco") int index) {
		Pessoa pessoa = pessoaService.findById(id);
		if (pessoa == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa not found");
		}
		if (pessoa.canSetMainEndereco(index)) {
			pessoaService.update(pessoa.getId(), pessoa);
			return ResponseEntity.ok(pessoa.getMainEndereco());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereco not found");
		}
	}

	@GetMapping("{pessoaId}/endereco")
	public ResponseEntity<?> getEnderecos(
			@PathVariable Long pessoaId) {

		Pessoa pessoa = pessoaService.findById(pessoaId);
		if (pessoa == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa not found");
		}

		return ResponseEntity.ok(pessoa.getEnderecos());
	}

	@GetMapping("{pessoaId}/endereco/{enderecoIndex}")
	public ResponseEntity<?> getEndereco(
			@PathVariable Long pessoaId,
			@PathVariable Integer enderecoIndex) {

		Pessoa pessoa = pessoaService.findById(pessoaId);
		if (pessoa == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa not found");
		}

		if (enderecoIndex > pessoa.getEnderecos().size() || enderecoIndex < 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereco not found");
		}

		return ResponseEntity.ok(pessoa.getEnderecos().get(enderecoIndex));
	}
}
