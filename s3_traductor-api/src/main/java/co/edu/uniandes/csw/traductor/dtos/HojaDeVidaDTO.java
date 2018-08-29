/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.HojaDeVidaEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author ANDRES
 */
public class HojaDeVidaDTO {

    private String estudios;
    private String trayectoria;
    private String hojaDeVida;

    public HojaDeVidaDTO() {

    }

    public HojaDeVidaDTO(HojaDeVidaEntity hojaEntity) {
        if (hojaEntity != null) {
            estudios = hojaEntity.getEstudios();
            trayectoria = hojaEntity.getHojaDeVida();
            trayectoria = hojaEntity.getTrayectoria();
        }
    }

    public HojaDeVidaEntity toEntity() {
        HojaDeVidaEntity hoja = new HojaDeVidaEntity();
        hoja.setEstudios(estudios);
        hoja.setHojaDeVida(hojaDeVida);
        hoja.setTrayectoria(trayectoria);
        return hoja;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getEstudios() {
        return estudios;
    }

    public void setEstudios(String estudios) {
        this.estudios = estudios;
    }

    public String getTrayectoria() {
        return trayectoria;
    }

    public void setTrayectoria(String trayectoria) {
        this.trayectoria = trayectoria;
    }

    public String getHojaDeVida() {
        return hojaDeVida;
    }

    public void setHojaDeVida(String hojaDeVida) {
        this.hojaDeVida = hojaDeVida;
    }

}
