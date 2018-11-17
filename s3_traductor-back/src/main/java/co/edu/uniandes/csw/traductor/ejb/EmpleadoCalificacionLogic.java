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
        LOGGER.log(Level.INFO,"Inicia proceso de asocicacion de calificacion a un empleado con id {0}",empleadoId);
        EmpleadoEntity empleadoEntity=empleadoPersistence.find(empleadoId);
        CalificacionEntity calificacionEntity=calificacionPersistence.find(calificacionId);
        calificacionEntity.setEmpleado(empleadoEntity);
        empleadoEntity.getCalificaciones().add(calificacionEntity);
        LOGGER.log(Level.INFO,"Termina proceso de agregar una calificacion a un empleado con id {0}",empleadoId);
        return calificacionEntity;
    }
    /**
     * Metodo que permite consultar una calificacion de un empleado especifico
     * 
     * @param idCalificacion la id del la calificacion que se desea consultar
     * @param idEmpleado la id del empleado sobre el cual se hara la consulta
     * @return Calificacion consultada
     * @throws BusinessLogicException en caso que la calificacion no este asociada al empleado
     */
    public CalificacionEntity getCalificacion(Long idCalificacion,Long idEmpleado) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la calificacion con id = {0} de del empleado con id = " + idEmpleado, idCalificacion);
        List<CalificacionEntity> caliicaciones = empleadoPersistence.find(idEmpleado).getCalificaciones();
        CalificacionEntity calificacionEntity = calificacionPersistence.find(idCalificacion);
        int index = caliicaciones.indexOf(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la califiacion con id = {0} del empleado con id = " + idEmpleado, idCalificacion);
        if (index >= 0) {
            return caliicaciones.get(index);
        }
        throw new BusinessLogicException("la calificion no está asociada al empleado");
    }
    /**
     * Metodo que permite consultar todas las calificaciones asociadas a un empleado
     * 
     * @param idEmpleado id del empleado del cual se desea conocer las calificaciones
     * @return lista de calificaciones asociadas a un empleado
     */
    public List<CalificacionEntity> getCalificaciones(Long idEmpleado) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las calificaciones asociados al empleado con id = {0}", idEmpleado);
        return empleadoPersistence.find(idEmpleado).getCalificaciones();
    }
}
