package com.example.creditservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
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
        TextView nameOfBank = (TextView) view.findViewById(R.id.labelNameBankListMessage);
        TextView idMessage = (TextView) view.findViewById(R.id.textViewIDMessage);
        TextView approved =  (TextView) view.findViewById(R.id.textViewApprovedListMessage);
        Message message = messageList.get(position);

        nameUser.setText("ОТ пользователя: "+message.userName);
        header.setText("Заголовок: "+message.header);
        idMessage.setText(""+message.idMessage);
        nameOfBank.setText("Для представителя банка: "+message.bankName);
        approved.setText((message.approved==true)?"Одобрено":"Увы, но нет(");

        return  view;
    }
}
