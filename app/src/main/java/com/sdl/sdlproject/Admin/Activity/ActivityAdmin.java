package com.sdl.sdlproject.Admin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.sdl.sdlproject.Admin.Adapter.NavigationAdapter;
import com.sdl.sdlproject.Admin.Fragments.AddBookFragment;
import com.sdl.sdlproject.Admin.Fragments.UpdateBookFragment;
import com.sdl.sdlproject.Admin.Fragments.ViewStDetailsFragment;
import com.sdl.sdlproject.Model.NavListItem;
import com.sdl.sdlproject.R;

import java.util.ArrayList;

public class ActivityAdmin extends AppCompatActivity {
    NavigationView navigationView;
    ArrayList<NavListItem> navListItemArrayList = new ArrayList<>();
    private NavigationAdapter adapter;
    ListView mDrawerList;
    Bundle savedInstanceState;
    DrawerLayout drawerLayout;
    MaterialButton nav_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        this.savedInstanceState = savedInstanceState;
        mDrawerList = findViewById(R.id.navlistview);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        nav_btn = findViewById(R.id.menu);
        setNavigationView();


        nav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnMenuClick();
            }
        });

    }


    public void OnMenuClick() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
            View view = navigationView.getHeaderView(0);


        }
    }


    private void setNavigationView() {
        View headerview = navigationView.getHeaderView(0);


        navListItemArrayList.add(new NavListItem("Add Book", R.drawable.ic_outline_add_box_24));
        navListItemArrayList.add(new NavListItem("Update Books", R.drawable.ic_baseline_book_24));
        navListItemArrayList.add(new NavListItem("View Student Details", R.drawable.ic_baseline_student_details_24));
        navListItemArrayList.add(new NavListItem("Sign Out", R.drawable.ic_baseline_exit_to_app_24));
        adapter = new NavigationAdapter(ActivityAdmin.this, navListItemArrayList);
        adapter.setOnItemClickListner(new NavigationAdapter.OnItemClickListner() {
            @Override
            public void switchnavitem(int position) {
                //Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                switchnavitem_withinterface(position);
            }
        });
        mDrawerList.setAdapter(adapter);

    }

    public void switchnavitem_withinterface(int position) {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
//            Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        switch (position) {
            case 0: {
                if (savedInstanceState == null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_admin, new AddBookFragment()).commit();
                }
            }
            break;
            case 1: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_admin, new UpdateBookFragment()).commit();
            }
            break;
            case 2: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_admin, new ViewStDetailsFragment()).commit();
            }
            break;
            case 3: {
                finish();
            }
            break;

        }


    }


}