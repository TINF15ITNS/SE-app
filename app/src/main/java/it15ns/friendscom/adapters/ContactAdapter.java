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

import java.util.List;

import it15ns.friendscom.R;
import it15ns.friendscom.handler.ChatHandler;
import it15ns.friendscom.handler.UserHandler;
import it15ns.friendscom.model.Handler;
import it15ns.friendscom.model.User;

import static it15ns.friendscom.R.id.nickname;

/**
 * Created by danie on 15/05/2017.
 */

public class ContactAdapter extends BaseAdapter {
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        users = UserHandler.getSortedUsers();
    }

    private List<User> users;
    private LayoutInflater inflater;

    public ContactAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        users = UserHandler.getSortedUsers();
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
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
            convertView = inflater.inflate(R.layout.contact_icon_text,
                    parent, false);
            // Holder erzeugen
            holder = new ViewHolder();
            holder.name =
                    (TextView) convertView.findViewById(R.id.text1);

            holder.icon = (ImageView) convertView.findViewById(R.id.icon);

            convertView.setTag(holder);
        } else {
            // Holder bereits vorhanden
            holder = (ViewHolder) convertView.getTag();
        }


        User user = users.get(position);
        String name = user.getSurname();

        holder.name.setText(name);

        TextDrawable drawable = TextDrawable.builder().beginConfig()
                .width(150)  // width in px
                .height(150) // height in px
                .endConfig()
                .buildRound(name.substring(0,2).toUpperCase(), Color.parseColor("#007ac1"));
        holder.icon.setImageDrawable(drawable);

        if (++position >= getCount()) {
            position = 0;
        }
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        ImageView icon;
    }
}
