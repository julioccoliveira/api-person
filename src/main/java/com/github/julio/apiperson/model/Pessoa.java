package com.github.julio.apiperson.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Pessoa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomeCompleto;
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	@ElementCollection
	@Embedded
	private List<Endereco> enderecos = new ArrayList<>();

	public Pessoa(Long id, String nomeCompleto, Date dataNascimento, List<Endereco> enderecos) {
		this.id = id;
		this.nomeCompleto = nomeCompleto;
		this.dataNascimento = dataNascimento;
		this.enderecos = enderecos;
	}

	public Pessoa() {
	}

	public Long getId() {
		return id;
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

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public void addEnderecos(ArrayList<Endereco> lista) {
		for (Endereco endereco : lista) {
			if (enderecos.stream().noneMatch(e -> e.equals(endereco))) {
				this.enderecos.add(endereco);
			}
		}

	}

	public boolean canSetMainEndereco(int index) {
		if (enderecos.size() >= index && index >= 0) {
			setMainEndereco(index);
			return true;
		} else {
			return false;
		}
	}

	public Endereco getMainEndereco() {
		return enderecos.get(0);
	}

	private void setMainEndereco(int index) {
		Endereco endereco = enderecos.get(index);
		enderecos.remove(index);
		enderecos.add(0, endereco);
	}
}
