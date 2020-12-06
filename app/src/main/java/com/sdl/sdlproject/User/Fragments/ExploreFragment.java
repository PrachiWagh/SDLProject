package com.sdl.sdlproject.User.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.sdl.sdlproject.R;
import com.sdl.sdlproject.User.Adapter.BookAdapter;
import com.sdl.sdlproject.Model.Books;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.sdl.sdlproject.LoginActivity.booksList;

public class ExploreFragment extends Fragment implements SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    SearchView searchView;
    DrawerLayout drawerLayout;
    TextView student_name;
   public BookAdapter bookAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        //  recyclerView.setHasFixedSize(true);
        Log.d("MusicFiles", String.valueOf(booksList.size()));
        if (!(booksList.size() < 1)) {
            bookAdapter = new BookAdapter( booksList,getContext());
            recyclerView.setAdapter(bookAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }
        searchView=view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);

        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        System.out.println("Inside query text listener");
        String userInput=newText.toLowerCase();
        ArrayList<Books> newBooks=new ArrayList<>();
        for(Books book:booksList){

            if(book.getTitle().toLowerCase().contains(userInput) || book.getAuthor().toLowerCase().contains(userInput) || book.getCategory().toLowerCase().contains(userInput)){
                newBooks.add(book);
                System.out.println(book.getTitle());
                Log.d("newbook",book.getTitle());
            }
        }
        bookAdapter.updateBooks(newBooks);
        return true;
    }
}
