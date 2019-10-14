package com.example.games.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.games.MainActivity;
import com.example.games.R;
import com.example.games.http.RequisicaoConcorrentePost;
import com.example.games.model.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class FragmentoAtualizarDados extends Fragment {

    private BotaoSairActionCompartilhada interfaceEscuta;
    SharedPreferences sharedpreferences;
    EditText editApelido,editEmail,edtNovaSenha,edtConfirmarNovaSenha, edtSenhaAtual;
    TextView erroMensagem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view  =inflater.inflate(R.layout.fragmento_atualizar_perfil, container, false);

        //--------------------------grupo alterar apelido
        TextView textViewApelido = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_apelido);
        editApelido = (EditText) view.findViewById(R.id.fragmento_atualizar_perfil_edittext_apelido);
        Button btnAlterarApelido = (Button) view.findViewById(R.id.fragmento_atualizar_perfil_button_atualizar_apelido);

        //----------------------- grupo alterar email
        TextView textViewEmail = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_email) ;
        editEmail = (EditText) view.findViewById(R.id.fragmento_atualizar_perfil_edittext_novoemail);
        Button btnAlterarEmail = (Button) view.findViewById(R.id.fragmento_atualizar_perfil_button_atualizar_email);

        //-------------------grupo atualizar senha
        TextView textViewNovaSenha = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_novasenha);
        edtNovaSenha = (EditText) view.findViewById(R.id.fragmento_atualizar_perfil_edittext_novasenha);
        TextView textViewConfirmarNovaSenha = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_confirmarnovasenha) ;
        edtConfirmarNovaSenha = (EditText) view.findViewById(R.id.fragmento_atualizar_perfil_edittext_confirmarnovasenha);
        TextView textViewSenhaAtual = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_confirmarsenha);
        edtSenhaAtual = (EditText) view.findViewById(R.id.fragmento_atualizar_perfil_edittext_senhaatual);
        Button btnAtualizarSenha = (Button) view.findViewById(R.id.fragmento_atualizar_perfil_button_atualizar_senha);

        //-----------grupo excluir conta
        TextView textViewExcluirConta = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_excluirconta);
        EditText edtTextExcluirconta = (EditText) view.findViewById(R.id.fragmento_atualizar_perfil_edittext_senhaecluirconta);
        Button btnExcluirConta = (Button) view.findViewById(R.id.fragmento_atualizar_perfil_button_atualizar_excluirconta);

        //vamos pegar o tipo de pagina
        Bundle bundle = getArguments();
        String acao = bundle.getString("acao");
        bundle.clear();

        if(acao.equals("alteraremail")){
            //----------------grupo atualizar apelido
            textViewApelido.setVisibility(View.GONE);
            editApelido.setVisibility(View.GONE);
            btnAlterarApelido.setVisibility(View.GONE);
            //-------------------grupo atualizar senha
            textViewNovaSenha.setVisibility(View.GONE);
            edtNovaSenha.setVisibility(View.GONE);
            textViewConfirmarNovaSenha.setVisibility(View.GONE);
            edtConfirmarNovaSenha.setVisibility(View.GONE);
            textViewSenhaAtual.setVisibility(View.GONE);
            edtSenhaAtual.setVisibility(View.GONE);
            btnAtualizarSenha.setVisibility(View.GONE);
            //-----------grupo excluir conta
            textViewExcluirConta.setVisibility(View.GONE);
            edtTextExcluirconta.setVisibility(View.GONE);
            btnExcluirConta.setVisibility(View.GONE);
        }else if(acao.equals("alterarapelido")){
            //----------------------- grupo alterar email
            textViewEmail.setVisibility(View.GONE);
            editEmail.setVisibility(View.GONE);
            btnAlterarEmail.setVisibility(View.GONE);
            //-------------------grupo atualizar senha
            textViewNovaSenha.setVisibility(View.GONE);
            edtNovaSenha.setVisibility(View.GONE);
            textViewConfirmarNovaSenha.setVisibility(View.GONE);
            edtConfirmarNovaSenha.setVisibility(View.GONE);
            textViewSenhaAtual.setVisibility(View.GONE);
            edtSenhaAtual.setVisibility(View.GONE);
            btnAtualizarSenha.setVisibility(View.GONE);
            //-----------grupo excluir conta
            textViewExcluirConta.setVisibility(View.GONE);
            edtTextExcluirconta.setVisibility(View.GONE);
            btnExcluirConta.setVisibility(View.GONE);
        }else if(acao.equals("alterarsenha")){
            //----------------------- grupo alterar email
            textViewEmail.setVisibility(View.GONE);
            editEmail.setVisibility(View.GONE);
            btnAlterarEmail.setVisibility(View.GONE);
            //----------------grupo atualizar apelido
            textViewApelido.setVisibility(View.GONE);
            editApelido.setVisibility(View.GONE);
            btnAlterarApelido.setVisibility(View.GONE);
            //-----------grupo excluir conta
            textViewExcluirConta.setVisibility(View.GONE);
            edtTextExcluirconta.setVisibility(View.GONE);
            btnExcluirConta.setVisibility(View.GONE);
        }else if(acao.equals("desativarconta")){
            //----------------------- grupo alterar email
            textViewEmail.setVisibility(View.GONE);
            editEmail.setVisibility(View.GONE);
            btnAlterarEmail.setVisibility(View.GONE);
            //----------------grupo atualizar apelido
            textViewApelido.setVisibility(View.GONE);
            editApelido.setVisibility(View.GONE);
            btnAlterarApelido.setVisibility(View.GONE);
            //-------------------grupo atualizar senha
            textViewNovaSenha.setVisibility(View.GONE);
            edtNovaSenha.setVisibility(View.GONE);
            textViewConfirmarNovaSenha.setVisibility(View.GONE);
            edtConfirmarNovaSenha.setVisibility(View.GONE);
            textViewSenhaAtual.setVisibility(View.GONE);
            edtSenhaAtual.setVisibility(View.GONE);
            btnAtualizarSenha.setVisibility(View.GONE);
        }



        //pegando preferencias da secao
        sharedpreferences = getActivity().getSharedPreferences("usuario", Context.MODE_PRIVATE);

        //criando um objeto usuario com construtor de seção
        Usuario usuario = new Usuario(sharedpreferences);




        //---------------------------------botões--------------------------------------------------




        //botão atualizar Apelido
        Button atualizarApelido = (Button) view.findViewById(R.id.fragmento_atualizar_perfil_button_atualizar_apelido);
        atualizarApelido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = getActivity().getSharedPreferences("usuario", Context.MODE_PRIVATE);
                Usuario usuario = new Usuario(sharedpreferences);
                editApelido = (EditText) view.findViewById(R.id.fragmento_atualizar_perfil_edittext_apelido);

                usuario.setApelido(editApelido.getText().toString());

                try {

                    RequisicaoConcorrentePost reqReq = new RequisicaoConcorrentePost("https://julianoportfolio.000webhostapp.com/AtualizarApelidoUsuario.php");

                    reqReq.execute(usuario.toJsonObject().toString());

                    //pegar retorno da requisição da atualização de cadastro
                    String retornoString = reqReq.get();

                    //transformar texto de retorno em objeto json
                    JSONObject jsonRetorno = new JSONObject(retornoString);

                    String codigo = jsonRetorno.getString("codigo");
                    String menssagem = jsonRetorno.getString("msg");

                    if(codigo.equals("200")) {
                        //retornando um shared editor de secao com os dados configurado
                        SharedPreferences.Editor editor = usuario.atualizarSecaoDoUsuario(sharedpreferences);

                        //submetendo a seção para a memoria
                        editor.commit();

                        erroMensagem = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_mensagemdeerro);
                        erroMensagem.setText("Atualizado com sucesso");

                    }else{
                        erroMensagem = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_mensagemdeerro);
                        erroMensagem.setText(menssagem);
                        Log.i("erro---------", "---------codigo----------"+usuario.toJsonObject().toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });




        //--------botao atualizar email
        Button btnAtualizarEmail = (Button) view.findViewById(R.id.fragmento_atualizar_perfil_button_atualizar_email);
        btnAtualizarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = getActivity().getSharedPreferences("usuario", Context.MODE_PRIVATE);
                Usuario usuario = new Usuario(sharedpreferences);
                editEmail = (EditText) view.findViewById(R.id.fragmento_atualizar_perfil_edittext_novoemail);

                usuario.setEmail(editEmail.getText().toString());

                try {

                    RequisicaoConcorrentePost reqReq = new RequisicaoConcorrentePost("https://julianoportfolio.000webhostapp.com/AtualizarEmailUsuario.php");

                    reqReq.execute(usuario.toJsonObject().toString());

                    //pegar retorno da requisição da atualização de cadastro
                    String retornoString = reqReq.get();

                    //transformar texto de retorno em objeto json
                    JSONObject jsonRetorno = new JSONObject(retornoString);

                    String codigo = jsonRetorno.getString("codigo");
                    String menssagem = jsonRetorno.getString("msg");

                    if(codigo.equals("200")) {
                        //retornando um shared editor de secao com os dados configurado
                        SharedPreferences.Editor editor = usuario.atualizarSecaoDoUsuario(sharedpreferences);

                        //submetendo a seção para a memoria
                        editor.commit();

                        erroMensagem = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_mensagemdeerro);
                        erroMensagem.setText("Atualizado com sucesso");

                    }else{
                        erroMensagem = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_mensagemdeerro);
                        erroMensagem.setText(menssagem);
                        Log.i("erro---------", "---------codigo----------"+usuario.toJsonObject().toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });



        //------botão atualizar senha
        Button btnatualizarSenha = (Button) view.findViewById(R.id.fragmento_atualizar_perfil_button_atualizar_senha);
        btnAtualizarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = getActivity().getSharedPreferences("usuario", Context.MODE_PRIVATE);
                Usuario usuario = new Usuario(sharedpreferences);

                edtNovaSenha = (EditText) view.findViewById(R.id.fragmento_atualizar_perfil_edittext_novasenha);
                edtConfirmarNovaSenha = (EditText) view.findViewById(R.id.fragmento_atualizar_perfil_edittext_confirmarnovasenha);
                edtSenhaAtual = (EditText) view.findViewById(R.id.fragmento_atualizar_perfil_edittext_senhaatual);

                edtSenhaAtual.setText(usuario.getSenha());

                String novaSenha = edtNovaSenha.getText().toString();
                String confirmarNovaSenha = edtConfirmarNovaSenha.getText().toString();
                String senhaAtual = edtSenhaAtual.getText().toString();

                if(novaSenha.length() < 8 || confirmarNovaSenha.length() < 8 || senhaAtual.length() < 8){
                    erroMensagem = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_mensagemdeerro);
                    erroMensagem.setText("Senha em branco ou menor que 8 caracteres");
                } else if (!novaSenha.equals(confirmarNovaSenha)){
                    erroMensagem = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_mensagemdeerro);
                    erroMensagem.setText("Nova senha diferente da confirmação");
                } else if (novaSenha.equals(confirmarNovaSenha) && !senhaAtual.equals(usuario.getSenha())){
                    erroMensagem = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_mensagemdeerro);
                    erroMensagem.setText("Senha atual incorreta");
                }else {

                    usuario.setSenha(novaSenha);

                    try {

                        RequisicaoConcorrentePost reqReq = new RequisicaoConcorrentePost("https://julianoportfolio.000webhostapp.com/atualizardados.php");

                        reqReq.execute(usuario.toJsonObject().toString());

                        //pegar retorno da requisição da atualização de cadastro
                        String retornoString = reqReq.get();

                        //transformar texto de retorno em objeto json
                        JSONObject jsonRetorno = new JSONObject(retornoString);

                        String codigo = jsonRetorno.getString("codigo");
                        String menssagem = jsonRetorno.getString("msg");

                        if (codigo.equals("200")) {
                            //retornando um shared editor de secao com os dados configurado
                            SharedPreferences.Editor editor = usuario.atualizarSecaoDoUsuario(sharedpreferences);

                            //submetendo a seção para a memoria
                            editor.commit();

                            erroMensagem = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_mensagemdeerro);
                            erroMensagem.setText("Atualizado com sucesso");

                        } else {
                            erroMensagem = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_mensagemdeerro);
                            erroMensagem.setText(menssagem);
                            Log.i("erro---------", "---------codigo----------" + usuario.toJsonObject().toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //------------------bota excluir conta
        btnExcluirConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = getActivity().getSharedPreferences("usuario", Context.MODE_PRIVATE);
                Usuario usuario = new Usuario(sharedpreferences);
                EditText editSenha = (EditText) view.findViewById(R.id.fragmento_atualizar_perfil_edittext_senhaecluirconta);
                String strSenha = editSenha.getText().toString();


                if(strSenha.equals(usuario.getSenha())){
                    try {

                        RequisicaoConcorrentePost reqReq = new RequisicaoConcorrentePost("https://julianoportfolio.000webhostapp.com/ExcluirUsuario.php");

                        reqReq.execute(usuario.toJsonObject().toString());

                        //pegar retorno da requisição da atualização de cadastro
                        String retornoString = reqReq.get();

                        //transformar texto de retorno em objeto json
                        JSONObject jsonRetorno = new JSONObject(retornoString);

                        String codigo = jsonRetorno.getString("codigo");
                        String menssagem = jsonRetorno.getString("msg");

                        if (codigo.equals("200")) {
                            //retornando um shared editor de secao com os dados configurado
                            SharedPreferences.Editor editor = usuario.atualizarSecaoDoUsuario(sharedpreferences);

                            //submetendo a seção para a memoria
                            editor.commit();

                            erroMensagem = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_mensagemdeerro);
                            erroMensagem.setText("excluido com sucesso");

                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("usuario", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editorFinal = sharedPreferences.edit();
                            editor.clear();
                            editor.commit();

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            getActivity().startActivity(intent);


                        } else {
                            erroMensagem = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_mensagemdeerro);
                            erroMensagem.setText("teste");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }else{
                    erroMensagem = (TextView) view.findViewById(R.id.fragmento_atualizar_perfil_textview_mensagemdeerro);
                    erroMensagem.setText("Erro tente novamente mais tarde");
                }
/*
                    */
            }
        });


        //listener botao cancelar alteração
        Button btnSair = (Button) view.findViewById(R.id.fragmento_atualizar_perfil_button_cancelar);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sair("s");
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BotaoSairActionCompartilhada) {
            interfaceEscuta = (BotaoSairActionCompartilhada) context;
        } else {
            throw new ClassCastException();
        }
    }

    public interface BotaoSairActionCompartilhada{
        public void aoClicar(String a);
    }

    //méto para sair
    public void sair(String a){

        interfaceEscuta.aoClicar(a);
    }

    //métodos de atualização
    public void atualizarNome(){

    }
}
