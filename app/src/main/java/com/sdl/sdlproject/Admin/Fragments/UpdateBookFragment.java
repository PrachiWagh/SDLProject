package com.sdl.sdlproject.Admin.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sdl.sdlproject.Admin.Adapter.UpdateBookAdapter;
import com.sdl.sdlproject.Model.Books;
import com.sdl.sdlproject.R;
import com.sdl.sdlproject.User.Adapter.BookAdapter;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.sdl.sdlproject.LoginActivity.booksList;
import static com.sdl.sdlproject.LoginActivity.getBooks;

public class UpdateBookFragment extends Fragment {
    RecyclerView recyclerView;
    UpdateBookAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_book, container, false);


        recyclerView = view.findViewById(R.id.recyclerview_update);
        recyclerView.setHasFixedSize(true);
        if (!(booksList.size() < 1)) {
            adapter = new UpdateBookAdapter(booksList, getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }
        adapter.setUpdatebooks(new UpdateBookAdapter.Updatebooks() {
            @Override
            public void updateInterface(int position, Map<String, Object> map) {
                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
//                getBooks();
                booksList.remove(position);
                booksList.add(position, new Books((String) map.get("title"), (String) map.get("author"), (String) map.get("category"), (String) map.get("publication"), (String) map.get("shelf"), Integer.valueOf((int) map.get("totalCopy")), Integer.valueOf((int) map.get("availableCopy"))));

                adapter.notifyDataSetChanged();

            }

            @Override
            public void deleteInterface(int positon) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
//                db.collection("Books").document("b"+(positon + 1)).addSnapshotListener(new ).delete();
                booksList.remove(positon);
                adapter.notifyDataSetChanged();

                db.collection("Books").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("books",String.valueOf(positon));
                        queryDocumentSnapshots.getDocuments().get(positon).getReference()
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("books","Deleted Successfully");
                            }
                        });
                    }
                });



            }
        });


        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return view;
    }
}
