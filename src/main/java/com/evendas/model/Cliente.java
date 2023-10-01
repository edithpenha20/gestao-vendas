package com.evendas.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    private String nome;

    private String telefone;

    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    public Cliente() {
    }

    public Cliente(Long codigo, String nome, String telefone, Boolean ativo, Endereco endereco) {
        this.codigo = codigo;
        this.nome = nome;
        this.telefone = telefone;
        this.ativo = ativo;
        this.endereco = endereco;
    }

    public Cliente(String nome, String telefone, Boolean ativo, Endereco endereco) {
        this.nome = nome;
        this.telefone = telefone;
        this.ativo = ativo;
        this.endereco = endereco;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente cliente)) return false;
        return Objects.equals(codigo, cliente.codigo) && Objects.equals(nome, cliente.nome) && Objects.equals(telefone, cliente.telefone) && Objects.equals(ativo, cliente.ativo) && Objects.equals(endereco, cliente.endereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nome, telefone, ativo, endereco);
    }
}
