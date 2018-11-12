/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author ANDRES
 */
@Entity
public class TarjetaDeCreditoEntity extends BaseEntity implements Serializable {

    private Long numeroTarjetaCredito;
    private Integer ccv;
    private Integer mesExpiracion;
    private Integer anioExpiracion;
    private String redBancaria;
    private String nombreTitular;

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    @PodamExclude
    @ManyToOne
    private ClienteEntity cliente;

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public Long getNumeroTarjetaCredito() {
        return numeroTarjetaCredito;
    }

    public void setNumeroTarjetaCredito(Long numeroTarjetaCredito) {
        this.numeroTarjetaCredito = numeroTarjetaCredito;
    }

    public Integer getAnioExpiracion() {
        return anioExpiracion;
    }

    public void setAnioExpiracion(Integer anioExpiracion) {
        this.anioExpiracion = anioExpiracion;
    }

    public Integer getMesExpiracion() {
        return mesExpiracion;
    }

    public void setMesExpiracion(Integer mesExpiracion) {
        this.mesExpiracion = mesExpiracion;
    }

    public Integer getCcv() {
        return ccv;
    }

    public void setCcv(Integer ccv) {
        this.ccv = ccv;
    }

    public String getRedBancaria() {
        return redBancaria;
    }

    public void setRedBancaria(String redBancaria) {
        this.redBancaria = redBancaria;
    }

}
