/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.PagosEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.ClientePersistence;
import co.edu.uniandes.csw.traductor.persistence.PagosPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author ANDRES
 */
public class PagosLogic {
    private static final Logger LOGGER = Logger.getLogger(TarjetaDeCreditoLogic.class.getName());

    @Inject
    private PagosPersistence persistence;
    
    @Inject
    private ClientePersistence clientePersistence;

    /**
     * Guardar una nueva tarjeta
     *
     * @param pagosEntity La entidad de tipo pago de la nueva tarjeta a persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException Si el estado de aprobacion no existe
     */
    public PagosEntity createPago(PagosEntity pagosEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del pago");
        if (pagosEntity.getPagoAprobado()==null) {
            throw new BusinessLogicException("El estado de aprobacion del pago es invalido");
        }
        if (clientePersistence.find(pagosEntity.getCliente().getId())==null) {
            throw new BusinessLogicException("El cliente no existe");
        }
        
        persistence.create(pagosEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del pago");
        return pagosEntity;
    }
 /**
     * Devuelve todos los pagos que hay en la base de datos.
     *
     * @return Lista de entidades de tipo pago.
     */
    public List<PagosEntity> getPagos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los pagos");
        List<PagosEntity> pagos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los pagos");
        return pagos;
    }

    /**
     * Busca un pago por ID
     *
     * @param idTransaccion El id del pago a buscar
     * @return El pago encontrada, null si no lo encuentra.
     */
    public PagosEntity getPago(Long idTransaccion) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el pago con id = {0}", idTransaccion);
        PagosEntity pagosEntity = persistence.find(idTransaccion);
        if (pagosEntity == null) {
            LOGGER.log(Level.SEVERE, "El pago con el id = {0} no existe", idTransaccion);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el pago con id = {0}", idTransaccion);
        return pagosEntity;
    }

    /**
     * Actualizar un pago por ID
     *
     * @param idTarjeta El ID del pago a actualizar
     * @param pagoEntity La entidad del pago con los cambios deseados
     * @return La entidad del pago luego de actualizarla
     * @throws BusinessLogicException Si el numero de la tarjeta es inválido
     */
    public PagosEntity updateTarjeta(Long idTarjeta, PagosEntity pagoEntity) throws BusinessLogicException {
         LOGGER.log(Level.INFO, "Inicia proceso de actualizar el pago con id = {0}", idTarjeta);
       if (pagoEntity.getPagoAprobado()==null) {
            throw new BusinessLogicException("El estado de aprobacion del pago es invalido");
        }
        if (clientePersistence.find(pagoEntity.getCliente().getId())==null) {
            throw new BusinessLogicException("El cliente no existe");
        }
       
        PagosEntity newEntity = persistence.update(pagoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el pago con id = {0}", pagoEntity.getId());
        return newEntity;
    }

    /**
     * Eliminar un pago por ID
     *
     * @param idTransaccion El ID de la tarjeta a eliminar
     */
    public void deleteTarjeta(Long idTransaccion) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el pago con id = {0}", idTransaccion);
        
        persistence.delete(idTransaccion);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el pago con id = {0}", idTransaccion);
    }


}
