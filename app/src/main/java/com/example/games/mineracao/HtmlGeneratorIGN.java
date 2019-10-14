package com.example.games.mineracao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HtmlGeneratorIGN {
    public String selecionar(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();

        Elements newsHeadline = doc.getElementsByTag("article");
        Elements newsHeadline2 = newsHeadline.select("#id_text");
        return "<html><body>"+newsHeadline2+"</body></html>";
    }
}
