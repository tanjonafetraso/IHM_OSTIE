/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import Controller.ConnexionDBBCtrl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author EQIMA
 */
public class MysqlConnection {

    public static Connection instance; 
    
    public static Connection getInstance() {
//        String basename = ConnexionDBBCtrl.DBBname.trim();
        if (instance == null) {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                return DriverManager.getConnection("jdbc:mysql://178.170.39.186/OSTIE?autoReconnect=true", "ostieuser", "ostieuser");
            } catch (ClassNotFoundException e) {
                System.out.println("PILOTE INTROUVABLE");
                return null;
            } catch (SQLException e) {
                System.out.println("BASE INTROUVABLE");
                return null;
            }
        }
        return instance;
    }

}
