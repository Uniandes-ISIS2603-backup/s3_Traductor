/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import java.util.Collection;

/**
 *
 * @author ANDRES
 */
public class TarjetaDeCreditoDetailDTO {
    private Collection<pagosDTO>pagos;

    public Collection<pagosDTO> getPagos() {
        return pagos;
    }

    public void setPagos(Collection<pagosDTO> pagos) {
        this.pagos = pagos;
    }

    public TarjetaDeCreditoDetailDTO() {
        
    }

}