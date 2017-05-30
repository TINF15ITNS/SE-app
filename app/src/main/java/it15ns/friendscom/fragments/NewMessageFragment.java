package it15ns.friendscom.fragments;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import it15ns.friendscom.activities.ChatActivity;
import it15ns.friendscom.datatypes.TextMessage;
import it15ns.friendscom.model.Chat;
import it15ns.friendscom.model.Handler;
import it15ns.friendscom.R;
import it15ns.friendscom.model.User;

/**
 * Created by danie on 12/05/2017.
 */

public class NewMessageFragment extends android.support.v4.app.Fragment {
    View view;
    EditText text_receiver;
    EditText text_message;
    Button btn_sent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //text_receiver = (EditText) findViewById();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final FragmentManager fragmentManager = getFragmentManager();

        btn_sent = (Button) getView().findViewById(R.id.btn_sent);
        btn_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nickname = text_receiver.getText().toString();
                    String message = text_message.getText().toString();

                    Handler handler = Handler.getInstance();
                    User receiver = handler.getUsers(nickname);
                    Chat chat = receiver.getChat();

                    chat.sendTextMessage(new TextMessage(new Date(), handler.getMe(), message));

                    Snackbar.make(getView(), "Versendet!", Snackbar.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Snackbar.make(getView(), ex.toString(), Snackbar.LENGTH_LONG).show();
                } finally {
                    ChatActivity.setFab(true);
                    fragmentManager.beginTransaction().replace(R.id.container, new ChatListFragment()).addToBackStack(null).commit();
                }
            }
        });

        text_receiver = (EditText) getView().findViewById(R.id.text_receiver);
        text_message = (EditText) getView().findViewById(R.id.text_message);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_new_message, container, false );
        return view;
    }
}
