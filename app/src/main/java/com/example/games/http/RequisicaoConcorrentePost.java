package com.example.games.http;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

// doInBackGround = parametros, onProgressUpdate = progresso, onPostExecute = resultado
public class RequisicaoConcorrentePost extends AsyncTask<String, Void, String> {

    //recebimento de atributos
    private Requisicoes requisicoes;
    private String link;

    //objetos para serem alterados
    private TextView textView;

    public RequisicaoConcorrentePost(String link){
        this.link = link;
    }

    //antes de começar
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //execução
    @Override
    protected String doInBackground(String... params) {
        requisicoes = new Requisicoes();
        String resposta="";

        try {
            resposta = requisicoes.post(link, params[0]);
        } catch (IOException e) {
            e.printStackTrace();

            //metodo vinculado ao onPostExecute
            publishProgress();
        }
        return resposta;
    }

    //quando terminar
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
