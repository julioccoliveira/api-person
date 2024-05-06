package com.github.julio.apiperson.dto;

import com.github.julio.apiperson.model.Endereco;

public class EnderecoDto {
	private String logradouro;
	private String numero;
	private String cep;
	private String cidade;
	private String estado;

	public EnderecoDto() {
	}

	public EnderecoDto(Endereco e) {
		this.logradouro = e.getLogradouro();
		this.numero = e.getNumero();
		this.cep = e.getCep();
		this.cidade = e.getCidade();
		this.estado = e.getEstado();
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Endereco toEndereco() {
		return new Endereco(this.logradouro, this.numero, this.cep, this.cidade, this.estado);
	}
}
