package com.sdl.sdlproject.User.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.TextView;

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.itemreservedbook, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.MyViewHolder holder, int position) {
        holder.book_name.setText(books.get(position).getTitle());
        holder.author_name.setText(books.get(position).getAuthor());
        holder.category.setText(books.get(position).getCategory());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookItemActivity.class);
                intent.putExtra("position", position);

                mContext.startActivity(intent);
            }
        });

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView book_name, author_name, category;
        SlidingDrawer slidingDrawer;
        ImageView dropHandle;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_name = itemView.findViewById(R.id.book_name);
            author_name = itemView.findViewById(R.id.author_name);
            category = itemView.findViewById(R.id.category);
            // slidingDrawer=itemView.findViewById(R.id.sliding_drawer);
            //dropHandle=itemView.findViewById(R.id.handle1);
        }
    }

    public void updateBooks(ArrayList<Books> newBooks) {
        books = new ArrayList<>();
        books.addAll(newBooks);
        notifyDataSetChanged();
    }
}
