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
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class EmpleadoSolicitudLogic {
    
    private static final Logger LOGGER = Logger.getLogger(EmpleadoSolicitudLogic.class.getName());
    
    @Inject 
    private EmpleadoPersistence empleadoPersistence;
    
    @Inject
    private SolicitudPersistence solicitudPersistence;
    
    
    
    public SolicitudEntity addSolicitud(Long solicitudId, Long empleadoId)
    {
        LOGGER.log(Level.INFO, "Inicia el proceso de agregarle una solicitud al empleado con id = {0}", empleadoId);
        EmpleadoEntity  empleadoEntity = empleadoPersistence.find(empleadoId);
        SolicitudEntity solicitudEtity = solicitudPersistence.find(solicitudId);
        empleadoEntity.getSolicitudes().add(solicitudEtity);
        solicitudEtity.setEmpleado(empleadoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una solicitud al empleado con id = {0}" , empleadoId);
        return solicitudEtity;
    }

/**
     * Retorna un book asociado a una editorial
     *
     * @param empleadoId El id del empleado a buscar.
     * @param solicitudId El id la solicitud a buscar
     * @return La solicitud encontrada dentro de la editorial.
     * @throws BusinessLogicException Si la solicitud no se encuentra en el
     * empleado
     */
    public SolicitudEntity getSolicitud(Long empleadoId, Long solicitudId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la solicitud con id = {0} del empleado con id = " + empleadoId, solicitudId);
        List<SolicitudEntity> solicitudes = empleadoPersistence.find(empleadoId).getSolicitudes();
        SolicitudEntity soliEntity = solicitudPersistence.find(solicitudId);
        int index = solicitudes.indexOf(soliEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la solicitud con id = {0} del empleado con id = " + empleadoId, solicitudId);
        if (index >= 0) {
            return solicitudes.get(index);
        }
        throw new BusinessLogicException("La solicitud no está asociado al empleado");
    }
    /**
     * Obtiene una colección de instancias de SolicitudEntity asociadas a una
     * instancia de Empleado
     *
     * @param empleadoId Identificador de la instancia de Empleado
     * @return Colección de instancias de SolicitudEntity asociadas a la instancia de
     * Empleado
     */
    public List<SolicitudEntity> getSolicitudes(Long empleadoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las solicitudes del empleado con id = {0}", empleadoId);
        return empleadoPersistence.find(empleadoId).getSolicitudes();
    }
	
	/**
     * Obtiene una instancia de SolicitudEntity asociada a una instancia de Empleado
     * @param empleadoId Identificador de la instancia de Empleado
     * @param solicitudId Identificador de la instancia de solicitud
     * @return La entidad de Solicitud del empleado
     * @throws BusinessLogicException Si la solicitud no está asociado al empleado
     */
    
        /**
     * 
     * @param empleadoId empleado a buscar.
     * @param solicitudId solicitud a eliminar.
     * @throws BusinessLogicException 
     */

    public void deleteSolicitud(Long empleadoId, Long solicitudId) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "EmpleadoSolicitudResoruce deleteSolicitud: input: empleadoId {0}, solicitudId: {1}", new Object[]{empleadoId, solicitudId});
        EmpleadoEntity empleado = empleadoPersistence.find(empleadoId);
        SolicitudEntity solicitud = solicitudPersistence.find(solicitudId);
        int index = empleado.getSolicitudes().indexOf(solicitud);
        if(index >= 0)
        {
          empleado.getSolicitudes().remove(index);
        }
        throw new BusinessLogicException("El solicitud no esta asociada al empleado");
    }

}
