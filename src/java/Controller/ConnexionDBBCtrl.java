/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import database.MysqlConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author eqima
 */
@RestController
public class ConnexionDBBCtrl {

    public static String DBBname = "BDD 2000";
/**
 * CHANGER LA CONNEXION DU BASE
 * @param nameDBB
 * @return
 * @throws SQLException 
 */
    @RequestMapping(value = "/ConnexionDataBASE", method = RequestMethod.GET)
    public int connexionChange(@RequestParam("NameDBB") String nameDBB) throws SQLException {
        DBBname = nameDBB;
        if (MysqlConnection.instance!=null) {
            MysqlConnection.instance.close();
            MysqlConnection.instance=null;
        }
        Connection conn = null;
        conn = MysqlConnection.getInstance();
        if (conn != null) {
            return 1;
        } else {
            return 0;
        }
    }
}
