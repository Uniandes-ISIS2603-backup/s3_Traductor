/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.TarjetaDeCreditoPersistence;
import java.util.Date;
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
public class TarjetaDeCreditoLogic {
      private static final Logger LOGGER = Logger.getLogger(TarjetaDeCreditoLogic.class.getName());

    @Inject
    private TarjetaDeCreditoPersistence persistence;
    


    /**
     * Guardar una nueva tarjeta
     *
     * @param tarjetaEntity La entidad de tipo tarjeta de la nueva tarjeta a persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException Si el numero de la tarjeta es inválido o ya existe en la
     * persistencia.
     */
    public TarjetaDeCreditoEntity createTarjeta(TarjetaDeCreditoEntity tarjetaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la tarjeta");
        if (tarjetaEntity.getNombreTitular().equals("")||tarjetaEntity.getNombreTitular()==null) {
            throw new BusinessLogicException("El nombre es inválida");
        }
        if (validateNumber(tarjetaEntity.getNumeroTarjetaCredito())) {
            throw new BusinessLogicException("El numero es inválido");
        }
        if (persistence.find(tarjetaEntity.getId()) != null) {
            throw new BusinessLogicException("La tarjeta ya existe");
        }
        if (persistence.findByNumeroTarjeta(tarjetaEntity.getNumeroTarjetaCredito()) != null) {
            throw new BusinessLogicException("La tarjeta ya existe");
        }
        
        if(tarjetaEntity.getCcv()<0||tarjetaEntity.getCcv().toString().length()!=3)
        {
            System.out.println(tarjetaEntity.getCcv());
            throw new BusinessLogicException("El ccv es invalido");
        }
         if(tarjetaEntity.getAnioExpiracion()==null)
        {
            throw new BusinessLogicException("El año de expiracion es invalido");
        }
         if(tarjetaEntity.getMesExpiracion()==null)
        {
            throw new BusinessLogicException("El mes de expiracion es invalido");
        }
         Date fechaActual=new Date();
          if(tarjetaEntity.getAnioExpiracion()<fechaActual.getYear())
        {
            throw new BusinessLogicException("El fecha de expiracion es menor a la actual");
        }
          if(tarjetaEntity.getAnioExpiracion()==fechaActual.getYear()&&tarjetaEntity.getMesExpiracion()<fechaActual.getMonth())
        {
            throw new BusinessLogicException("El fecha de expiracion es menor a la actual");
        }
          if (tarjetaEntity.getRedBancaria().equals("")||tarjetaEntity.getRedBancaria()==null) {
            throw new BusinessLogicException("La red bancaria es invalida");
        }
        persistence.create(tarjetaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la tarjeta");
        return tarjetaEntity;
    }
 /**
     * Devuelve todos las tarjetas que hay en la base de datos.
     *
     * @return Lista de entidades de tipo tarjeta.
     */
    public List<TarjetaDeCreditoEntity> getTarjetas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las tarjetas");
        List<TarjetaDeCreditoEntity> tarjetas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las tarjetas");
        return tarjetas;
    }

    /**
     * Busca una tarjeta por ID
     *
     * @param idTarjeta El id de la tarjeta a buscar
     * @return La tarjeta encontrada, null si no lo encuentra.
     */
    public TarjetaDeCreditoEntity getTarjetaDeCredito(Long idTarjeta) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la tarjeta con id = {0}", idTarjeta);
        TarjetaDeCreditoEntity tarjetaEntity = persistence.find(idTarjeta);
        if (tarjetaEntity == null) {
            LOGGER.log(Level.SEVERE, "La tarjeta con el id = {0} no existe", idTarjeta);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la tarjeta con id = {0}", idTarjeta);
        return tarjetaEntity;
    }

    /**
     * Actualizar una tarjeta por ID
     *
     * @param idTarjeta El ID de la tarjeta a actualizar
     * @param tarjetaEntity La entidad de la tarjeta con los cambios deseados
     * @return La entidad de la tarjeta luego de actualizarla
     * @throws BusinessLogicException Si el numero de la tarjeta es inválido
     */
    public TarjetaDeCreditoEntity updateTarjeta(Long idTarjeta, TarjetaDeCreditoEntity tarjetaEntity) throws BusinessLogicException {
         LOGGER.log(Level.INFO, "Inicia proceso de actualizar la tarjeta con id = {0}", idTarjeta);
        if (tarjetaEntity.getNombreTitular().equals("")||tarjetaEntity.getNombreTitular()==null) {
            throw new BusinessLogicException("El nombre es inválida");
        }
        if (!validateNumber(tarjetaEntity.getNumeroTarjetaCredito())) {
            throw new BusinessLogicException("El numero es inválido");
        }
        if (persistence.find(tarjetaEntity.getId()) == null) {
            throw new BusinessLogicException("La tarjeta no existe");
        }
      
        if(tarjetaEntity.getCcv()<0||tarjetaEntity.getCcv().toString().length()!=3)
        {
            throw new BusinessLogicException("El ccv es invalido");
        }
         if(tarjetaEntity.getAnioExpiracion()==null)
        {
            throw new BusinessLogicException("El año de expiracion es invalido");
        }
         if(tarjetaEntity.getMesExpiracion()==null)
        {
            throw new BusinessLogicException("El mes de expiracion es invalido");
        }
         Date fechaActual=new Date();
          if(tarjetaEntity.getAnioExpiracion()<fechaActual.getYear())
        {
            throw new BusinessLogicException("El fecha de expiracion es menor a la actual");
        }
          if(tarjetaEntity.getAnioExpiracion()==fechaActual.getYear()&&tarjetaEntity.getMesExpiracion()<fechaActual.getMonth())
        {
            throw new BusinessLogicException("El fecha de expiracion es menor a la actual");
        }
          if(tarjetaEntity.getMesExpiracion()>12)
        {
            throw new BusinessLogicException("El fecha de expiracion es invalida");
        }
          if(tarjetaEntity.getMesExpiracion()<1)
        {
            throw new BusinessLogicException("El fecha de expiracion es invalida");
        }
          if (tarjetaEntity.getRedBancaria().equals("")||tarjetaEntity.getRedBancaria()==null) {
            throw new BusinessLogicException("La red bancaria es invalida");
        }
        TarjetaDeCreditoEntity newEntity = persistence.update(tarjetaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la tarjeta con id = {0}", tarjetaEntity.getId());
        return newEntity;
    }

    /**
     * Eliminar una tarjeta por ID
     *
     * @param idTarjeta El ID de la tarjeta a eliminar
     */
    public void deleteTarjeta(Long idTarjeta) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la tarjeta con id = {0}", idTarjeta);
        
        persistence.delete(idTarjeta);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la tarjeta con id = {0}", idTarjeta);
    }

    
    /**
     * Verifica que el numero de la tarjeta no sea invalido.
     *
     * @param numero a verificar
     * @return true si el numero es valido.
     */
    private boolean validateNumber(Long numero) {
        boolean inValido= ( numero == null);
        if(inValido)
        {
        return !inValido;
        }
        else
        {
            String numStr=numero.toString();
            System.out.println(numStr);
            inValido=(numStr.length()==16);
        }
        return inValido;
        
    }
}
