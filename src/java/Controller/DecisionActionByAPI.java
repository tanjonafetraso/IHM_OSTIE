/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.DeccisionAction;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author eqima
 */
@RestController
public class DecisionActionByAPI {

    public static String DecisionAction = "insription";
    public static String AFF_ID = "";
    public static String DoigtLibres = "";
    public static String SessionUser = "";

    /**
     * API POUR RECEVOIRE LA DECISION SOIT INSCRIPTION SOIT IDENTIFICATION
     *
     * @param Aff_Id
     * @param Action
     * @param doigtLibres
     * @param session
     */
    @RequestMapping(value = "/AFF_ID/Decision/Action", method = RequestMethod.GET)
    public void rechercherCollaborateurDansAPI(@RequestParam("Aff_Id") String Aff_Id, @RequestParam("Action") String Action, @RequestParam("DoigtLibres") String doigtLibres, @RequestParam("codeAgent") String session) {
        SessionUser = session;
        DecisionAction = Action;
        AFF_ID = Aff_Id;
        DoigtLibres = doigtLibres;

    }

    /**
     * FUNCTION POUR RECUPERER LA DECISION
     *
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/getDecissionByAPIAction", method = RequestMethod.GET)
    public DeccisionAction getDecissionByAPI(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        DeccisionAction p = new DeccisionAction(DecisionAction, DoigtLibres, SessionUser);
        if (session.getAttribute("CodeAgent").toString().equals(SessionUser)) {
            session.setAttribute("Aff_Id", AFF_ID);
            session.setAttribute("Decision", DecisionAction);
            return p;
        } else {
            return null;
        }
    }
}
