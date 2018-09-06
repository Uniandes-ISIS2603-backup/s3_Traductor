/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author ANDRES
 */
@Entity
public class PagosEntity extends BaseEntity implements Serializable {

    private Boolean pagoAprobado;
    @PodamExclude
    @ManyToOne
    private TarjetaDeCreditoEntity tarjeta;

    public TarjetaDeCreditoEntity getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(TarjetaDeCreditoEntity tarjeta) {
        this.tarjeta = tarjeta;
    }

    public PagosEntity() {

    }

    public Boolean getPagoAprobado() {
        return pagoAprobado;
    }

    public void setPagoAprobado(Boolean pagoAprobado) {
        this.pagoAprobado = pagoAprobado;
    }


}
