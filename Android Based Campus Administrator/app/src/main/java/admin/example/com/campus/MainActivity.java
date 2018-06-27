package admin.example.com.campus;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import admin.example.com.a.Average;
import admin.example.com.a.InterestCalculation;
import admin.example.com.a.Mixtures;
import admin.example.com.a.NumberSystem;
import admin.example.com.a.Percentage;
import admin.example.com.a.Permutation;
import admin.example.com.a.Probability;
import admin.example.com.a.Ratios;
import admin.example.com.a.TimSpedDis;
import admin.example.com.a.TimeAndWork;
import admin.example.com.nav.R;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {


	private GoogleSignInClient mGoogleSignInClient;

	private String uname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

		if (user != null) {
			uname = user.getEmail();
			//Toast.makeText(getApplicationContext(),""+uname,Toast.LENGTH_LONG).show();
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		NavigationView navigationView2 = (NavigationView) findViewById(R.id.nav_view2);
		navigationView2.setNavigationItemSelectedListener(this);
		//add this line to display menu1 when the activity is loaded


		View header=navigationView.getHeaderView(0);
		View header1 = navigationView2.getHeaderView(0);
		TextView email = (TextView)header.findViewById(R.id.gmailtxt);
		TextView email1 = (TextView)header1.findViewById(R.id.gmailtxt);
		email.setText(uname);
		email1.setText(uname);

		displaySelectedScreen(R.id.nav_menu);

	}


	@Override
	public void onBackPressed() {
		//Context context=this;
		//ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE) ;
		//Activity ca = ((MainActivity)context.getApplicationContext().getCu)
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else if (drawer.isDrawerOpen(GravityCompat.END)) {
			drawer.closeDrawer(GravityCompat.END);}

			else {
			//Toast.makeText(getApplication(),""+a,Toast.LENGTH_SHORT).show();
			new AlertDialog.Builder(this).setIcon(R.drawable.ic_error).setTitle("Exit")
					.setMessage("Are you sure?")
					.setPositiveButton("yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							Intent intent = new Intent(Intent.ACTION_MAIN);
							intent.addCategory(Intent.CATEGORY_HOME);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);
							finish();
						}
					}).setNegativeButton("no", null).show();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to == the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		final ProgressDialog progress = new ProgressDialog(MainActivity.this);


		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
			startActivity(intent);
			//Toast.makeText(MainActivity.this, " Clicked... ", Toast.LENGTH_SHORT).show();
		}
		if(id==R.id.action_signout){
			/*
			progress.setMessage("Logging Out :) ");
			progress.setTitle("Log Status"); // Setting Title
			progress.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
			progress.show();
			progress.setCancelable(false);
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(100000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					progress.dismiss();
				}
			}).start(); */
			FirebaseAuth.getInstance().signOut();
			//FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
			finish();

			startActivity(new Intent(this,LoginActivity.class));

			Toast.makeText(MainActivity.this, " Logging Out... ", Toast.LENGTH_SHORT).show();
		}


		return super.onOptionsItemSelected(item);
	}

	private void displaySelectedScreen(int itemId) {



		//creating fragment object
		Fragment fragment = null;

		//initializing the fragment object which is selected
		switch (itemId) {
			case R.id.nav_menu:

				fragment = new RecView();

				break;
			case R.id.nav_menu1:
				Bundle bundle = new Bundle();
				bundle.putString("params", "My String data");
				fragment = new NumberSystem();//NumberSystem
				fragment.setArguments(bundle);
				break;
			case R.id.nav_menu2:
				fragment = new Percentage();//Percentages
				break;
			case R.id.nav_menu3:
				fragment = new Ratios();//Ratios
				break;
			case R.id.nav_menu4:
				fragment = new Average();//Averages
				break;
			case R.id.nav_menu5:
				fragment = new Mixtures();//Mixtures
				break;
			case R.id.nav_menu6:
				fragment = new TimeAndWork();//TimeWork
				break;
			case R.id.nav_menu7:
				fragment = new TimSpedDis();//TimeSpeedDis
				break;
			case R.id.nav_menu8:
				fragment = new InterestCalculation();//Interest
				break;
			case R.id.nav_menu9:
				fragment = new Permutation();//Permutation
				break;
			case R.id.nav_menu10:
				fragment = new Probability();//Probability
				break;
			case R.id.nav_menu11:
				fragment = new MenuTutorial();
				break;
			case R.id.nav_menu12:
				Intent i = new Intent(MainActivity.this,SampleContent.class);
				//i.putExtra("Some", "Some thing");
				startActivity(i);
				break;
			case R.id.nav_menu13:
				fragment = new RecView();
				break;

		}

		//replacing the fragment
		if (fragment != null) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.content_frame, fragment);
			ft.commit();
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		//drawer.closeDrawer(GravityCompat.START);

		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else if (drawer.isDrawerOpen(GravityCompat.END)) {
			drawer.closeDrawer(GravityCompat.END);}
	}


	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {

		//calling the method displayselectedscreen and passing the id of selected menu
		displaySelectedScreen(item.getItemId());
		//make this method blank
		return true;
	}


}
