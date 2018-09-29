/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.controle;

import br.edu.ifrs.dv.lista3.DAO.AutorDAO;
import br.edu.ifrs.dv.lista3.modelo.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jader
 */
@RestController
@RequestMapping(path = "api/autores")
public class AutorControle {

    @Autowired AutorDAO autorDAO;
    
    @RequestMapping(path = "/",method = RequestMethod.GET)
    
    public Iterable<Autor> busca() {
        return autorDAO.findAll();

    }
    @RequestMapping(path = "/",method = RequestMethod.POST)
    public Autor inserir(@RequestBody Autor autor){
        autor.setId(0);
        
        return autorDAO.save(autor);
        
    }
    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        autorDAO.deleteById(id);
        
    }
    @RequestMapping(path = "/{id}",method = RequestMethod.GET)
    public Iterable<Autor> recuperar(@PathVariable int id){
        return autorDAO.findAllById(id);
        
    }
}
