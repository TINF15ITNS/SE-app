package it15ns.friendscom.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import it15ns.friendscom.datatypes.ChatMessage;
import it15ns.friendscom.datatypes.TextMessage;

/**
 * Created by valentin on 5/16/17.
 */

public class SQLiteHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "messages";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //glaube des brauchen wir net
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //wird aufgerufen, wenn man ne neue version veröffentlicht
            //so professionell sind wir noch net, ist unnötig
    }

    public void createChatTable(String name){
        SQLiteDatabase db = this.getWritableDatabase(); //open db object
        db.execSQL("Create Table " + name + "(String sender, Date date, String msg)"); //sql richtig? wollen wir en PK?
    }

    public void addMessageToDB(ChatMessage msg) {
        SQLiteDatabase db = this.getWritableDatabase(); //open db object
        ContentValues values = new ContentValues(); //create values
        TextMessage txt = (TextMessage)msg;
        values.put("sender", msg.getSender().getNickname());    //add values (key, value)
        values.put("date", msg.getDate().toString());
        values.put("msg", txt.getMessage());
        db.insert(msg.getSender().getNickname(), null, values); //insert(tableName, null, values)
        db.close(); //close object to save performance
    }

    public List<ChatMessage> getAllMessages(String name) {
        List<ChatMessage> msgtList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + name;  //alle nachrichten der tabelle mim (user/group)Name
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) { //check if its empty
            do {
                //in DB (sender, date, msg)
                Date d = new Date(cursor.getString(0).toString());  //TODO: string in date umwandeln, dann läufts :D
                TextMessage msg = new TextMessage(cursor.getString(1), d, cursor.getString(2));
                msgtList.add(msg);
            } while (cursor.moveToNext());
        }
        return msgtList;    //return list with all msg's
    }
}
