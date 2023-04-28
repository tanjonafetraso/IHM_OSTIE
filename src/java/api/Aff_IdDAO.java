/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import database.MysqlConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import servicesImpl.Aff_IdDAOImpl;
import static servicesImpl.EmpreinteDAOImpl.SIZE;

/**
 *
 * @author EQIMA
 */
public class Aff_IdDAO extends Aff_IdDAOImpl {

    private static Aff_IdDAO instance;

    /**
     * Instanciation de la class
     *
     * @return
     */
    public static Aff_IdDAO getInstance() {
        if (instance == null) {
            instance = new Aff_IdDAO();
        }
        return instance;
    }

    /**
     * Inserer l'AFF_Id au BDD
     *
     * @param Aff_Id
     */
    public void create(String Aff_Id) {
        try {
            PreparedStatement statement = MysqlConnection.getInstance().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, Aff_Id);

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * COnsulter l'AFF_ID dans la BDD
     * @param Aff_id
     * @return 
     */
    public int getAll(String Aff_id) {
        int resultat = 0;
        try {
            PreparedStatement statement = MysqlConnection.getInstance().prepareStatement(FIND);
            statement.setString(1, Aff_id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                resultat = rs.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultat;
    }
    public String getFiveLastRecord() {
       String affString="";
        try {
            PreparedStatement statement = MysqlConnection.getInstance().prepareStatement(FIND_FIVE_LAST_RECORD);
           
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                affString+= String.valueOf(rs.getString("aff_id"))+",";
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return affString;
    }
    
    /**
     * getsize in BD
     * @return 
     */
     public int getSize() {
        int res = 0;
        try {
            PreparedStatement statement = MysqlConnection.getInstance().prepareStatement(SIZE);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }
}
