package com.example.games.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.games.R;
import com.example.games.mineracao.Noticia;
import com.squareup.picasso.Picasso;

public class FragmentoNoticiaCard extends Fragment {

    private InterfaceCompartilharDadosComHome interfaceEscuta;

    private ImageView ImgViewimagem;
    private TextView txtViewTitulo;
    private TextView txtViewPrevia;

    private String titulo;
    private String previa;
    private String imagerm;
    private String link;

    public FragmentoNoticiaCard(Noticia noticia){
        titulo = noticia.getTitulo();
        previa = noticia.getConteudo();
        imagerm = noticia.getLinkimg();
        link = noticia.getLink();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_noticia_card, container, false);

        txtViewTitulo = (TextView) view.findViewById(R.id.fragmento_card_noticia_titulo);
        txtViewTitulo.setText(titulo);

        txtViewPrevia = (TextView) view.findViewById(R.id.fragmento_card_noticia_previa) ;
        txtViewPrevia.setText(previa);

        ImgViewimagem = (ImageView) view.findViewById(R.id.fragmento_noticia_card_imagem);
        Picasso.get().load(imagerm).into(ImgViewimagem);

        LinearLayout ab = (LinearLayout) view.findViewById(R.id.fragmentoCard_linearLayout);
        ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicar(link);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InterfaceCompartilharDadosComHome) {
            interfaceEscuta = (InterfaceCompartilharDadosComHome) context;
        } else {
            throw new ClassCastException();
        }
    }

    public interface InterfaceCompartilharDadosComHome{
        public void noticiaCardClick(String a);
    }

    public void clicar(String a){
        interfaceEscuta.noticiaCardClick(link);
    }
}
