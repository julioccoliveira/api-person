package com.github.julio.apiperson.repository;

import com.github.julio.apiperson.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
