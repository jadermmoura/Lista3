/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.controle;

import br.edu.ifrs.dv.lista3.DAO.AutorDAO;
import br.edu.ifrs.dv.lista3.DAO.EmprestimoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.edu.ifrs.dv.lista3.DAO.UsuarioDAO;
import br.edu.ifrs.dv.lista3.erros.CamposObrigatorios;
import br.edu.ifrs.dv.lista3.erros.EmailJaCadastrado;
import br.edu.ifrs.dv.lista3.erros.NaoEncontrado;
import br.edu.ifrs.dv.lista3.modelo.Emprestimo;
import br.edu.ifrs.dv.lista3.modelo.Telefone;
import br.edu.ifrs.dv.lista3.modelo.Usuario;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author jader
 */
@RestController
@RequestMapping(path = "/api/usuario")
//@Valid
public class UsuarioControle {

    @Autowired
    UsuarioDAO usuarioDAO;
    @Autowired
    AutorDAO autorDAO;
    @Autowired
    EmprestimoDAO emprestimoDAO;
    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Usuario> buscar() {
        return usuarioDAO.findAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Usuario recuperar(@PathVariable int id) {
        Optional<Usuario> usuarioId = usuarioDAO.findAllById(id);
        if (usuarioId.isPresent()) {
            return usuarioId.get();
   
        } else {
            throw new NaoEncontrado("Id não encontrado");
        }
    }
   @RequestMapping(path = "/todosEmprestimosUsuario/{id}", method = RequestMethod.GET)
    public Iterable<Emprestimo> todosEmprestimosUsuario(@PathVariable int id) {
        Usuario user = usuarioDAO.findById(id).get();
        return emprestimoDAO.usuario(user);
    }
    @RequestMapping(path = "/nome/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Usuario> buscarNome(@PathVariable("nome") String nome) {
        return usuarioDAO.findByNome(nome);
    }

    @RequestMapping(path = "/cpf/{cpf}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Usuario> buscarCpf(@PathVariable("cpf") String cpf
    ) {
        return usuarioDAO.findByCpf(cpf);
    }
    
    @RequestMapping(path = "/email/{email}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public boolean buscarEmail(@PathVariable("email") String email) {
        Optional<Usuario> usuario = usuarioDAO.findAllByEmail(email);
        return usuario.isPresent();
    }

    @RequestMapping(path = "/{idProduto}/telefones/", method = RequestMethod.GET)
    public Iterable<Telefone> listarTelefonePeloIdUsuario(@PathVariable int idProduto) {
        return this.recuperar(idProduto).getTelefones();
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario inserirUsuario(@RequestBody Usuario usuarioNovo) {
        usuarioNovo.setId(0);
        if (usuarioNovo.getCpf().equals("") || usuarioNovo.getCpf() == null
                || usuarioNovo.getEmail().equals("") || usuarioNovo.getEmail() == null 
                || usuarioNovo.getNome().equals("") || usuarioNovo.getNome() == null 
                || usuarioNovo.getTelefones().equals("") || usuarioNovo.getTelefones() == null ) {
            throw new CamposObrigatorios("Todos os campos são obrigatórios");
        }
        if (this.buscarEmail(usuarioNovo.getEmail())) {
            throw new EmailJaCadastrado(("Email já cadastrado"));
        }
        return usuarioDAO.save(usuarioNovo);
    }

    @RequestMapping(path = "/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete() {

        usuarioDAO.deleteAll();

    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id) {

        usuarioDAO.deleteById(id);

    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Usuario editar(@PathVariable int id, @RequestBody Usuario usuarioNovo) {
        usuarioNovo.setId(id);

        Usuario usuarioAntigo = this.recuperar(id);
        usuarioAntigo.setNome(usuarioNovo.getNome());
        usuarioAntigo.setCpf(usuarioNovo.getCpf());
        usuarioAntigo.setEmail(usuarioNovo.getEmail());
        usuarioAntigo.setTelefones(usuarioNovo.getTelefones());

        return usuarioDAO.save(usuarioAntigo);

    }

}
