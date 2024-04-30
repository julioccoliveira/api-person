package com.github.julio.apiperson.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Endereco {

	private String logradouro;
	private String numero;
	private String cep;
	private String cidade;
	private String estado;

	public Endereco() {
	}

	public Endereco(String logradouro, String numero, String cep, String cidade, String estado) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.cep = cep;
		this.cidade = cidade;
		this.estado = estado;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
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
}
