package uwi.dcit.AgriExpenseTT;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import fragments.NewPurchaseLists;

public class NewPurchaseRedesign extends ActionBarActivity {
	TextView sub_head;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_cycle_redesigned);
		TextView tv_main=(TextView)findViewById(R.id.tv_mainNew_header);
		tv_main.setText("Purchasing new materials");
		setupUI();
		setupInitialFrag();
	}
	private void setupInitialFrag() {
		Bundle pass=new Bundle();
		pass.putString("type","category");
		FragmentManager fm=getFragmentManager();
		FragmentTransaction ft=fm.beginTransaction();
		ListFragment listfrag=new NewPurchaseLists();
		listfrag.setArguments(pass);
		ft.add(R.id.NewCycleListContainer,listfrag);
		ft.commit();
	}
	
	public static void hideSoftKeyboard(Activity activity) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Context.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
	public void setupUI() {
		View v=findViewById(R.id.container_newcycle);
		TouchL l=new TouchL();
		v.setOnTouchListener(l);
	}
	public class TouchL implements OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(v.getId()!=R.id.et_listReuse_search)
				hideSoftKeyboard(NewPurchaseRedesign.this);
			return false;
		}
	   
   }
	@Override
	public void onBackPressed(){
	    FragmentManager fm = getFragmentManager();
	    if (fm.getBackStackEntryCount() > 0) {
	        Log.i("MainActivity", "popping backstack");
	        fm.popBackStack();
	    } else {
	        Log.i("MainActivity", "nothing on backstack, calling super");
	        super.onBackPressed();  
	    }
	}
	public void replaceSub(String text){
		sub_head=(TextView)findViewById(R.id.tv_mainNew_subheader);
		sub_head.setText(text);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_purchase_redesign, menu);
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

	

}
