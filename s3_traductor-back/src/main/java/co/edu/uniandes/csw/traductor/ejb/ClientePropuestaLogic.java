/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.ClientePersistence;
import co.edu.uniandes.csw.traductor.persistence.PropuestaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Logica de la clase ClientePropuestaLogic
 * @author Geovanny Andres Gonzalez
 */

@Stateless
public class ClientePropuestaLogic 
{
	private static final Logger LOGGER = Logger.getLogger(ClientePropuestaLogic.class.getName());
	
	@Inject
	private ClientePersistence clientePersistence;
	
	@Inject
	private PropuestaPersistence propuestaPersistence;

	/**
     * Asocia una propuesta con un cliente existente
     * @param clienteId Identificador de la instancia de Cliente
     * @param propuestaId Identificador de la instancia de propuesta
     * @return Instancia de PropuestaEntity que fue asociada a un cliente
     */
    public PropuestaEntity addPropuesta(Long clienteId, Long propuestaId)
	{
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una propuesta al cliente con id = {0}", clienteId);
        ClienteEntity entidadPadre = clientePersistence.find(clienteId);
        PropuestaEntity entidadHija = propuestaPersistence.findSoloId(propuestaId);
        entidadHija.setCliente(entidadPadre); // Se asocia la propuesta al cliente como ManyToOne según ejemplo.
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una propuesta al cliente con id = {0}", propuestaId);
        return entidadHija;
    }

	/**
     * Obtiene una colección de instancias de PropuestaEntity asociadas a una
     * instancia de Cliente
     *
     * @param clienteId Identificador de la instancia de Cliente
     * @return Colección de instancias de PropuestaEntity asociadas a la instancia de
     * Cliente
     */
    public List<PropuestaEntity> getPropuestas(Long clienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las propuestas del cliente con id = {0}", clienteId);
        return clientePersistence.find(clienteId).getPropuestas();
    }
	
	/**
     * Obtiene una instancia de PropuestaEntity asociada a una instancia de Cliente
     * @param clienteId Identificador de la instancia de Cliente
     * @param propuestaId Identificador de la instancia de propuesta
     * @return La entidad de Propuesta del cliente
     * @throws BusinessLogicException Si la propuesta no está asociado al cliente
     */
	
    public PropuestaEntity getPropuesta(Long clienteId, Long propuestaId) throws BusinessLogicException
	{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la propuesta con id = {0} del cliente con id = {1}", new Object[]{clienteId, propuestaId});
        List<PropuestaEntity> entidadesHijasPadre = clientePersistence.find(clienteId).getPropuestas();
        PropuestaEntity entidadHija = propuestaPersistence.findSoloId(propuestaId);
        int index = entidadesHijasPadre.indexOf(entidadHija);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la propuesta con id = {0} del cliente con id = {1}", new Object[]{clienteId, propuestaId});
        
		if (index >= 0) {
            return entidadesHijasPadre.get(index);
        }
        throw new BusinessLogicException("La propuesta no esta asociada con el cliente");
    }	
    /**
     * Delete
     * @param clienteId cliente a buscar.
     * @param propuestaId propuesta a eliminar.
     * @throws BusinessLogicException 
     */

    public void deletePropuesta(Long clienteId, Long propuestaId) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "ClientePropuestaResoruce deletePropuesta: input: clienteId {0}, propuestaId: {1}", new Object[]{clienteId, propuestaId});
        ClienteEntity cliente = clientePersistence.find(clienteId);
        PropuestaEntity propuesta = propuestaPersistence.findSoloId(propuestaId);
        int index = cliente.getPropuestas().indexOf(propuesta);
        if(index >= 0)
        {
          cliente.getPropuestas().remove(index);
        }
        throw new BusinessLogicException("El propuesta no esta asociada al cliente");
    }
}
