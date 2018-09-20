/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.CalificacionEntity;
import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.traductor.persistence.EmpleadoPersistence;
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
    public   CalificacionEntity  addCalificaion(Long empleadoId,Long calificacionId){
        LOGGER.log(Level.INFO,"Inicia proceso de asocicacion de calificacion a un empleado con id {0}",empleadoId);
        //EmpleadoEntity entidadSuperior=empleadoPersistence.find(clienteId);
        CalificacionEntity entidadInferior=calificacionPersistence.find(calificacionId);
        //entidadSuperior.getCalificaciones().add(entidadInferior);
        LOGGER.log(Level.INFO,"Termina proceso de agregar una calificacion a un empleado con id {0}",empleadoId);
        return calificacionPersistence.find(calificacionId);
    }

}
