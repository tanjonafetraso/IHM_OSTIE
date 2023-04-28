/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

/**
 *
 * @author eqima
 */
public class AllPath {

    public static String pathIdentification= "http://pp.eqima.org/api/idmultiples?code_agent=";
    public static String pathIdentificationMultiple= "http://pp.eqima.org/api/idgroupees?code_agent=00&aff_id=";
    public static String pathInscription= "http://pp.eqima.org/api/inscription?code_agent=";
    public static String pathModification= "http://pp.eqima.org/api/updateEmpreinte?code_agent=";
    public static String pathGetAllInscription= "http://pp.eqima.org/api/getAllInscription";
    public static String getAllIdentification= "http://pp.eqima.org/api/getAllIdentification";
  //  public static String pathEmpreinte = "C:\\Users\\" + AllPath.getUserNameComputeur() + "\\Empreinte";

    private static String getUserNameComputeur() {
        return System.getProperty("user.name");
    }

}
