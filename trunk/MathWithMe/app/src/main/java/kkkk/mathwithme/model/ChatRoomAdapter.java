package kkkk.mathwithme.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import kkkk.mathwithme.R;

/**
 * Created by Gilad Kinory on 16/04/2016.
 * giladkinory2000@gmail.com
 */
public class ChatRoomAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;

    public ChatRoomAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO: return actually count

        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.chat_message, null);

        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView usernameTextView = (TextView) convertView.findViewById(R.id.usernameTextView);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.timeTextView);

        // TODO: change text of message, username and time

        return convertView;
    }
}
