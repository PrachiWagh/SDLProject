package com.sdl.sdlproject.Admin.Fragments;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdl.sdlproject.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    TextView news_text;

    Spanned news= Html.fromHtml("<pre><br>\n" +
            "o\t<b>1. Library is planning to host a essay writing competition on eve of Gandhi Jayanti</b><br>\n" +
            "o\t<b>2.Online session on Digital Library</b><br>\n" +
            "</pre>\n");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_home,container,false);


        news_text=view.findViewById(R.id.news_text);
        news_text.setText(news);


        return view;
    }
}
