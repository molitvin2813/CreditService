package com.example.creditservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StateAdapter extends ArrayAdapter<State> {

    private LayoutInflater inflater;
    private int layout;
    private List<State> states;

    public StateAdapter(Context context, int resource, List<State> states) {
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
        State state = states.get(position);

        flagView.setImageResource(state.getImageBank());
        nameView.setText(state.getPercent());
        capitalView.setText(state.getMinMaxAmount());
        nameOfBank.setText(state.getNameOfBank());
        return view;
    }
}