package com.bharathmg.irctc.utils;

/**
 * Created by bharathmg on 12/01/14.
 */

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public enum CommunicationManager {
    INSTANCE;

    final JSONObject requireLogin;
    final JSONObject accessDenied;

    private CommunicationManager() {
        requireLogin = new JSONObject();
        accessDenied = new JSONObject();
        try {
            requireLogin.put("require_login", true);
            accessDenied.put("access_denied", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public StringBuilder postRequest(String requestPath, String params) throws IOException {

        HttpURLConnection con = null;
        try {
            con = openConnection(requestPath, "POST");
            if (params != null) {
                PrintWriter out = new PrintWriter(con.getOutputStream());
                out.print(params);
                out.close();
            }
            long startTime = System.currentTimeMillis();
            con.connect();
            InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                    sb.append(line);

            }

            return sb;
        } finally {
            closeConnection(con);
        }
    }

    public StringBuilder putRequest(String requestPath, String params) throws IOException {

        HttpURLConnection con = null;
        try {
            con = openConnection(requestPath, "PUT");
            if (params != null) {
                PrintWriter out = new PrintWriter(con.getOutputStream());
                out.print(params);
                out.close();
            }
            long startTime = System.currentTimeMillis();
            con.connect();
            InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                    sb.append(line);

            }

            return sb;
        } finally {
            closeConnection(con);
        }
    }

    public StringBuilder getRequest(String requestPath) throws IOException {
        HttpURLConnection con = null;
        try {
            con = openConnection(requestPath, "GET");
            long startTime = System.currentTimeMillis();
            InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                    sb.append(line);
            }
            return sb;
        } finally {
            closeConnection(con);
        }

    }

    public StringBuilder deleteRequest(String requestPath) throws IOException {

        HttpURLConnection con = null;
        try {
            con = openConnection(requestPath, "DELETE");

            long startTime = System.currentTimeMillis();
            InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {

                    sb.append(line);

            }
            return sb;
        } finally {
            closeConnection(con);
        }
    }

    private void closeConnection(HttpURLConnection con) {
        if (con != null) {
            con.disconnect();
        }
    }

    private HttpURLConnection openConnection(String requestPath, String method) throws IOException {

        URL url;

            url = new URL(host + requestPath);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setDoOutput(method.equals("POST") || method.equals("PUT"));
        con.setRequestProperty("Accept", "application/json");
        if (encodedAuthToken != null) {
            // Login request alone doesn't go this way.
            con.setRequestProperty("Authorization", encodedAuthToken);
        }
        return con;
    }

    private String host = "";
    private String encodedAuthToken;

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return this.host;
    }


}
