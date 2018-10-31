/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.controle;

import br.edu.ifrs.dv.lista3.DAO.EmprestimoDAO;
import br.edu.ifrs.dv.lista3.DAO.LivroDAO;
import br.edu.ifrs.dv.lista3.erros.CamposObrigatorios;
import br.edu.ifrs.dv.lista3.erros.DataDevolucao;
import br.edu.ifrs.dv.lista3.modelo.Bibliotecario;
import br.edu.ifrs.dv.lista3.modelo.Emprestimo;
import br.edu.ifrs.dv.lista3.modelo.Livro;
import br.edu.ifrs.dv.lista3.modelo.Usuario;
import java.time.LocalDate;
import java.util.ArrayList;
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
@RequestMapping(path = "api/emprestimos")
public class EmprestimoControle {

    @Autowired
    EmprestimoDAO emprestimoDAO;

    @Autowired
    LivroDAO livroDAO;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public Iterable<Emprestimo> buscarTodos() {
        return emprestimoDAO.findAll();
    }

    @RequestMapping(path = "/", method = RequestMethod.DELETE)
    public void apagaTodos() {
        emprestimoDAO.deleteAll();
    }

    @RequestMapping(path = "/livro/titulo/{titulo}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public boolean verificaCnpjRepetido(@PathVariable("titulo") String titulo) {
        Optional<Livro> livroEmprestado = emprestimoDAO.findAllById(titulo);
        return livroEmprestado.isPresent();
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Emprestimo inserir(@RequestBody Emprestimo emprestimo) {
        emprestimo.setId(0);
        LocalDate hoje = LocalDate.now();
        emprestimo.setRetirada(hoje);
        LocalDate diaPrevisao = hoje.plusDays(7);
        emprestimo.setPrevisaoDevolucao(diaPrevisao);
        if (!(emprestimo.getBibliotecario() instanceof Bibliotecario && emprestimo.getUsuario() instanceof Usuario && emprestimo.getLivro() instanceof Livro)) {
            throw new CamposObrigatorios("Todos campos são obrigatórios");
        }//verifica se data devolução maior que data atual 
        if (emprestimo.getDevolucao() != null && emprestimo.getDevolucao().isBefore(hoje)) {
            throw new DataDevolucao("Data não pode ser menor que data atual");
        }

        return emprestimoDAO.save(emprestimo);

    }
//  @RequestMapping(path = "/buscar/{data}",method = RequestMethod.GET)
//   public Iterable<Emprestimo> buscar(@RequestBody LocalDate data) {
//      Iterable<Emprestimo> todos = emprestimoDAO.findAll();
//      ArrayList<Emprestimo> emprestimos = new ArrayList<>();
//      for (Emprestimo emprestimo : todos) {
//           if(emprestimo.getDevolucao().isAfter(data)){
//                emprestimos.add(emprestimo);
//            }
//       }
//       return emprestimos;
//   }

}
