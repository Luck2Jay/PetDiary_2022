package com.example.petdiary_2022;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DiaryAdapter extends BaseAdapter {

    List<Diary> diary;
    Context context;
    LayoutInflater inflater;

    public DiaryAdapter(List<Diary> f, Context context) {
        this.diary = f;
        this.context = context;
        this.inflater = (LayoutInflater) context. getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return diary.size();
    }

    @Override
    public Object getItem(int position) {
        return diary.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(R.layout.activity_diary_item,null);
        }

        TextView item1 = (TextView)convertView.findViewById(R.id.diary_name_item);
        TextView item2 = (TextView)convertView.findViewById(R.id.diary_id_item);
        TextView item3 = (TextView)convertView.findViewById(R.id.diary_date_item);

        Diary f = diary.get(position);
        item1.setText(f.name);
        item2.setText(f.date);
        item3.setText(f.id);


        return convertView;
    }
}