/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.PagosEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.ClientePersistence;
import co.edu.uniandes.csw.traductor.persistence.PagosPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Conexión de la persistencia para la asociación
 * entre Cliente y pagos
 * @author Santiago Salazar
 */
@Stateless
public class ClientePagosLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ClientePagosLogic.class.getName());

    @Inject
    private ClientePersistence clientePersistence;

    @Inject
    private PagosPersistence pagosPersistence;

    /**
     * Asocia un pago existente a un Cliente
     *
     * @param clientesId Identificador de la instancia de Cliente
     * @param pagosId Identificador de la instancia de pagos
     * @return Instancia de PagosEntity que fue asociada a Cliente
     */
    public PagosEntity addPago(Long clientesId, Long pagosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un pago al cliente con id = {0}", clientesId);
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        PagosEntity pagosEntity = pagosPersistence.find(pagosId);
        pagosEntity.setCliente(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un pago al cliente con id = {0}", clientesId);
        return pagosPersistence.find(pagosId);
    }
    
    /**
     * Obtiene una colección de instancias de PagosEntity asociadas a una
     * instancia de Cliente
     *
     * @param clientesId Identificador de la instancia de Cliente
     * @return Colección de instancias de PagosEntity asociadas a la instancia de
     * Cliente
     */
    public List<PagosEntity> getPagos(Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los pagos del cliente con id = {0}", clientesId);
        return clientePersistence.find(clientesId).getPagos();
    }

    /**
     * Obtiene una instancia de PagosEntity asociada a una instancia de Cliente
     *
     * @param clientesId Identificador de la instancia de Cliente
     * @param pagosId Identificador de la instancia de Pagos
     * @return La entidad de Pagos del cliente
     * @throws BusinessLogicException Si la tarjeta no está asociada al cliente
     */
    public PagosEntity getPago(Long clientesId, Long pagosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el pago con id = {0} del cliente con id = " + clientesId, pagosId);
        List<PagosEntity> pagos = clientePersistence.find(clientesId).getPagos();
        PagosEntity pagosEntity = pagosPersistence.find(pagosId);
        int index = pagos.indexOf(pagosEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el pago con id = {0} del cliente con id = " + clientesId, pagosId);
        if (index >= 0) {
            return pagos.get(index);
        }
        throw new BusinessLogicException("El pago no está asociada al cliente");
    }

    /**
     * Borrar el Cliente de un pago y viceversa
     *
     * @param pagoId El pago que se desea borrar del cliente.
     * @throws BusinessLogicException si el pago no tiene cliente
     */
    public void removePago(Long pagoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el Cliente del pago con id = {0}", pagoId);
        PagosEntity pagoEntity = pagosPersistence.find(pagoId);
        if (pagoEntity.getCliente()== null) {
            throw new BusinessLogicException("La tarjeta no tiene cliente");
        }
        ClienteEntity clienteEntity = clientePersistence.find(pagoEntity.getCliente().getId());
        pagoEntity.setCliente(null);
        clienteEntity.getPagos().remove(pagoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el cliente con id = {0} del pago con id = " + pagoId, clienteEntity.getId());
    }
}
