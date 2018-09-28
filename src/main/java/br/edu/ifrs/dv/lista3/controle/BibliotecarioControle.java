/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.controle;

import br.edu.ifrs.dv.lista3.DAO.BibliotecarioDAO;
import br.edu.ifrs.dv.lista3.DAO.UsuarioDAO;
import br.edu.ifrs.dv.lista3.erros.NaoEncontrado;
import br.edu.ifrs.dv.lista3.modelo.Bibliotecario;
import br.edu.ifrs.dv.lista3.modelo.Usuario;
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
 * @author Jader
 */
@RestController
@RequestMapping(path = "api/bibliotecario")
public class BibliotecarioControle {
    @Autowired
    BibliotecarioDAO bibliotecarioDAO;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Bibliotecario> buscar() {
        return null;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Bibliotecario recuperar(@PathVariable int id) {
        Optional<Bibliotecario> bibliotecario = bibliotecarioDAO.findAllById(id);
        if (bibliotecario.isPresent()) {
            return bibliotecario.get();

        } else {
            throw new NaoEncontrado("Id não encontrado");

        }
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Bibliotecario inserir(@RequestBody Bibliotecario bibliotecario) {
        bibliotecario.setId(0);
        return bibliotecarioDAO.save(bibliotecario);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id) {

        bibliotecarioDAO.deleteById(id);

    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Bibliotecario editar(@PathVariable int id, @RequestBody Bibliotecario bibliotecarioNovo) {
        bibliotecarioNovo.setId(id);

        Bibliotecario bibliotecarioAntigo = this.recuperar(id);
        bibliotecarioAntigo.setNome(bibliotecarioNovo.getNome());
        bibliotecarioAntigo.setEmail(bibliotecarioNovo.getEmail());

        return bibliotecarioDAO.save(bibliotecarioAntigo);

    }

}
