/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.DAO;

import br.edu.ifrs.dv.lista3.modelo.Telefone;
import br.edu.ifrs.dv.lista3.modelo.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jader
 */
@Repository
public interface UsuarioDAO  extends CrudRepository<Usuario, Integer>{

    public Iterable<Usuario> findByNome(String nome);

     public Optional<Usuario> findAllById(int id);

    public void findByNome(Usuario usuario);
    
    public void findByTelefones(List<Telefone> telefones);

    public Iterable<Usuario> findByCpf(String cpf);
    
    public Iterable<Usuario> findByEmail(String email);

    public Optional<Usuario> findAllByEmail(String email);
//    public Optional<Telefone> inserirTelefone(Telefone tel);

    
    
}
