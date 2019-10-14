package com.example.games.mineracao;

import android.util.Log;

import com.example.games.http.RequisicaoConcorrenteGet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MineracaoGameSpot {
    private Noticia noticia;
    private RequisicaoConcorrenteGet get;

    public ArrayList<Noticia> buscar()throws IOException {
        //categorias moudada no em opções
        Document doc = Jsoup.connect("https://br.ign.com/").get();

        Elements newsHeadlines = doc.getElementsByTag("article");

        ArrayList<Noticia> noticias = new ArrayList<>();

        int tam = 0;
        for (Element headline : newsHeadlines) {

            if(tam > 4) {
                //--------------- titulo
                Elements tituloNode = headline.getElementsByTag("h3");
                String titulo = tituloNode.text();

                Log.i("titulo", titulo);

                //---------------previa
                Elements previaNode = headline.getElementsByTag("p");
                String previa = previaNode.text();

                //--------------- link imagem
                Elements img = headline.getElementsByTag("img");
                String linkImg = img.attr("src");

                Elements linkNode = headline.getElementsByTag("a");
                String link = linkNode.attr("href");

                noticia = new Noticia(linkImg, titulo, previa, link);
                noticias.add(noticia);
            }else{
                tam = tam + 1;
            }
        }

    return  noticias;
    }
}
