package com.sdl.sdlproject.User.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sdl.sdlproject.LoginActivity;
import com.sdl.sdlproject.R;

import static com.sdl.sdlproject.LoginActivity.curr_doc;

public class HomeActivity extends AppCompatActivity {
DrawerLayout drawerLayout;
TextView student_name,news_text;
Spanned news= Html.fromHtml("<pre><br>\n" +
        "o\t<b>1. Library is planning to host a essay writing competition on eve of Gandhi Jayanti</b><br>\n" +
        "o\t<b>2.Online session on Digital Library</b><br>\n" +
        "</pre>\n");
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout=findViewById(R.id.drawer_layout);
        student_name=findViewById(R.id.profile_name);
        news_text=findViewById(R.id.news_text);
        news_text.setText(news);
        String profile_name=curr_doc.get("firstName")+" "+curr_doc.get("lastName");
        student_name.setText(profile_name);
    }
    public  void clickMenu(View view){
        Log.i("clickMenu","inside click menu of HomeActivity");
        openDrawer(drawerLayout);

    }

    public static void openDrawer(DrawerLayout drawerLayout1) {
        drawerLayout1.openDrawer(GravityCompat.START);
    }
    public void clickProfile(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void clickHome(View view){
        redirectActivity(this, HomeActivity.class);
    }
    public void clickExplore(View view){
        redirectActivity(this, ExploreActivity.class);
    }
    public void clickMyBooks(View view){
        redirectActivity(this, MyBooks.class);
    }
    public void clickGuidelines(View view){
        redirectActivity(this, GuidelinesActivity.class);
    }
    public void clickLogout(View view){
        logout(this);
        SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("remember", "false");
        editor.apply();
    }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle("logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                redirectActivity(activity, LoginActivity.class);

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public static void redirectActivity(Activity activity,Class aclass) {
        Intent intent=new Intent(activity,aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}