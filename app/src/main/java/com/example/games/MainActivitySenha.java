package com.example.games;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.games.http.RequisicaoConcorrentePost;
import com.example.games.model.RequisicaoModelo;
import com.example.games.model.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivitySenha extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    RequisicaoModelo requisicaoModelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_senha);

        TextView nomearEmailOuApelido = (TextView) findViewById(R.id.editText_main2_email_apelido);
        final EditText campoEmail = (EditText) findViewById(R.id.editText_main2_email);
        final EditText campoSenha = (EditText) findViewById(R.id.editText_main2_senha);
        Button btnEntrar = (Button) findViewById(R.id.button_main2_entrar);

        final Intent intencao = getIntent();

        //pegando o tipo e o email ou apelido da tela anterior
        String intencaoTipo = intencao.getStringExtra("tipo");
        String intencaoTexto = intencao.getStringExtra("texto");

        //discriminando na tela para o usuario se foi usado email ou apelido
        nomearEmailOuApelido.setText(intencaoTipo);
        campoEmail.setText(intencaoTexto);

        //botão entrar
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //criando um objeto json para enviar no corpo da requisição post
                JSONObject jsonSaida = new JSONObject();

                try {
                    //definindo alguns atributos na requisição
                    jsonSaida.put("acao", intencao.getStringExtra("tipo"));
                    jsonSaida.put("email", campoEmail.getText().toString());
                    jsonSaida.put("senha", campoSenha.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //criando objeto asyncrono
                RequisicaoConcorrentePost requisicaoConcorrentePost = new RequisicaoConcorrentePost("https://julianoportfolio.000webhostapp.com/EntrarComEmailOuApelido.php");

                //incluindo o json de saida e executando seu método de execução contido no doInBackground
                requisicaoConcorrentePost.execute(jsonSaida.toString());

                try{
                    //tentando pegar o retorno do execute e convertendo para texto
                    String retorno = requisicaoConcorrentePost.get();


                    //convertendo o texto resposta para um objeto json
                    JSONObject jsonObject = new JSONObject(retorno);

                    //acessando o parametro codigo da resposta json para validações
                    String codigo = jsonObject.getString("codigo");

                    //validando se foi bem sucedido a comparação de email ou apelido e senha
                    if(codigo.equals("200")){

                        //criei a sesap usuario
                        sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);

                        //criando usuario a partir dos dados json recebido
                        Usuario usuario = new Usuario(jsonObject);

                        //retornando um shared editor de secao com os dados configurado
                        SharedPreferences.Editor editor = usuario.atualizarSecaoDoUsuario(sharedPreferences);

                        //submetendo a seção para a memoria
                        editor.commit();

                        //configurando uma intenção da tela atua para a tela principal
                        Intent mudarTela1 = new Intent(MainActivitySenha.this, HomeActivity.class);

                        //usando metodo estatico para executar troca de telas
                        startActivity(mudarTela1);

                    }else{
                        //caso não seja bem sucedido
                        int duracao = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(MainActivitySenha.this, jsonObject.get("msg").toString(), duracao);
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

    @Override
    public void onStart() {
        super.onStart();

        final SharedPreferences sharedpreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        if(sharedpreferences.contains("nome")){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }
}
