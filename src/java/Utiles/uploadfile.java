package Utiles;

//import Model.ModelRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//import org.json.simple.JsonObject;

public class uploadfile {

    public static String PostFileIndentificationMultiple(ArrayList<String> b64, String Url) throws Exception {

        String data1 = "[";
        for (int i = 0; i < b64.size(); i++) {
            data1 += "{\"file\":\"" + b64.get(i) + "\"}";
            if (i < b64.size() - 1) {
                data1 += ",";
            }
        }
        data1 += "]";

        String data = null;
        URL url = new URL(Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("x-api-key", "eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiJTZWFyY2gyMDIyIiwiaWF0IjoxNjQ3NTMyMzgwLCJzdWIiOiJPc3RpZU1hZGEiLCJpc3MiOiJFcWltYVNlcnZpY2UifQ.QaVwNnJbKbasw0RuXX6EH8J2TFM1GoXDN_TzRxLfiOuHvRX_1wf32KvnxePPbsFJ");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = data1.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        conn.connect();
        int resCode = conn.getResponseCode();
        if (resCode == 200) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("JSON========================" + response);
                String res = response.toString();
                JSONParser parser = new JSONParser();
                JSONObject resultat = (JSONObject) parser.parse(res);
                data = (String) resultat.get("resultat");
                System.out.println("score===================" + data);
                //dataImage = data;
                //mr = new ModelRequest(dataImage, TypeAction);
                //iscapture = true;
                //uploadfile.PostFile(mr.getImage(), "http://localhost:8080/FaceReconnaissanceAPI/reconnaissance/facial");
                //uploadfile.PostFile(mr.getImage(), "http://localhost:8080/FaceReconnaissanceAPI/saveimage/tranning/model/action?matriculle=" + TypeAction);
            }
        } else {
            System.out.println("erreur======================" + resCode);
        }

        return data;
    }

    public static String PostFileIndentification(String Base64fingerprintTemplate, String Url) throws Exception {

        String data = null;
        URL url = new URL(Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("x-api-key", "eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiJTZWFyY2gyMDIyIiwiaWF0IjoxNjQ3NTMyMzgwLCJzdWIiOiJPc3RpZU1hZGEiLCJpc3MiOiJFcWltYVNlcnZpY2UifQ.QaVwNnJbKbasw0RuXX6EH8J2TFM1GoXDN_TzRxLfiOuHvRX_1wf32KvnxePPbsFJ");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = Base64fingerprintTemplate.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        conn.connect();
        int resCode = conn.getResponseCode();
        if (resCode == 200) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("JSON========================" + response);
                String res = response.toString();
                JSONParser parser = new JSONParser();
                JSONObject resultat = (JSONObject) parser.parse(res);
                data = (String) resultat.get("resultat");
                System.out.println("score===================" + data);

            }
        } else {
            System.out.println("erreur======================" + resCode);
        }

        return data;
    }

    public static String PostFileInscription(String Base64fingerprintTemplate, String Url) throws Exception {
        String data = null;
        URL url = new URL(Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("x-api-key", "eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiJTZWFyY2gyMDIyIiwiaWF0IjoxNjQ3NTMyMzgwLCJzdWIiOiJPc3RpZU1hZGEiLCJpc3MiOiJFcWltYVNlcnZpY2UifQ.QaVwNnJbKbasw0RuXX6EH8J2TFM1GoXDN_TzRxLfiOuHvRX_1wf32KvnxePPbsFJ");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = Base64fingerprintTemplate.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        conn.connect();
        int resCode = conn.getResponseCode();
        if (resCode == 200) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("JSON========================" + response);
                String res = response.toString();
                JSONParser parser = new JSONParser();
                JSONObject resultat = (JSONObject) parser.parse(res);
                data = (String) resultat.get("resultat");
                System.out.println("score===================" + data);
                //dataImage = data;
                //mr = new ModelRequest(dataImage, TypeAction);
                //iscapture = true;
                //uploadfile.PostFile(mr.getImage(), "http://localhost:8080/FaceReconnaissanceAPI/reconnaissance/facial");
                //uploadfile.PostFile(mr.getImage(), "http://localhost:8080/FaceReconnaissanceAPI/saveimage/tranning/model/action?matriculle=" + TypeAction);
            }
        } else {
            System.out.println("erreur======================" + resCode);
        }

        return data;
    }

    public static String GetAllInstription(String Url,String date1,String date2) throws MalformedURLException, IOException {
        String data = null;
        URL url = new URL(Url+"?date1="+date1+"&date2="+date2);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("x-api-key", "eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiJTZWFyY2gyMDIyIiwiaWF0IjoxNjQ3NTMyMzgwLCJzdWIiOiJPc3RpZU1hZGEiLCJpc3MiOiJFcWltYVNlcnZpY2UifQ.QaVwNnJbKbasw0RuXX6EH8J2TFM1GoXDN_TzRxLfiOuHvRX_1wf32KvnxePPbsFJ");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.connect();
        int resCode = conn.getResponseCode();
        if (resCode == 200) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("JSON========================" + response);
                String res = response.toString();
                JSONParser parser = new JSONParser();
                JSONObject resultat = (JSONObject) parser.parse(res);
                data = (String) resultat.get("resultat");
                System.out.println("score===================" + data);
            } catch (ParseException ex) {
                Logger.getLogger(uploadfile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("erreur======================" + resCode);
        }
        return data;
    }
    public static String GetAllIdentification(String Url,String date1,String date2) throws MalformedURLException, IOException {
        String data = null;
        URL url = new URL(Url+"?date1="+date1+"&date2="+date2);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("x-api-key", "eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiJTZWFyY2gyMDIyIiwiaWF0IjoxNjQ3NTMyMzgwLCJzdWIiOiJPc3RpZU1hZGEiLCJpc3MiOiJFcWltYVNlcnZpY2UifQ.QaVwNnJbKbasw0RuXX6EH8J2TFM1GoXDN_TzRxLfiOuHvRX_1wf32KvnxePPbsFJ");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.connect();
        int resCode = conn.getResponseCode();
        if (resCode == 200) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("JSON========================" + response);
                String res = response.toString();
                JSONParser parser = new JSONParser();
                JSONObject resultat = (JSONObject) parser.parse(res);
                data = (String) resultat.get("resultat");
                System.out.println("score===================" + data);
            } catch (ParseException ex) {
                Logger.getLogger(uploadfile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("erreur======================" + resCode);
        }
        return data;
    }

}
