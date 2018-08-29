/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.util.Date;

/**
 *
 * @author ANDRES
 */
public class TarjetaDeCreditoEntity {

    private Long numeroTarjetaCredito;
    private Integer ccv;
    private Date fechaExpiracion;
    private String redBancaria; 

public TarjetaDeCreditoEntity() {
        
    }


    public Long getNumeroTarjetaCredito() {
        return numeroTarjetaCredito;
    }

    public void setNumeroTarjetaCredito(Long numeroTarjetaCredito) {
        this.numeroTarjetaCredito = numeroTarjetaCredito;
    }

    public Integer getCcv() {
        return ccv;
    }

    public void setCcv(Integer ccv) {
        this.ccv = ccv;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getRedBancaria() {
        return redBancaria;
    }

    public void setRedBancaria(String redBancaria) {
        this.redBancaria = redBancaria;
    }
    
   
}
