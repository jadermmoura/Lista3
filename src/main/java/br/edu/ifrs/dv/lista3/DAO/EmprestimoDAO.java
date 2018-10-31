/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.DAO;

import br.edu.ifrs.dv.lista3.modelo.Emprestimo;
import br.edu.ifrs.dv.lista3.modelo.Livro;
import br.edu.ifrs.dv.lista3.modelo.Usuario;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jader
 */
@Repository
public interface EmprestimoDAO extends CrudRepository<Emprestimo, Iterable>{

    public Optional<Livro> findAllById(String livro);


//    public Iterable<Emprestimo> findByEmprestimo(Usuario user);

//    public Iterable<Emprestimo> findByIdEmprestimo(int id);

    public Iterable<Emprestimo> usuario(Usuario user);

}
