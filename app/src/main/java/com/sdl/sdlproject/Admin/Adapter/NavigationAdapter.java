package com.sdl.sdlproject.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdl.sdlproject.Model.NavListItem;
import com.sdl.sdlproject.R;


import java.util.ArrayList;

public class NavigationAdapter extends BaseAdapter {


    Context mContext;
    ArrayList<NavListItem> mNavItems;
    public OnItemClickListner onItemClickListner;

    public NavigationAdapter(Context mContext, ArrayList<NavListItem> mNavItems) {
        this.mContext = mContext;
        this.mNavItems = mNavItems;
    }

    @Override
    public int getCount() {
        return mNavItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mNavItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public interface OnItemClickListner {
        void switchnavitem(int position);
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        NavListItem navListItem = mNavItems.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_navigation, null);
        } else {
            view = convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.switchnavitem(position);
            }
        });


        TextView titleView = (TextView) view.findViewById(R.id.navitemmaintext);
        ImageView iconView = (ImageView) view.findViewById(R.id.navimage);

        iconView.setImageResource(navListItem.getmIcon());
        titleView.setText(navListItem.getmTitle());

        return view;
    }
}
