package com.github.julio.apiperson.dto;

import com.github.julio.apiperson.model.Endereco;
import com.github.julio.apiperson.model.Pessoa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PessoaDto {

	private Long id;
	private String nomeCompleto;
	private Date dataNascimento;
	private List<EnderecoDto> enderecos = new ArrayList<>();

	public PessoaDto(Pessoa pessoa) {
		this.id = pessoa.getId();
		this.nomeCompleto = pessoa.getNomeCompleto();
		this.dataNascimento = pessoa.getDataNascimento();
		this.enderecos = pessoa.getEnderecos().stream().map(EnderecoDto::new).toList();
	}

	public PessoaDto() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<EnderecoDto> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoDto> enderecos) {
		this.enderecos = enderecos;
	}

	public Pessoa toEntity() {
		List<Endereco> enderecos = this.enderecos.stream().map(e ->
						new Endereco(e.getLogradouro(),
								e.getNumero(),
								e.getCep(),
								e.getCidade(),
								e.getEstado()))
				.toList();
		return new Pessoa(
				this.getId(),
				this.getNomeCompleto(),
				this.getDataNascimento(),
				enderecos);
	}
}
