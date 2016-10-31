package com.springs.springs.page;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.springs.springs.R;
import com.synnapps.carouselview.CarouselView;

import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    CarouselView carouselView;
    private Toolbar toolbar;
    private BottomBar bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
//        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
//        setSupportActionBar(toolbar);


        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {



                if (tabId == R.id.tab_newsletter) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.

                  //  n.setBadgeCount(5);
//
//                    Toast.makeText(getApplicationContext(), "selected! NewsLetter", Toast.LENGTH_SHORT).show();
                    NewsLetter newsletter = new NewsLetter();
                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.tabcontent, newsletter);
                    transaction.commit();

                }
                else if(tabId == R.id.tab_cart){
                    Toast.makeText(getApplicationContext(), "selected! Cart", Toast.LENGTH_SHORT).show();
                    ShoppingCart shoppingCart = new ShoppingCart();
                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.tabcontent, shoppingCart);
                    transaction.commit();


                }
                else if(tabId == R.id.tab_home){

                    Toast.makeText(getApplicationContext(), "selected! Home", Toast.LENGTH_SHORT).show();
                    Home home = new Home();
                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.tabcontent, home);
                    transaction.commit();

                }
                else if(tabId == R.id.tab_books){
                    Toast.makeText(getApplicationContext(), "selected! Books", Toast.LENGTH_SHORT).show();
                    Library library = new Library();
                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.tabcontent, library);
                    transaction.commit();

                }
                else if(tabId == R.id.tab_profile){
                    Toast.makeText(getApplicationContext(), "selected! Profile", Toast.LENGTH_SHORT).show();
                    Profile profile = new Profile();
                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.tabcontent, profile);
                    transaction.commit();

                }

            }
        });
        bottomBar.selectTabAtPosition(2);


    }

}
