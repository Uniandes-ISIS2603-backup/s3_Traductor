/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import java.io.Serializable;

/**
 * Clase que representa a un cliente dentro de la app.
 * @author Santiago Salazar
 */
public class ClienteDTO implements Serializable
{
    protected Long id;
    protected String nombreUsuario;
    protected String contrasenia;
    protected String correoElectronico;
    protected String nombre;
    protected String identificacion;
    protected ClienteEntity.TipoCliente tipoCliente;

    /**
     * Da el nombre de un cliente en el sistema
     * @return El nombre del cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre del cliente
     * @param nombre El nuevo nombre del cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Da la identificacion (NIT o documento) del cliente.
     * @return El documento identificacion del cliente.
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * Cambia la identificacion del cliente.
     * @param identificacion La nueva identificacion
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    
    /**
     * Metodo que da el id del cliente
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
     * Da el tipo del cliente en cuestion
     * @return El tipo de cliente
     */
    public ClienteEntity.TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    /**
     * Cambia el tipo de cliente por el parametro
     * @param tipoCliente En nuevo tipo de cliente
     */
    public void setTipoCliente(ClienteEntity.TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
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
            this.nombre = clienteEntity.getNombre();
            this.identificacion = clienteEntity.getIdentificacion();
            this.tipoCliente = clienteEntity.getTipoCliente();
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
        clienteEntity.setNombre(this.nombre);
        clienteEntity.setIdentificacion(this.identificacion);
        clienteEntity.setTipoCliente(this.tipoCliente);
        return clienteEntity;
    }
}
