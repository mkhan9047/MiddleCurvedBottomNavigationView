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

        curvedBottomNavigationBar.setNewMenuItemSelectedListener(new MiddleCurvedBottomNavigationBar.SetMenuItemSelectedListener() {
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.first:
                        Toast.makeText(MainActivity.this, "first one clicked!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.second:
                        Toast.makeText(MainActivity.this, "first one clicked!", Toast.LENGTH_SHORT).show();
                        break;
                }

                return true;
            }
        });








    }
}
