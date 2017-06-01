package it15ns.friendscom.fragments;

//import android.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import it15ns.friendscom.R;
import it15ns.friendscom.activities.MainActivity;
import it15ns.friendscom.activities.ProfileActivity;
import it15ns.friendscom.adapters.ContactAdapter;
import it15ns.friendscom.model.User;

/**
 * Created by danie on 12/05/2017.
 */

public class ContactListFragment extends Fragment {
    View view;
    ListView contactList;
    FragmentManager fragmentManager;
    SpecificChatFragment_deprecated specificChatFragment;
    ContactAdapter contactAdapter;

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setFab(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragmentManager = getFragmentManager();
        contactList = (ListView) getView().findViewById(R.id.chatList);
        contactAdapter = new ContactAdapter(getView().getContext());
        contactList.setAdapter(contactAdapter);

        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) parent.getItemAtPosition(position);

                //Bundle bundle = new Bundle();
                //bundle.putString("nickname", chat.getName());

                //specificChatFragment = new SpecificChatFragment_deprecated();
                //specificChatFragment.setArguments(bundle);

                Intent startSpecificProfile = new Intent(getActivity(), ProfileActivity.class);
                startSpecificProfile.putExtra("nickname", user.getNickname());
                startActivity(startSpecificProfile);

                //MainActivity.setFab(false);
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
        contactAdapter.notifyDataSetChanged();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_chat_list, container, false );

        return view;
    }

}
