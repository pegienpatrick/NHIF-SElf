package com.nhif.ui.session.consultations;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nhif.R;


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



        return view;
    }

    private void configureWebview() {

        botWebview.getSettings().setJavaScriptEnabled(true);
        botWebview.loadUrl(botpressServerUrl);

    }

    private void sendMessage(String toString) {



//        BotpressChat.getInstance()
//        Botpress botpress = new Botpress(botpressServerUrl);


    }


}
