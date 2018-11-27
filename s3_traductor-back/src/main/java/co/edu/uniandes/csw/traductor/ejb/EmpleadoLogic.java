/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;


import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import javax.ejb.Stateless;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.EmpleadoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Alvaro Ayte
 */
@Stateless
public class EmpleadoLogic {
    private final static Logger LOGGER = Logger.getLogger(EmpleadoLogic.class.getName());
    
    /**
     * injeccion de persistencia para conectar con la base de datos
     */
    @Inject
    private EmpleadoPersistence persistence; 
    
    /**
     * encargado de persistir un empleado en la base de datos
     * @param empleadoEntity entidad que representa un Empleado, EmpleadoEntity !=null
     * @return El mismo empleadoEntity luego de persistirlo
     * @throws BusinessLogicException si ya existe un Empleado en el sistema con ese mismo id o nombre
     */
    public EmpleadoEntity createEmpleado(EmpleadoEntity empleadoEntity)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del empleado");
        // Verifica la regla de negocio que dice que no puede haber 2 empleados con el mismo nombre de usuario.
        if (persistence.findByNombreUsuario(empleadoEntity.getNombreUsuario()) != null) {
            throw new BusinessLogicException("Ya existe un empleado con el nombre de usuario \"" + empleadoEntity.getNombreUsuario()+ "\"");
        }
        // Verifica la regla de negocio que dice que no puede haber 2 empleados con el mismo correo electronico.
        if (persistence.findByCorreo(empleadoEntity.getCorreoElectronico()) != null) {
            throw new BusinessLogicException("Ya existe un empleados con el correo electronico \"" + empleadoEntity.getCorreoElectronico()+ "\"");
        }
        // Invoca la persistencia para crear el empleado
        persistence.create(empleadoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del empleado");
        return empleadoEntity;
    }
    /**
     * entrega una lista con todos los Empleados almacenados en el sistema
     * @return Lista con los Empleados almacenados en la persitencia de la aplicacion
     */
    public List<EmpleadoEntity> getEmpleados(){
        LOGGER.log(Level.INFO,"Inicia la consulta de todos los Empleados");
        List<EmpleadoEntity> Empleados=persistence.findAll();
        LOGGER.log(Level.INFO, "Termina la consulta");
        return Empleados;
    }
    /**
     * obtiene un Empleado a partir de un id que llega por parametro
     * @param docID id que identifica el Empleado a representar
     * @return La entidad que representa al Empleado que llega por parametro
     */
    public EmpleadoEntity getEmpleado(Long docID){
        LOGGER.log(Level.INFO, "Inicia el proceso de consulta de un empleado con el id = {0}",docID);
        EmpleadoEntity EmpleadoEntity = persistence.find(docID);
        if(EmpleadoEntity==null)
            LOGGER.log(Level.SEVERE,"El empleado con el id = {0} no existe", docID);
        LOGGER.log(Level.INFO, "Termina el proceso de consulta de un empleado con el id={0}",docID);
        return EmpleadoEntity;
    }
    /**
     * encargado de eliminar de la persistencia un Empleado 
     * @param empID id que identifica el Empleado a eliminar del sistema
     * @throws BusinessLogicException  si el Empleado identificado con el id del parametro no existe
     */
    public void deleteEmpleado(Long empID)throws BusinessLogicException{
        List<SolicitudEntity> solicitudes = getEmpleado(empID).getSolicitudes();
        if (solicitudes != null && !solicitudes.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el empleado con id = " + empID + " porque tiene solicitudes asociadas");
        }
        List<PropuestaEntity> propuestas = getEmpleado(empID).getPropuestas();
        if (propuestas != null && !propuestas.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el empleado con id = " + empID + " porque tiene propuestas asociadas");
        }
        List<InvitacionEntity> invitaciones = getEmpleado(empID).getInvitaciones();
        if (invitaciones != null && !invitaciones.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el clinte con id = " + empID + " porque tiene invitaciones asociadas");
        }
        persistence.delete(empID);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un Empleado id={0}", empID);
    }
    /**
     *
     * Actualizar un empleado.
     *
     * @param empleadoId: id del empleado para buscarla en la base de
     * datos.
     * @param empleadoEntity: empleado con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return El empleado con los cambios actualizados en la base de datos.
     */
    public EmpleadoEntity updateEmpleado(Long empleadoId, EmpleadoEntity empleadoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el empleado con id = {0}", empleadoId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        EmpleadoEntity newEntity = persistence.update(empleadoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el emplado con id = {0}", empleadoEntity.getId());
        return newEntity;
    }
}