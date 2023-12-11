package com.nhif.ui.session.consultations;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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

            configureWebview();

//        botgeckoview=view.findViewById(R.id.geckoview);
//        try {
//            configureGeckoView();
//        }catch (Exception es)
//        {
//            es.printStackTrace();
//        }



        return view;
    }

    private void configureGeckoView() {
        GeckoSession session = new GeckoSession();
        GeckoRuntime runtime = GeckoRuntime.create(this.getContext());

        session.open(runtime);
        botgeckoview.setSession(session);
        session.loadUri(botpressServerUrl);

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void configureWebview() {

        botWebview.getSettings().setJavaScriptEnabled(true);

        botWebview.setWebViewClient(new WebViewClient());


        botWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                // Log console messages
                Log.d("WebView", consoleMessage.message());
                return false;
            }




        });

//        botWebview.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                // Ensure this method returns false to allow WebView to handle the URL
//                System.out.println(request);
//                return false;
//            }
//        });


        botWebview.loadUrl(botpressServerUrl);




    }

    private void sendMessage(String toString) {



//        BotpressChat.getInstance()
//        Botpress botpress = new Botpress(botpressServerUrl);


    }


}
