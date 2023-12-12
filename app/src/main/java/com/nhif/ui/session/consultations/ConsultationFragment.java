package com.nhif.ui.session.consultations;


import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nhif.R;

import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoView;

import java.net.URL;


public class ConsultationFragment extends Fragment {

    private String botpressServerUrl = "https://mediafiles.botpress.cloud/cea3660a-3f84-4f1c-8797-a3d813d2539c/webchat/bot.html";

    // Declare views
    private TextView titleTextView;
    private RecyclerView messagesRecyclerView;
    private TextInputLayout lastNameLayout;
    private TextInputEditText lastNameEditText;
    private ImageView sendImageView;
    private RelativeLayout sendMessageLayout;
    private FloatingActionButton addNewWatchlistButton;

    private WebView botWebview;

    private GeckoView botgeckoview;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consultations, container, false);

        // Initialize views
        titleTextView = view.findViewById(R.id.title);
//        messagesRecyclerView = view.findViewById(R.id.messagesRecycleView);
//        lastNameLayout = view.findViewById(R.id.lastNameLayout);
//        lastNameEditText = view.findViewById(R.id.lastNameEditText);
//        sendImageView = view.findViewById(R.id.send);
//        sendMessageLayout = view.findViewById(R.id.sendMessage);
//        addNewWatchlistButton = view.findViewById(R.id.add_new_watchlist);
//
//        sendImageView.setOnClickListener((v)->{
//            sendMessage(lastNameEditText.getText().toString());
//        });
            botWebview=view.findViewById(R.id.botWebview);

            configNew();
//            configureWebview();

//        botgeckoview=view.findViewById(R.id.geckoview);
//        try {
//            configureGeckoView();
//        }catch (Exception es)
//        {
//            es.printStackTrace();
//        }



        return view;
    }

    private void configNew() {
        botWebview.setWebViewClient(new WebViewClient());
        // Get the settings for the WebView
        WebSettings webSettings = botWebview.getSettings();

        // Enable JavaScript (if not already enabled)
        webSettings.setJavaScriptEnabled(true);

        // Enable CSS (if not already enabled)
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // Load a URL
        botWebview.loadUrl(botpressServerUrl);





    }

    private String[] avoids={
            "\uD83D\uDCA1 Make sure to open the \"❓ Answering Questions\" workflow to follow along this example.",
            "Alright, let's see how your chatbot can answer questions based on custom knowledge bases.\n" +
                    "\n" +
                    "In this example, we added one Knowledge Base and provided a few website pages from the official Botpress website.",
            "Now, try asking me \"What is Botpress?\" to see question answering in action."
    };

    private void configureGeckoView() {
        GeckoSession session = new GeckoSession();
        GeckoRuntime runtime = GeckoRuntime.create(this.getContext());

        session.open(runtime);
        botgeckoview.setSession(session);
        session.loadUri(botpressServerUrl);

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void configureWebview() {

//        botWebview.getSettings().setJavaScriptEnabled(true);

        WebSettings webSettings = botWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }



        botWebview.setWebViewClient(new WebViewClient());


        botWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                // Log console messages

                Log.d("WebView", consoleMessage.message());
                System.out.println(consoleMessage.messageLevel());
                System.out.println("line : "+consoleMessage.lineNumber());
                System.out.println("source : "+consoleMessage.sourceId());

                return true;
            }










        });

        botWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // Ensure this method returns false to allow WebView to handle the URL
                System.out.println(" This is a request "+request);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }


        });


//        botWebview.loadUrl(botpressServerUrl);
        botWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        botWebview.loadUrl(botpressServerUrl);





    }

    private void sendMessage(String toString) {



//        BotpressChat.getInstance()
//        Botpress botpress = new Botpress(botpressServerUrl);


    }


}
