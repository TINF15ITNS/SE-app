package it15ns.friendscom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import it15ns.friendscom.R;
import it15ns.friendscom.adapters.ChatAdapter;

/**
 * Created by danie on 12/05/2017.
 */

public class ChatListFragment extends Fragment {
    View view;
    ListView chatList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        chatList = (ListView) getView().findViewById(R.id.chatList);
        ChatAdapter chatListAdapter = new ChatAdapter(getView().getContext());
        chatList.setAdapter(chatListAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText text_receiver;
        EditText text_message;
        Button btn_sent;

        //text_receiver = (EditText) findViewById();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_chat_list, container, false );

        return view;
    }


}
