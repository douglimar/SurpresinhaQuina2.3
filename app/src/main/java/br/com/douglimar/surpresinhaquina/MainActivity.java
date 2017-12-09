package br.com.douglimar.surpresinhaquina;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE  = "br.com.douglimar.surpresinha.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "br.com.douglimar.surpresinha.MESSAGE2";

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = findViewById(R.id.linearSelectGame);

        Button btnJogoUnico = findViewById(R.id.btnSingleGame);
        Button btnJogosMultiplos = findViewById(R.id.btnMultipleGames);
        Button btnLastResults = findViewById(R.id.btnLastResults);

        final Surpresinha surpresinha = new Surpresinha();
        final Intent intent = getIntent();
        final String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //TextView tvTitulo = findViewById(R.id.tvSelectGameTitle2);
        //tvTitulo.setText(R.string.quina);

        // Create a AdView
        // Load Advertisement Banner
        AdView mAdView = findViewById(R.id.adViewMain);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        btnJogoUnico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "SelectGame");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "JogoUnicoClick");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                Intent intent2 = new Intent(getBaseContext(), ResultActivity.class);

                intent2.putExtra(MainActivity.EXTRA_MESSAGE, message);
                intent2.putExtra(MainActivity.EXTRA_MESSAGE2, surpresinha.generateQuinaGame());
                intent2.putExtra("XPTO", 1);

                startActivity(intent2);
            }
        });

        btnJogosMultiplos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "SelectGame");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "JogoMultiplo");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                Intent intent1 = new Intent(getApplicationContext(), ConfiguradorActivity.class);
                intent1.putExtra(MainActivity.EXTRA_MESSAGE, message);

                startActivity(intent1);
            }
        });

        btnLastResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //carregaWebView(surpresinha.getUrl(getString(R.string.quina)), message);
                carregaWebView2(surpresinha.getUrl(getString(R.string.quina)), message);

            }
        });

/*
        switch (message) {

            case "MEGA-SENA": {
                linearLayout.setBackgroundResource(R.color.colorMegasena);
                break;
            }
            case "QUINA": {
                linearLayout.setBackgroundResource(R.color.colorQuina);
                break;
            }
            case "LOTOFÁCIL": {
                linearLayout.setBackgroundResource(R.color.colorLotofacil);
                break;
            }
            case "LOTOMANIA": {
                linearLayout.setBackgroundResource(R.color.colorLotomania);
                break;
            }
            case "DUPLA-SENA": {
                linearLayout.setBackgroundResource(R.color.colorDuplasena);
                break;
            }
        }
*/
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private String openActivity2(Surpresinha pSurpresinha, String pMessage) {

                return pSurpresinha.generateQuinaGame();
    }

    private void carregaWebView2(String url, String pMessage) {

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "SelectGame");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "CarregaWebView");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        Intent intent = new Intent(getApplicationContext(), WebviewActivity.class);
        intent.putExtra("URL", url);

        startActivity(intent);


    }



        private void carregaWebView(String url, String pMessage) {

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "SelectGame");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "CarregaWebView");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        setContentView(R.layout.activity_webview);

        WebView myWebView;

        AdView adViewWeb = findViewById(R.id.adViewWebview);
        AdRequest adRequestWeb = new AdRequest.Builder().build();
        adViewWeb.loadAd(adRequestWeb);

        WebViewClient myWebViewClient = new WebViewClient() {

            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
                progressDialog.setMessage("Aguarde... Carregando Resultados");
                progressDialog.setCancelable(false);

                progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener(){

                    //@Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        return keyCode == KeyEvent.KEYCODE_SEARCH;
                    }
                });

                progressDialog.show();
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                progressDialog.setMessage("Aguarde... Carregando Resultados na internet");
                progressDialog.setCancelable(true);

                progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener(){
                    //@Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                        return keyCode == KeyEvent.KEYCODE_SEARCH;
                    }});

                progressDialog.show();
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

			/* @Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				if (url.contains("www1.caixa.gov.br") == true )
					return false;

				return true;

			} */
        };

        // Get Web view
        myWebView = findViewById( R.id.webView1 ); //This is the id you gave
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setSupportZoom(true);       //Zoom Control on web (You don't need this if ROM supports Multi-Touch
        myWebView.getSettings().setBuiltInZoomControls(true); //Enable Multitouch if supported by ROM
        myWebView.getSettings().setLoadsImagesAutomatically(true); // Dont loud the images
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(false);

        /*

        if ( pMessage.equals("LOTOMANIA") || pMessage.equals("LOTOFACIL") )
            myWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        else */
            myWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        myWebView.setWebViewClient(myWebViewClient);

        // Load URL
        myWebView.loadUrl(url);


        LinearLayout llWeb = findViewById(R.id.llWebview);

    }
}
