/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import api.Aff_IdDAO;
import api.EmpreinteDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.size;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author eqima
 */
@RestController
public class SizeDataBaseCtrl {
@RequestMapping(value = "/getsizeAction",method = RequestMethod.GET)
    public size getSizeEmpreinteUtilisateur(HttpServletRequest request) {
        HttpSession session=request.getSession();
        int sizeUser=Aff_IdDAO.getInstance().getSize();
        System.out.println(sizeUser);
        int sizeFinger=EmpreinteDAO.getInstance().getSize();
        System.out.println(sizeFinger);
        size s=new size(sizeUser, sizeFinger);
        return s;
    }

    
}
