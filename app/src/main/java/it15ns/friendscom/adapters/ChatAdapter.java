package it15ns.friendscom.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import org.w3c.dom.Text;

import java.util.List;

import it15ns.friendscom.datatypes.TextMessage;
import it15ns.friendscom.handler.LocalUserHandler;
import it15ns.friendscom.handler.UserHandler;
import it15ns.friendscom.model.Chat;
import it15ns.friendscom.handler.ChatHandler;
import it15ns.friendscom.R;
import it15ns.friendscom.model.User;

/**
 * Created by danie on 15/05/2017.
 */

public class ChatAdapter extends BaseAdapter {
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        chats = ChatHandler.getChats();
    }

    private List<Chat> chats;
    private LayoutInflater inflater;

    public ChatAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        chats = ChatHandler.getChats();
    }

    @Override
    public int getCount() {
        return chats.size();
    }

    @Override
    public Object getItem(int position) {
        return chats.get(position);
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
            convertView = inflater.inflate(R.layout.chat_icon_text_text,
                    parent, false);
            // Holder erzeugen
            holder = new ViewHolder();
            holder.name =
                    (TextView) convertView.findViewById(R.id.text1);
            holder.lastMessage = (TextView) convertView
                    .findViewById(R.id.text2);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);

            convertView.setTag(holder);
        } else {
            // Holder bereits vorhanden
            holder = (ViewHolder) convertView.getTag();
        }
        Chat chat = chats.get(position);
        //TODO:chat instance of group chat?

        String nickname = chat.getNickname();
        User user = UserHandler.getUser(nickname);

        holder.name.setText(user.getSurname());

        TextDrawable drawable = TextDrawable.builder().beginConfig()
                .width(100)  // width in px
                .height(100) // height in px
                .endConfig()
                .buildRound(nickname.substring(0,2).toUpperCase(), Color.parseColor("#007ac1"));
        holder.icon.setImageDrawable(drawable);

        String text = "";
        if(chat.getNewestMessage() instanceof TextMessage){
            TextMessage message = (TextMessage) chat.getNewestMessage();
            if(message.getSender() == LocalUserHandler.getLocalUser())
                text = "Ich: ";
            else
                text = message.getSender().getSurname();

            text += message.getMessage();
        }

        holder.lastMessage.setText(text);

        if (++position >= getCount()) {
            position = 0;
        }

        return convertView;
    }

    static class ViewHolder {
        TextView name, lastMessage;
        ImageView icon;
    }
}
