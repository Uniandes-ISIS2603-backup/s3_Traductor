/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;
import co.edu.uniandes.csw.traductor.entities.TarjetaDeCreditoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author ANDRES
 */
public class TarjetaDeCreditoDTO implements Serializable
{
    private Long idTarjeta;
    private Long numeroTarjetaCredito;
    private Integer ccv;
    private Integer mesExpiracion;
     private Integer anioExpiracion;
    private String redBancaria; 
    private String nombreTitular;

  

    public TarjetaDeCreditoDTO(TarjetaDeCreditoEntity tarjeta) {
        idTarjeta=tarjeta.getId();
        numeroTarjetaCredito=tarjeta.getNumeroTarjetaCredito();
        ccv=tarjeta.getCcv();
        mesExpiracion=tarjeta.getMesExpiracion();
        anioExpiracion=tarjeta.getAnioExpiracion();
        redBancaria=tarjeta.getRedBancaria();
    }
    
    public TarjetaDeCreditoDTO()
    {
        
    }
   
public TarjetaDeCreditoEntity toEntity()
	{
		TarjetaDeCreditoEntity tarjetaDeCredito = new TarjetaDeCreditoEntity();
                tarjetaDeCredito.setId(idTarjeta);
		tarjetaDeCredito.setCcv(ccv);
                tarjetaDeCredito.setMesExpiracion(mesExpiracion);
                tarjetaDeCredito.setAnioExpiracion(anioExpiracion);
                tarjetaDeCredito.setRedBancaria(redBancaria);
                tarjetaDeCredito.setNombreTitular(nombreTitular);
                tarjetaDeCredito.setNumeroTarjetaCredito(numeroTarjetaCredito);
		return tarjetaDeCredito;
	}	
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
      public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }
    
    public Long getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(Long idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public Long getNumeroTarjetaCredito() {
        return numeroTarjetaCredito;
    }

    public void setNumeroTarjetaCredito(Long numeroTarjetaCredito) {
        this.numeroTarjetaCredito = numeroTarjetaCredito;
    }

    public Integer getCcv() {
        return ccv;
    }

    public void setCcv(Integer ccv) {
        this.ccv = ccv;
    }

    public String getRedBancaria() {
        return redBancaria;
    }

    public void setRedBancaria(String redBancaria) {
        this.redBancaria = redBancaria;
    }
    
    public Integer getMesExpiracion() {
        return mesExpiracion;
    }

    public void setMesExpiracion(Integer mesExpiracion) {
        this.mesExpiracion = mesExpiracion;
    }

    public Integer getAnioExpiracion() {
        return anioExpiracion;
    }

    public void setAnioExpiracion(Integer anioExpiracion) {
        this.anioExpiracion = anioExpiracion;
    }
    }

