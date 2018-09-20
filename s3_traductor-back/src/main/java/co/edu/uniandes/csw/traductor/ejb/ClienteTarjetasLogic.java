/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.ClientePersistence;
import co.edu.uniandes.csw.traductor.persistence.TarjetaDeCreditoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Conexión de la persistencia para la asociación entre cliente
 * y sus tarjetas de crédito.
 * @author Santiago Salazar
 */
@Stateless
public class ClienteTarjetasLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ClienteTarjetasLogic.class.getName());

    @Inject
    private ClientePersistence clientePersistence;

    @Inject
    private TarjetaDeCreditoPersistence tarjetaPersistence;

    /**
     * Asocia una tarjeta existente a un Cliente
     *
     * @param clientesId Identificador de la instancia de Cliente
     * @param tarjetasId Identificador de la instancia de TarjetaDeCredito
     * @return Instancia de TarjetaDeCreditoEntity que fue asociada a Cliente
     */
    public TarjetaDeCreditoEntity addTarjeta(Long clientesId, Long tarjetasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una tarjeta al cliente con id = {0}", clientesId);
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        TarjetaDeCreditoEntity tarjetaEntity = tarjetaPersistence.find(tarjetasId);
        tarjetaEntity.setCliente(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una tarjeta al cliente con id = {0}", clientesId);
        return tarjetaPersistence.find(tarjetasId);
    }
    
    /**
     * Obtiene una colección de instancias de TarjetaDeCreditoEntity asociadas a una
     * instancia de Cliente
     *
     * @param clientesId Identificador de la instancia de Cliente
     * @return Colección de instancias de TarjetaDeCreditoEntity asociadas a la instancia de
     * Cliente
     */
    public List<TarjetaDeCreditoEntity> getTarjetas(Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las tarjetas del cliente con id = {0}", clientesId);
        return clientePersistence.find(clientesId).getTarjetas();
    }

    /**
     * Obtiene una instancia de TarjetaDeCreditoEntity asociada a una instancia de Cliente
     *
     * @param clientesId Identificador de la instancia de Cliente
     * @param tarjetasId Identificador de la instancia de Tarjeta
     * @return La entidad de Tarjeta del cliente
     * @throws BusinessLogicException Si la tarjeta no está asociada al cliente
     */
    public TarjetaDeCreditoEntity getTarjeta(Long clientesId, Long tarjetasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la tarjeta con id = {0} del cliente con id = " + clientesId, tarjetasId);
        List<TarjetaDeCreditoEntity> tarjetas = clientePersistence.find(clientesId).getTarjetas();
        TarjetaDeCreditoEntity tarjetaEntity = tarjetaPersistence.find(tarjetasId);
        int index = tarjetas.indexOf(tarjetaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la tarjeta con id = {0} del cliente con id = " + clientesId, tarjetasId);
        if (index >= 0) {
            return tarjetas.get(index);
        }
        throw new BusinessLogicException("La tarjeta no está asociada al cliente");
    }

    /**
     * Borrar el Cliente de una tarjeta y viceversa
     *
     * @param tarjetaId La tarjeta que se desea borrar del cliente.
     * @throws BusinessLogicException si la tarjeta no tiene cliente
     */
    public void removeTarjeta(Long tarjetaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el Cliente de la tarjeta con id = {0}", tarjetaId);
        TarjetaDeCreditoEntity tarjetaEntity = tarjetaPersistence.find(tarjetaId);
        if (tarjetaEntity.getCliente()== null) {
            throw new BusinessLogicException("La tarjeta no tiene cliente");
        }
        ClienteEntity clienteEntity = clientePersistence.find(tarjetaEntity.getCliente().getId());
        tarjetaEntity.setCliente(null);
        clienteEntity.getTarjetas().remove(tarjetaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el cliente con id = {0} de la tarjeta con id = " + tarjetaId, clienteEntity.getId());
    }
}
