/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;


import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
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
     * @param EmpleadoEntity entidad que representa un Empleado, EmpleadoEntity !=null
     * @return El mismo empleadoEntity luego de persistirlo
     * @throws BusinessLogicException si ya existe un Empleado en el sistema con ese mismo id o nombre
     */
    public EmpleadoEntity createEmpleado(EmpleadoEntity EmpleadoEntity)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"Inicia el proceso de creacion de un Empleado");
        
        if(persistence.find(EmpleadoEntity.getId()) !=null)
            throw new BusinessLogicException("Este Empleado ya existe en el sistema");
        persistence.create(EmpleadoEntity);
        LOGGER.log(Level.INFO,"Termina el proceso de creacion del Empleado");
        return EmpleadoEntity;
    }
    /**
     * entrega una lista con todos los Empleados almacenados en el sistema
     * @return List<EmpleadoEntity> con los Empleados almacenados en la persitencia de la aplicacion
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
        LOGGER.log(Level.INFO, "Inicia la consulta de empleado segun el id "+docID);
        EmpleadoEntity EmpleadoEntity=persistence.find(docID);
        if(EmpleadoEntity==null)
            LOGGER.log(Level.SEVERE,"El empleado con el id = {0} no existe", docID);
        LOGGER.log(Level.INFO, "Termina el proceso de consulta de un empleado con el id={0}",docID);
        return EmpleadoEntity;
    }
    /**
     * encargado de eliminar de la persistencia un Empleado 
     * @param docID id que identifica el Empleado a eliminar del sistema
     * @throws BusinessLogicException  si el Empleado identificado con el id del parametro no existe
     */
    public void deleteEmpleado(Long docID)throws BusinessLogicException{
        if(this.getEmpleado(docID)==null)
            throw new BusinessLogicException("El Empleado con id= "+docID+" no existe, por lo tanto no se puede borrar");
        persistence.delete(docID);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un Empleado id={0}", docID);
    }
}