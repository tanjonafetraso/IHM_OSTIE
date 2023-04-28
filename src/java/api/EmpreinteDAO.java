/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import database.MysqlConnection;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Empreinte;
import servicesImpl.EmpreinteDAOImpl;
import static servicesImpl.EmpreinteDAOImpl.FIND_IMAGE_BY_PERSONNE_ID_DOIGT;

/**
 *
 * @author EQIMA
 */
public class EmpreinteDAO extends EmpreinteDAOImpl {

    private static EmpreinteDAO instance;

    /**
     * Get l'instanciation de la classe
     *
     * @return
     */
    public static EmpreinteDAO getInstance() {
        if (instance == null) {
            instance = new EmpreinteDAO();
        }
        return instance;
    }

    /**
     * Inserer l'empreinte digitale dans la BDD
     *
     * @param obj
     * @return
     * @throws IOException
     * @throws Exception
     */
    public Empreinte create(Empreinte obj) throws IOException, Exception {
        try {
            PreparedStatement statement = MysqlConnection.getInstance().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, obj.getDoigt());
            statement.setBlob(2, new FileInputStream(Empreinte.fileEmpreinte));
            statement.setInt(3, obj.getCollaborateur_id());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                obj.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmpreinteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }

    /**
     * Get Empreintes dans la DBB where id=?
     *
     * @param personne_id
     * @return
     */
    public List<Blob> getImageEmpreinteBDD(String personne_id) {
        List<Blob> image = new ArrayList<>();
        try {
            PreparedStatement statement = MysqlConnection.getInstance().prepareStatement(FIND_IMAGE_BY_PERSONNE_ID_DOIGT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, personne_id);
            ResultSet rs = statement.executeQuery();
            int i = 1;
            while (rs.next()) {
                System.out.println(i);
                image.add(rs.getBlob(1));
                i++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return image;
    }

    /**
     * getsize in BDD
     *
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
