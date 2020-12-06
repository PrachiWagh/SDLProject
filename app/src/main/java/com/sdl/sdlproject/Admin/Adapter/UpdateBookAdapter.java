package com.sdl.sdlproject.Admin.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sdl.sdlproject.Model.Books;
import com.sdl.sdlproject.R;
import com.sdl.sdlproject.User.Activity.BookItemActivity;
import com.sdl.sdlproject.User.Adapter.BookAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.sdl.sdlproject.LoginActivity.booksList;

public class UpdateBookAdapter extends RecyclerView.Adapter<UpdateBookAdapter.MyViewHolder> {

    public ArrayList<Books> books;
    private Context mContext;


    Updatebooks updatebooks;

    public UpdateBookAdapter(ArrayList<Books> books, Context mContext) {
        this.books = books;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_updatebook, parent, false);
        return new MyViewHolder(view);
    }

    public interface Updatebooks {
        void updateInterface(int position, Map<String, Object> map);

        void deleteInterface(int positon);
    }

    public void setUpdatebooks(Updatebooks updatebooks) {
        this.updatebooks = updatebooks;
    }

    int[] imageArray = new int[]{
            R.drawable.bookimg1,
            R.drawable.bookimg2,
            R.drawable.bookimg3,
            R.drawable.bookimg4,
            R.drawable.bookimg5
    };

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.book_name.setText(books.get(position).getTitle());
        holder.author_name.setText(books.get(position).getAuthor());
        holder.category.setText(books.get(position).getCategory());
        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(imageArray[position%5]));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout title, author, publication, available, totalc;
                AutoCompleteTextView shelf, category;
                Dialog dialog2 = new Dialog(mContext);
                dialog2.setCancelable(false);
                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog2.setContentView(R.layout.dialog_update_card);
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                title = dialog2.findViewById(R.id.title_addBook);
                author = dialog2.findViewById(R.id.author_addBook);
                publication = dialog2.findViewById(R.id.publication_addBook);
                category = dialog2.findViewById(R.id.category_addBook_autocompleteview);
                shelf = dialog2.findViewById(R.id.shelf_addBook_autocompleteview);
                available = dialog2.findViewById(R.id.available_addbook);
                totalc = dialog2.findViewById(R.id.total_addbook);

                title.getEditText().setText(books.get(position).getTitle());
                author.getEditText().setText(books.get(position).getAuthor());
                publication.getEditText().setText(books.get(position).getPublication());
                category.setText(books.get(position).getCategory());
                shelf.setText(books.get(position).getShelf());
                available.getEditText().setText(String.valueOf(books.get(position).getAvailableCopy()));
                totalc.getEditText().setText(String.valueOf(books.get(position).getTotalCopy()));

                ArrayAdapter<CharSequence> categoryadapter = ArrayAdapter.createFromResource(mContext, R.array.categoryArray, android.R.layout.simple_spinner_dropdown_item);
                ArrayAdapter<CharSequence> shelfadapter = ArrayAdapter.createFromResource(mContext, R.array.shelfArray, android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(categoryadapter);
                shelf.setAdapter(shelfadapter);

                Button updatebtn = dialog2.findViewById(R.id.dialog_updatebtn);
                Button cancelbtn = dialog2.findViewById(R.id.dialog_cancel);
                dialog2.show();

                updatebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        DocumentReference ref = db.document("Books/b" + (position + 1));

                        Map<String, Object> map = new HashMap<>();
                        map.put("title", title.getEditText().getText().toString());
                        map.put("author", author.getEditText().getText().toString());
                        map.put("publication", publication.getEditText().getText().toString());
                        map.put("category", category.getText().toString());
                        map.put("shelf", shelf.getText().toString());
                        map.put("availableCopy", Integer.valueOf(available.getEditText().getText().toString()));
                        map.put("totalCopy", Integer.valueOf(totalc.getEditText().getText().toString()));

                        ref.update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("books", "SUCCESSListener");
                                updatebooks.updateInterface(position, map);

                                Toast.makeText(v.getContext(), "SUCCESSListener", Toast.LENGTH_SHORT).show();

                            }
                        });
                        dialog2.cancel();
                        Toast.makeText(mContext, "Updated", Toast.LENGTH_SHORT).show();
                    }
                });

                cancelbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.cancel();
                    }
                });
            }
        });

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder deleteAlert = new AlertDialog.Builder(mContext);
                deleteAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updatebooks.deleteInterface(position);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setMessage("are you sure do you want to delete").create().show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return books.size();
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
