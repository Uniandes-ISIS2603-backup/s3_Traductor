/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.ClientePersistence;
import co.edu.uniandes.csw.traductor.persistence.SolicitudPersistence;
import co.edu.uniandes.csw.traductor.persistence.SolicitudPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa la conexion con la persistencia para la entidad
 * Solicitud.
 *
 * @author Jhonattan Fonseca
 */
@Stateless
public class SolicitudLogic {

    private static final Logger LOGGER = Logger.getLogger(SolicitudLogic.class.getName());

    @Inject
    private SolicitudPersistence solicitudPersistence; //Invocación a la tabla de solicitud para trabajar en la base de datos.

    @Inject
    private ClientePersistence clientePersistence;


    /**
     * Crea una solicitud en la persistencia.
     *
     * @param clienteId Identificacion del cliente
     * @param solicitudEntity La entidad que representa la solicitud a
     * persistir.
     * @return La entidad de la solicitud luego de persistirla.
     * @throws BusinessLogicException Si la solicitud a persistir ya existe o
     * si no se cumple la integridad de los datos requeridos.
     */
    public SolicitudEntity createSolicitud(Long clienteId, SolicitudEntity solicitudEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la solicitud");

        // Verifica la regla de negocio. Verificacion de los datos del Entity.
         if (solicitudEntity.getDescripcion().length() == 0 || solicitudEntity.getDescripcion() == null) {
            throw new BusinessLogicException("La descripcion de la solicitud no puede ser nula o vacia");
        }else if (solicitudEntity.getArchivo().length()==0||solicitudEntity.getArchivo()==null){
            throw new BusinessLogicException("el archivo de la solicitud no puede ser nula o vacio");
        }

        // Invoca la persistencia para crear la solicitud pues se ha validado todas las reglas.
        ClienteEntity entidadPadre = clientePersistence.find(clienteId);
        solicitudEntity.setCliente(entidadPadre);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la solicitud");
        return solicitudPersistence.create(solicitudEntity);
    }

    /**
     * Actualiza la solicitud con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param solicitudEntity Entidad con la informacion a actualizar en la
     * base de datos.
     * @param clienteId Identificacion del cliente
     * @return La solicitud con la información actualizada.
     */
    public SolicitudEntity updateSolicitud(Long clienteId, SolicitudEntity solicitudEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la solicitud del cliente con el id = {0}", clienteId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        ClienteEntity entidadPadre = clientePersistence.find(clienteId);
        solicitudEntity.setCliente(entidadPadre);
        solicitudPersistence.update(solicitudEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la solicitud del cliente con el id = {0}", clienteId);
        return solicitudEntity;
    }

    /**
     * Obtener una solicitud por medio de su id.
     *
     * @param solicitudId: id de la solicitud para ser buscada.
     * @param clienteId Identificacion del cliente
     * @return la solicitud solicitada por medio de su id.
     */
    public SolicitudEntity getSolicitud(Long clienteId, Long solicitudId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la solicitud con id = {0} del cliente: {1}", new Object[]{solicitudId,clienteId});
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        return solicitudPersistence.find(clienteId, solicitudId);
    }

    /**
     * Obtener todas las Solicitudes existentes en la base de datos.
     *
     * @param clienteId Identificacion del cliente
     * @return Una lista de las Solicitudes existentes.
     */
    public List<SolicitudEntity> getAllSolicitudes(Long clienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las Solicitudes del cliente id ={0}", clienteId);
        // Note que, por medio de la inyección de dependencias se llama al método "getAll()" que se encuentra en la persistencia.
        ClienteEntity entidadPadre = clientePersistence.find(clienteId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las del cliente id ={0}", clienteId);
        return entidadPadre.getSolicitudes();
    }
    public SolicitudEntity getSolicitudSoloId(Long solicitudId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la solicitud con id = {0}", solicitudId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        return solicitudPersistence.findSoloId(solicitudId);
    }
    /**
     * Borrar un solicitud existente en la base de datos.
     *
     * @param clienteId Identificacion del cliente
     * @param solicitudId: id de la solicitud a borrar
     * @throws BusinessLogicException si la solicitud no esta asociada al
     * cliente.
     */
    public void deleteSolicitud(Long clienteId, Long solicitudId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la solicitud con id = {0}", solicitudId);
        SolicitudEntity entidadEliminar = getSolicitud(clienteId, solicitudId);
        if (entidadEliminar == null) {
            throw new BusinessLogicException("La solicitud con id = " + solicitudId + " no esta asociado a el cliente con id = " + clienteId);
        }
        solicitudPersistence.delete(solicitudId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la solicitud con id = {0} del cliente: {1}", new Object[]{solicitudId,clienteId});
    }
}
