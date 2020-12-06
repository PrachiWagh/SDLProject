package com.sdl.sdlproject.Admin.Adapter;

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

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IssueBookAdapter extends RecyclerView.Adapter<IssueBookAdapter.MyViewHolder> {

    public ArrayList<MyBooksItem> books;
    private Context mContext;

    public IssueBookAdapter(ArrayList<MyBooksItem> books, Context mContext) {
        this.books = books;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public IssueBookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_updatebook, parent, false);
        return new IssueBookAdapter.MyViewHolder(view);
    }


    int[] imageArray = new int[]{
            R.drawable.bookimg1,
            R.drawable.bookimg2,
            R.drawable.bookimg3,
            R.drawable.bookimg4,
            R.drawable.bookimg5
    };

    @Override
    public void onBindViewHolder(@NonNull IssueBookAdapter.MyViewHolder holder, int position) {
        holder.layout.setVisibility(View.VISIBLE);
        holder.book_name.setText(books.get(position).getTitle());
        holder.author_name.setVisibility(View.GONE);
        holder.category.setVisibility(View.GONE);
        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(imageArray[(position+4)% 5]));
        holder.deletebtn.setVisibility(View.GONE);
        holder.issuedate.setText(books.get(position).getIssueDate());
        holder.returndate.setText(books.get(position).getReturnDate());

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView book_name, author_name, category, issuedate, returndate;
        ShapeableImageView imageView;
        ImageButton deletebtn;
        LinearLayout layout;

        public MyViewHolder(@NonNull View itemView) {
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
