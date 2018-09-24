/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.EmpleadoPersistence;
import co.edu.uniandes.csw.traductor.persistence.SolicitudPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
public class EmpleadoSolicitudLogic {
    
    private static final Logger LOGGER = Logger.getLogger(EmpleadoSolicitudLogic.class.getName());
    
    @Inject 
    private EmpleadoPersistence empleadoPersistence;
    
    @Inject
    private SolicitudPersistence solicitudPersitence;
    
    
    
    public SolicitudEntity añadirSolicitud(Long solicitudId, Long empleadoId)
    {
        LOGGER.log(Level.INFO, "Inicia el proceso de agregarle una solicitud al empleado con id = {0}", empleadoId);
        EmpleadoEntity  empleadoEntity = empleadoPersistence.find(empleadoId);
        SolicitudEntity solicitudEtity = solicitudPersitence.find(solicitudId);
        empleadoEntity.getSolicitudes().add(solicitudEtity);
        solicitudEtity.setEmpleado(empleadoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una solicitud al empleado con id = {0}" , empleadoId);
        return solicitudEtity;
    }

/**
     * Retorna un book asociado a una editorial
     *
     * @param empleadoId El id del empleado a buscar.
     * @param solicitudID El id la solicitud a buscar
     * @return La solicitud encontrada dentro de la editorial.
     * @throws BusinessLogicException Si la solicitud no se encuentra en el
     * empleado
     */
    public SolicitudEntity getSolicitud(Long empleadoId, Long solicitudId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la solicitud con id = {0} del empleado con id = " + empleadoId, solicitudId);
        List<SolicitudEntity> solicitudes = empleadoPersistence.find(empleadoId).getSolicitudes();
        SolicitudEntity soliEntity = solicitudPersitence.find(solicitudId);
        int index = solicitudes.indexOf(soliEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la solicitud con id = {0} del empleado con id = " + empleadoId, solicitudId);
        if (index >= 0) {
            return solicitudes.get(index);
        }
        throw new BusinessLogicException("El libro no está asociado a la editorial");
    }

}
