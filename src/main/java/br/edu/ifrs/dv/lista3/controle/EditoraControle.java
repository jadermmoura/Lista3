/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.controle;

import br.edu.ifrs.dv.lista3.DAO.EditoraDAO;
import br.edu.ifrs.dv.lista3.modelo.Editora;
import br.edu.ifrs.dv.lista3.modelo.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jader
 */
@RestController
@RequestMapping(path = "/api/editoras")
public class EditoraControle {
    
    @Autowired EditoraDAO editoraDAO;
    
    
    @RequestMapping(path = "/",method = RequestMethod.GET)
    public Iterable<Editora> busca(){
        return editoraDAO.findAll();
        
    }
    
    @RequestMapping(path = "/{id}",method = RequestMethod.GET)
    public Iterable<Editora> buscaLivro(@PathVariable int id){
        
        return editoraDAO.findAllById(id);
    }
    
    
    
    @RequestMapping(path = "/",method = RequestMethod.POST)
    public Editora inserir(@RequestBody Editora editora){
        editora.setId(0);
        return editoraDAO.save(editora);
        
    }
}
