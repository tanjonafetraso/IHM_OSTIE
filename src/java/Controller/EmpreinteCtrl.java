/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.Empreinte;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author eqima
 */
@RestController
public class EmpreinteCtrl {

    private static String EmpreinteCapture = null;
    private static String sessionUser;
    private static String quality;
    private static long mhBD;

    /**
     * Fonction API pour recuperer l'image capture à partir du .JAR de
     * l'utilisateur
     *
     *     * @param mhDB
     * @param fingerTemplate
     * @param codeAgent
     * @param qualite
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendEmpreinte/API", method = RequestMethod.POST)
    public String getEmpreintAPI(@RequestBody String fingerTemplate, @RequestParam("CodeAgent") String codeAgent, @RequestParam("quality") String qualite, HttpServletRequest request) {
        try {
            System.out.println("finger= " + Arrays.toString(fingerTemplate.getBytes()));
            EmpreinteCapture = fingerTemplate;
            System.out.println(EmpreinteCapture);
            sessionUser = codeAgent;
            quality = qualite;
            return "Ok";
        } catch (Exception e) {
            return "Eurrer server";
        }

    }

    /**
     * Fonction recuper l'empreinte cature et l'envoyer à la page web
     *
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/getEmpreinte/API", method = RequestMethod.GET)
    public static Empreinte getFileCapture(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        Empreinte p = null;

        if ((EmpreinteCapture != null) && (session.getAttribute("CodeAgent").toString().equals(sessionUser))) {
            session.setAttribute("Empreinte", EmpreinteCapture);
            session.setAttribute("quality", quality);
        }
        if (session.getAttribute("Empreinte").toString().equals("")) {
            return null;
        } else {
            return p = new Empreinte(session.getAttribute("Empreinte").toString());
        }
    }

    /**
     * RESETE DATA
     *
     * @param request
     */
    @RequestMapping(value = "resetDataAction", method = RequestMethod.POST)
    public void resetData(HttpServletRequest request) {
        HttpSession session = request.getSession();
        EmpreinteCapture = null;
        DecisionActionByAPI.DecisionAction = "";
        DecisionActionByAPI.AFF_ID = "";
        DecisionActionByAPI.DoigtLibres = "";
        DecisionActionByAPI.SessionUser = "";
        session.setAttribute("Aff_Id", "");
        session.setAttribute("Decision", "");
        session.setAttribute("Empreinte", "");
        session.setAttribute("quality", "");

    }

    @RequestMapping(value = "resetDataAction1", method = RequestMethod.POST)
    public void resetData1(HttpServletRequest request) {
        HttpSession session = request.getSession();
        EmpreinteCapture = null;
        session.setAttribute("Empreinte", "");
    }
}
