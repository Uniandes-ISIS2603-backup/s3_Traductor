/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.PagosEntity;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.persistence.PagosPersistence;
import co.edu.uniandes.csw.traductor.persistence.PropuestaPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
public class PagoPropuestaLogic {
    
        private static final Logger LOGGER = Logger.getLogger(PagoPropuestaLogic.class.getName());

    @Inject
    private PagosPersistence pagoPersistence;

    @Inject
    private PropuestaPersistence propuestaPersistence;
    
    /**
     * Remplazar la editorial de un book.
     *
     * @param idTransaccion id del libro que se quiere actualizar.
     * @param idPropuesta El id de la editorial que se será del libro.
     * @return el nuevo libro.
     */
    public PagosEntity replacePropuesta(Long idCliente,Long idTransaccion, Long idPropuesta) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar libro con id = {0}", idTransaccion);
        PagosEntity pagosEntity = pagoPersistence.find(idCliente,idPropuesta);
        PropuestaEntity propuestaEntity = propuestaPersistence.find(idPropuesta);
        pagosEntity.setPropuesta(propuestaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar propuesta con id = {0}", propuestaEntity.getId());
        return pagosEntity;
    }
}
