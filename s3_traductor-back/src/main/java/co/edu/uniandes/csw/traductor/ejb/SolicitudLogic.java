/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.DocumentoEntity;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.SolicitudPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa la conecion con la persostencia para la entidad
 * Solicitud.
 *
 * @author Jhonattan Fonseca
 */
public class SolicitudLogic {

    private static final Logger LOGGER = Logger.getLogger(SolicitudLogic.class.getName());

    @Inject
    private SolicitudPersistence persistence;

    public SolicitudEntity createSolicitud(SolicitudEntity solicitudEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia el proceso de cracion de la solicitud");
        if (solicitudEntity.getEstado() != SolicitudEntity.EN_ESPERA) {
            throw new BusinessLogicException("El estado de la solicitud es invlido");
        }
        persistence.create(solicitudEntity);
        LOGGER.log(Level.INFO, "Termina el proceso de cracion de la solicitud");
        return solicitudEntity;
    }

    /**
     * Busca un libro por ID.
     *
     * @param solicitudId El id de la solicitud buscada.
     * @return La solicitud econtrada, null si no lo encuentra.
     */
    public SolicitudEntity getSolicitud(Long solicitudId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar una solicitud especifica con id = {0} ", solicitudId);
        SolicitudEntity solicitudEntity = persistence.find(solicitudId);
        if (solicitudId == null) {
            LOGGER.log(Level.SEVERE, "La solicitud con id = {0} no existe", solicitudId);
        }

        LOGGER.log(Level.INFO, "Inicia proceso de consultar una solicitud especifica con id = {0} ", solicitudId);

        return solicitudEntity;

    }

    /**
     * Obtiene la lista de lso registros de SolicitudEntity.
     *
     * @return Collección de objetos de SolicitudEntity.
     */
    public List<SolicitudEntity> getSolicitudes() {
        LOGGER.log(Level.INFO, "Comienza el proceso para consultar los documentos");
        List<SolicitudEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar ");
        return lista;
    }

    public void deleteSolicitud(Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia el proceso de borrar el libro con el id = {0}", id);
        SolicitudEntity solicitudEntity = persistence.find(id);
        //Se arregla el bug aparecido en Sonarcube debido a la confirmacion de un null inutil.
        //Geovanny Gonzalez.

        if (solicitudEntity == null) {
            throw new WebApplicationException("No se encontro la solicitud con el id:" + id, 404);
        } else if (SolicitudEntity.EN_ESPERA.intValue() == solicitudEntity.getEstado().intValue()) {
            throw new BusinessLogicException("La solicitud con id = " + id + " no se puede eliminar porque esta en espera de una respuesta");
        }

        persistence.delete(id);
    }

    public void cambiarEstado(Long id) {

        LOGGER.log(Level.INFO, "Inicia el proceso de cambiar de estado la solicitud con el id: " + id);
        SolicitudEntity solicitudACambiar = persistence.find(id);
        if (solicitudACambiar == null) {
            throw new WebApplicationException("No se encontro la solicitud con el id:" + id, 404);
        }
        solicitudACambiar.setEstado(SolicitudEntity.CANCELADO);
        LOGGER.log(Level.INFO, "Se cambio el estado de la solicitud con id: " + id + "satisfactoriamente");
    }
}
