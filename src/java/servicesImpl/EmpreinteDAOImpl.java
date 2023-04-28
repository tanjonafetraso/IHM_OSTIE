/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesImpl;
/*
Requête SQL pour l'empreinte Digitale
*/
public abstract class EmpreinteDAOImpl {
    
    public static final String TABLE_NAME = "empreintes";
    public static final String COL_ID = "id_empreinte";
    public static final String COL_DOIGT = "doigt";
    public static final String COL_IMAGE = "empreinte";
    public static final String COL_IMAGE_MINITIE = "empreinte_minutie";
    public static final String COL_AFF_ID = "aff_id";
    public static final String SIZE = "select count(*) from empreintes";
    public static final String INSERT = "INSERT INTO "+TABLE_NAME+"("+COL_DOIGT+", "+COL_IMAGE+", "+COL_AFF_ID+") VALUES(?,AES_ENCRYPT(?,'key'), ?)";
    public static final String FIND_IMAGE_BY_PERSONNE_ID_DOIGT = "SELECT AES_DECRYPT("+COL_IMAGE+",'key')as blobImg FROM "+TABLE_NAME+" INNER JOIN user_aff_id ON empreintes.aff_id=user_aff_id.id  WHERE user_aff_id.aff_id=?";
}
