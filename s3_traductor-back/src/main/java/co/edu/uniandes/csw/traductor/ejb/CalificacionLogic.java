/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.CalificacionEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.traductor.persistence.IdiomaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Juan Felipe Parra
 */
public class CalificacionLogic {
    private final static Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());
    
    @Inject
    private CalificacionPersistence persistence; 
    /**
     * encargado de persistir una calificacion en la base de datos
     * @param calificacionEntity  entidad que representa la informacion a persistir en la base de datos
     * @return la misma entidad que es persistida en el metodo
     * @throws BusinessLogicException  si ya existe una calificacion con ese mismo id
     */
    public CalificacionEntity createCalificacion(CalificacionEntity calificacionEntity)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"Inicia el proceso de creacion de un idioma");
        
        if(persistence.find(calificacionEntity.getId()) !=null){
            throw new BusinessLogicException("Esta calificacion ya existe en el sistema");
            
        }
        persistence.create(calificacionEntity);
        LOGGER.log(Level.INFO,"Termina el proceso de creacion de la calificacion");
        return calificacionEntity;
    }
    /**
     * reotrna todas las calificaciones almacenadas en el sistema
     * @return  Lista de las calificaciones almacenadas en el sistes
     */
    public List<CalificacionEntity> getCalificaciones(){
        LOGGER.log(Level.INFO,"Imicia la consulta de todas las califiacaciones");
        List<CalificacionEntity> idiomas=persistence.findAll();
        LOGGER.log(Level.INFO, "Termina la consulta");
        return idiomas;
    }
    /**
     * retorna la entidad que representa la informacion  de la entidad buscada por id
     * @param calificacionID identifiacdor mediante el cual se va  buscar la informacion de la entidad a retornar
     * @return la entidad que representa la informacion de la calificacion buscada
     */
    public CalificacionEntity getCalificacion(Long calificacionID)throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia la consulta de calificacion segun el id "+calificacionID);
        CalificacionEntity calificacionEntity=persistence.find(calificacionID);
        if(calificacionEntity==null){
                        LOGGER.log(Level.SEVERE,"La calificacion con el id = {0} no existe", calificacionID);
                        throw new BusinessLogicException("La calificacion con el id: "+calificacionID+" no existe");
        }
        LOGGER.log(Level.INFO, "Termina el proceso de consulta de una calificacion con el id={0}",calificacionID);
        return calificacionEntity;
    }
}
