package com.sdl.sdlproject.User.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sdl.sdlproject.R;
import com.sdl.sdlproject.User.Activity.ExploreActivity;
import com.sdl.sdlproject.User.Activity.HomeActivity;
import com.sdl.sdlproject.User.Activity.MyBooks;

import static com.sdl.sdlproject.User.Activity.HomeActivity.closeDrawer;
import static com.sdl.sdlproject.LoginActivity.curr_doc;

public class GuidelinesActivity extends AppCompatActivity {
DrawerLayout drawerLayout;
    TextView student_name,guidelines_text;
    public static Spanned guidelines= Html.fromHtml("<pre><br>\n" +
            "o\t<b>Book Issue:</b><br>\n" +
            " &nbsp •\tFE-TE Students        : 3 books for one week<br>\n" +
            "&nbsp •\tBE,ME & Ph.D. students  : 3 books for two weeks<br>\n" +
            " &nbsp •\tBranch Toppers(First Five) : 5 additional books for one semester<br>\n" +
            " &nbsp •\tBook Bank(First come first serve basis) :3 books for whole semester<br>\n" +
            " &nbsp •\tPeriodical Borrowing     : 1 periodical- for overnight<br><br><br>\n" +
            "o\t<b>Book Transaction:</b><br>\n" +
            "&nbsp •\tWorking days    :8:30 AM – 7:30 PM<br>\n" +
            "&nbsp •\tweekly-offs, holidays during exam period : 10:00 AM – 5:00 PM<br>\n" +
            "</pre>\n");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelines);
        drawerLayout=findViewById(R.id.drawer_layout);
        student_name=findViewById(R.id.profile_name);
        guidelines_text=findViewById(R.id.guidelines_text);
        guidelines_text.setText(guidelines);
        String profile_name=curr_doc.get("firstName")+" "+curr_doc.get("lastName");
        student_name.setText(profile_name);
    }
    public  void clickMenu(View view){
        Log.i("clickMenu","inside click menu of guidelines");
        drawerLayout.openDrawer(GravityCompat.START);

    }
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
        finish();
    }
    public void clickMyBooks(View view){
        HomeActivity.redirectActivity(this, MyBooks.class);
    }
    public void clickGuidelines(View view){
      recreate();
    }
    public void clickExplore(View view){
        HomeActivity.redirectActivity(this, ExploreActivity.class);
    }
    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}