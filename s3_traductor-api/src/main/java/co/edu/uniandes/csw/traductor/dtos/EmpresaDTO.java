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
    /**
     * nit de la empresa
     */
    private String nit;
    /**
     * rason social de la empresa
     */
    private String rasonSocial;
    /**
     * constructor vacío
     */
    public EmpresaDTO(){
        
    }
    /**
     * constructor de una empresa a partir de su entity
     * @param empresaEntity Entity de la empresa con la cual se construyen los DTO
     */
    public EmpresaDTO(EmpresaEntity empresaEntity){
        super(empresaEntity);
        if(empresaEntity!=null){
            
            this.nit=empresaEntity.getNit();
            this.rasonSocial=empresaEntity.getRasonSocial();
        }
    }
    /**
     * @return el nit de la empresa
     */
    public String getNit() {
        return nit;
    }

    /**
     * @param nit, el nit por el cual se cambiará el actual
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     * @return la rason social de la empresa
     */
    public String getRasonSocial() {
        return rasonSocial;
    }

    /**
     * @param socialReason, la rason social por la cual se va a cambiar la actual
     */
    public void setRasonSocial(String socialReason) {
        this.rasonSocial = socialReason;
    }
    /**
     * Convertir en entity el DTO actual
     * @return  EmpresaEntity, entidad de la empresa actual
     */
    public EmpresaEntity toEntity(){
        super.toEntity();
        EmpresaEntity empresa=new EmpresaEntity();
        empresa.setNit(nit);
        empresa.setRasonSocial(rasonSocial);
        return empresa;
    }
    @Override
    /**
     * convertir la empresaDTO actual a String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
