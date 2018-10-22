/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.DAO;

import br.edu.ifrs.dv.lista3.modelo.Autor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jader
 */
@Repository
public interface AutorDAO extends CrudRepository<Autor, Integer>{

    public Iterable<Autor> findAllById(int id);

    public Iterable<Autor> findByPrimeiroNomeStartingWith(String inicia);

    public Iterable<Autor> findByPrimeiroNomeContaining(String contem);

    public Iterable<Autor> findBySegundoNomeStartingWith(String inicia);

    public Iterable<Autor> findBySegundoNomeContaining(String contem);

    
}
