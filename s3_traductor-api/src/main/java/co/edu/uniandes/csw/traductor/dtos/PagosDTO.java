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
    private PropuestaDTO propuestaDto;

    public Long getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Long id) {
        this.idTransaccion = id;
    }

    public PagosDTO() {
    }

    public PagosDTO(PagosEntity entity) {
        if (entity != null) {
            this.idTransaccion = entity.getId();
            this.pagoAprobado = entity.getPagoAprobado();
        }
    }

    public PagosEntity toEntity() {
        PagosEntity pagoEntity = new PagosEntity();
        pagoEntity.setId(this.idTransaccion);
        pagoEntity.setPagoAprobado(this.pagoAprobado);
        return pagoEntity;
    }

    public Boolean getPagoAprobado() {
        return pagoAprobado;
    }

    public void setPagoAprobado(Boolean pagoAprobado) {
        this.pagoAprobado = pagoAprobado;
    }

    public PropuestaDTO getPropuestaDto() {
        return propuestaDto;
    }

    public void setPropuestaDto(PropuestaDTO propuestaDto) {
        this.propuestaDto = propuestaDto;
    }
}
