/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;
import co.edu.uniandes.csw.traductor.dtos.DocumentoDTO;
import co.edu.uniandes.csw.traductor.entities.DocumentoEntity;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import co.edu.uniandes.csw.traductor.ejb.*;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
/**
 *
 * @author Alvaro
 */
@Produces("application/json")
@Consumes("application/json")
public class DocumentoResource {
   //Logger
	private static final Logger LOGGER = Logger.getLogger(DocumentoResource.class.getName());

	//Inyeccion de Logica
	
	@Inject
	private DocumentoLogic documentoLogic;
	
	/**
	 * Crea un nuevo Documento con la informacion que se recibe en el cuerpo de la petición y se regresa un objeto identico con un id auto-generado por la base de datos.
	 *

	 *
	 * @param nuevoDocumento {@link DocumentoDTO} - el Documento que se desea guardar.
	 * @return JSON {@link DocumentoDTO} - el Documento recibida.
	 */
	@POST
	public DocumentoDTO createDocumento(DocumentoDTO nuevoDocumento) throws BusinessLogicException {
		
	
		//Llamado al Logger, no se para que sirve :(
		LOGGER.log(Level.INFO, "DocumentoResources createDocumento: input: {0}", nuevoDocumento.toString());

		// Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
		DocumentoEntity DocumentoEntity = nuevoDocumento.toEntity();
		// Invoca la lógica para crear el Documento nuevo. Ahi abajo debe ir la logica.
		DocumentoEntity nuevaEntity = documentoLogic.createDocumento(DocumentoEntity);
		// Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo.		        
		DocumentoDTO respuestaDTO = new DocumentoDTO(nuevaEntity);
		LOGGER.log(Level.INFO, "DocumentoResources createDocumento: output: {0}", respuestaDTO.toString());
		return respuestaDTO;
	}

	/**
	 * Busca y devuelve todos los Documentos 
	 * @return JSONArray {@link DocumentoDTO} - Las Documentos que posee el Documento. Si no hay ninguna retorna una lista vacía.
	 */
	
	@GET
	public List<DocumentoDTO> getDocumentos() {
		
		LOGGER.info("DocumentoResources getAllDocumentos: input: void");
		List<DocumentoDTO> listaDocumentos = listEntity2DetailDTO(documentoLogic.getDocumentos()); //Se llama a la logica para que devuelva la lista !
		LOGGER.log(Level.INFO, "DocumentoResources getAllDocumentos: output: {0}", listaDocumentos.toString());
		return listaDocumentos;
	}

	/**
	 * Busca El Documento con el id asociado recibido en la URL y la devuelve.
	 * @param DocumentoId Identificador de la Documento que se esta buscando. Este debe ser una cadena de dígitos.
	 * @return JSON {@link DocumentoDTO} - La editorial buscada
	 * @throws WebApplicationException {@link WebApplicationExceptionMapper} - Error de lógica que se genera cuando no se encuentra la editorial.
	 */
	
	@GET
	public DocumentoDTO getDocumento(@PathParam("DocumentoId") Long documentoId) throws WebApplicationException {
		
		// TODO: [getDocumento] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
		
		LOGGER.log(Level.INFO, "DocumentoResources getDocumento: input: {0}", documentoId);		
		DocumentoDTO entityBuscada = null; //DTO respuesta.	
                entityBuscada = new DocumentoDTO(documentoLogic.getDocumento(documentoId));					
		
		LOGGER.log(Level.INFO, "DocumentoResources getDocumento: output: {0}", entityBuscada.toString());
		return entityBuscada;
	}
	
	/**
     * Borra la Documento con el id asociado recibido en la URL.
     *
     * @param DocumentoId Identificador de la Documento que se desea borrar.
     * Este debe ser una cadena de dígitos.     
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
	
    @DELETE
    @Path("{DocumentoId: \\d+}")
    public void deleteDocumento(@PathParam("DocumentoId") Long DocumentoId) throws WebApplicationException {
        
		
		LOGGER.log(Level.INFO, "DocumentoResources deleteDocumento: input: {0}", DocumentoId);
        
		try
		{
			documentoLogic.getDocumento(DocumentoId); //Si no existe salta al catch y manda la excepcion.
			documentoLogic.deleteDocumento(DocumentoId);
		}
		
		catch(BusinessLogicException e)
		{
			throw new WebApplicationException("El recurso /Documentos/" + DocumentoId + " no existe.", 404);
		}	
		
        LOGGER.info("DocumentoResources deleteDocumento: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos DocumentoEntity a una lista de
     * objetos DocumentoDTO (json)
     *
     * @param DocumentoList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
	
    private List<DocumentoDTO> listEntity2DetailDTO(List<DocumentoEntity> emplList) {
        List<DocumentoDTO> list = new LinkedList<>();
        for (DocumentoEntity entity : emplList) {
            list.add(new DocumentoDTO(entity));
        }
        return list;
    }
}
