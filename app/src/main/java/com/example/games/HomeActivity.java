package com.example.games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.games.fragmentos.FragmentoAtualizarDados;
import com.example.games.fragmentos.FragmentoNoticiaCard;
import com.example.games.fragmentos.FragmentoNoticias;
import com.example.games.fragmentos.FragmentoPerfilUsuario;
import com.example.games.mineracao.HtmlGeneratorIGN;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity implements FragmentoAtualizarDados.BotaoSairActionCompartilhada,
        FragmentoNoticiaCard.InterfaceCompartilharDadosComHome {

    private TextView lbNome, lbEmail;
    private ImageView imageView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        SharedPreferences sharedpreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);

        if(sharedpreferences.contains("nome")) {

            FragmentManager fgm = getSupportFragmentManager();
            FragmentTransaction ft = fgm.beginTransaction();

            FragmentoNoticias fpn = new FragmentoNoticias();

            ft.replace(R.id.scrollview_home_container, fpn);
            ft.commit();

        }else{

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }


        Button btnHome = (Button) findViewById(R.id.button_home_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fgm = getSupportFragmentManager();
                FragmentTransaction ft = fgm.beginTransaction();

                FragmentoNoticias fpn = new FragmentoNoticias();

                ft.replace(R.id.scrollview_home_container, fpn);
                ft.commit();
            }
        });

        Button btn = (Button) findViewById(R.id.button_home_perfil);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fgm = getSupportFragmentManager();
                FragmentTransaction ft = fgm.beginTransaction();

                Bundle bundle = new Bundle();

                SharedPreferences sharedpreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);

                bundle.putString("nome", sharedpreferences.getString("nome", "erro"));
                bundle.putString("email", sharedpreferences.getString("email", "erro"));
                bundle.putString("apelido", sharedpreferences.getString("apelido", "erro"));
                bundle.putString("estado", sharedpreferences.getString("estado", "erro"));
                bundle.putString("senha", sharedpreferences.getString("senha", "erro"));

                FragmentoPerfilUsuario fpu = new FragmentoPerfilUsuario();

                fpu.setArguments(bundle);

                ft.replace(R.id.scrollview_home_container, fpu);
                ft.commit();
            }
        });
    }

    @Override
    public void aoClicar(String a) {
        FragmentoAtualizarDados fad = new FragmentoAtualizarDados();

        Bundle acao = new Bundle();
        acao.putString("acao", "atualizarnome");

        FragmentManager fgm = getSupportFragmentManager();
        FragmentTransaction ft = fgm.beginTransaction();

        ft.replace(R.id.fragmentoPerfil_scroll_atualizar_dados_container, fad);
        ft.remove(fad);

        ft.commit();
    }

    @Override
    public void noticiaCardClick(String a) {


        MyTask myTask = new MyTask();
        myTask.execute(a);

    }







    class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HtmlGeneratorIGN htmlG = new HtmlGeneratorIGN();
            String texto="";
            try {
                texto = htmlG.selecionar(params[0]);
                Intent mudarTela3 = new Intent(HomeActivity.this, WebViewActivity.class);
                mudarTela3.putExtra("link", texto);
                startActivity(mudarTela3);
            } catch (IOException e) {

            }


            return texto;
        }

    }
}
