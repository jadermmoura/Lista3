/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.DAO;

import br.edu.ifrs.dv.lista3.modelo.Editora;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jader
 */
@Repository
public interface EditoraDAO  extends CrudRepository<Editora, Iterable>{

    public Iterable<Editora> findAllById(int idEditora);
    
}
