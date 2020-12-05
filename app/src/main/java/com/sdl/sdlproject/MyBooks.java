package com.sdl.sdlproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

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
        initViewPager();
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
    private void initViewPager() {
        ViewPager viewPager=findViewById(R.id.view_pager);
        TabLayout tabLayout=findViewById(R.id.tab_layout);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new MyBooksFragment(),"Issued Books");
        viewPagerAdapter.addFragment(new ReservedBooksFragment(),"Reserved Books");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;
        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            fragments=new ArrayList<>();
            titles=new ArrayList<>();
        }
        public void addFragment(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}