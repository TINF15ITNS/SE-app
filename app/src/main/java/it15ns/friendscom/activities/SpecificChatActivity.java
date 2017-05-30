package it15ns.friendscom.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import it15ns.friendscom.R;
import it15ns.friendscom.datatypes.ChatMessage;
import it15ns.friendscom.datatypes.TextMessage;
import it15ns.friendscom.fragments.ChatListFragment;
import it15ns.friendscom.model.Chat;
import it15ns.friendscom.model.Handler;
import it15ns.friendscom.xmpp.XMPPClient;

public class SpecificChatActivity extends AppCompatActivity {
    View view;
    Chat chat;
    //activity attributes
    private Button btn_send;
    private EditText txt_msg;
    private TextView txt_title;
    private Context ctx_main;
    private TableLayout tbl_msg;
    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_chat);

        XMPPClient.getInstance().setSpecificChatActivity(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText txt_chatPartner = (EditText) findViewById(R.id.chatPartner);

        ctx_main = this;
        txt_title = (TextView) findViewById(R.id.chatTitle);

        //table layout to display messages
        tbl_msg = (TableLayout) findViewById(R.id.chatTableLayout);
        //get text msg
        txt_msg = (EditText) findViewById(R.id.chatMsg);
        //set auto scroll on view
        scroll = (ScrollView) findViewById(R.id.scrollView);
        scroll.fullScroll(View.FOCUS_DOWN);

        //init send button
        btn_send = (Button) findViewById(R.id.btn_sendMsg);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTextBoxEmpty()){

                }else{
                    if(chat != null)
                        sendMessage(new TextMessage(txt_msg.getText().toString(), Handler.getInstance().getMe()));
                    else {
                        String partner = txt_chatPartner.getText().toString();
                        if(!partner.equals("Chat Partner") && partner.length() > 4) {
                            chat = Handler.getInstance().getUserChat(partner);
                            for(ChatMessage chatMessage: chat.getMessages()) {
                                TextMessage message = (TextMessage) chatMessage;
                                addMessage(message);
                            }
                            sendMessage(new TextMessage(txt_msg.getText().toString(), Handler.getInstance().getMe()));
                            txt_chatPartner.setEnabled(false);
                        } else {
                            Toast.makeText(ctx_main, "Probleme mit dem Namen des Chatpartners", Toast.LENGTH_SHORT);
                        }
                    }
                }
            }
        });

        if(getIntent().getExtras() != null) {
                String nickname = getIntent().getExtras().getString("nickname");
                txt_chatPartner.setText(nickname);
                txt_chatPartner.setEnabled(false);

                chat = Handler.getInstance().getUserChat(nickname);
                for(ChatMessage chatMessage: chat.getMessages()) {
                    TextMessage message = (TextMessage) chatMessage;
                    addMessage(message);
                }
        } else {
            txt_chatPartner.setEnabled(true);
            txt_chatPartner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txt_chatPartner.setText("");
                }
            });
        }
    }

    public boolean isTextBoxEmpty(){
        return txt_msg.getText().toString().isEmpty();
    }

    public void addMessage(TextMessage message){
        String time = DateFormat.format("dd.MM.yyyy - hh:mm:ss", message.getDate()).toString();
        addMsgToTable(message.getSender().getSurname(), time, message.getMessage());
    }

    public void sendMessage(TextMessage message){
        String time = DateFormat.format("dd.MM.yyyy - hh:mm:ss", new Date()).toString();
        addMsgToTable(Handler.getInstance().getMe().getSurname(), time, message.getMessage());

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

        if(name.equals("Me")){
            tv.setTextColor(Color.argb(255, 14, 26, 84));   //dark blue
        }else{
            tv.setTextColor(Color.argb(255, 100, 0, 0));   //dark red
        }

        tv.setText(Html.fromHtml("<b>" + name + ": </b>" + time + "<br>" + msg));
        row.addView(tv);
        tbl_msg.addView(row);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ChatActivity.update();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        ChatActivity.update();
        finish();
    }


    /*
    @Override
    protected void onStop() {
        XMPPClient.getInstance().disconnectConnection();
        super.onStop();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        try {
            XMPPClient.getInstance().connectConnection();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG);
        }
        super.onRestart();
    }
}
