/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.PagosEntity;
import java.io.Serializable;

/**
 *
 * @author ANDRES
 */
public class PagosDTO implements Serializable {

    private Boolean pagoAprobado;
    private Long idTransaccion;

    public Long getId() {
        return idTransaccion;
    }

    public void setId(Long id) {
        this.idTransaccion = id;
    }

    public PagosDTO() {

    }

    public PagosDTO(PagosEntity entity) {
        if(entity!=null){
            pagoAprobado=entity.getPagoAprobado();
        }
            }

    public Boolean getPagoAprobado() {
        return pagoAprobado;
    }

    public void setPagoAprobado(Boolean pagoAprobado) {
        this.pagoAprobado = pagoAprobado;
    }

   
}
