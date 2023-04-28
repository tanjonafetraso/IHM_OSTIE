/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Utiles.AllPath;
import Utiles.uploadfile;
import api.Aff_IdDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.CSV;
import models.Empreinte;
import models.responseSendAffId;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author eqima
 */
@RestController
public class InscriptionCtrllOSTIE {

    ArrayList<String> lisetEmpreinteIdentifier = new ArrayList<>();

    @RequestMapping(value = "/resetMultipleDataEmpreinteAction", method = RequestMethod.POST)
    public void resetData(HttpServletRequest request) {
        lisetEmpreinteIdentifier.clear();
    }

    /**
     * MACTHING IDENTIFICATIONS EMPREINTE
     *
     * @param matricule
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/IdentifierControllerAction", method = RequestMethod.GET)
    public String ScoreIdentification(@RequestParam("Aff_Id") String matricule, HttpServletRequest request) throws IOException {
        String score = "";
        try {
            HttpSession session = request.getSession();
            score = uploadfile.PostFileIndentification(session.getAttribute("Empreinte").toString(), AllPath.pathIdentification + session.getAttribute("CodeAgent") + "&aff_id=" + matricule + "&quality=" + session.getAttribute("quality"));
        } catch (Exception ex) {
            Logger.getLogger(InscriptionCtrllOSTIE.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "{\"couleur\":\"" + score + "\"}";
    }

    @RequestMapping(value = "/getAllInscription", method = RequestMethod.POST)
    public ArrayList<CSV> getAllInscription(@RequestParam("date1") String date1, @RequestParam("date2") String date2, HttpServletRequest request) {
        try {

            ArrayList<CSV> csv = new ArrayList<>();
            HttpSession session = request.getSession();
            String res = uploadfile.GetAllInstription(AllPath.pathGetAllInscription, date1, date2);
            String[] data = res.split("dataLigne");
            for (String string : data) {
                String[] propt = string.split("dataEmp");
                csv.add(new CSV(propt[0], propt[1], propt[2], propt[3], propt[4], propt[5]));
            }

            return csv;
        } catch (IOException ex) {
            Logger.getLogger(InscriptionCtrllOSTIE.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @RequestMapping(value = "/getAllIdentification", method = RequestMethod.POST)
    public ArrayList<CSV> getAllIdentification(@RequestParam("date1") String date1, @RequestParam("date2") String date2, HttpServletRequest request) {
        try {

            ArrayList<CSV> csv = new ArrayList<>();
            HttpSession session = request.getSession();
            String res = uploadfile.GetAllIdentification(AllPath.getAllIdentification, date1, date2);
            if (res.length() > 0) {
                String[] data = res.split("dataLigne");
                for (String string : data) {
                    String[] propt = string.split("dataEmp");
                    csv.add(new CSV(propt[0], propt[1], propt[2], propt[3]));
                }
            }

            return csv;
        } catch (IOException ex) {
            Logger.getLogger(InscriptionCtrllOSTIE.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @RequestMapping(value = "/verificationControllerAction", method = RequestMethod.GET)
    public String ScoreVerification(HttpServletRequest request) throws IOException {
        Aff_IdDAO affDao = new Aff_IdDAO();
        String affIdString = affDao.getFiveLastRecord();
        String score = "";
        try {
            HttpSession session = request.getSession();
            String b64 = session.getAttribute("Empreinte").toString();
            score = uploadfile.PostFileIndentification(b64, AllPath.pathIdentification + affIdString);
        } catch (Exception ex) {
            Logger.getLogger(InscriptionCtrllOSTIE.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "{\"couleur\":\"" + score + "\"}";
    }

    @RequestMapping(value = "/IdentifierMultipleControllerAction", method = RequestMethod.GET)
    public String ScoreIdentificationMultiple(@RequestParam("Aff_Id") String matricule, HttpServletRequest request) throws IOException {
        String score = "";
        try {
            HttpSession session = request.getSession();
            String b64 = session.getAttribute("Empreinte").toString();
            score = uploadfile.PostFileIndentification(b64, AllPath.pathIdentificationMultiple + matricule);

//            score = uploadfile.PostFileIndentificationMultiple(b64, AllPath.pathIdentificationMultiple + matricule);
        } catch (Exception ex) {
            Logger.getLogger(InscriptionCtrllOSTIE.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "{\"couleur\":\"" + score + "\"}";
    }

    /**
     *
     * @param request
     * @throws IOException
     */
    @RequestMapping(value = "/nextIdentification", method = RequestMethod.POST)
    public void nextIdentification(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        String empreinteIdentifier = session.getAttribute("Empreinte").toString();
        Empreinte p = null;
        if (empreinteIdentifier.length() > 10) {

            lisetEmpreinteIdentifier.add(empreinteIdentifier);

        }
    }

    /**
     * ENVOYER L'AFF_ID VERS L' API POUR PRENDRE UNE DECISION
     *
     * @param aff_id
     * @param actionType
     * @param action
     * @param request
     * @return
     * @throws ProtocolException
     * @throws IOException
     * @throws org.json.simple.parser.ParseException
     */
    @RequestMapping(value = "/sendAffIDAction", method = RequestMethod.GET)
    public responseSendAffId sendAffID(@RequestParam("Aff_Id") String aff_id, @RequestParam("Action") String actionType, HttpServletRequest request) throws ProtocolException, IOException, ParseException {
        responseSendAffId responseAffId = null;
        HttpSession session = request.getSession();
        System.out.println("sed AFF ID");
        session.setAttribute("Aff_Id", aff_id);
        String doigt = null;
        String action = null;
        System.out.println("actionType=" + actionType);
        if (actionType.equals("2")) {
            try {
                System.out.println("ici");
                URL url = new URL("http://apiostie.eqima.org/api/getdoigt?aff_id=" + aff_id);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.connect();
                int responsecode = conn.getResponseCode();
                if (responsecode == 200) {

                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine = null;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        doigt = response.toString();
                        action = "REINSCRIPTION";
                        System.out.println("doigt====" + doigt);
                        responseAffId = new responseSendAffId(doigt, action);
                    }
                } else {
                    System.out.println("erreur=" + responsecode);
                    return null;
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(InscriptionCtrllOSTIE.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                URL url = new URL("http://apiostie.eqima.org/ideqima/recherche/" + aff_id + "/" + session.getAttribute("CodeAgent").toString() + "/" + actionType);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("x-api-key", "eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiJTZWFyY2gyMDIyIiwiaWF0IjoxNjQ3NTMyMzgwLCJzdWIiOiJPc3RpZU1hZGEiLCJpc3MiOiJFcWltYVNlcnZpY2UifQ.QaVwNnJbKbasw0RuXX6EH8J2TFM1GoXDN_TzRxLfiOuHvRX_1wf32KvnxePPbsFJ");
                conn.connect();
                int responsecode = conn.getResponseCode();
                if (responsecode == 200) {
                    String data;
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine = null;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        String res = response.toString();
                        if (res.contains("liste_doigt")) {
                            String res1 = res;
                            int rang = (res1.indexOf("action")) - res1.indexOf("liste_doigt");
                            System.out.println("rang====" + rang);
                            if (rang > 20) {
                                doigt = (res1.substring(res1.indexOf("liste_doigt") + 13, res1.indexOf("action") - 3)).replace("\"", "");
                                doigt = doigt.substring(doigt.indexOf("[") + 1, doigt.indexOf("]"));
                            }

                        }
                        if (res.contains("INSCRIPTION") || res.contains("REINSCRIPTION") || res.contains("IDENTIFICATION")) {
                            String res12 = res;
                            System.out.println("res12====" + res12);
                            action = res12.substring(res12.indexOf("action") + 10, res12.indexOf("ION") + 3);
                            System.out.println("Actio=====" + action);
                        }
                        responseAffId = new responseSendAffId(doigt, action);
                    }
                } else {
                    return null;
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(InscriptionCtrllOSTIE.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return responseAffId;
    }

    /**
     * FAIRE L'INSCRIPTION
     *
     * @param doigt
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/writeEmpreinteAff_IdAction", method = RequestMethod.GET)
    public int writeEmpreinteAff_Id(@RequestParam("Doigt") String doigt, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String res = null;
        if (session.getAttribute("Empreinte").toString().length() > 0) {
            String pathurl = AllPath.pathInscription + session.getAttribute("CodeAgent").toString() + "&doigt=" + doigt + "&aff_id=" + session.getAttribute("Aff_Id").toString() + "&quality=" + session.getAttribute("quality");
            System.out.println(pathurl);
            res = uploadfile.PostFileInscription(session.getAttribute("Empreinte").toString(), pathurl);
        }

        session.setAttribute("Decision", "");
        DecisionActionByAPI.DecisionAction = "";
        if (res.equals("Succes")) {
            return 1;
        } else {
            return 0;
        }

    }

    @RequestMapping(value = "/updateEmpreinte", method = RequestMethod.GET)
    public int updateEmpreinte(@RequestParam("Doigt") String doigt, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String res = null;
        if (session.getAttribute("Empreinte").toString().length() > 0) {
            String pathurl = AllPath.pathModification + session.getAttribute("CodeAgent").toString() + "&doigt=" + doigt + "&aff_id=" + session.getAttribute("Aff_Id").toString()+ "&quality=" + session.getAttribute("quality").toString();
            res = uploadfile.PostFileInscription(session.getAttribute("Empreinte").toString(), pathurl);
        }
        session.setAttribute("Decision", "");
        DecisionActionByAPI.DecisionAction = "";
        if (res.equals("Succes")) {
            return 1;
        } else {
            return 0;
        }

    }
}
