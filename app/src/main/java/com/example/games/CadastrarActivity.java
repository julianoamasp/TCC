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

public class CadastrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        final EditText edtNome = (EditText) findViewById(R.id.editText_cadastro_nome);
        final EditText edtApelido = (EditText) findViewById(R.id.editText_cadastro_apelido);
        final EditText edtEmail = (EditText) findViewById(R.id.editText_cadastro_email);
        final EditText edtSenha = (EditText) findViewById(R.id.editText_cadastro_senha);

        Button btnCadastrar  = (Button)findViewById(R.id.button_cadastro_cadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = edtNome.getText().toString();
                String apelido = edtApelido.getText().toString();
                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();

                //criar o json para enviar na requisicao
                JSONObject json = new JSONObject();
                try {
                    json.put("nome", nome);
                    json.put("apelido", apelido);
                    json.put("email", email);
                    json.put("senha", senha);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //cria thread
                MyTask myTask = new MyTask();
                //executa a trhead e o metodo post
                myTask.execute(json.toString());

                try{
                    String texto = myTask.get();

                    JSONObject jsonObject = new JSONObject(texto);
                    String codigo = jsonObject.getString("codigo");
                    String msg = jsonObject.getString("msg");

                    Log.i("erro---------", "---------codigo----------"+texto);

                    if(codigo.equals("200")){

                        int duracao = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(CadastrarActivity.this, "Cadastrado com sucesso!", duracao);
                        toast.show();

                        Intent mudarTela = new Intent(CadastrarActivity.this, MainActivity.class);
                        startActivity(mudarTela);
                    }else{
                        int duracao = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(CadastrarActivity.this, msg, duracao);
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
                resposta = reqs.post("https://julianoportfolio.000webhostapp.com/cadastro.php",params[0]);
            } catch (IOException e) {
            }
            return resposta;
        }
    }
}
