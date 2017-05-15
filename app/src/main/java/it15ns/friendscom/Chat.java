package it15ns.friendscom;

//connected to activity chat

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it15ns.friendscom.Datatypes.ChatMessage;
import it15ns.friendscom.Datatypes.Location;
import it15ns.friendscom.model.Calendar;
import it15ns.friendscom.model.ToDoList;
import it15ns.friendscom.model.User;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import it15ns.friendscom.Datatypes.TextMessage;
import it15ns.friendscom.R;

/**
 * Created by valentin on 5/9/17.
 */

public class Chat extends AppCompatActivity{
    //activity attributes
    private Button btn_send;
    private EditText txt_msg;
    private Context ctx_main;
    private TableLayout tbl_msg;
    private ScrollView scroll;
    //class attributes
    private List<User> participants;
    private List<ChatMessage> messages;
    private List<ToDoList> toDoLists;
    private Calendar chatCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //get context of activity
        ctx_main = this;
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
                    Toast.makeText(ctx_main, "Messagebox is empty", Toast.LENGTH_SHORT);
                }else{
                    sendMessage(new TextMessage(txt_msg.getText().toString()));
                }

            }
        });
    }

    public Chat(){}

    public Chat(Calendar chatCalendar){
        this.chatCalendar = chatCalendar;
        this.participants = new ArrayList<User>();
        this.messages = new ArrayList<ChatMessage>();
        this.toDoLists = new ArrayList<ToDoList>();
    }

    public void setChatCalendar(Calendar chatCalendar){
        this.chatCalendar = chatCalendar;
    }
    public Calendar getChatCalendar(){
        return this.chatCalendar;
    }

    public void createTextMessage(String stringOfTextBox){
        //TODO:
    }
    public void createTodoListMessage(ToDoList toDoList){
        //TODO:
    }
    public void createSharLocationMessage(Location location){
        //TODO:
    }
    //public void createEventMessage(GoogleCalendarEntry calendarEntry){
        //TODO:
    //}
    public boolean isTextBoxEmpty(){
        return txt_msg.getText().toString().isEmpty();
    }
    public void sendMessage(ChatMessage message){
        //TODO: send message to server
        TextMessage txt = (TextMessage)message;
        String time = DateFormat.format("dd.MM.yyyy - hh:mm:ss", new Date()).toString();
        addMsgToTable("Me", time, txt.getMessage());
        clearTextbox();
    }
    public void clearTextbox(){
        txt_msg.setText("");
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
