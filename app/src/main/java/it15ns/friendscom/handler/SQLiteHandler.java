package it15ns.friendscom.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import it15ns.friendscom.datatypes.ChatMessage;
import it15ns.friendscom.datatypes.TextMessage;
import it15ns.friendscom.model.Calendar;
import it15ns.friendscom.model.Chat;
import it15ns.friendscom.model.User;

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
        //da erstellt man eig die tables aber des wollen wir ja net
        //wollen die ja erst zur laufzeit haben
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //wird aufgerufen, wenn man ne neue version veröffentlicht
            //so professionell sind wir noch net, ist unnötig
    }
    public boolean isTableExists(String nickname) {
        SQLiteDatabase db = this.getWritableDatabase(); //open db object
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+ nickname +"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void deleteAllTables() {
        // query to obtain the names of all tables in your database
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        List<String> tables = new ArrayList<>();

// iterate over the result set, adding every table name to a list
        while (c.moveToNext()) {
            tables.add(c.getString(0));
        }

// call DROP TABLE on every table name
        for (String table : tables) {
            String dropQuery = "DROP TABLE IF EXISTS " + table;
            db.execSQL(dropQuery);
        }
    }

    public void createChatTable(String nickname){
        SQLiteDatabase db = this.getWritableDatabase(); //open db object
        db.execSQL("Create Table " + nickname + "(sender varchar(255), date int, msg varchar(255))"); //sql richtig? wollen wir en PK?
    }

    public void addMessageToDB(ChatMessage msg, Chat chat) {
        SQLiteDatabase db = this.getWritableDatabase(); //open db object
        ContentValues values = new ContentValues(); //create values
        TextMessage txt = (TextMessage)msg; //was wenns keine textmessage ist??
        values.put("sender", msg.getSender().getNickname());    //add values (key, value)
        values.put("date", msg.getDate().getTime());
        values.put("msg", txt.getMessage());
        db.insert(chat.getNickname(), null, values); //insert(tableName, null, values)
        db.close(); //close object to save performance
    }

    public LinkedList<ChatMessage> getAllMessages(String name, Context context) {
        LinkedList<ChatMessage> msgList = new LinkedList<>();
        String selectQuery = "SELECT  * FROM " + name;  //alle nachrichten der tabelle mim (user/group)Name
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) { //check if its empty
            do {
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.setTimeInMillis(cursor.getInt(1));
                Date d = calendar.getTime();
                TextMessage msg = new TextMessage(d, cursor.getString(0), cursor.getString(2), context); //constructor (date, sender, msg)
                msgList.add(msg);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return msgList;    //return list with all msg's
    }

    public HashMap<String, User> getAllUsers(Context context) {
        HashMap<String, User> userList = new HashMap<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (cursor.moveToFirst()) { //check if its empty
            do {
                String name = cursor.getString(0);
                if(!name.equals("android_metadata"))
                    userList.put(name, UserHandler.createUser(name, context));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return userList;    //return list with all msg's
    }
}
