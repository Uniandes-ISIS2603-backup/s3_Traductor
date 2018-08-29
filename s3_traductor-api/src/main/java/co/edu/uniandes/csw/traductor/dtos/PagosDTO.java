/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.PagosEntity;

/**
 *
 * @author ANDRES
 */
public class PagosDTO {

    private Boolean pagoAprobado;
    private Long idTransaccion;

    public PagosDTO() {

    }

    public PagosDTO(PagosEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Boolean getPagoAprobado() {
        return pagoAprobado;
    }

    public void setPagoAprobado(Boolean pagoAprobado) {
        this.pagoAprobado = pagoAprobado;
    }

    public Long getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

}
