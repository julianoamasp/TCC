package com.example.games.fragmentos;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.games.R;
import com.example.games.http.RequisicaoConcorrenteGet;
import com.example.games.mineracao.MineracaoGameSpot;
import com.example.games.mineracao.Noticia;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class FragmentoNoticias extends Fragment {

    String titulo;
    String previa;
    String imagem;
    String link;

    private ProgressBar progressBar;

    View tela;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tela = inflater.inflate(R.layout.fragmento_noticias, container, false);

        progressBar = (ProgressBar) tela.findViewById(R.id.fragmento_noticias_progressbar);

        MyTask myTask = new MyTask();
        myTask.execute();

        return tela;
    }









    class MyTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... params) {
            MineracaoGameSpot gspot = new MineracaoGameSpot();
            Noticia noticia = new Noticia();
            ArrayList<Noticia> noticias = new ArrayList<>();
            try {
               noticias = gspot.buscar();

                for(Noticia not : noticias) {
                    FragmentManager fgm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fgm.beginTransaction();

                    ft.add(R.id.scroll_fragmentos_card, new FragmentoNoticiaCard(not)).commit();
                }

            } catch (IOException e) {

            }


            return "Task Completed.";
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);

        }

    }
}
