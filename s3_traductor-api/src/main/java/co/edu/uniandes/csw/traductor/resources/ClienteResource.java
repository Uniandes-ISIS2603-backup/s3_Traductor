/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.ClienteDTO;
import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Clase que implementa el recurso "clientes"
 * @author Santiago Salazar
 */
@Path("clientes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ClienteResource 
{
    private static final Logger LOGGER = Logger.getLogger(ClienteResource.class.getName());
    
    //TODO: inyección de la dependencia de lógica: clienteLogic
    
    /**
     * Crea un cliente con la informacion que se recibe en formato JSON
     * en el cuerpo de la peticion y recibe el mismo cliente ingresado
     * con un id generado automaticamente.
     * @param cliente {@link ClienteDTO} - El cliente que se quiere crear
     * @return JSON {@link ClienteDTO} - El cliente creado con el id autogenerado
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el cliente
     */
    @POST
    public ClienteDTO createCliente(ClienteDTO cliente) throws BusinessLogicException
    {
        //TODO: Completar cascarón.
         LOGGER.log(Level.INFO, "EditorialResource createEditorial: input: {0}", cliente.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        //ClienteEntity clienteEntity = cliente.toEntity();
        // Invoca la lógica para crear la editorial nueva
        //ClienteEntity nuevoClienteEntity = clienteLogic.createCliente(clienteEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        //ClienteDTO nuevoClienteDTO = new ClienteDTO(nuevoClienteEntity);
        //LOGGER.log(Level.INFO, "EditorialResource createEditorial: output: {0}", nuevoClienteDTO.toString());
        //return nuevoClienteDTO;
        return cliente;
    }
}
