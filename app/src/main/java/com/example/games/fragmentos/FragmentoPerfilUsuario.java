package com.example.games.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.games.HomeActivity;
import com.example.games.MainActivity;
import com.example.games.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class FragmentoPerfilUsuario extends Fragment  {
    private String nome;
    private String email;
    private String apelido;
    SharedPreferences sharedPreferences;
    FragmentoAtualizarDados fad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_perfil_usuario, container, false);

        TextView txtNome = (TextView) view.findViewById(R.id.textview_fragmentoperfil_nome);
        TextView txtApelido = (TextView) view.findViewById(R.id.textview_fragmentoperfil_apelido);
        TextView txtEmail = (TextView) view.findViewById(R.id.textview_fragmentoperfil_email);

        final Bundle bundle = getArguments();

        txtNome.setText(bundle.getString("nome"));
        txtEmail.setText(bundle.getString("email"));
        txtApelido.setText(bundle.getString("apelido"));

        String estado = bundle.getString("estado");

        if(estado.equals("3")){
            Button btnBlockEmail = (Button) view.findViewById(R.id.fragmentoPerfil_button_alterar_email);
            Button btnBlockSenha = (Button) view.findViewById(R.id.fragmentoPerfil_button_alterar_senha);
            Button btnBlockExcluir = (Button) view.findViewById(R.id.fragmentoPerfil_button_desativar_conta);

            btnBlockEmail.setVisibility(View.GONE);
            btnBlockSenha.setVisibility(View.GONE);
            btnBlockExcluir.setVisibility(View.GONE);
        }

        Button btnSair = (Button) view.findViewById(R.id.button_fragmentoperfilusuario_sair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("usuario", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);

            }
        });
//------------------------------------------------botões

        //botão alterar email
        Button btnAlterarEmail = (Button) view.findViewById(R.id.fragmentoPerfil_button_alterar_email);
        btnAlterarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("acao","alteraremail");

                fad = new FragmentoAtualizarDados();

                fad.setArguments(bundle);

                FragmentManager fgm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fgm.beginTransaction();

                ft.replace(R.id.fragmentoPerfil_scroll_atualizar_dados_container, fad);

                ft.commit();
            }
        });

        //alterar apelido
        Button btnAlterarApelido = (Button) view.findViewById(R.id.fragmentoPerfil_button_alterar_apelido);
        btnAlterarApelido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("acao","alterarapelido");

                fad = new FragmentoAtualizarDados();

                fad.setArguments(bundle);

                FragmentManager fgm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fgm.beginTransaction();

                ft.replace(R.id.fragmentoPerfil_scroll_atualizar_dados_container, fad);

                ft.commit();
            }
        });

        //alterar senha
        Button btnAlterarSenha = (Button) view.findViewById(R.id.fragmentoPerfil_button_alterar_senha);
        btnAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("acao","alterarsenha");

                fad = new FragmentoAtualizarDados();

                fad.setArguments(bundle);

                FragmentManager fgm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fgm.beginTransaction();

                ft.replace(R.id.fragmentoPerfil_scroll_atualizar_dados_container, fad);

                ft.commit();
            }
        });

        //botão desativar conta
        Button vtnDesativarConta = (Button) view.findViewById(R.id.fragmentoPerfil_button_desativar_conta);
        vtnDesativarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("acao","desativarconta");

                fad = new FragmentoAtualizarDados();

                fad.setArguments(bundle);

                FragmentManager fgm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fgm.beginTransaction();

                ft.replace(R.id.fragmentoPerfil_scroll_atualizar_dados_container, fad);

                ft.commit();
            }
        });
        return view;
    }
}
