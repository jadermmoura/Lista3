/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.controle;

import br.edu.ifrs.dv.lista3.DAO.EmprestimoDAO;
import br.edu.ifrs.dv.lista3.modelo.Emprestimo;
import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    
    @RequestMapping(path = "/",method = RequestMethod.GET)
    public Iterable<Emprestimo> emprestimos(){
        return emprestimoDAO.findAll();
    }
    
    @RequestMapping(path = "/",method = RequestMethod.POST)
    public Emprestimo inserir(@RequestBody Emprestimo emprestimo) {
        emprestimo.setId(0);
        LocalDate hoje = LocalDate.now();
        emprestimo.setRetirada(hoje);
        LocalDate diaPrevisao = hoje.plusDays(7);
        emprestimo.setPrevisaoDevolucao(diaPrevisao);
        if (emprestimo.getLivro().equals("") || emprestimo.getLivro().equals("null")
       || emprestimo.getUsuario().equals("") || emprestimo.getUsuario().equals("null")     
       || emprestimo.getBibliotecario().equals("") || emprestimo.getBibliotecario().equals("null")) {
            
        }
        return emprestimoDAO.save(emprestimo);
    }

}
