package com.sdl.sdlproject.Admin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.NumberPicker;

import com.sdl.sdlproject.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddBookFragment extends Fragment {
    AutoCompleteTextView categoryAutocomplt,shelfAutocomplt;
    NumberPicker numberpicker_available,numberpicker_total;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_book,container,false);
        categoryAutocomplt = view.findViewById(R.id.category_addBook_autocompleteview);
        shelfAutocomplt = view.findViewById(R.id.shelf_addBook_autocompleteview);

        ArrayAdapter<CharSequence> categoryadapter = ArrayAdapter.createFromResource(getContext(), R.array.categoryArray, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> shelfadapter = ArrayAdapter.createFromResource(getContext(), R.array.shelfArray, android.R.layout.simple_spinner_dropdown_item);
        categoryAutocomplt.setAdapter(categoryadapter);
        shelfAutocomplt.setAdapter(shelfadapter);


        return view;
    }
}
