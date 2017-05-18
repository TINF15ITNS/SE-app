package it15ns.friendscom.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Comparator;
import java.util.List;

import it15ns.friendscom.model.Chat;
import it15ns.friendscom.model.Handler;
import it15ns.friendscom.R;

/**
 * Created by danie on 15/05/2017.
 */

public class ChatAdapter extends BaseAdapter {

    private List<Chat> chats;
    private LayoutInflater inflater;
    private Handler handler;

    public ChatAdapter(Context context) {
        inflater = LayoutInflater.from(context);

        handler = Handler.getInstance();
        chats = handler.getChats();
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
            holder.datumsbereich = (TextView) convertView
                    .findViewById(R.id.text2);
            holder.icon =
                    (ImageView) convertView.findViewById(R.id.icon);

            convertView.setTag(holder);
        } else {
            // Holder bereits vorhanden
            holder = (ViewHolder) convertView.getTag();
        }

        Context context = parent.getContext();
        String name = chats.get(position).getName();

        holder.name.setText(name);
        holder.icon.setImageResource(R.drawable.ic_menu_camera);

        if (++position >= getCount()) {
            position = 0;
        }

        holder.datumsbereich.setText("Datum");
        return convertView;
    }

    static class ViewHolder {
        TextView name, datumsbereich;
        ImageView icon;
    }
}
