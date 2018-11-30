package com.middlecurved.custombottomnavigationbar;

import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MiddleCurvedBottomNavigationBar curvedBottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        curvedBottomNavigationBar = findViewById(R.id.bottombar);

        curvedBottomNavigationBar.setMenuIcons(R.drawable.ic_archive_black_24dp,R.drawable.ic_archive_black_24dp, R.drawable.ic_archive_black_24dp, R.drawable.ic_archive_black_24dp);

        curvedBottomNavigationBar.setIconSize(25);

        curvedBottomNavigationBar.setBarColor(R.color.colorPrimaryDark);

        
        curvedBottomNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                
                switch (item.getItemId()){
                    case R.id.first:
                        Toast.makeText(MainActivity.this, "First clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.second:
                        Toast.makeText(MainActivity.this, "Second clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.third:
                        Toast.makeText(MainActivity.this, "third clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.fourth:
                        Toast.makeText(MainActivity.this, "Fourth clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
                
                return true;
            }
        });








    }
}
