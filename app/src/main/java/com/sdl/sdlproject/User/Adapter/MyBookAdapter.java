package com.sdl.sdlproject.User.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.sdl.sdlproject.Model.MyBooksItem;
import com.sdl.sdlproject.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MyBookAdapter extends  RecyclerView.Adapter<MyBookAdapter.MyViewHolder1>{

    private Context mContext;
    public ArrayList<MyBooksItem> books;

    public MyBookAdapter(ArrayList<MyBooksItem> books, Context mContext) {
        this.books = books;
        this.mContext = mContext;
    }

    int[] imageArray = new int[]{
            R.drawable.bookimg1,
            R.drawable.bookimg2,
            R.drawable.bookimg3,
            R.drawable.bookimg4,
            R.drawable.bookimg5
    };

    @NonNull
    @Override
    public MyBookAdapter.MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_updatebook,parent,false);
        return new MyBookAdapter.MyViewHolder1(view);

    }
    @Override
    public int getItemCount() {
        return books.size();
    }
    @Override
    public void onBindViewHolder(@NonNull MyBookAdapter.MyViewHolder1 holder, int position) {
        String date_today= Calendar.YEAR+"-"+Calendar.MONTH+"-"+Calendar.DATE;
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        holder.layout.setVisibility(View.VISIBLE);
        holder.book_name.setText(books.get(position).getTitle());
        holder.author_name.setVisibility(View.GONE);
        holder.category.setVisibility(View.GONE);
        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(imageArray[(position+4)% 5]));
        holder.deletebtn.setVisibility(View.GONE);
        holder.issuedate.setText(books.get(position).getIssueDate());
        holder.returndate.setText(books.get(position).getReturnDate());

    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder{
        TextView book_name, author_name, category, issuedate, returndate;
        ShapeableImageView imageView;
        ImageButton deletebtn;
        LinearLayout layout;


        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);
            book_name = itemView.findViewById(R.id.book_name_update);
            author_name = itemView.findViewById(R.id.author_name_update);
            category = itemView.findViewById(R.id.category_update);
            imageView = itemView.findViewById(R.id.imageview_update);
            deletebtn = itemView.findViewById(R.id.delete_book);
            layout = itemView.findViewById(R.id.date_layout);
            issuedate = itemView.findViewById(R.id.issuedate_text);
            returndate = itemView.findViewById(R.id.returndate_text);
        }
    }
}
