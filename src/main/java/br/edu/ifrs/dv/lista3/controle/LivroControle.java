/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.controle;

import br.edu.ifrs.dv.lista3.DAO.AutorDAO;
import br.edu.ifrs.dv.lista3.erros.NaoEncontrado;
import br.edu.ifrs.dv.lista3.modelo.Autor;
import br.edu.ifrs.dv.lista3.modelo.Editora;
import br.edu.ifrs.dv.lista3.modelo.Livro;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.edu.ifrs.dv.lista3.DAO.LivroDAO;

/**
 *
 * @author Jader
 */
@RestController
@RequestMapping(path = "/api/livros")
@Valid
public class LivroControle {

    @Autowired
    LivroDAO livroDAO;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Livro> buscar() {
        return livroDAO.findAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Livro recuperar(@PathVariable int id) {
        Optional<Livro> livroId = livroDAO.findAllById(id);
        if (livroId.isPresent()) {
            return livroId.get();
        } else {
            throw new NaoEncontrado("Id não encontrado");
        }
    }

    @RequestMapping(path = "/titulo/{titulo}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Livro> buscarTitulo(@PathVariable("titulo") String titulo
    ) {
        return livroDAO.findByTitulo(titulo);
    }
    
      @RequestMapping(path = "/{idProduto}/autores/",
            method = RequestMethod.GET)
    public List<Autor> listarAutor(@PathVariable int idProduto) {
        return this.recuperar(idProduto).getAutor();

    }

    @RequestMapping(path = "/{idProduto}/editoras/",
            method = RequestMethod.GET)
    public List<Editora> listarEditoras(@PathVariable int idProduto) {
        return this.recuperar(idProduto).getEditora();

    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Livro inserir(@RequestBody Livro livro) {
        livro.setId(0);
        return livroDAO.save(livro);
    }

    @RequestMapping(path = "livro/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id) {

        livroDAO.deleteById(id);

    }

    @RequestMapping(path = "/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteTudo() {

        livroDAO.deleteAll();

    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Livro editar(@PathVariable int id, @RequestBody Livro livroNovo) {

        livroNovo.setId(id);
        Livro livroAntigo = this.recuperar(id);
        livroAntigo.setTitulo(livroNovo.getTitulo());
        livroAntigo.setAnoPublicacao(livroNovo.getAnoPublicacao());
        livroAntigo.setDoacao(livroNovo.isDoacao());
        return livroDAO.save(livroAntigo);

    }
}
