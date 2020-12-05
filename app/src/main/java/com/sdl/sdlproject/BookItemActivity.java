package com.sdl.sdlproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.sdl.sdlproject.BookAdapter.books;
import static com.sdl.sdlproject.LoginActivity.resBooksId;
import static com.sdl.sdlproject.LoginActivity.reservedBooks;

public class BookItemActivity extends AppCompatActivity {
TextView title,author,publication,category,totalNoOfCopy,availableCopy,shelf;
Button notify_me;
int position1=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_item);
        title=findViewById(R.id.detail_title);
        author=findViewById(R.id.detail_author);
        category=findViewById(R.id.detail_category);
        publication=findViewById(R.id.detail_publication);
        totalNoOfCopy=findViewById(R.id.detail_tot_copy);
        availableCopy=findViewById(R.id.detail_avail_copy);
        shelf=findViewById(R.id.detail_shelf);
        notify_me=findViewById(R.id.notify_me);
        position1=getIntent().getIntExtra("position",-1);
        Books book_item=books.get(position1);
        title.setText("Title: "+book_item.getTitle());
        author.setText("Author: "+book_item.getAuthor());
        category.setText("Category: "+book_item.getCategory());
        publication.setText("Publication: "+book_item.getPublication());
        totalNoOfCopy.setText("Total copies: "+book_item.getTotalCopy());
        availableCopy.setText("Available copies: "+book_item.getAvailableCopy());
        shelf.setText("Shelf: "+book_item.getShelf());
        if(book_item.getAvailableCopy()==0 ){

            notify_me.setVisibility(View.VISIBLE);
        }else{
            notify_me.setVisibility(View.GONE);
        }
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        String userId = sp.getString("user_id", "C2K18105847");

        notify_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag=0;
                for(Books book:reservedBooks){
                    if(book_item.getTitle().equals(book.getTitle()) && book_item.getAuthor().equals(book.getAuthor())){
                        flag=1;
                        break;
                    }
                }
                if(flag==0) {
                    reservedBooks.add(book_item);
                    FirebaseFirestore db1 = FirebaseFirestore.getInstance();
                    db1.collection("Books").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    if (doc.get("title").equals(book_item.getTitle())) {
                                        String book_id = doc.getId();
                                        resBooksId.add(book_id);
                                        db1.collection("Students").document(userId).update("reservedBooks", resBooksId);
                                        break;
                                    }
                                }
                            }
                        }

                    });
                }
            }
        });

    }
}