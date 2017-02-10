package com.example.user.e_gigi;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView navView;
    private Toolbar appbar;
    private long lastPress=0;
    private long timeLimit=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout =(DrawerLayout)findViewById(R.id.drawer_layout);

        navView = (NavigationView)findViewById(R.id.navview);

        navView.setNavigationItemSelectedListener(
               new NavigationView.OnNavigationItemSelectedListener(){
                   @Override
                   public boolean onNavigationItemSelected(MenuItem menuItem) {
                    boolean fragmentTransaction = false;
                    Fragment fragment=null;

                switch (menuItem.getItemId()) {
                    case R.id.menu_section_1:
                        fragment = new FragmentProductos();
                        fragmentTransaction = true;
                        break;
                   case R.id.menu_section_2:
                        fragment = new FragmentPedidos();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_section_3:
                        fragment = new ECommerce();
                        fragmentTransaction = true;
                        break;

                }
                if(fragmentTransaction) {

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .addToBackStack(null)
                            .commit();

                    menuItem.setChecked(true);
                    getSupportActionBar().setTitle(menuItem.getTitle());
                }

                mDrawerLayout.closeDrawers();

                return true;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
         return super.onOptionsItemSelected(item);

    }
     public void onBackPressed(){
         Toast toastBack=Toast.makeText(this,"Pulse de nuevo para salir.", Toast.LENGTH_SHORT);
         long currentTime = System.currentTimeMillis();


         if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
             mDrawerLayout.closeDrawer(GravityCompat.START);
         }else if(getFragmentManager().getBackStackEntryCount()>0) {
             getFragmentManager().popBackStack();
         }else if (currentTime-lastPress>timeLimit){
                 toastBack.show();
                 lastPress=currentTime;
         }else{
                 toastBack.cancel();
                 super.onBackPressed();
             }
         }
     }

