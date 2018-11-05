/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.controle;

import br.edu.ifrs.dv.lista3.DAO.AutorDAO;
import br.edu.ifrs.dv.lista3.DAO.EditoraDAO;
import br.edu.ifrs.dv.lista3.DAO.EmprestimoDAO;
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
    @Autowired
    EmprestimoDAO emprestimoDAO;
    @Autowired
    EditoraDAO editoraDAO;

    @RequestMapping(path = "/pesquisar/titulo/", method = RequestMethod.GET)
    public Iterable<Livro> pesquisaPorNomeLivro(
            @RequestParam(required = false) String inicia,
            @RequestParam(required = false) String contem) {
        if (inicia != null) {
            return livroDAO.findByTituloStartingWith(inicia);
        }
        if (contem != null) {
            return livroDAO.findByTituloContaining(contem);
        }

        throw new RequisicaoInvalida("Indique um dos 2 valores");

    }

    @RequestMapping(path = "/pesquisar/primeiroNome/", method = RequestMethod.GET)
    public Iterable<Autor> pesquisaPorNomeAutor(
            @RequestParam(required = false) String inicia,
            @RequestParam(required = false) String contem) {
        if (inicia != null) {
            return autorDAO.findByPrimeiroNomeStartingWith(inicia);
        }
        if (contem != null) {
            return autorDAO.findByPrimeiroNomeContaining(contem);
        }

        throw new RequisicaoInvalida("Indique um dos 2 valores");

    }

    @RequestMapping(path = "/pesquisar/segundoNome/", method = RequestMethod.GET)
    public Iterable<Autor> pesquisaPorSobreNomeAutor(
            @RequestParam(required = false) String inicia,
            @RequestParam(required = false) String contem) {
        if (inicia != null) {
            return autorDAO.findBySegundoNomeStartingWith(inicia);
        }
        if (contem != null) {
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

    @RequestMapping(path = "/{id}/editoras/", method = RequestMethod.GET)
    public List<Editora> listarEditoraDoLivro(@PathVariable int id) {
        return this.recuperar(id).getEditora();

    }

//    @RequestMapping(path = "/{id}/autor/", method = RequestMethod.GET)
//    public List<Autor> listarAutorPeloID(@PathVariable int id) {
//        return this.recuperar(id).getAutor();
//    }
    @RequestMapping(path = "/titulo/{titulo}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Livro> buscarTitulo(@PathVariable("titulo") String titulo
    ) {
        return livroDAO.findByTitulo(titulo);
    }

    @RequestMapping(path = "/{idProduto}/autores/",method = RequestMethod.GET)
    public List<Autor> listarAutor(@PathVariable int idProduto) {
        return this.recuperar(idProduto).getAutor();
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Livro inserir(@RequestBody Livro livro) {
        livro.setId(0);
        if (!(livro.getAnoPublicacao() == 0 || livro.getTitulo().equals("") || livro.getTitulo() == null)) {
            return livroDAO.save(livro);
        } else {
            throw new CamposObrigatorios("Todos os campos são obrigatórios");
        }
    }

    @RequestMapping(path = "/{id}/editora/",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Editora inserirEditoraNoLivro(@PathVariable int id,
            @RequestBody Editora editora) {
      editora.setId(0);
        if (editora.getNome().isEmpty() || editora.getNome() == null
                || editora.getCnpj().isEmpty() | editora.getCnpj() == null) {
            throw new CamposObrigatorios("Todos os campos são obrigatórios");
        }if (this.verificaCnpjRepetidoEditora(editora.getCnpj())) {
            throw new CamposObrigatorios(("Cnpj já cadastrado"));
        }
        Editora editoraSalvo = editoraDAO.save(editora);
        Livro livro = this.recuperar(id);
        livro.getEditora().add(editoraSalvo);
        livroDAO.save(livro);
        return editoraSalvo;
    }

    @RequestMapping(path = "/{id}/autor",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Autor inserirAutorNoLivro(@PathVariable int id,
            @RequestBody Autor autor) {
        autor.setId(0);
        if (autor.getPrimeiroNome().isEmpty() || autor.getPrimeiroNome() == null
                || autor.getSegundoNome().isEmpty() || autor.getSegundoNome() == null) {
            throw new CamposObrigatorios("Todos os campos são obrigatórios");
        }
        Autor autorSalvo = autorDAO.save(autor);
        Livro livro = this.recuperar(id);
        livro.getAutor().add(autorSalvo);
        livroDAO.save(livro);
        return autorSalvo;
    }

    @RequestMapping(path = "/{idProduto}/editoras/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagarEditora(@PathVariable int idProduto,
            @PathVariable int id) {
        Editora editoraAchado = null;
        Livro editora = this.recuperar(idProduto);
        List<Editora> editoras = editora.getEditora();
        for (Editora editoraLista : editoras) {
            if (id == editoraLista.getId()) {
                editoraAchado = editoraLista;
            }
        }
        if (editoraAchado != null) {
            editora.getEditora().remove(editoraAchado);
            livroDAO.save(editora);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }
    @RequestMapping(path = "/{idProduto}/autor/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagarAutor(@PathVariable int idProduto,
            @PathVariable int id) {
        Autor autorAchado = null;
        Livro autor = this.recuperar(idProduto);
        List<Autor> autores = autor.getAutor();
        for (Autor editoraLista : autores) {
            if (id == editoraLista.getId()) {
                autorAchado = editoraLista;
            }
        }
        if (autorAchado != null) {
            autor.getAutor().remove(autorAchado);
            livroDAO.save(autor);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
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
    @RequestMapping(path = "/editora/cnpj/{cnpj}", method = RequestMethod.GET)
    public boolean verificaCnpjRepetidoEditora(@PathVariable("cnpj") String cnpj) {
        Optional<Editora> editora = editoraDAO.findAllByCnpj(cnpj);
        return editora.isPresent();
    }

}
