package com.github.julio.apiperson.service.impl;

import com.github.julio.apiperson.model.Endereco;
import com.github.julio.apiperson.model.Pessoa;
import com.github.julio.apiperson.repository.PessoaRepository;
import com.github.julio.apiperson.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaServiceImpl implements PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public void insert(Pessoa pessoa) {
		pessoaRepository.save(pessoa);
	}

	@Override
	public void update(Long id, Pessoa pessoa) {
		Optional<Pessoa> optionalPessoa = pessoaRepository.findById(id);
		if (optionalPessoa.isPresent()) {
			Pessoa updatedPessoa = optionalPessoa.get();
			updatedPessoa.setNomeCompleto(pessoa.getNomeCompleto());
			List<Endereco> enderecos = new ArrayList<>(pessoa.getEnderecos());
			updatedPessoa.setEnderecos(enderecos);
			updatedPessoa.setDataNascimento(pessoa.getDataNascimento());
			pessoaRepository.save(updatedPessoa);
		}
	}

	@Override
	public Pessoa findById(Long id) {
		return pessoaRepository.findById(id)
				.orElse(null);
	}

	@Override
	public Iterable<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}
}
