/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.DAO;

import br.edu.ifrs.dv.lista3.modelo.Editora;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jader
 */
@Repository
public interface EditoraDAO extends CrudRepository<Editora, Iterable> {

    public Iterable<Editora> findAllById(int idEditora);

    public Optional<Editora> findById(int id);

    public Iterable<Editora> findAll();

    public Iterable<Editora> findByCnpj(String cnpj);

    public Optional<Editora> findAllByCnpj(String cnpj);




    








    
}
