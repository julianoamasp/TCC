package com.example.games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = (WebView) findViewById(R.id.webviewActivity_webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setAppCacheEnabled(false);


        Intent intent = getIntent();
        String pagina = "<html><body>"+intent.getStringExtra("link")+"</body></html>";

        webView.loadData(pagina,"text/html","UTF-8");
        //webView.loadUrl(intent.getStringExtra("link"));
    }
}
