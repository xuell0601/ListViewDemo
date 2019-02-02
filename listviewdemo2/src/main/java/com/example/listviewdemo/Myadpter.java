package com.example.listviewdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by xueliang on 2019/2/2 0002.
 */
public class Myadpter extends BaseAdapter {
    private final ArrayList<Student> data;
    private Context con;
    private ViewHoder viewHoder;

    public Myadpter(ArrayList<Student> students, MainActivity mainActivity) {
        this.con=mainActivity;
        this.data=students;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
               if(convertView==null){
                   viewHoder = new ViewHoder();
                   convertView= View.inflate(con, R.layout.listview, null);

                  viewHoder.tv=(TextView)convertView.findViewById(R.id.tvs);
                   convertView.setTag(viewHoder);
               }else{
                   viewHoder = (ViewHoder) convertView.getTag();
               }
              viewHoder.tv.setText(data.get(position).getTitle());

        return convertView;
    }

    //创建盒子
    class ViewHoder{
           private TextView tv;

    }
}
