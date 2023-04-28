/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.swing.JApplet;

/**
 *
 * @author eqima
 */
@RestController
public class rooterPage {

    @RequestMapping(value = "/")
    public static ModelAndView AuthentificationPage(HttpServletRequest request) {
       
            return new ModelAndView("Authentification");
        
    }
    @RequestMapping(value = "/Inscription")
    public static ModelAndView Inscription(HttpServletRequest request) {
        HttpSession session = request.getSession();
        boolean isAuth = false;
        try {
            isAuth = (boolean) session.getAttribute("isAuth");
        } catch (java.lang.NullPointerException e) {
        }
        if (isAuth) {
            ModelAndView mv = new ModelAndView("Inscription");
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }
    @RequestMapping(value = "/Deconnexion")
    public static ModelAndView deconnexion(HttpServletRequest request) {
        HttpSession session = request.getSession();       
        boolean isAuth = false;
        try {
            isAuth = (boolean) session.getAttribute("isAuth");
        } catch (java.lang.NullPointerException e) {
        }
        if (isAuth) {
             session.invalidate();
             return new ModelAndView("redirect:/");
        } else {
            return new ModelAndView("redirect:/");
        }
    }

//    @RequestMapping(value = "/Authentification")
//    public ModelAndView login() {
//        ModelAndView mv = new ModelAndView("Authentification");
//        return mv;
//    }
//    @RequestMapping(value = "/Accueil")
//    public static ModelAndView Accueil() {
//        return new ModelAndView("Accueil");
//    }
//
//    @RequestMapping(value = "/Empreinte")
//    public static ModelAndView Empreinte() {
//        return new ModelAndView("Empreinte");
//    }
//
//    @RequestMapping(value = "/Identification")
//    public static ModelAndView Identification() {
//        return new ModelAndView("Identification");
//    }
//
//    @RequestMapping(value = "/Inscription")
//    public static ModelAndView Inscription() {
//        return new ModelAndView("Inscription");
//    }

}
