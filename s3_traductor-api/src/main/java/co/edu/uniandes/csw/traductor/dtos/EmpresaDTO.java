/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

/**
 *
 * @author Juan Felipe Parra Camargo
 */
public class EmpresaDTO extends ClienteDTO{
    private String nit;
    private String socialReason;
    public EmpresaDTO(){
        
    }

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
     * @return the socialReason
     */
    public String getSocialReason() {
        return socialReason;
    }

    /**
     * @param socialReason the socialReason to set
     */
    public void setSocialReason(String socialReason) {
        this.socialReason = socialReason;
    }
    
}
