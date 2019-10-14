package com.example.games;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.games.http.Requisicoes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivityBloqueado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bloqueado);

        TextView textViewEmail = (TextView) findViewById(R.id.textView_main_bloqueado);

        textViewEmail.setText(getIntent().getStringExtra("texto"));

        Button button = (Button) findViewById(R.id.button_main_bloqueado);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getIntent().getStringExtra("texto");
                String acao = getIntent().getStringExtra("tipo");

                JSONObject jsonSaida = new JSONObject();

                try {
                    jsonSaida.put("acao", acao);
                    jsonSaida.put("email", email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MyTask myTask = new MyTask();
                myTask.execute(jsonSaida.toString());

                try{
                    String retorno = myTask.get();

                    Log.i("erro---------", "---emaial: "+email+" acao: "+acao+"---------codigo----------"+retorno+"----");

                    JSONObject jsonObject = new JSONObject(retorno);

                    String codigo = jsonObject.getString("codigo");

                    if(codigo.equals("200")){


                        int duracao = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(MainActivityBloqueado.this, jsonObject.get("msg").toString(), duracao);
                        toast.show();

                        Intent mudarTela1 = new Intent(MainActivityBloqueado.this, MainActivity.class);

                        startActivity(mudarTela1);

                    }else{
                        int duracao = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(MainActivityBloqueado.this, jsonObject.get("msg").toString(), duracao);
                        toast.show();
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }







    //atividade asinncrona
    class MyTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            Requisicoes reqs = new Requisicoes();

            String resposta = null;
            try {

                resposta = reqs.post("https://julianoportfolio.000webhostapp.com/ReenviarEmailDeConfirmacaoDeCadastro.php",params[0]);
            } catch (IOException e) {
            }
            return resposta;
        }
    }
}
