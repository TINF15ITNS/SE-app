package it15ns.friendscom.fragments;

//import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import it15ns.friendscom.R;
import it15ns.friendscom.activities.ChatActivity;
import it15ns.friendscom.activities.SpecificChatActivity;
import it15ns.friendscom.adapters.ChatAdapter;
import it15ns.friendscom.model.Chat;
import it15ns.friendscom.model.Handler;

/**
 * Created by danie on 12/05/2017.
 */

public class ChatListFragment extends Fragment {
    View view;
    ListView chatList;
    FragmentManager fragmentManager;
    SpecificChatFragment specificChatFragment;
    Handler handler;
    ChatAdapter chatListAdapter;

    @Override
    public void onResume() {
        super.onResume();
        ChatActivity.setFab(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragmentManager = getFragmentManager();
        chatList = (ListView) getView().findViewById(R.id.chatList);
        chatListAdapter = new ChatAdapter(getView().getContext());
        chatList.setAdapter(chatListAdapter);

        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chat chat = (Chat) parent.getItemAtPosition(position);

                //Bundle bundle = new Bundle();
                //bundle.putString("nickname", chat.getName());

                //specificChatFragment = new SpecificChatFragment();
                //specificChatFragment.setArguments(bundle);

                Intent startSpecificChat = new Intent(getActivity(), SpecificChatActivity.class);
                startSpecificChat.putExtra("nickname", chat.getName());
                startActivity(startSpecificChat);

                //ChatActivity.setFab(false);
                //fragmentManager.beginTransaction().replace(R.id.content_frame, specificChatFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText text_receiver;
        EditText text_message;
        Button btn_sent;

        //text_receiver = (EditText) findViewById();
    }

    public void update() {
        chatListAdapter.notifyDataSetChanged();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_chat_list, container, false );

        return view;
    }

}
