package com.github.julio.apiperson.service;

import com.github.julio.apiperson.model.Pessoa;

public interface PessoaService {

	void insert(Pessoa pessoa);

	Pessoa findById(Long id);

	Iterable<Pessoa> findAll();

	void update(Long id, Pessoa pessoa);

}
