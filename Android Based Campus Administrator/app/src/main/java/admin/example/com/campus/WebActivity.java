package admin.example.com.campus;


import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import admin.example.com.nav.R;

public class WebActivity extends Activity {

    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.webactivity);

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.google.co.in/");

        //setContentView(webView);
       // String customHtml = "<html><body><h2>Greetings from IT-ian</h2></body></html>";
       // webView.loadData(customHtml, "text/html", "UTF-8");

    }

}