/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

/**
 *
 * @author estudiante
 */
public class EmpresaEntity extends ClienteEntity{
    private String nit;
    private String rasonSocial;

    /**
     * @return the nit
     */
    public String getNit() {
        return nit;
    }

    /**
     * @param nit the nit to set
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     * @return the rasonSocial
     */
    public String getRasonSocial() {
        return rasonSocial;
    }

    /**
     * @param rasonSocial the rasonSocial to set
     */
    public void setRasonSocial(String rasonSocial) {
        this.rasonSocial = rasonSocial;
    }
    
}
