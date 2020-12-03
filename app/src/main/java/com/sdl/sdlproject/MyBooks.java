package com.sdl.sdlproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static com.sdl.sdlproject.HomeActivity.closeDrawer;
import static com.sdl.sdlproject.LoginActivity.curr_doc;

public class MyBooks extends AppCompatActivity {
DrawerLayout drawerLayout;
    TextView student_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);
        drawerLayout=findViewById(R.id.drawer_layout);
        student_name=findViewById(R.id.profile_name);
        String profile_name=curr_doc.get("firstName")+" "+curr_doc.get("lastName");
        student_name.setText(profile_name);
    }
    public  void clickMenu(View view){
        Log.i("clickMenu","inside click menu of myBooks");
        drawerLayout.openDrawer(GravityCompat.START); }
    public void clickProfile(View view){
        HomeActivity.closeDrawer(drawerLayout);
    }
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
        recreate();
    }
    public void clickGuidelines(View view){
        HomeActivity.redirectActivity(this,Guidelines.class);
    }
    public void clickExplore(View view){
        HomeActivity.redirectActivity(this,Explore.class);
    }
    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}