/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.controle;

import br.edu.ifrs.dv.lista3.DAO.EditoraDAO;
import br.edu.ifrs.dv.lista3.DAO.LivroDAO;
import br.edu.ifrs.dv.lista3.erros.NaoEncontrado;
import br.edu.ifrs.dv.lista3.modelo.Editora;
import br.edu.ifrs.dv.lista3.modelo.Livro;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jader
 */
@RestController
@RequestMapping(path = "/api/editoras")
public class EditoraControle {

    @Autowired
    EditoraDAO editoraDAO;
    @Autowired
    LivroDAO livroDAO;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public Iterable<Editora> buscaTodasEditoras() {
        return editoraDAO.findAll();
    }

    @RequestMapping(path = "/{id}/buscaId", method = RequestMethod.GET)
    public Iterable<Editora> buscaEditoraPeloId(@PathVariable int id) {
        return editoraDAO.findAllById(id);
    }
    @RequestMapping(path = "/titulo/{titulo}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Livro> buscarTitulo(@PathVariable("titulo") String titulo
    ) {
        return livroDAO.findByTitulo(titulo);
    }
    
    
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Editora inserir(@RequestBody Editora editora) {
        editora.setId(0);
        
        return editoraDAO.save(editora);

    }
    @RequestMapping(path = "/{id}/editora/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Editora> mostraEditoraPeloIdLivro(@PathVariable int id) {
        Optional<Livro> livroId = livroDAO.findAllById(id);

        if (livroId.isPresent()) {
            return livroId.get().getEditora();
        } else {
            throw new NaoEncontrado("Id não encontrado");

        }
    }
      
        
    
  

    
}
