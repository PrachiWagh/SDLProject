package com.sdl.sdlproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.sdl.sdlproject.HomeActivity.closeDrawer;
import static com.sdl.sdlproject.LoginActivity.booksList;
import static com.sdl.sdlproject.LoginActivity.curr_doc;
import static java.security.AccessController.getContext;

public class Explore extends AppCompatActivity {
DrawerLayout drawerLayout;
    TextView student_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        drawerLayout=findViewById(R.id.drawer_layout);
        student_name=findViewById(R.id.profile_name);
        String profile_name=curr_doc.get("firstName")+" "+curr_doc.get("lastName");
        student_name.setText(profile_name);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, ExploreFragment.class, null)
                    .commit();
        }

    }




    public  void clickMenu(View view){
        Log.i("clickMenu","inside click menu of explore");
        drawerLayout.openDrawer(GravityCompat.START); }
    public void clickProfile(View view){ HomeActivity.closeDrawer(drawerLayout); }
    public void clickHome(View view){
        HomeActivity.redirectActivity(this,HomeActivity.class);
    }
    public void clickLogout(View view){
        HomeActivity.logout(this);
        SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("remember", "false");
        editor.apply();
    }
    public void clickMyBooks(View view){
        HomeActivity.redirectActivity(this,MyBooks.class);
    }
    public void clickGuidelines(View view){
       HomeActivity.redirectActivity(this,Guidelines.class);
    }
    public  void clickExplore(View view){
        recreate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

}