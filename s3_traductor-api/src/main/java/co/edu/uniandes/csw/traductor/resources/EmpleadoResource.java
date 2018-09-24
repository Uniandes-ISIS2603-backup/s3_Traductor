/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.*;
import co.edu.uniandes.csw.traductor.ejb.EmpleadoLogic;
import co.edu.uniandes.csw.traductor.ejb.PropuestaLogic;
import co.edu.uniandes.csw.traductor.entities.*;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso Empleado
 * @author Alvaro Javier Ayte
 */

@Path("empleados")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EmpleadoResource
{

	//Logger
	private static final Logger LOGGER = Logger.getLogger(EmpleadoResource.class.getName());

	//Inyeccion de Logica
	
	@Inject
	private EmpleadoLogic empleadoLogic;	
	
	@Inject
	private PropuestaLogic propuestaLogic;
	
	/**
	 * Crea un nuevo Empleado con la informacion que se recibe en el cuerpo de la petición y se regresa un objeto identico con un id auto-generado por la base de datos. *
	 * @param nuevoEmpleado {@link EmpleadoDTO} - La Empleado que se desea guardar.
	 * @return JSON {@link EmpleadoDTO} - La Empleado recibida.
	 */
	
	@POST
	public EmpleadoDTO createEmpleado(EmpleadoDTO nuevoEmpleado) throws BusinessLogicException {	
		//Llamado al Logger, no se para que sirve :(
		LOGGER.log(Level.INFO, "EmpleadoResources createEmpleado: input: {0}", nuevoEmpleado.toString());
		// Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
		EmpleadoEntity empleadoEntity = nuevoEmpleado.toEntity();
		// Invoca la lógica para crear el Empleado nuevo. Ahi abajo debe ir la logica.
		EmpleadoEntity nuevaEntity = empleadoLogic.createEmpleado(empleadoEntity);
		// Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo.		        
		EmpleadoDTO respuestaDTO = new EmpleadoDTO(nuevaEntity);
		LOGGER.log(Level.INFO, "EmpleadoResources createEmpleado: output: {0}", respuestaDTO.toString());
		return respuestaDTO;
	}

	/**
	 * Busca y devuelve todos los Empleados 
	 * @return JSONArray {@link EmpleadoDTO} - Las Empleados que posee el empleado. Si no hay ninguna retorna una lista vacía.
	 */
	
	@GET
	public List<EmpleadoDetailDTO> getEmpleados() {
		
		LOGGER.info("EmpleadoResources getAllEmpleados: input: void");
		List<EmpleadoDetailDTO> listaEmpleados = listEntity2DetailDTO(empleadoLogic.getEmpleados()); //Se llama a la logica para que devuelva la lista !
		LOGGER.log(Level.INFO, "EmpleadoResources getAllEmpleados: output: {0}", listaEmpleados.toString());
		return listaEmpleados;
	}

	/**
	 * Busca El empleado con el id asociado recibido en la URL y la devuelve.
	 * @param empleadoId Identificador de la empleado que se esta buscando. Este debe ser una cadena de dígitos.
	 * @return JSON {@link empleadoDTO} - La editorial buscada
	 * @throws WebApplicationException {@link WebApplicationExceptionMapper} - Error de lógica que se genera cuando no se encuentra la editorial.
	 */
	
	@GET
	@Path("{empleadoId: \\d+}")
	public EmpleadoDTO getempleado(@PathParam("empleadoId") Long empleadoId) throws WebApplicationException {
		
		// TODO: [getempleado] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
		
		LOGGER.log(Level.INFO, "empleadoResources getempleado: input: {0}", empleadoId);		
		EmpleadoDTO entityBuscada = null; //DTO respuesta.	
                entityBuscada = new EmpleadoDetailDTO(empleadoLogic.getEmpleado(empleadoId));					
		
		LOGGER.log(Level.INFO, "empleadoResources getempleado: output: {0}", entityBuscada.toString());
		return entityBuscada;
	}
	
	/**
     * Borra la empleado con el id asociado recibido en la URL.
     *
     * @param empleadoId Identificador de la empleado que se desea borrar.
     * Este debe ser una cadena de dígitos.     
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
	
    @DELETE
    @Path("{empleadoId: \\d+}")
    public void deleteEmpleado(@PathParam("empleadoId") Long empleadoId) throws WebApplicationException {
        
		
		LOGGER.log(Level.INFO, "empleadoResources deleteempleado: input: {0}", empleadoId);
        
		try
		{
			empleadoLogic.getEmpleado(empleadoId); //Si no existe salta al catch y manda la excepcion.
			empleadoLogic.deleteEmpleado(empleadoId);
		}
		
		catch(BusinessLogicException e)
		{
			throw new WebApplicationException("El recurso /empleados/" + empleadoId + " no existe.", 404);
		}	
		
        LOGGER.info("empleadoResources deleteempleado: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos EmpleadoEntity a una lista de
     * objetos EmpleadoDTO (json)
     *
     * @param EmpleadoList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */	
	
    private List<EmpleadoDetailDTO> listEntity2DetailDTO(List<EmpleadoEntity> emplList) {
        List<EmpleadoDetailDTO> list = new LinkedList<>();
        for (EmpleadoEntity entity : emplList) {
            list.add(new EmpleadoDetailDTO(entity));
        }
        return list;
    }	
	
	//==================================================
	//Metodos para el control de subrecursos
	//==================================================
	
	/**
     * Guarda una propuesta dentro de un empleado con la informacion que recibe el
     * la URL. Se devuelve la propuesta que se guarda en el empleado.     
     * @param empleadoId Identificador del cliente que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param propuestaId Identificador de la propuesta que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link PropuestaDTO} - La propuesta creada por el cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la propuesta.
	 * @author Geovanny Andres Gonzalez
     */	
    
	@POST
    @Path("{empleadoId: \\d+}/propuestas/{propuestaId: \\d+}")
    public PropuestaDTO addPropuesta(@PathParam("empleadoId") Long empleadoId, @PathParam("propuestaId") Long propuestaId)
	{
        //Verifica que el empleado exista
		if(empleadoLogic.getEmpleado(empleadoId) == null) {
			throw new WebApplicationException("El recurso /empleados/" + empleadoId + " no existe.", 404);
		}
		
		//Verifica que la propuesta exista.
		PropuestaEntity entidadHija = null;
		try {
			entidadHija = propuestaLogic.getPropuesta(propuestaId);
		} 
		
		catch (BusinessLogicException ex) {
			throw new WebApplicationException("El recurso /propuestas/" + propuestaId + " no existe.", 404);
		}	

		//Procediendo a agregar
		LOGGER.log(Level.INFO, "Inicia proceso de asociarle una propuesta al empleado con id = {0}", empleadoId);
        EmpleadoEntity entidadPadre = empleadoLogic.getEmpleado(empleadoId);        
        entidadHija.setEmpleado(entidadPadre); // Se asocia la propuesta al cliente como ManyToOne según ejemplo.
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una propuesta al empleado con id = {0}", empleadoId);
		return new PropuestaDTO(entidadHija);
    }

	/**
     * Busca y devuelve todos las propuestas que existen en un empleado.
     * @param empleadoId Identificador del empleado que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link PropuestasDTO} - Las propuestas encontradas en el
     * empleado. Si no hay ninguno retorna una lista vacía.
	 * @author Geovanny Andres Gonzalez
     */
	
    @GET
	@Path("{empleadoId: \\d+}/propuestas}")
    public List<PropuestaDTO> getAllPropuestas(@PathParam("empleadoId") Long empleadoId) {
        LOGGER.log(Level.INFO, "EmpleadoResource getAllPropuestas: input: {0}", empleadoId);
		//Encontrar el empleado y sus propuestas.
		
		//Verifica que el empleado exista
		EmpleadoEntity empleado = empleadoLogic.getEmpleado(empleadoId);
		if(empleado == null) {
			throw new WebApplicationException("El recurso /empleados/" + empleadoId + " no existe.", 404);
		}
				
        List<PropuestaDTO> listaObjetos = propuestasADTO(empleado.getPropuestas());
        LOGGER.log(Level.INFO, "EditorialBooksResource getBooks: output: {0}", listaObjetos.toString());
        return listaObjetos;
    }
	
	/**
     * Obtiene una instancia de PropuestaEntity asociada a una instancia de Cliente
     * @param empleadoId Identificador de la instancia de Empleado
     * @param propuestaId Identificador de la instancia de propuesta
     * @return La entidad de Propuesta del Empleado en caso que exista
	 * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * En caso de que el cliente o la propuesta no existan.
	 * @author Geovanny Andres Gonzalez
     */
	
	@GET
	@Path("{empleadoId: \\d+}/propuestas/{propuestaId: \\d+}")
    public PropuestaDTO getPropuesta(@PathParam("empleadoId") Long empleadoId, @PathParam("propuestaId") Long propuestaId)
	{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la propuesta con id = {0} del empleado con id = " + empleadoId, propuestaId);
		
		//Verifica que el empleado exista
		EmpleadoEntity entidadPadre = empleadoLogic.getEmpleado(empleadoId);
		
		if(entidadPadre == null) {
			throw new WebApplicationException("El recurso /empleados/" + empleadoId + " no existe.", 404);
		}

		PropuestaEntity entidadHija = null;
		try {
			entidadHija = propuestaLogic.getPropuesta(propuestaId);
		} 
		
		catch (BusinessLogicException ex) {
			throw new WebApplicationException("El recurso /propuestas/" + propuestaId + " no existe.", 404);
		}
		
        List<PropuestaEntity> entidadesHijasPadre = entidadPadre.getPropuestas();        
        int index = entidadesHijasPadre.indexOf(entidadHija);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la propuesta con id = {0} del empleado con id = " + empleadoId, propuestaId);
        
		if (index >= 0) {
            return new PropuestaDTO(entidadesHijasPadre.get(index));
        }
		
        throw new WebApplicationException("La propuesta " + propuestaId + " no esta asociada con el empleado " + empleadoId);
    }
	

	/**
     * Convierte una lista de PropuestaEntity a una lista de PropuestaDTO.     *
     * @param entities Lista de PropuestaEntities a convertir.
     * @return Lista de PropuestaDTO convertida.
     */
    private List<PropuestaDTO> propuestasADTO(List<PropuestaEntity> listaEntities) {
        List<PropuestaDTO> list = new ArrayList<>();
        for (PropuestaEntity entity : listaEntities) {
            list.add(new PropuestaDTO(entity));
        }
        return list;
    }
}
