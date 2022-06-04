package com.example.petdiary_2022;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PathAdapter extends BaseAdapter {

    List<Path> path;
    Context context;
    LayoutInflater inflater;

    public PathAdapter(List<Path> f, Context context) {
        this.path = f;
        this.context = context;
        this.inflater = (LayoutInflater) context. getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return path.size();
    }

    @Override
    public Object getItem(int position) {
        return path.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(R.layout.activity_path_item,null);
        }

        TextView item1 = (TextView)convertView.findViewById(R.id.path_item);

        Path f = path.get(position);
        item1.setText(f.name);

        return convertView;
    }
}
