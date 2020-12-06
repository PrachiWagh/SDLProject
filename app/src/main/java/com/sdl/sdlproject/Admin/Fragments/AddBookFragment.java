package com.sdl.sdlproject.Admin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sdl.sdlproject.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import static com.sdl.sdlproject.LoginActivity.booksize;

public class AddBookFragment extends Fragment {
    AutoCompleteTextView categoryAutocomplt,shelfAutocomplt;
    NumberPicker numberpicker_available,numberpicker_total;
    EditText title_text,author_text,publication_text,available_text,total_text;
    Button add_to_databse;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_book,container,false);
        categoryAutocomplt = view.findViewById(R.id.category_addBook_autocompleteview);
        shelfAutocomplt = view.findViewById(R.id.shelf_addBook_autocompleteview);
        title_text=view.findViewById(R.id.title_addBook);
        author_text=view.findViewById(R.id.author_addBook);
        publication_text=view.findViewById(R.id.publication_addBook);
        available_text=view.findViewById(R.id.available_addBook);
        total_text=view.findViewById(R.id.total_addBook);
        add_to_databse=view.findViewById(R.id.add_to_database);
        ArrayAdapter<CharSequence> categoryadapter = ArrayAdapter.createFromResource(getContext(), R.array.categoryArray, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> shelfadapter = ArrayAdapter.createFromResource(getContext(), R.array.shelfArray, android.R.layout.simple_spinner_dropdown_item);
        categoryAutocomplt.setAdapter(categoryadapter);
        shelfAutocomplt.setAdapter(shelfadapter);
        FirebaseFirestore db_addBook=FirebaseFirestore.getInstance();
        add_to_databse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> data=new HashMap<>();
                data.put("title",title_text.getText().toString());
                data.put("author",author_text.getText().toString());
                data.put("publication",publication_text.getText().toString());
                data.put("category",categoryAutocomplt.getText().toString());
                data.put("shelf",shelfAutocomplt.getText().toString());
                data.put("availableCopy",Integer.parseInt(available_text.getText().toString()));
                data.put("totalCopy",Integer.parseInt(total_text.getText().toString()));
                db_addBook.collection("Books").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), "Book Added Successfully", Toast.LENGTH_SHORT).show();
                        booksize+=1;
                    }
                });/*addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to add book", Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });

        return view;
    }
}
