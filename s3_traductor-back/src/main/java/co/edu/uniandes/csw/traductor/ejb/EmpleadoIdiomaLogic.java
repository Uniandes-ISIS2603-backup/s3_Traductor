/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.entities.IdiomaEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.EmpleadoPersistence;
import co.edu.uniandes.csw.traductor.persistence.IdiomaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Alvaro
 */
public class EmpleadoIdiomaLogic {
     private final static Logger LOGGER=Logger.getLogger(EmpleadoIdiomaLogic.class.getName());
    
    @Inject
    private EmpleadoPersistence empleadoPersistence;
    @Inject
    private IdiomaPersistence idiomaPersistence;
    /**
     * 
     * @param empleadoId identificador del empleado 
     * @param idiomaId identificador del id a asignar
     * @return la entidad encontrada luego de la persistencia
     */
    public   IdiomaEntity  addIdioma(Long empleadoId,Long idiomaId){
       LOGGER.log(Level.INFO, "Inicia proceso de agregarle un idioma id = {0}", empleadoId);
        EmpleadoEntity empleadoEntity = empleadoPersistence.find(empleadoId);
        IdiomaEntity idiomaEntity = idiomaPersistence.find(idiomaId);
        empleadoEntity.getIdiomas().add(idiomaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un idioma al empleado con id = {0}", empleadoId);
        return idiomaEntity;
    }
    /**
     * Retorna todas los idiomas del empleado buscado
     *
     * @param empleadoId El ID de el empleado buscado
     * @return La lista de idiomas del empleado
     */
    public List<IdiomaEntity> getIdiomas(Long empleadoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los idiomas asociadas al empleado con id = {0}", empleadoId);
        return empleadoPersistence.find(empleadoId).getIdiomas();
    }
    /**
     * Retorna un idioma asociada a un empleado
     *
     * @param empleadoId  El id del empleado a buscar.
     * @param idiomaId  El id del idioma a buscar
     * @return El idioma encontrada dentro del empleado.
     * @throws BusinessLogicException si el idioma no se encuentra en el empleado
     */
    public IdiomaEntity getIdioma(Long empleadoId,Long idiomaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el idioma con id = {0} del empleado con id = " + empleadoId, idiomaId);
        List<IdiomaEntity> idiomas = empleadoPersistence.find(empleadoId).getIdiomas();
        IdiomaEntity idiomaEntity = idiomaPersistence.find(idiomaId);
        int index = idiomas.indexOf(idiomaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el idioma con id = {0} del empleado con id = " + empleadoId, idiomaId);
        if (index >= 0) {
            return idiomas.get(index);
        }
        throw new BusinessLogicException("El idioma no esta asociada al empleado");
    }
    /**
     * 
     * @param empleadoId empleado a buscar.
     * @param idiomaId idioma a eliminar.
     * @throws BusinessLogicException 
     */

    public void deleteIdioma(Long empleadoId, Long idiomaId) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "EmpleadoIdiomaResoruce deleteIdioma: input: empleadoId {0}, idiomaId: {1}", new Object[]{empleadoId, idiomaId});
        EmpleadoEntity empleado = empleadoPersistence.find(empleadoId);
        IdiomaEntity idioma = idiomaPersistence.find(idiomaId);
        int index = empleado.getIdiomas().indexOf(idioma);
        if(index >= 0)
        {
          empleado.getIdiomas().remove(index);
        }
        throw new BusinessLogicException("El idioma no esta asociada al empleado");
    }
}
