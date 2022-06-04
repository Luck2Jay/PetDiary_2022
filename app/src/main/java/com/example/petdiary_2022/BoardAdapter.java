package com.example.petdiary_2022;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BoardAdapter extends BaseAdapter {

    List<Board> board;
    Context context;
    LayoutInflater inflater;

    public BoardAdapter(List<Board> f, Context context) {
        this.board = f;
        this.context = context;
        this.inflater = (LayoutInflater) context. getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return board.size();
    }

    @Override
    public Object getItem(int position) {
        return board.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(R.layout.activity_read_item,null);
        }

        TextView item1 = (TextView)convertView.findViewById(R.id.text_name);
        TextView item2 = (TextView)convertView.findViewById(R.id.text_date);
        TextView item3 = (TextView)convertView.findViewById(R.id.text_writer);

        Board f = board.get(position);
        item1.setText(f.name);
        item2.setText(f.date);
        item3.setText(f.id);


        return convertView;
    }
}
