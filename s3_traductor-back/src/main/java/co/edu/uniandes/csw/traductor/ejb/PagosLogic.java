/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.PagosEntity;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.ClientePersistence;
import co.edu.uniandes.csw.traductor.persistence.PagosPersistence;
import co.edu.uniandes.csw.traductor.persistence.PropuestaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ANDRES
 */
@Stateless
public class PagosLogic {
     private static final Logger LOGGER = Logger.getLogger(PagosLogic.class.getName());

    @Inject
    private PagosPersistence pagosPersistence;

    @Inject
    private ClientePersistence clientePersistence;
    
     @Inject
    private PropuestaPersistence propuestaPersistence;

    /**
     * Guardar un nuevo Pago
     *
     * @param pagosEntity La entidad de tipo pago del nuevo pago a
     * persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException si la el cliente o la propuesta no existen
     */
    public PagosEntity createPago(Long idCliente,Long idPropuesta,PagosEntity pagosEntity) throws BusinessLogicException {
        LOGGER.info("Inicia proceso de creación de pago");
        
        ClienteEntity clienteEntity = clientePersistence.find(idCliente);
        if (clienteEntity == null) {
            throw new BusinessLogicException("El cliente es inválido");
        }
        
        PropuestaEntity propEntity = propuestaPersistence.findSoloId(idPropuesta);
        if (propEntity == null) {
            throw new BusinessLogicException("La propuesta es inválida");
        }
     
        pagosEntity.setCliente(clienteEntity);
        pagosEntity.setPropuesta(propEntity);
        pagosEntity = pagosPersistence.create(pagosEntity);    
        LOGGER.info("Termina proceso de creación un pago");
        return pagosEntity;
    }

    /**
     * Devuelve todos los pagos que hay en la base de datos.
     *
     * @return Lista de entidades de tipo pago.
     */
    public List<PagosEntity> getPagos(Long idCliente) {
        LOGGER.info("Inicia proceso de consultar todos los pagos");
        ClienteEntity cliente = clientePersistence.find(idCliente);
        LOGGER.info("Termina proceso de consultar todos los pagos");
        return cliente.getPagos();
    }

    /**
     * Busca un pago por ID
     *
     * @param idTransaccion El id del pago a buscar
     * @return El pago encontrado, null si no lo encuentra.
     */
    public PagosEntity getPago(Long idCliente,Long idTransaccion) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar pago con id = {0}", idTransaccion);
        PagosEntity pago = pagosPersistence.find(idCliente,idTransaccion);
        if (pago == null) {
            LOGGER.log(Level.SEVERE, "El pago con el id = {0} no existe", idTransaccion);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar pago con id = {0}", idTransaccion);
        return pago;
    }

    /**
     * Actualizar un pago por ID
     *
     * @param idTransaccion El ID del pago a actualizar
     * @param pagosEntity La entidad del pago con los cambios deseados
     * @return La entidad del pago luego de actualizarla
     */
    public PagosEntity updatePago(Long idTransaccion, PagosEntity pagosEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar pago con id = {0}", idTransaccion);
        PagosEntity newEntity = pagosPersistence.update(pagosEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar pago con id = {0}", pagosEntity.getId());
        return newEntity;
    }

    /**
     * Eliminar un pago por ID
     *
     * @param idTransaccion El ID del pago a eliminar
     * @throws BusinessLogicException si el ID del pago no se encuentra.
     */
    public void deletePago(Long idCliente,Long idTransaccion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar pago con id = {0}", idTransaccion);
        PagosEntity old=getPago(idCliente, idTransaccion);
        if (old == null) {
            throw new BusinessLogicException("El pago con id = " + idTransaccion + " no esta asociado a el cliente con id = " + idCliente);
        }
        pagosPersistence.delete(idTransaccion);
        LOGGER.log(Level.INFO, "Termina proceso de borrar pago con id = {0}", idTransaccion);
    }


}
