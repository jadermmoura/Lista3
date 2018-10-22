/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.controle;

import br.edu.ifrs.dv.lista3.DAO.BibliotecarioDAO;
import br.edu.ifrs.dv.lista3.erros.EmailJaCadastrado;
import br.edu.ifrs.dv.lista3.erros.NaoEncontrado;
import br.edu.ifrs.dv.lista3.modelo.Bibliotecario;
import java.util.Optional;
import javax.persistence.NonUniqueResultException;
import javax.validation.Valid;
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
 * @author Jader
 */
@RestController
@RequestMapping(path = "/api")
@Valid
public class BibliotecarioControle {

    @Autowired
    BibliotecarioDAO bibliotecarioDAO;

    @RequestMapping(path = "/bibliotecario/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Bibliotecario> buscar() {
        return bibliotecarioDAO.findAll();
    }

    @RequestMapping(path = "/bibliotecario/email/{email}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public boolean buscarEmail(@PathVariable("email") String email) {
        Optional<Bibliotecario> bibliotecario = bibliotecarioDAO.findAllByEmail(email);
       
        return bibliotecario.isPresent();
    }

    @RequestMapping(path = "/bibliotecario/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Bibliotecario recuperar(@PathVariable("id") int id) {
        Optional<Bibliotecario> bibliotecario = bibliotecarioDAO.findAllById(id);
        if (bibliotecario.isPresent()) {
            return bibliotecario.get();

        } else {
            throw new NaoEncontrado("Id não encontrado");

        }
    }
//      Só insere se email não estiver no banco já
    //effefefef
    @RequestMapping(path = "/bibliotecario/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Bibliotecario inserir(@RequestBody Bibliotecario bibliotecario) {
        bibliotecario.setId(0);
        if (!this.buscarEmail(bibliotecario.getEmail())) {
            return bibliotecarioDAO.save(bibliotecario);

        }else{
             throw new EmailJaCadastrado("Email já cadastrado");
        }
    }

    @RequestMapping(path = "/bibliotecario/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id) {

        bibliotecarioDAO.deleteById(id);
//bibliotecarioDAO.deleteAll();

    }

    @RequestMapping(path = "/bibliotecario/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Bibliotecario editar(@PathVariable int id, @RequestBody Bibliotecario bibliotecarioNovo) {
        bibliotecarioNovo.setId(id);

        Bibliotecario bibliotecarioAntigo = this.recuperar(id);
        bibliotecarioAntigo.setNome(bibliotecarioNovo.getNome());
        bibliotecarioAntigo.setEmail(bibliotecarioNovo.getEmail());

        return bibliotecarioDAO.save(bibliotecarioAntigo);

    }

}
