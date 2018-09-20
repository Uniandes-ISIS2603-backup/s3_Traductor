/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.IdiomaDTO;
import co.edu.uniandes.csw.traductor.ejb.IdiomaLogic;
import co.edu.uniandes.csw.traductor.ejb.SolicitudLogic;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Juan Felipe Parra
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SolicitudIdiomaResource {
  /**
   * 
   */
   private static final Logger LOGGER = Logger.getLogger(SolicitudIdiomaResource.class.getName());
    /**
     * logica inyectada de una solicitud
     */
   @Inject
   private SolicitudLogic solicitudLogic;
   /**
    * logica inyectada de un idioma
    */
  @Inject
  private IdiomaLogic idiomaLogic;
  /**
   * obtener el idioma con el cual se va a a tramitar la solicitud
   * @param solicitudId identificador de la solicitud a obtener el idioma
   * @param idiomaId identificador del idioma a buscar
   * @return dto del idioma asignado
   */
  @GET
  @Path("{idiomaId: \\d+}")
  public IdiomaDTO getIdioma(@PathParam("solicitudId") Long solicitudId,@PathParam ("idiomaId") Long idiomaId)
  {
      return null;
  }
   
}
