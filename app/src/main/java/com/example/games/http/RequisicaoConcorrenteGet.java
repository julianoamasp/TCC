package com.example.games.http;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class RequisicaoConcorrenteGet extends AsyncTask<String, Void, String>  {

    //recebimento de atributos
    private Requisicoes requisicoes;
    private String link;

    //objetos para serem alterados
    private TextView textView;

    public RequisicaoConcorrenteGet(String link){
        this.link = link;
    }

    //execução
    @Override
    protected String doInBackground(String... params) {
        requisicoes = new Requisicoes();
        String resposta="";

        try {
            resposta = requisicoes.get(link);
        } catch (IOException e) {
            e.printStackTrace();

            //metodo vinculado ao onPostExecute
            publishProgress();
        }
        return resposta;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
