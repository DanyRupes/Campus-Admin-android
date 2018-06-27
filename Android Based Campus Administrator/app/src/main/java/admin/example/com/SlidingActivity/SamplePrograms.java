package admin.example.com.SlidingActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import admin.example.com.campus.DetailsActivity;
import admin.example.com.nav.R;

public class SamplePrograms extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private  String message;
    // private int icon = R.drawable.home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        message = intent.getStringExtra("message");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        addTabs(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



    }


    private void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        switch (message){
            case "one":
                adapter.addFrag(new Tablayout_inout_C(), "C");
                adapter.addFrag(new Tablayout_inout_java(), "Java");
                adapter.addFrag(new Tablayout_inout_P(), "Python");
                viewPager.setAdapter(adapter);
                break;

            case "two":
                //ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
                adapter.addFrag(new Tablayout_OAE_C(), "C");
                adapter.addFrag(new Tablayout_OAE_java(), "Java");
                adapter.addFrag(new Tablayout_OAE_P(), "Python");
                viewPager.setAdapter(adapter);
                break;

            case "three":
                adapter.addFrag(new Tablayout_CDS_C(), "C");
                adapter.addFrag(new Tablayout_CDS_java(), "Java");
                adapter.addFrag(new Tablayout_CDS_P(), "Python");
                viewPager.setAdapter(adapter);
                break;

            case "five":
                adapter.addFrag(new Tablayout_CS_C(), "C");
                adapter.addFrag(new Tablayout_CS_java(), "Java");
                adapter.addFrag(new Tablayout_CS_P(), "Python");
                viewPager.setAdapter(adapter);
                break;

            case "six":
                adapter.addFrag(new Tablayout_FAP_C(), "C");
                adapter.addFrag(new Tablayout_FAP_java(), "Java");
                adapter.addFrag(new Tablayout_FAP_P(), "Python");
                viewPager.setAdapter(adapter);
                break;

            case "four":
                adapter.addFrag(new Tablayout_AAL_C(), "C");
                adapter.addFrag(new Tablayout_AAL_java(), "Java");
                adapter.addFrag(new Tablayout_AAL_P(), "Python");
                viewPager.setAdapter(adapter);
                break;
        }

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(SamplePrograms.this, DetailsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
