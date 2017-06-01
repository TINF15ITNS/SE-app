package it15ns.friendscom.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.List;

import it15ns.friendscom.R;
import it15ns.friendscom.datatypes.ChatMessage;
import it15ns.friendscom.datatypes.TextMessage;
import it15ns.friendscom.handler.LocalUserHandler;
import it15ns.friendscom.handler.UserHandler;
import it15ns.friendscom.model.Chat;
import it15ns.friendscom.model.User;

/**
 * Created by danie on 15/05/2017.
 */

public class MessageAdapter extends BaseAdapter {

    private List<ChatMessage> chatMessages;
    private LayoutInflater inflater;
    private Chat chat;

    public MessageAdapter(Context context, Chat chat) {
        inflater = LayoutInflater.from(context);
        this.chat = chat;
        this.chatMessages = chat.getMessagesList();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.chatMessages = chat.getMessagesList();
    }

    @Override
    public int getCount() {
        return chatMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return chatMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // falls nötig, convertView bauen
        if (convertView == null) {
            // Layoutdatei entfalten
            convertView = inflater.inflate(R.layout.message_incomming, parent, false);
            // Holder erzeugen
            holder = new ViewHolder();
            holder.message = (TextView) convertView.findViewById(R.id.text1);
            holder.time = (TextView) convertView
                    .findViewById(R.id.text2);

            convertView.setTag(holder);
        } else {
            // Holder bereits vorhanden
            holder = (ViewHolder) convertView.getTag();
        }
        ChatMessage chatMessage = chatMessages.get(position);
        //TODO:chat instance of group chat?

        if(chatMessage instanceof TextMessage){
            TextMessage textMessage = (TextMessage) chatMessage;

            holder.message.setText(textMessage.getMessage());
            holder.time.setText(textMessage.getDate().toString());
        }



        if (++position >= getCount()) {
            position = 0;
        }

        return convertView;
    }

    static class ViewHolder {
        TextView message, time;

    }
}
