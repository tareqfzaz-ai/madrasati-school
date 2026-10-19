package com.tareqfzaz.madrasati;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends Activity {
    private WebView web;
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        web = new WebView(this);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.setWebViewClient(new WebViewClient());
        web.setWebChromeClient(new WebChromeClient());
        web.addJavascriptInterface(new Bridge(), "Madrasati");
        setContentView(web);
        web.loadUrl("file:///android_asset/index.html");
    }
    private class Bridge {
        @JavascriptInterface public void share(String text) {
            runOnUiThread(() -> {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(i, "مشاركة عبر"));
            });
        }
        @JavascriptInterface public void exportBackup(String data) {
            runOnUiThread(() -> {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("application/json");
                i.putExtra(Intent.EXTRA_TEXT, data);
                i.putExtra(Intent.EXTRA_SUBJECT, "نسخة احتياطية - مدرستي");
                startActivity(Intent.createChooser(i, "حفظ أو إرسال النسخة الاحتياطية"));
            });
        }
    }
    @Override public void onBackPressed() {
        if (web != null && web.canGoBack()) web.goBack(); else super.onBackPressed();
    }
}
