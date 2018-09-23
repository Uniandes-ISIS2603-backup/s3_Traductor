/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.ClienteEntity.TipoCliente;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.ClientePersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Cliente.
 * @author Santiago Salazar
 */
@Stateless
public class ClienteLogic 
{
    
    private static final Logger LOGGER = Logger.getLogger(ClienteLogic.class.getName());

    @Inject
    private ClientePersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea un cliente en la persistencia.
     *
     * @param clienteEntity La entidad que representa el cliente a
     * persistir.
     * @return La entidad del cliente luego de persistirla.
     * @throws BusinessLogicException Si el cliente a persistir ya existe.
     * (es decir si tiene el mismo nombre usuario, mismo correo electrónico o
     * misma identificación)
     */
    public ClienteEntity createCliente(ClienteEntity clienteEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del cliente");
        // Verifica la regla de negocio que dice que no puede haber 2 clientes con el mismo nombre de usuario.
        if (persistence.findByNombreUsuario(clienteEntity.getNombreUsuario()) != null) {
            throw new BusinessLogicException("Ya existe un cliente con el nombre de usuario \"" + clienteEntity.getNombreUsuario()+ "\"");
        }
        // Verifica la regla de negocio que dice que no puede haber 2 clientes con el mismo correo electronico.
        if (persistence.findByCorreo(clienteEntity.getCorreoElectronico()) != null) {
            throw new BusinessLogicException("Ya existe un cliente con el correo electronico \"" + clienteEntity.getCorreoElectronico()+ "\"");
        }
        // Verifica la regla de negocio que dice que no puede haber 2 clientes con la misma identificacion.
        if (persistence.findByIdentificacion(clienteEntity.getIdentificacion()) != null) {
            throw new BusinessLogicException("Ya existe un cliente con la identificacion \"" + clienteEntity.getIdentificacion()+ "\"");
        }
        // Invoca la persistencia para crear el cliente
        persistence.create(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del cliente");
        return clienteEntity;
    }

    /**
     *
     * Obtener todos los clientes existentes en la base de datos.
     *
     * @return una lista de clientes.
     */
    public List<ClienteEntity> getClientes() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los clientes");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<ClienteEntity> clientes = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los clientes");
        return clientes;
    }

    /**
     *
     * Obtener todos los clientes existentes segun el tipo
     * de cliente del parametro.
     *
     * @param tipo El tipo de clientes a buscar.
     * @return una lista de clientes de ese tipo.
     */
    public List<ClienteEntity> getClientesByTipo(TipoCliente tipo) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los clientes del tipo: {0}", tipo);
        // Note que, por medio de la inyección de dependencias se llama al método "findAllByTipo()" que se encuentra en la persistencia.
        List<ClienteEntity> clientes = persistence.findAllByTipo(tipo);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los clientes del tipo: {0}", tipo);
        return clientes;
    }
    
    /**
     *
     * Obtener un cliente por medio de su id.
     *
     * @param clientesId: id del cliente para ser buscada.
     * @return El cliente solicitado por medio de su id.
     */
    public ClienteEntity getCliente(Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el cliente con id = {0}", clientesId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        ClienteEntity clienteEntity = persistence.find(clientesId);
        if (clienteEntity == null) {
            LOGGER.log(Level.SEVERE, "El cliente con el id = {0} no existe", clientesId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el cliente con id = {0}", clientesId);
        return clienteEntity;
    }

    /**
     *
     * Actualizar un cliente.
     *
     * @param clientesId: id del cliente para buscarla en la base de
     * datos.
     * @param clienteEntity: cliente con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return El cliente con los cambios actualizados en la base de datos.
     */
    public ClienteEntity updateCliente(Long clientesId, ClienteEntity clienteEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el cliente con id = {0}", clientesId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        ClienteEntity newEntity = persistence.update(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el cliente con id = {0}", clienteEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un cliente
     *
     * @param clientesId: id del cliente a borrar
     * @throws BusinessLogicException Si el cliente a eliminar tiene solicitudes.
     */
    public void deleteCliente(Long clientesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el cliente con id = {0}", clientesId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        List<SolicitudEntity> solicitudes = getCliente(clientesId).getSolicitudes();
        if (solicitudes != null && !solicitudes.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el cliente con id = " + clientesId + " porque tiene solicitudes asociadas");
        }
        List<PropuestaEntity> propuestas = getCliente(clientesId).getPropuestas();
        if (propuestas != null && !propuestas.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el cliente con id = " + clientesId + " porque tiene propuestas asociadas");
        }
        List<InvitacionEntity> invitaciones = getCliente(clientesId).getInvitaciones();
        if (invitaciones != null && !invitaciones.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el clinte con id = " + clientesId + " porque tiene invitaciones asociadas");
        }
        persistence.delete(clientesId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el cliente con id = {0}", clientesId);
    }
}
