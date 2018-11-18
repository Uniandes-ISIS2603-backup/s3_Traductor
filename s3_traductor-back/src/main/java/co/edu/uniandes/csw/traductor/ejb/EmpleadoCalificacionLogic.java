/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.CalificacionEntity;
import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.traductor.persistence.EmpleadoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan Felipe Parra
 */
@Stateless
public class EmpleadoCalificacionLogic {
    private final static Logger LOGGER=Logger.getLogger(EmpleadoCalificacionLogic.class.getName());
    
    @Inject
    private EmpleadoPersistence empleadoPersistence;
    @Inject
    private CalificacionPersistence calificacionPersistence;
    /**
     * agrega una calificacion al empleado indicado por el parametro
     * @param empleadoId identificador del empleado al cual se le asignara una calificacion
     * @param calificacionId identificador del id a asignar
     * @return la entidad encontrada luego de la persistencia
     */
    public   CalificacionEntity  addCalificacion(Long empleadoId,Long calificacionId){
       LOGGER.log(Level.INFO, "Inicia proceso de agregarle una calificacion al empleado con id = {0}", empleadoId);
        EmpleadoEntity empleadoEntity = empleadoPersistence.find(empleadoId);
        CalificacionEntity calificacionEntity = calificacionPersistence.find(calificacionId);
        calificacionEntity.setEmpleado(empleadoEntity);
        empleadoEntity.getCalificaciones().add(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una calificacion al empleado con id = {0}", empleadoId);
        return calificacionEntity;
    }
    /**
     * Retorna todos las calificaciones del empleado buscado
     *
     * @param empleadoId El ID de el empleado buscado
     * @return La lista de calificaciones del empleado
     */
    public List<CalificacionEntity> getCalificaciones(Long empleadoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las calificaciones asociadas al empleado con id = {0}", empleadoId);
        return empleadoPersistence.find(empleadoId).getCalificaciones();
    }
    /**
     * Retorna una calificacion asociada a un empleado
     *
     * @param empleadoId  El id del empleado a buscar.
     * @param calificacionId  El id de la calificacion a buscar
     * @return La calificacion encontrada dentro del empleado.
     * @throws BusinessLogicException si la califiacion no se encuentra en el empleado
     */
    public CalificacionEntity getCalificacion(Long empleadoId,Long calificacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la calificacion con id = {0} del empleado con id = " + empleadoId, calificacionId);
        List<CalificacionEntity> calificaciones = empleadoPersistence.find(empleadoId).getCalificaciones();
        CalificacionEntity calificacionEntity = calificacionPersistence.find(calificacionId);
        int index = calificaciones.indexOf(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la calificacion con id = {0} del empleado con id = " + empleadoId, calificacionId);
        if (index >= 0) {
            return calificaciones.get(index);
        }
        throw new BusinessLogicException("La calificacion no esta asociada al empleado");
    }
}
