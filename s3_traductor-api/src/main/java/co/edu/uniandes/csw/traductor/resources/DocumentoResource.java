/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;
import co.edu.uniandes.csw.traductor.dtos.DocumentoDTO;
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
/**
 *
 * @author Alvaro
 */
@Path("documentos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class DocumentoResource {
    //Logger
	private static final Logger LOGGER = Logger.getLogger(PropuestaResources.class.getName());
    /**
	 * Crea un nuevo Documento con la informacion que se recibe en el cuerpo de la petición y se regresa un objeto identico con un id auto-generado por la base de datos.
	 *
	 * Septiembre 1: Esta operacion solo esta puesta para retornar lo recibido.
	 *
	 * @param nuevaDocumento {@link DocumentoDTO} - el Documento que se desea guardar.
	 * @return JSON {@link DocumentoDTO} - el Documento recibido.
	 */
	
	@POST
	public DocumentoDTO createDocumento(DocumentoDTO nuevoDocumento) {
		
		// TODO: [createDocumento] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
	
		//Llamado al Logger
		LOGGER.log(Level.INFO, "DocumentoResources createDocumento: input: {0}", nuevoDocumento.toString());

		// Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
		//DocumentoEntity DocumentoEntity = nuevoDocumento.toEntity();
		// Invoca la lógica para crear la Documento nuevo
		return nuevoDocumento;
	}
}
