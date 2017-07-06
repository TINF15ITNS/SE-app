package it15ns.friendscom.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.grpc.serverPackage.SearchUserResponse;
import it15ns.friendscom.R;
import it15ns.friendscom.adapters.MessageAdapter;
import it15ns.friendscom.datatypes.ChatMessage;
import it15ns.friendscom.datatypes.TextMessage;
import it15ns.friendscom.grpc.GrpcRunnableFactory;
import it15ns.friendscom.grpc.GrpcSyncTask;
import it15ns.friendscom.grpc.GrpcTask;
import it15ns.friendscom.grpc.runnables.SyncSearchProfileRunnable;
import it15ns.friendscom.handler.ChatHandler;
import it15ns.friendscom.handler.LocalUserHandler;
import it15ns.friendscom.model.Chat;
import it15ns.friendscom.model.User;
import it15ns.friendscom.xmpp.XMPPClient;

public class SpecificChatActivity extends AppCompatActivity {
    View view;
    Chat chat;
    //activity attributes
    private Button btn_send;
    private EditText txt_msg;
    private TextView txt_title;
    private Context ctx_main;
    private ListView messageList;
    private ScrollView scroll;

    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_chat);

        XMPPClient.setSpecificChatActivity(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText txt_chatPartner = (EditText) findViewById(R.id.chatPartner);

        ctx_main = this;
        txt_title = (TextView) findViewById(R.id.chatTitle);

        //get text msg
        txt_msg = (EditText) findViewById(R.id.chatMsg);
        //set auto scroll on view
        scroll = (ScrollView) findViewById(R.id.scrollView);
        scroll.fullScroll(View.FOCUS_DOWN);

        if(getIntent().getExtras() != null) {
            String nickname = getIntent().getExtras().getString("nickname");
            txt_chatPartner.setText(nickname);
            txt_chatPartner.setEnabled(false);

            setTitle(nickname);
            chat = ChatHandler.getChat(nickname, this);
            messageAdapter = new MessageAdapter(this, chat);
            messageList = (ListView) findViewById(R.id.chatTableLayout);
            messageList.setAdapter(messageAdapter);

            scrollMassageListToBottom();

            //for(ChatMessage chatMessage: chat.getMessages()) {
            //    TextMessage message = (TextMessage) chatMessage;
                //addMessage(message);
            //}
        } else {
            txt_chatPartner.setEnabled(true);
            txt_chatPartner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txt_chatPartner.setText("");
                }
            });
        }

        //init send button
        btn_send = (Button) findViewById(R.id.btn_sendMsg);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextMessage message = new TextMessage(txt_msg.getText().toString(), LocalUserHandler.getLocalUser(getApplicationContext()));
                message.setDate(new Date());

                if(isTextBoxEmpty()){
                    Snackbar.make(v.getRootView(), "Nachricht leer!", Snackbar.LENGTH_LONG);
                }else{
                    if(chat != null)
                        sendMessage(message);
                    else {
                        String partner = txt_chatPartner.getText().toString().trim().toLowerCase();
                        if(!partner.equals("Chat Partner") && partner.length() > 4) {
                            SearchUserResponse response = (SearchUserResponse) GrpcSyncTask.execute(new SyncSearchProfileRunnable(partner));
                            if(response.getSuccess()) {
                                chat = ChatHandler.getChat(partner, getApplicationContext());

                                messageAdapter = new MessageAdapter(ctx_main, chat);
                                messageList = (ListView) findViewById(R.id.chatTableLayout);
                                messageList.setAdapter(messageAdapter);

                                sendMessage(message);
                                txt_chatPartner.setEnabled(false);
                            }else {
                                Toast.makeText(SpecificChatActivity.this, "Account nicht gefunden", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SpecificChatActivity.this, "Probleme mit dem Namen des Chatpartners", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public boolean isTextBoxEmpty(){
        return txt_msg.getText().toString().isEmpty();
    }

    public void addMessage(ChatMessage message){
        String time = DateFormat.format("dd.MM.yyyy - hh:mm:ss", message.getDate()).toString();

        //display message in table layout
        TableRow row = new TableRow(ctx_main);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);

        TextView tv = new TextView(ctx_main);
        tv.setTextSize(20);

        if(message.getSender() == LocalUserHandler.getLocalUser(this)){
            tv.setTextColor(Color.parseColor("#ffffff"));   //dark blue
        }else{
            tv.setTextColor(Color.parseColor("#000"));   //dark red

        }

        tv.setBackground(getResources().getDrawable(R.drawable.incoming_bubble));
        tv.setPadding(10,10,10,10);
        TextMessage textMessage = (TextMessage) message;
        tv.setText(textMessage.getMessage());
        row.addView(tv);
        messageList.addView(row);
    }

    public void sendMessage(TextMessage message){
        //addMessage(message);
        chat.sendTextMessage(message, this);
        update();
        //versenden der Nachricht

        clearTextbox();
    }

    public void clearTextbox(){
        txt_msg.setText("");
    }

    public void update() {
        messageAdapter.notifyDataSetChanged();
        scrollMassageListToBottom();
    }

    private void scrollMassageListToBottom() {
        messageList.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                messageList.setSelection(messageAdapter.getCount() - 1);
            }
        });
    }

    private void clearTable() {
        //messageList.removeAllViews();
        //TODO:

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MainActivity.update();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        MainActivity.update();
        finish();
    }

    /*
    @Override
    protected void onStop() {
        XMPPClient.disconnect();
        super.onStop();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        try {
            XMPPClient.connect();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG);
        }
        super.onRestart();
    }
}
