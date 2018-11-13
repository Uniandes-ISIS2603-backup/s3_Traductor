/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.IdiomaEntity;
import javax.ejb.Stateless;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.IdiomaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Juan Felipe Parra
 */
@Stateless
public class IdiomaLogic {

    private final static Logger LOGGER = Logger.getLogger(IdiomaLogic.class.getName());

    /**
     * injeccion de persistencia para conectar con la base de datos
     */
    @Inject
    private IdiomaPersistence persistence;

    /**
     * encargado de persistir un idioma en la base de datos
     *
     * @param idiomaEntity entidad que representa un idioma, idiomaEntity !=null
     * @return El mismo idiomaEntity luego de persistirlo
     * @throws BusinessLogicException si ya existe un idioma en el sistema con
     * ese mismo id o nombre
     */
    public IdiomaEntity createIdioma(IdiomaEntity idiomaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia el proceso de creacion de un idioma");

        if (persistence.findByName(idiomaEntity.getIdioma()) != null) {
            throw new BusinessLogicException("Este idioma ya existe en el sistema");
        }
        persistence.create(idiomaEntity);
        LOGGER.log(Level.INFO, "Termina el proceso de creacion del idioma");
        return idiomaEntity;
    }

    /**
     * entrega una lista con todos los idiomas almacenados en el sistema
     *
     * @return List<IdiomaEntity> con los idiomas almacenados en la persitencia
     * de la aplicacion
     */
    public List<IdiomaEntity> getIdiomas() {
        LOGGER.log(Level.INFO, "Imicia la consulta de todos los idiomas");
        List<IdiomaEntity> idiomas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina la consulta");
        return idiomas;
    }

    /**
     * obtiene un idioma a partir de un id que llega por parametro
     *
     * @param idiomaID id que identifica el idioma a representar
     * @return La entidad que representa al idioma que llega por parametro
     */
    public IdiomaEntity getIdioma(Long idiomaID) {
        LOGGER.log(Level.INFO, "Inicia la consulta de idioma segun el id={0}", idiomaID);
        IdiomaEntity idiomaEntity = persistence.find(idiomaID);
        if (idiomaEntity == null) {
            LOGGER.log(Level.SEVERE, "El idioma con el id = {0} no existe", idiomaID);
        }
        LOGGER.log(Level.INFO, "Termina el proceso de consulta de un idioma con el id={0}", idiomaID);
        return idiomaEntity;
    }

    /**
     * encargado de eliminar de la persistencia un idioma
     *
     * @param idiomaID id que identifica el idioma a eliminar del sistema
     */
    public void deleteIdioma(Long idiomaID) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un idioma con id = {0}", idiomaID);
        persistence.delete(idiomaID);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un idioma id={0}", idiomaID);
    }
}
