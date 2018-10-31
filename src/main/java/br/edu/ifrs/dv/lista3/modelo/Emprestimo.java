/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.modelo;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author jader
 */
@Entity
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate retirada;
    private LocalDate previsaoDevolucao;
    private LocalDate devolucao;

    @ManyToOne
    private Bibliotecario bibliotecario;

    @ManyToOne
    private Livro livro;

    @ManyToOne
    private Usuario usuario;

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getRetirada() {
        return retirada;
    }

    public void setRetirada(LocalDate retirada) {
        this.retirada = retirada;
    }

    public LocalDate getPrevisaoDevolucao() {
        return previsaoDevolucao;
    }

    public void setPrevisaoDevolucao(LocalDate previsaoDevolucao) {
        this.previsaoDevolucao = previsaoDevolucao;
    }

    public LocalDate getDevolucao() {
        return devolucao;
    }

    public void setDevolucao(LocalDate devolucao) {
        this.devolucao = devolucao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
