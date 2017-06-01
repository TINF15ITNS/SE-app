package it15ns.friendscom.fragments;

//connected to activity chat

import java.util.Date;

import it15ns.friendscom.datatypes.ChatMessage;
import it15ns.friendscom.model.Chat;
import it15ns.friendscom.handler.ChatHandler;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import it15ns.friendscom.datatypes.TextMessage;
import it15ns.friendscom.R;
import it15ns.friendscom.handler.LocalUserHandler;

/**
 * Created by valentin on 5/9/17.
 */

public class SpecificChatFragment extends Fragment{

    View view;
    Chat chat;
    //activity attributes
    private Button btn_send;
    private EditText txt_msg;
    private TextView txt_title;
    private Context ctx_main;
    private TableLayout tbl_msg;
    private ScrollView scroll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_specific_chat, container, false );
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //XMPPClient.getInstance().setSpecificChatFragment(this);

        ctx_main = getView().getContext();
        txt_title = (TextView) getView().findViewById(R.id.chatTitle);

        //table layout to display messages
        tbl_msg = (TableLayout) getView().findViewById(R.id.chatTableLayout);
        //get text msg
        txt_msg = (EditText) getView().findViewById(R.id.chatMsg);
        //set auto scroll on view
        scroll = (ScrollView) getView().findViewById(R.id.scrollView);
        scroll.fullScroll(View.FOCUS_DOWN);

        //init send button
        btn_send = (Button) getView().findViewById(R.id.btn_sendMsg);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTextBoxEmpty()){
                    Toast.makeText(ctx_main, "Messagebox is empty", Toast.LENGTH_SHORT);
                }else{
                    sendMessage(new TextMessage(txt_msg.getText().toString(), LocalUserHandler.getLocalUser()));
                }
            }
        });

        chat = ChatHandler.getChat(getArguments().getString("nickname"));

        txt_title.setText(chat.getNickname());
        for(ChatMessage chatMessage: chat.getMessages()) {
            TextMessage message = (TextMessage) chatMessage;
            addMessage(message);
        }
    }

    public boolean isTextBoxEmpty(){
        return txt_msg.getText().toString().isEmpty();
    }

    public void addMessage(TextMessage message){
        String time = DateFormat.format("dd.MM.yyyy - hh:mm:ss", message.getDate()).toString();
        addMsgToTable(message.getSender().getNickname(), time, message.getMessage());
    }

    public void sendMessage(TextMessage message){
        String time = DateFormat.format("dd.MM.yyyy - hh:mm:ss", new Date()).toString();
        addMsgToTable("Me", time, message.getMessage());

        //versenden der Nachricht
        chat.sendTextMessage(message);

        clearTextbox();
    }

    public void clearTextbox(){

        txt_msg.setText("");
    }

    public void update() {
        clearTable();

        for(ChatMessage chatMessage: chat.getMessages()) {
            TextMessage message = (TextMessage) chatMessage;
            addMessage(message);
        }
    }

    private void clearTable() {
        tbl_msg.removeAllViews();
        //TODO:
    }

    public void addMsgToTable(String name, String time, String msg){
        //display message in table layout
        TableRow row = new TableRow(ctx_main);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        //convert ChatMessage to TextMessage

        TextView tv = new TextView(ctx_main);
        tv.setTextSize(20);

        if(true){ //TODO: check if this is my user id (my own message), so we have differen colors
            tv.setTextColor(Color.argb(255, 14, 26, 84));   //dark blue
        }else{
            tv.setTextColor(Color.argb(255, 100, 0, 0));   //dark red
        }

        tv.setText(Html.fromHtml("<b>" + name + ": </b>" + time + "<br>" + msg));
        row.addView(tv);
        tbl_msg.addView(row);
    }
}
