/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.EmpleadoPersistence;
import co.edu.uniandes.csw.traductor.persistence.InvitacionPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Alvaro
 */
public class EmpleadoInvitacionLogic {
    private static final Logger LOGGER = Logger.getLogger(EmpleadoInvitacionLogic.class.getName());
	
	@Inject
	private EmpleadoPersistence empleadoPersistence;
	
	@Inject
	private InvitacionPersistence invitacionPersistence;

	/**
     * Asocia una invitacion con un empleado existente
     * @param empleadoId Identificador de la instancia de Empleado
     * @param invitacionId Identificador de la instancia de invitacion
     * @return Instancia de InvitacionEntity que fue asociada a un empleado
     */
    public InvitacionEntity addInvitacion(Long empleadoId, Long invitacionId)
	{
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una invitacion al empleado con id = {0}", empleadoId);
        EmpleadoEntity entidadPadre = empleadoPersistence.find(empleadoId);
        InvitacionEntity entidadHija = invitacionPersistence.findSoloId(invitacionId);
		System.out.println("Es entidad padre" + entidadPadre);
		System.out.println("Es entidad hija" + entidadHija);
        entidadHija.setEmpleado(entidadPadre); // Se asocia la invitacion al empleado como ManyToOne según ejemplo.
		invitacionPersistence.update(entidadHija); //Actualizar la llave 
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una invitacion al empleado con id = {0}", invitacionId);
        return entidadHija;
    }

	/**
     * Obtiene una colección de instancias de InvitacionEntity asociadas a una
     * instancia de Empleado
     *
     * @param empleadoId Identificador de la instancia de Empleado
     * @return Colección de instancias de InvitacionEntity asociadas a la instancia de
     * Empleado
     */
    public List<InvitacionEntity> getInvitaciones(Long empleadoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las invitaciones del empleado con id = {0}", empleadoId);
        return empleadoPersistence.find(empleadoId).getInvitaciones();
    }
	
	/**
     * Obtiene una instancia de InvitacionEntity asociada a una instancia de Empleado
     * @param empleadoId Identificador de la instancia de Empleado
     * @param invitacionId Identificador de la instancia de invitacion
     * @return La entidad de Invitacion del empleado
     * @throws BusinessLogicException Si la invitacion no está asociado al empleado
     */
	
    public InvitacionEntity getInvitacion(Long empleadoId, Long invitacionId) throws BusinessLogicException
	{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la invitacion con id = {0} del empleado con id = " + empleadoId, invitacionId);
        List<InvitacionEntity> entidadesHijasPadre = empleadoPersistence.find(empleadoId).getInvitaciones();
        InvitacionEntity entidadHija = invitacionPersistence.findSoloId(invitacionId); 
        int index = entidadesHijasPadre.indexOf(entidadHija);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la invitacion con id = {0} del empleado con id = " + empleadoId, invitacionId);
        
		if (index >= 0) {
            return entidadesHijasPadre.get(index);
        }
        throw new BusinessLogicException("La invitacion no esta asociada con el empleado");
    }	
    /**
     * 
     * @param empleadoId empleado a buscar.
     * @param invitacionId invitacion a eliminar.
     * @throws BusinessLogicException 
     */

    public void deleteInvitacion(Long empleadoId, Long invitacionId) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "EmpleadoInvitacionResoruce deleteInvitacion: input: empleadoId {0}, invitacionId: {1}", new Object[]{empleadoId, invitacionId});
        EmpleadoEntity empleado = empleadoPersistence.find(empleadoId);
        InvitacionEntity invitacion = invitacionPersistence.findSoloId(invitacionId);
        int index = empleado.getInvitaciones().indexOf(invitacion);
        if(index >= 0)
        {
          empleado.getInvitaciones().remove(index);
        }
        throw new BusinessLogicException("El invitacion no esta asociada al empleado");
    }
}
