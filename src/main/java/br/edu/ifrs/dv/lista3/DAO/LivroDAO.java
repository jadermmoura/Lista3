/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.DAO;

import br.edu.ifrs.dv.lista3.modelo.Autor;
import br.edu.ifrs.dv.lista3.modelo.Editora;
import br.edu.ifrs.dv.lista3.modelo.Livro;
import br.edu.ifrs.dv.lista3.modelo.Usuario;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jader
 */
@Repository
public interface LivroDAO extends CrudRepository<Livro, Integer>{

    public Iterable<Livro> findAll();

    public Optional<Livro> findAllById(int id);

    public Livro save(Usuario usuario);

    public Iterable<Livro> findByTitulo(String titulo);

    public Iterable<Livro> findByTituloStartingWith(String inicia);

    public Iterable<Livro> findByTituloContaining(String contem);
    public Iterable<Livro> findByEditora(Editora e);
    public Iterable<Livro> findByAutor(Autor a);






    
    
}
