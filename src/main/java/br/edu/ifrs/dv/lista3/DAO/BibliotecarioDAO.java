/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.DAO;

import br.edu.ifrs.dv.lista3.modelo.Bibliotecario;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jader
 */
@Repository
public interface BibliotecarioDAO extends CrudRepository<Bibliotecario, Integer>{

    public Optional<Bibliotecario> findAllById(int id);

    public Bibliotecario save(Bibliotecario bibliotecario);
    
}
