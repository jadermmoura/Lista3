/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.controle;

import br.edu.ifrs.dv.lista3.DAO.AutorDAO;
import br.edu.ifrs.dv.lista3.DAO.EditoraDAO;
import br.edu.ifrs.dv.lista3.erros.NaoEncontrado;
import br.edu.ifrs.dv.lista3.modelo.Autor;
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
import br.edu.ifrs.dv.lista3.erros.CamposObrigatorios;
import br.edu.ifrs.dv.lista3.erros.RequisicaoInvalida;
import br.edu.ifrs.dv.lista3.modelo.Editora;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    AutorDAO autorDAO;
    

    
     @RequestMapping( path = "/pesquisar/titulo/", method = RequestMethod.GET)
    public Iterable<Livro> pesquisaPorNomeLivro(
            @RequestParam(required = false) String inicia, 
            @RequestParam(required = false) String contem ) {
        if(inicia!=null) {
            return livroDAO.findByTituloStartingWith(inicia);
        }
        if(contem!=null) {
            return livroDAO.findByTituloContaining(contem);
        }
        
        throw new RequisicaoInvalida("Indique um dos 2 valores");
    
    }
     @RequestMapping( path = "/pesquisar/primeiroNome/", method = RequestMethod.GET)
    public Iterable<Autor> pesquisaPorNomeAutor(
            @RequestParam(required = false) String inicia, 
            @RequestParam(required = false) String contem ) {
        if(inicia!=null) {
            return autorDAO.findByPrimeiroNomeStartingWith(inicia);
        }
        if(contem!=null) {
            return autorDAO.findByPrimeiroNomeContaining(contem);
        }
        
        throw new RequisicaoInvalida("Indique um dos 2 valores");
    
    }
     @RequestMapping( path = "/pesquisar/segundoNome/", method = RequestMethod.GET)
    public Iterable<Autor> pesquisaPorSobreNomeAutor(
            @RequestParam(required = false) String inicia, 
            @RequestParam(required = false) String contem ) {
        if(inicia!=null) {
            return autorDAO.findBySegundoNomeStartingWith(inicia);
        }
        if(contem!=null) {
            return autorDAO.findBySegundoNomeContaining(contem);
        }
        
        throw new RequisicaoInvalida("Indique um dos 2 valores");
    
    }
    
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
     @RequestMapping(path = "/{idProduto}/editoras/",method = RequestMethod.GET)
    public List<Editora> listarPeloIdLivroPegaAutor(@PathVariable int idProduto) {
        return this.recuperar(idProduto).getEditora();

    }
     @RequestMapping(path = "/{id}/autor/",method = RequestMethod.GET)
    public List<Autor> listarAutorPeloID(@PathVariable int id) {
        
        return this.recuperar(id).getAutor();

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

    @RequestMapping(path = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Livro inserir(@RequestBody Livro livro) {
        livro.setId(0);
        if (!(livro.getAnoPublicacao() == 0)) {
           return livroDAO.save(livro);
        }else{
            throw new CamposObrigatorios("Faltou algum campo");
        }
        
    }

    @RequestMapping(path = "/delete/{id}/", method = RequestMethod.DELETE)
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
