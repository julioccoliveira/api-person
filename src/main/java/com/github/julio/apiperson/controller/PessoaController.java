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
}
