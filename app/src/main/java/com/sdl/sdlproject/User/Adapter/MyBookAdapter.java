package com.sdl.sdlproject.User.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdl.sdlproject.Model.MyBooksItem;
import com.sdl.sdlproject.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyBookAdapter extends  RecyclerView.Adapter<MyBookAdapter.MyViewHolder1>{

    private Context mContext;
    public ArrayList<MyBooksItem> mybooks;

    public MyBookAdapter(ArrayList<MyBooksItem> books, Context mContext) {
        this.mybooks = books;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyBookAdapter.MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.my_book_item,parent,false);
        return new MyBookAdapter.MyViewHolder1(view);

    }
    @Override
    public int getItemCount() {
        return mybooks.size();
    }
    @Override
    public void onBindViewHolder(@NonNull MyBookAdapter.MyViewHolder1 holder, int position) {
        String date_today= Calendar.YEAR+"-"+Calendar.MONTH+"-"+Calendar.DATE;
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        holder.book_name.setText(mybooks.get(position).getTitle());
        holder.issue_date.setText("Issue Date: "+mybooks.get(position).getIssueDate());
        holder.return_date.setText("Return Date: "+mybooks.get(position).getReturnDate());

        try {
            Date today=df.parse(date_today);
            Date ret=df.parse(mybooks.get(position).getReturnDate());
            if(ret.compareTo(today)>0){
                holder.return_date.setTextColor(Color.RED);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder{
        TextView book_name,issue_date,return_date;



        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);
            book_name=itemView.findViewById(R.id.mybook_name);
            issue_date=itemView.findViewById(R.id.issue_date);
            return_date=itemView.findViewById(R.id.return_date);
            // slidingDrawer=itemView.findViewById(R.id.sliding_drawer);
            //dropHandle=itemView.findViewById(R.id.handle1);
        }
    }
}
