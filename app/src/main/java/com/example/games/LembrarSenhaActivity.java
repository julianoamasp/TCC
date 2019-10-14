package com.example.games;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.games.http.Requisicoes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LembrarSenhaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembrar_senha);

        final EditText edtEmail = (EditText) findViewById(R.id.editText_lembrarsenha_email);

        Button btnLembrar = (Button) findViewById(R.id.button_lembrasenha_enviaremail);
        btnLembrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();

                //converter os edit text para json
                JSONObject json = new JSONObject();
                try {
                    json.put("email", email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MyTask myTask = new MyTask();
                myTask.execute(json.toString());



                try{
                    String texto = myTask.get();

                    JSONObject jsonObject = new JSONObject(texto);
                    String codigo = jsonObject.getString("codigo");

                    Log.i("erro---------", "---------codigo----------"+texto);

                    if(codigo.equals("200")){

                        int duracao = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(LembrarSenhaActivity.this, "Email enviado!", duracao);
                        toast.show();

                        String codigoSessao = jsonObject.getString("email");

                        Intent mudarTela1 = new Intent(LembrarSenhaActivity.this, MainActivity.class);
                        startActivity(mudarTela1);
                    }else{
                        int duracao = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(LembrarSenhaActivity.this, jsonObject.get("codigo").toString(), duracao);
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
                resposta = reqs.post("https://julianoportfolio.000webhostapp.com/enviarsenha.php",params[0]);
            } catch (IOException e) {
            }
            return resposta;
        }
    }
}
