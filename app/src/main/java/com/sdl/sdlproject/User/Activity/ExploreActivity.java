package com.sdl.sdlproject.User.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sdl.sdlproject.User.Fragments.ExploreFragment;
import com.sdl.sdlproject.R;

import static com.sdl.sdlproject.User.Activity.HomeActivity.closeDrawer;
import static com.sdl.sdlproject.LoginActivity.curr_doc;

public class ExploreActivity extends AppCompatActivity {
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
        HomeActivity.redirectActivity(this, MyBooks.class);
    }
    public void clickGuidelines(View view){
       HomeActivity.redirectActivity(this, GuidelinesActivity.class);
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