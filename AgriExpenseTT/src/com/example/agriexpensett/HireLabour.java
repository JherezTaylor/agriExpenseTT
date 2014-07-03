package com.example.agriexpensett;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class HireLabour extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_cycle_redesigned);
		setupInitial();
	}

	private void setupInitial() {
		TextView tv_main=(TextView)findViewById(R.id.tv_mainNew_header);
		tv_main.setText("Hiring Labour");
		ListFragment start=new HireLabourLists();
		Bundle b=new Bundle();
		b.putString("type","workers");
		//b.putString(key, value);
		start.setArguments(b);
		FragmentManager fm=getFragmentManager();
		FragmentTransaction ft=fm.beginTransaction();
		ft.add(R.id.NewCycleListContainer, start);
		ft.commit();
	}
	
	public void appendSub(String extras){
		TextView sub_head=(TextView)findViewById(R.id.tv_mainNew_subheader);
		String s=sub_head.getText().toString();
		s=s+" "+extras;
		sub_head.setText(s);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hire_labour, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */

}