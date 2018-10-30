/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.controle;

import br.edu.ifrs.dv.lista3.DAO.AutorDAO;
import br.edu.ifrs.dv.lista3.DAO.LivroDAO;
import br.edu.ifrs.dv.lista3.erros.NaoEncontrado;
import br.edu.ifrs.dv.lista3.modelo.Autor;
import br.edu.ifrs.dv.lista3.modelo.Editora;
import br.edu.ifrs.dv.lista3.modelo.Livro;
import br.edu.ifrs.dv.lista3.modelo.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jader
 */
@RestController
@RequestMapping(path = "api/autores")
public class AutorControle {

    @Autowired
    AutorDAO autorDAO;

    @Autowired
    LivroDAO livroDAO;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public Iterable<Autor> busca() {
        return autorDAO.findAll();
    }

    @RequestMapping(path = "/primeiroNome/", method = RequestMethod.GET)
    public Iterable<Autor> buscaPrimeiroNome(@RequestParam(required = false) String primeiroNome) {
        return autorDAO.findByPrimeiroNomeStartingWith(primeiroNome);
    }

    @RequestMapping(path = "/segundoNome/", method = RequestMethod.GET)
    public Iterable<Autor> buscaSegundoNome(@RequestParam(required = false) String segundoNome) {
        return autorDAO.findBySegundoNome(segundoNome);
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Autor inserir(@RequestBody Autor autor) {
        autor.setId(0);
        if (autor.getPrimeiroNome().equals("") || autor.getPrimeiroNome().equals("null")
                || autor.getSegundoNome().equals("") || autor.getSegundoNome().equals("null")) {

        }
        return autorDAO.save(autor);

    }

    @RequestMapping(path = "/todosLivrosAutor/{id}", method = RequestMethod.GET)
    public Iterable<Livro> testar(@PathVariable int id) {
        Autor autor = autorDAO.findById(id).get();
        return livroDAO.findByAutor(autor);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        autorDAO.deleteById(id);

    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Iterable<Autor> recuperar(@PathVariable int id) {
        return autorDAO.findAllById(id);

    }

    @RequestMapping(path = "/livros/{id}/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Autor> recuperarAutorLivroId(@PathVariable int id) {
        Optional<Livro> livroId = livroDAO.findAllById(id);
        if (livroId.isPresent()) {
            return livroId.get().getAutor();
        } else {
            throw new NaoEncontrado("Id não encontrado");
        }
    }

}
