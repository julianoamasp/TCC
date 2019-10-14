package com.example.games.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Requisicoes {
    public String post(String requisicao, String json)throws IOException {

        URL url = new URL(requisicao);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");

        connection.setRequestProperty("Content-type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");

        connection.setDoOutput(true);

        PrintStream printStream = new PrintStream(connection.getOutputStream()); printStream.println(json);
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        if (inputStream == null) {
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String linha;
        StringBuffer buffer = new StringBuffer();
        while ((linha = reader.readLine()) != null) {
            buffer.append(linha);
        }
        connection.disconnect();

        return buffer.toString();
    }

    public String get(String link) throws IOException {

        URL url = new URL(link);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        connection.setRequestProperty("Accept", "application/json");

        connection.connect();

        String jsonDeResposta = new Scanner(connection.getInputStream()).next();

        return jsonDeResposta;
    }
}

