/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.PagosEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author ANDRES
 */
public class PagosDTO {

    private Boolean pagoAprobado;
    private Long idTransaccion;

    public PagosDTO() {

    }
    
    public PagosDTO(PagosEntity pago)
    {
        pagoAprobado=pago.getPagoAprobado();
        idTransaccion=pago.getIdTransaccion();
    }
    public PagosEntity toEntity()
	{
		PagosEntity pago = new PagosEntity();
		pago.setIdTransaccion(idTransaccion);
                pago.setPagoAprobado(pagoAprobado);
		return pago;
	}	
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
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
