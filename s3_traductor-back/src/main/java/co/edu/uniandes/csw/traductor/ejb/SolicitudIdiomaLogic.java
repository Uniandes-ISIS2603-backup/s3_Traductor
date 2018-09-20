/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.IdiomaEntity;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import co.edu.uniandes.csw.traductor.persistence.IdiomaPersistence;
import co.edu.uniandes.csw.traductor.persistence.SolicitudPersistence;
import java.util.logging.*;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan Felipe Parra
 */
@Stateless
public class SolicitudIdiomaLogic {
    /**
     * conexion de log
     */
    private final static Logger LOGGER = Logger.getLogger(SolicitudIdiomaLogic.class.getName());
    /**
     * persistencia asociada a la solicitud
     */
    @Inject
    private SolicitudPersistence solicitudPersistence;
    /**
     * persistencia asociada al idioma
     */
    @Inject
    private IdiomaPersistence idiomaPersistence;
    /**
     * asociar un idioma a la solicitud indicasa
     * @param solicitudId identificador de la solicitud a asociar
     * @param idiomaId identificador del idioma a asociar
     * @return el idioma descrito luego de ser persistido en el sistema
     */
    public IdiomaEntity getIdioma(Long solicitudId,Long idiomaId){
        LOGGER.log(Level.INFO,"Inicia proceso de asociarle un idioma a la solicitud con id {0}",solicitudId);
        SolicitudEntity entidadSuperior=solicitudPersistence.find(solicitudId);
        IdiomaEntity entidadInferior=idiomaPersistence.find(idiomaId);
        entidadSuperior.setIdiomaSalida(entidadInferior);
        LOGGER.log(Level.INFO,"Termina proceso de asociacion a una solicitud con id {0} un idioma",solicitudId);
        return idiomaPersistence.find(idiomaId);
    }
}
