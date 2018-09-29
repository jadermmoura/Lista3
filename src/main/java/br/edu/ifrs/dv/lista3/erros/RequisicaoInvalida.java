/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.dv.lista3.erros;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author jader
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequisicaoInvalida extends RuntimeException {

    public RequisicaoInvalida(String msg) {
        super(msg);
    }
    
    
}
