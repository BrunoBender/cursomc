package br.com.senior.cursomc.dto;

import br.com.senior.cursomc.domain.Estado;
import br.com.senior.cursomc.services.EstadoService;

import java.io.Serializable;

public class EstadoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public EstadoDto(){

    }

    public EstadoDto(Estado obj){
        this.id = obj.getId();
        this.nome = obj.getNome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
