/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


import java.io.File;
import java.sql.Blob;

/**
 *
 * @author EQIMA
 */
public class Empreinte {
    private int id;
    private String doigt;
    private int collaborateur_id;
    private String dataEmpreinte;
    public static File fileEmpreinte=new File("FingerPrinte.bmp");
    
    
    public static String getClasseName(){
    
        return "Empreint";
    
    }

    public Empreinte() {
    }

    public Empreinte(String dataEmpreinte) {
        this.dataEmpreinte = dataEmpreinte;
    }

    public Empreinte(String doigt, int collaborateur_id) {
        this.doigt = doigt;
        this.collaborateur_id = collaborateur_id;
    }

    public File getFileEmpreinte() {
        return fileEmpreinte;
    }

    public void setFileEmpreinte(File fileEmpreinte) {
        this.fileEmpreinte = fileEmpreinte;
    }

    public Empreinte(String doigt, int collaborateur_id, File fileEmpreinte) {
        this.doigt = doigt;
        this.collaborateur_id = collaborateur_id;
        this.fileEmpreinte = fileEmpreinte;
    }

    public Empreinte(int id, String doigt, int collaborateur_id) {
        this.id = id;
        this.doigt = doigt;
        this.collaborateur_id = collaborateur_id;
    }

    public String getDataEmpreinte() {
        return dataEmpreinte;
    }

    public void setDataEmpreinte(String dataEmpreinte) {
        this.dataEmpreinte = dataEmpreinte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoigt() {
        return doigt;
    }

    public void setDoigt(String doigt) {
        this.doigt = doigt;
    }

    public int getCollaborateur_id() {
        return collaborateur_id;
    }

    public void setCollaborateur_id(int collaborateur_id) {
        this.collaborateur_id = collaborateur_id;
    }
    
    public Blob getImage(){
    
        return null;//DAOFactory.getEmpreinteDAO().getImageByIdAndDoigt(collaborateur_id, doigt);
    
    }
}
