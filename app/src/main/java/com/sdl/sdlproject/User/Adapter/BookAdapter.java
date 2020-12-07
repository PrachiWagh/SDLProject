package com.sdl.sdlproject.User.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.sdl.sdlproject.User.Activity.BookItemActivity;
import com.sdl.sdlproject.Model.Books;
import com.sdl.sdlproject.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {
    public static ArrayList<Books> books;
    private Context mContext;

    public BookAdapter(ArrayList<Books> books, Context mContext) {
        this.books = books;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_updatebook, parent, false);
        return new MyViewHolder(view);

    }
    int[] imageArray = new int[]{
            R.drawable.bookimg1,
            R.drawable.bookimg2,
            R.drawable.bookimg3,
            R.drawable.bookimg4,
            R.drawable.bookimg5
    };
    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.MyViewHolder holder, int position) {
        holder.book_name.setText(books.get(position).getTitle());
        holder.author_name.setText(books.get(position).getAuthor());
        holder.category.setText(books.get(position).getCategory());
        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(imageArray[position % 5]));

//        holder.deletebtn.setVisibility(View.GONE);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView book_name, author_name, category;
        ShapeableImageView imageView;
        ImageButton deletebtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_name = itemView.findViewById(R.id.book_name_update);
            author_name = itemView.findViewById(R.id.author_name_update);
            category = itemView.findViewById(R.id.category_update);
            imageView = itemView.findViewById(R.id.imageview_update);
            deletebtn = itemView.findViewById(R.id.delete_book);

        }
    }


}
