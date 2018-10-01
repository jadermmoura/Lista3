/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.edu.ifrs.dv.lista3.DAO.UsuarioDAO;
import br.edu.ifrs.dv.lista3.erros.NaoEncontrado;
import br.edu.ifrs.dv.lista3.modelo.Telefone;
import br.edu.ifrs.dv.lista3.modelo.Usuario;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author jader
 */
@RestController
@RequestMapping(path = "/api/usuario")
@Valid
public class UsuarioControle {
    
    @Autowired
    UsuarioDAO usuarioDAO;
    
    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Usuario> buscar() {
        return usuarioDAO.findAll();
    }
    
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Usuario recuperar(@PathVariable int id) {
        Optional<Usuario> usuarioId = usuarioDAO.findAllById(id);
        if (usuarioId.isPresent()) {
            return usuarioId.get();
            
        } else {
            throw new NaoEncontrado("Id não encontrado");
            
        }
    }
    
    @RequestMapping(path = "/nome/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Usuario> buscarNome(@PathVariable("nome") String name
    ) {
//        if (igual != null) {
//            return usuarioDAO.findByNome(igual);
//        } else {
//
//            return usuarioDAO.findByNomeContaining(contem);
//        }
        return usuarioDAO.findByNome(name);
    }
    
   @RequestMapping(path = "/{idProduto}/telefones/", 
            method = RequestMethod.GET)
    public Iterable<Telefone> listarModelo(@PathVariable int idProduto) {
        return this.recuperar(idProduto).getTelefones();
   
    }
    
    @RequestMapping(path = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario inserir(@RequestBody Usuario usuarioNovo) {
        usuarioNovo.setId(0);
        Usuario usuarioSalvo = usuarioDAO.save(usuarioNovo);
        return usuarioNovo;
    }
    
    @RequestMapping(path = "/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete() {
        
        usuarioDAO.deleteAll();
        
    }
    
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id) {
        
        usuarioDAO.deleteById(id);
        
    }
    
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Usuario editar(@PathVariable int id, @RequestBody Usuario usuarioNovo) {
        usuarioNovo.setId(id);
        
        Usuario usuarioAntigo = this.recuperar(id);
        usuarioAntigo.setNome(usuarioNovo.getNome());
        usuarioAntigo.setCpf(usuarioNovo.getCpf());
        usuarioAntigo.setEmail(usuarioNovo.getEmail());
        usuarioAntigo.setTelefones(usuarioNovo.getTelefones());
        
        return usuarioDAO.save(usuarioAntigo);
        
    }
    
}
