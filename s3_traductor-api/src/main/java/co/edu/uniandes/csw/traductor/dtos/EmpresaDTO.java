/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;
import co.edu.uniandes.csw.traductor.entities.EmpresaEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 *
 * @author Juan Felipe Parra Camargo
 */
public class EmpresaDTO extends ClienteDTO{
    private String nit;
    private String rasonSocial;
    public EmpresaDTO(){
        
    }
    public EmpresaDTO(EmpresaEntity empresaEntity){
        super(empresaEntity);
        if(empresaEntity!=null){
            
            this.nit=empresaEntity.getNit();
            this.rasonSocial=empresaEntity.getRasonSocial();
        }
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
    public String getRasonSocial() {
        return rasonSocial;
    }

    /**
     * @param socialReason the socialReason to set
     */
    public void setRasonSocial(String socialReason) {
        this.rasonSocial = socialReason;
    }
    public EmpresaEntity toEntity(){
        super.toEntity();
        EmpresaEntity empresa=new EmpresaEntity();
        empresa.setNit(nit);
        empresa.setRasonSocial(rasonSocial);
        return empresa;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
