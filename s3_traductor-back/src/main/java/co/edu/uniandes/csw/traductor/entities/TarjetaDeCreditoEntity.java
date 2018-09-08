/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
    @Temporal(TemporalType.DATE)
    private Date fechaExpiracion;
    private String redBancaria;
    
    @PodamExclude
    @OneToMany(mappedBy = "tarjeta")
    private List<PagosEntity> pagos = new ArrayList<PagosEntity>();
    
    public List<PagosEntity> getPagos() {
        return pagos;
    }

    public void setPagos(List<PagosEntity> pagos) {
        this.pagos = pagos;
    }

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
