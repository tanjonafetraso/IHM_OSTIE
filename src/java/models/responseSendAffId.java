/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author eqima
 */
public class responseSendAffId {
    private String doigtlibre;
    private String action;

    public String getDoigtlibre() {
        return doigtlibre;
    }

    public void setDoigtlibre(String doigtlibre) {
        this.doigtlibre = doigtlibre;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public responseSendAffId(String doigtlibre, String action) {
        this.doigtlibre = doigtlibre;
        this.action = action;
    }
    
}
