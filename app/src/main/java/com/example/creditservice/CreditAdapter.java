package com.example.creditservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CreditAdapter extends ArrayAdapter<Credit> {

    private LayoutInflater inflater;
    private int layout;
    private List<Credit> states;

    public CreditAdapter(Context context, int resource, List<Credit> states) {
        super(context, resource, states);
        this.states = states;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        ImageView flagView = (ImageView) view.findViewById(R.id.imageViewFlag);
        TextView nameView = (TextView) view.findViewById(R.id.labelName);
        TextView capitalView = (TextView) view.findViewById(R.id.labelCapital);
        TextView nameOfBank = (TextView) view.findViewById(R.id.labelNameOfBank);
        TextView id = (TextView) view.findViewById(R.id.textViewID);
        Credit state = states.get(position);

        flagView.setImageResource(state.getImageBank());
        nameView.setText(state.getPercent());
        capitalView.setText(state.getMinMaxAmount());
        nameOfBank.setText(state.getNameOfBank());
        id.setText(state.ID + "");
        return view;
    }
}