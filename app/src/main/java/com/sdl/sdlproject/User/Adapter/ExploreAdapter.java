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

import com.sdl.sdlproject.Model.Books;
import com.sdl.sdlproject.R;
import com.sdl.sdlproject.User.Activity.BookItemActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.MyViewHolder> {
    public static ArrayList<Books> books2;
    private Context mContext;

    public ExploreAdapter(ArrayList<Books> books, Context mContext) {
        this.books2 = books;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_updatebook, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public int getItemCount() {
        return books2.size();
    }

    int[] imageArray = new int[]{
            R.drawable.bookimg1,
            R.drawable.bookimg2,
            R.drawable.bookimg3,
            R.drawable.bookimg4,
            R.drawable.bookimg5
    };



    @Override
    public void onBindViewHolder(@NonNull ExploreAdapter.MyViewHolder holder, int position) {
        holder.book_name.setText(books2.get(position).getTitle());
        holder.author_name.setText(books2.get(position).getAuthor());
        holder.category.setText(books2.get(position).getCategory());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookItemActivity.class);
                intent.putExtra("position", position);

                mContext.startActivity(intent);
            }
        });
        holder.deletebtn.setVisibility(View.GONE);
        holder.bookImg.setImageDrawable(mContext.getResources().getDrawable(imageArray[(position)% 5]));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView book_name, author_name, category;
        SlidingDrawer slidingDrawer;
        ImageView bookImg;
        ImageButton deletebtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_name = itemView.findViewById(R.id.book_name_update);
            author_name = itemView.findViewById(R.id.author_name_update);
            category = itemView.findViewById(R.id.category_update);
            deletebtn = itemView.findViewById(R.id.delete_book);
            bookImg = itemView.findViewById(R.id.imageview_update);
            // slidingDrawer=itemView.findViewById(R.id.sliding_drawer);
            //dropHandle=itemView.findViewById(R.id.handle1);
        }
    }

    public void updateBooks(ArrayList<Books> newBooks) {
        books2 = new ArrayList<>();
        books2.addAll(newBooks);
        notifyDataSetChanged();
    }
}
