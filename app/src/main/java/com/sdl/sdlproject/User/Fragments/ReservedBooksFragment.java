package com.sdl.sdlproject.User.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdl.sdlproject.R;
import com.sdl.sdlproject.User.Adapter.BookAdapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.sdl.sdlproject.LoginActivity.myBooksItems;
import static com.sdl.sdlproject.LoginActivity.reservedBooks;
import static com.sdl.sdlproject.User.Fragments.MyBooksFragment.late_fee;

public class ReservedBooksFragment extends Fragment {
    RecyclerView recyclerView2;
    BookAdapter reservedBooksAdapter;
    TextView lateFee;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserved_books, container, false);
        recyclerView2 = view.findViewById(R.id.recyclerView_reservedBooks);
        //  recyclerView.setHasFixedSize(true);
        Log.d("MusicFiles", String.valueOf(myBooksItems.size()));
        if (!(reservedBooks.size() < 1)) {
            reservedBooksAdapter= new BookAdapter(reservedBooks,getContext());
            recyclerView2.setAdapter(reservedBooksAdapter);
            recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }
        lateFee=view.findViewById(R.id.lateFee);
        lateFee.setText("Late Fee: ₹"+late_fee);

        return view;
    }
}
