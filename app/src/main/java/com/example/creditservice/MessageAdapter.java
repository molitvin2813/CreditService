package com.example.creditservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    private LayoutInflater inflater;
    private int layout;
    private List<Message> messageList;

    public MessageAdapter(Context context, int resource, List<Message> messageList) {
        super(context, resource, messageList);
        this.messageList = messageList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(this.layout, parent, false);
        TextView header = (TextView) view.findViewById(R.id.labelHeaderListMessage);
        TextView nameUser = (TextView) view.findViewById(R.id.labelNameUserListMessage);
        TextView nameBank = (TextView) view.findViewById(R.id.labelNameUserListMessage);
        TextView nameOfBank = (TextView) view.findViewById(R.id.labelNameOfBank);
        Message state = messageList.get(position);
        return  view;
    }
}
