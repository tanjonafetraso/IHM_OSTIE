/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Utiles.AllPath;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author eqima
 */
@RestController
public class Authentification {

    /**
     * AUTHENTIFICATION
     *
     * @param username
     * @param pass
     * @param request
     * @return
     */
    @RequestMapping(value = "/AuthentificationAction", method = RequestMethod.GET, produces = "application/json")
    public boolean autthentification(@RequestParam("Username") String username, @RequestParam("Password") String pass, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("isAuth", true);
        session.setAttribute("CodeAgent", username);
        session.setAttribute("Aff_Id", "");
        session.setAttribute("quality", "");
        session.setAttribute("Decision", "");
        session.setAttribute("Empreinte", "");
        return !session.getAttribute("CodeAgent").toString().isEmpty();
    }
}
