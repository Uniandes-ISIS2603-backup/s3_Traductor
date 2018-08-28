package co.edu.uniandes.csw.traductor.dtos;

import java.io.File;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan Felipe Parra Camargo
 */
public class PersonaNaturalDTO {
    private String name;
    private Date bornDate;
    private File profilePhoto;
    private Integer identificationNumber;
    public PersonaNaturalDTO(){
        
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the bornDate
     */
    public Date getBornDate() {
        return bornDate;
    }

    /**
     * @param bornDate the bornDate to set
     */
    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    /**
     * @return the profilePhoto
     */
    public File getProfilePhoto() {
        return profilePhoto;
    }

    /**
     * @param profilePhoto the profilePhoto to set
     */
    public void setProfilePhoto(File profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    /**
     * @return the identificationNumber
     */
    public Integer getIdentificationNumber() {
        return identificationNumber;
    }

    /**
     * @param identificationNumber the identificationNumber to set
     */
    public void setIdentificationNumber(Integer identificationNumber) {
        this.identificationNumber = identificationNumber;
    }
    
}
