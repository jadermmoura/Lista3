/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.controle;

import br.edu.ifrs.dv.lista3.DAO.EditoraDAO;
import br.edu.ifrs.dv.lista3.DAO.LivroDAO;
import br.edu.ifrs.dv.lista3.erros.AutorJaCadastrado;
import br.edu.ifrs.dv.lista3.erros.CamposObrigatorios;
import br.edu.ifrs.dv.lista3.erros.NaoEncontrado;
import br.edu.ifrs.dv.lista3.modelo.Autor;
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
  

    @RequestMapping(path = "/cnpj/{cnpj}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Editora> buscarCnpj(@PathVariable("cnpj") String cnpj
    ) {
        return editoraDAO.findByCnpj(cnpj);
    }

    @RequestMapping(path = "/todosLivrosEditora/{id}", method = RequestMethod.GET)
    public Iterable<Livro> listaTodosLivroEditora(@PathVariable int id) {
        Editora editora = editoraDAO.findById(id).get();
        return livroDAO.findByEditora(editora);
    }

 @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Editora recuperarEditoraID(@PathVariable int id) {
        Optional<Editora> editoraId = editoraDAO.findById(id);
        if (editoraId.isPresent()){
            return editoraId.get();
        } else {
            throw new NaoEncontrado("Id não encontrado");
        }
    }

  
}
