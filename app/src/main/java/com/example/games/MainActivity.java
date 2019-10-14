package com.example.games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.games.http.Requisicoes;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        //botao para cadastrar novos usuarios com email e senha
        Button google = findViewById(R.id.button_google_main);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });//fim



        //botão do xml
        Button btnLembrarsenha = (Button) findViewById(R.id.button_main_lembrarsenha);
        btnLembrarsenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mudarTela3 = new Intent(MainActivity.this, LembrarSenhaActivity.class);
                startActivity(mudarTela3);
            }
        });

        //botao logar
        Button btn = (Button) findViewById(R.id.button_main_entrar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressBar barra = (ProgressBar) findViewById(R.id.progressBar_main);
                barra.setVisibility(View.VISIBLE);

                //campo de email
                EditText edtEmail = (EditText) findViewById(R.id.editText_main_email);

                //pega o texto do campo
                String emailStr = edtEmail.getText().toString();

                //converter os edit text para json
                JSONObject json = new JSONObject();
                try {
                    json.put("texto", emailStr);
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

                        //estatus
                        String estado = jsonObject.getString("estado");

                        //estado de conta cadastrada mas a espera de confirmação do email
                        if(estado.equals("0")){

                            String em = jsonObject.getString("email");
                            String ap = jsonObject.getString("apelido");

                            Intent mudarTela1 = new Intent(MainActivity.this, MainActivityBloqueado.class);

                            if(emailStr.equals(em)){
                                mudarTela1.putExtra("tipo", "email");
                                mudarTela1.putExtra("texto", em);
                            }if(emailStr.equals(ap)){
                                mudarTela1.putExtra("tipo", "apelido");
                                mudarTela1.putExtra("texto", ap);
                            }

                            startActivity(mudarTela1);

                            //conta ativa
                        }else if(estado.equals("1")){

                            String em = jsonObject.getString("email");
                            String ap = jsonObject.getString("apelido");

                            Intent mudarTela1 = new Intent(MainActivity.this, MainActivitySenha.class);

                            if(emailStr.equals(em)){
                                mudarTela1.putExtra("tipo", "email");
                                mudarTela1.putExtra("texto", em);
                            }if(emailStr.equals(ap)){
                                mudarTela1.putExtra("tipo", "apelido");
                                mudarTela1.putExtra("texto", ap);
                            }
                            int duracao = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(MainActivity.this, "Insira a senha para entrar", duracao);
                            toast.show();

                            startActivity(mudarTela1);

                        }else if(estado.equals("2")){

                        }else if(estado.equals("3")){

                            int duracao = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(MainActivity.this, "Este usuario se conecta com a conta do google!", duracao);
                            toast.show();

                        }else{

                        }
                    }else{
                        int duracao = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(MainActivity.this, jsonObject.get("msg").toString(), duracao);
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
        });//----------------------------------------------fim botao logar

        //-------------------------------------------------botao cadastrar
        Button btnCadastrar = (Button) findViewById(R.id.button_main_cadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mudarTela2 = new Intent(MainActivity.this, CadastrarActivity.class);
                startActivity(mudarTela2);
            }
        });//-------------------------------------------------fim botao cadastrar







    }




//ocultar tudo







    //atividade asinncrona
    class MyTask extends AsyncTask<String, Void, String> {
        

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ProgressBar barra = (ProgressBar) findViewById(R.id.progressBar_main);
            barra.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            ProgressBar barra = (ProgressBar) findViewById(R.id.progressBar_main);
            barra.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            Requisicoes reqs = new Requisicoes();

            String resposta = null;
            try {

                resposta = reqs.post("https://julianoportfolio.000webhostapp.com/VerificarStatusComEmailOuApelido.php",params[0]);
            } catch (IOException e) {
            }
            return resposta;
        }
    }
    //atividade asinncrona
    class MyTask2 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            Requisicoes reqs = new Requisicoes();

            String resposta = null;
            try {

                resposta = reqs.post("https://julianoportfolio.000webhostapp.com/CadastrarEntrarComContaGoogle.php",params[0]);
            } catch (IOException e) {
            }
            return resposta;
        }
    }








//---------------------------------------google

    @Override
    public void onStart() {
        super.onStart();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        final SharedPreferences sharedpreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);

        //verifica instancia logada
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }if(sharedpreferences.contains("nome")){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

















//-------------------------------------------------
    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient mGoogleSignInClient;
    private TextView mStatusTextView;
    private TextView mDetailTextView;
//------------------------    1
    private void signIn() {
        ProgressBar barra = (ProgressBar) findViewById(R.id.progressBar_main);
        barra.setVisibility(View.VISIBLE);
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
//------------------------ retorno da tela
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                String res = e.getMessage();
                String res2 = e.getLocalizedMessage();

                Log.w(TAG, "================================Google sign in failed primeira parte=================================\n"
                        +res +"===============\n"+res2+"=====================\n");
                // ...
            }
        }
    }
//----------------------    2
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();

                            //converter os edit text para json
                            JSONObject json = new JSONObject();
                            try {
                                json.put("texto", user.getEmail());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            MyTask myTask = new MyTask();
                            myTask.execute(json.toString());

                            try{
                                String texto = myTask.get();

                                JSONObject jsonObject = new JSONObject(texto);
                                String codigo = jsonObject.getString("codigo");

                                if(codigo.equals("200")){

                                    AuthUI.getInstance()
                                            .signOut(MainActivity.this)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                                                    finish();
                                                }});

                                    //estatus
                                    String estado = jsonObject.getString("estado");

                                    //estado de conta cadastrada mas a espera de confirmação do email
                                    if(estado.equals("3")){
                                        String codigoSessao = jsonObject.getString("codigo");
                                        int idSessao = jsonObject.getInt("id");
                                        String nomeSessao = jsonObject.getString("nome");
                                        String apelidoSessao = jsonObject.getString("apelido");
                                        String emailSessao = jsonObject.getString("email");
                                        String senhaSessao = jsonObject.getString("senha");
                                        String estadoSessao = jsonObject.getString("estado");
                                        String preferenciasSessao = jsonObject.getString("preferencias");

                                        //criei a sesap usuario
                                        sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);

                                        //criei o objeto de alterar a seao usuario
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        editor.putString("codigo", codigoSessao);
                                        editor.putInt("id", idSessao);
                                        editor.putString("nome", nomeSessao);
                                        editor.putString("apelido", apelidoSessao);
                                        editor.putString("email", emailSessao);
                                        editor.putString("senha", senhaSessao);
                                        editor.putString("estado", estadoSessao);
                                        editor.putString("preferencias", preferenciasSessao);

                                        editor.commit();

                                        Intent mudartela = new Intent(MainActivity.this, HomeActivity.class);
                                        startActivity(mudartela);
                                    }else{


                                        AuthUI.getInstance()
                                                .signOut(MainActivity.this)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                                                        finish();
                                                    }});

                                        int duracao = Toast.LENGTH_LONG;
                                        Toast toast = Toast.makeText(MainActivity.this, "Usuario cadastrado com Email e Senha!", duracao);
                                        toast.show();

                                    }
                                }if (codigo.equals("500")){


                                    //converter os edit text para json
                                    JSONObject json2 = new JSONObject();

                                    try {
                                        json2.put("nome", user.getDisplayName());
                                        json2.put("apelido", user.getDisplayName());
                                        json2.put("email", user.getEmail());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    MyTask2 myTask2 = new MyTask2();
                                    myTask2.execute(json2.toString());

                                    try{
                                        String texto2 = myTask2.get();

                                        JSONObject jsonObject2 = new JSONObject(texto2);
                                        String codigo2 = jsonObject2.getString("codigo");

                                        if(codigo2.equals("200")){

                                            //criei a sesap usuario
                                            sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);

                                            //criei o objeto de alterar a seao usuario
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            
                                            editor.putString("nome", user.getDisplayName());
                                            editor.putString("apelido", user.getDisplayName());
                                            editor.putString("email", user.getEmail());
                                            editor.putString("senha", "000000");
                                            editor.putString("estado", "3");
                                            editor.putString("preferencias", "0,0,0,0");

                                            editor.commit();


                                            AuthUI.getInstance()
                                                    .signOut(MainActivity.this)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                                                            finish();
                                                        }});

                                            Intent mudartela3 = new Intent(MainActivity.this, HomeActivity.class);
                                            startActivity(mudartela3);

                                        }
                                } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "========================signInWithCredential:failure segunda parte===========================",
                                    task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }
    // [END auth_with_google]

}
