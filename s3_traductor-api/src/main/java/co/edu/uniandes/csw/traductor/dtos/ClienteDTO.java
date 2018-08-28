/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import java.io.Serializable;

/**
 *
 * @author Santiago Salazar
 */
public class ClienteDTO implements Serializable
{
    protected Long id;
    protected String nombreUsuario;
    protected String contrasenia;
    protected String correoElectronico;
    
    /**
     * Método que da el id del cliente
     * @return el id del cliente
     */
    public Long getId() {
        return id;
    }

    /**
     * Metodo para editar el id
     * @param id el nuevo id a poner
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Metodo que da el nombre de usuario
     * @return El nombre de usuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Cambia el nombre de usuario por el de parametro
     * @param nombreUsuario el nuevo nombre de usuario.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Metodo que obtiene la contrasenia.
     * @return La contrasenia del cliente.
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Cambia la contrasenia del cliente.
     * @param contrasenia La nueva contrasenia del cliente
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * Da el correo electronico del cliente.
     * @return El correo del cliente.
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Cambia el correo del cliente.
     * @param correoElectronico el nuevo correo del cliente.
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    
    /**
     * Constructor por defecto
     */
    public ClienteDTO() {
    }

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param clienteEntity: Es la entidad que se va a convertir a DTO
     */
    public ClienteDTO(ClienteEntity clienteEntity) {
        if (clienteEntity != null) {
            this.id = clienteEntity.getId();
            this.nombreUsuario = clienteEntity.getNombreUsuario();
            this.correoElectronico = clienteEntity.getCorreoElectronico();
            this.contrasenia = clienteEntity.getContrasenia();
        }
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public ClienteEntity toEntity() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(this.id);
        clienteEntity.setNombreUsuario(this.nombreUsuario);
        clienteEntity.setCorreoElectronico(this.correoElectronico);
        clienteEntity.setContrasenia(this.contrasenia);
        return clienteEntity;
    }
}
