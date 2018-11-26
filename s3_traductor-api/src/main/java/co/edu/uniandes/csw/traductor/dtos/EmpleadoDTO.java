package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity.TipoEmpleado;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro
 */
public class EmpleadoDTO implements Serializable {

    //----------------------------------------------------
    //Atributos
    //----------------------------------------------------
    protected Long id;
    protected String nombreEmpleado;
    protected String correoElectronico;
    protected Integer aniosExperiencia;
    protected String nombreUsuario;
    protected String contrasenia;
    protected String trayectoria;
    protected String hojaDeVida;
    protected TipoEmpleado tipoEmpleado;
    protected String estudios;
    protected String referencias;

    //----------------------------------------------------
    //constructores
    //----------------------------------------------------
    public EmpleadoDTO() {
    }

    public EmpleadoDTO(EmpleadoEntity empleado) {
        if (empleado != null) {
            this.id = empleado.getId();
            this.nombreEmpleado = empleado.getNombreEmpleado();
            this.correoElectronico = empleado.getCorreoElectronico();
            this.aniosExperiencia = empleado.getAniosExperiencia();
            this.nombreUsuario = empleado.getNombreUsuario();
            this.contrasenia = empleado.getContrasenia();
            this.trayectoria = empleado.getTrayectoria();
            this.hojaDeVida = empleado.getHojaDeVida();
            this.tipoEmpleado = empleado.getTipoEmpleado();
            this.estudios = empleado.getEstudios();
            this.referencias = empleado.getReferencias();
        }
    }
    //----------------------------------------------------
    //getters and setters
    //----------------------------------------------------

    public String getEstudios() {
        return estudios;
    }

    public void setEstudios(String estudios) {
        this.estudios = estudios;
    }

    public String getReferencias() {
        return referencias;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }

    public TipoEmpleado getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(TipoEmpleado tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    /**
     * obtiene la trayectoria de un empleado
     *
     * @return trayectoria
     */
    public String getTrayectoria() {
        return trayectoria;
    }

    /**
     * asigna la trayectoria a un empleado
     *
     * @param trayectoria trayectoria del empleado.
     */
    public void setTrayectoria(String trayectoria) {
        this.trayectoria = trayectoria;
    }

    public String getHojaDeVida() {
        return hojaDeVida;
    }

    public void setHojaDeVida(String hojaDeVida) {
        this.hojaDeVida = hojaDeVida;
    }

    /**
     * metodo para obtener la id del empleado
     *
     * @return id empleado
     */
    public Long getId() {
        return id;
    }

    /**
     * metodo para agrear la id del empleado
     *
     * @param id la id del documento
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * metodo para obtener el nombre del empleado
     *
     * @return nombre del empleado
     */
    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    /**
     * metodo para agrear el nombre del empleado
     *
     * @param nombreEmpleado
     */
    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    /**
     * metodo para obtener el correo del empleado
     *
     * @return correo electronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * metodo para asignar el correo electronico al empleado
     *
     * @param correoElectronico
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * metodo para obtener los anios de experiencia del empleado
     *
     * @return
     */
    public Integer getAniosExperiencia() {
        return aniosExperiencia;
    }

    /**
     * metodo para asignar el numero de anios de experiencia laboral del
     * empleado
     *
     * @param aniosExperiencia
     */
    public void setAniosExperiencia(Integer aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    /**
     * metodo para obtener el nombre de usuario del empleado
     *
     * @return nombre de usuario del empleado
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * metodo para asignar el nombre de usuario del empleado
     *
     * @param nombreUsuario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * metodo para obtener la contrasenia del empleado registrado
     *
     * @return contrasenia del empleado
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * metodo para asignar la contrasenia del empleado
     *
     * @param contrasenia contrasenia asiganada
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    //----------------------------------------------------
    //Metodos adicionales
    //----------------------------------------------------
    /**
     * convierte un objeto empleado dto a estring para generar el log
     *
     * @return string
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * convuierte un EmpleadoDTO a un objeto entity correspondiente para la
     * persistencia
     *
     * @return nuevo entity correspondiente
     */
    public EmpleadoEntity toEntity() {
        //nemen = newEmpleadoEntity
        EmpleadoEntity nemen = new EmpleadoEntity();
        nemen.setId(this.id);
        nemen.setNombreEmpleado(this.nombreEmpleado);
        nemen.setCorreoElectronico(this.correoElectronico);
        nemen.setAniosExperiencia(this.aniosExperiencia);
        nemen.setNombreUsuario(this.nombreUsuario);
        nemen.setContrasenia(this.contrasenia);
        nemen.setTrayectoria(this.trayectoria);
        nemen.setHojaDeVida(this.hojaDeVida);
        nemen.setTipoEmpleado(this.tipoEmpleado);
        nemen.setEstudios(this.estudios);
        nemen.setReferencias(this.referencias);
        return nemen;
    }
}
