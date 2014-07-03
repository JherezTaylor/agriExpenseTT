package com.example.agriexpensett;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChoosePurchase extends ListFragment {
	ArrayList<localResourcePurchase> pList;
	 String[] ids;
	SQLiteDatabase db;
	DbHelper dbh;
	String type=null;
	int cycleId;
	localCycle curr=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbh=new DbHelper(this.getActivity().getBaseContext());
		db=dbh.getReadableDatabase();
		try{//when called by ViewNavigation we dont need any particular cycle
			curr = getArguments().getParcelable("cycle");
		}catch(Exception e){
		}
		if(curr!=null){
			cycleId=curr.getId();
		}
		
		try {//for when called by ViewNavigation the type will be null so we can see all types of purhases
			type=getArguments().getString("det");
		} catch (Exception e) {
		}
		System.out.println("type: "+type);
		
		pList=new ArrayList<localResourcePurchase>();
		DbQuery.getResourcePurchases(db, dbh, pList, type, null);
		ids=new String[pList.size()];
		Iterator<localResourcePurchase> i=pList.iterator();
		int n=0;
		while(i.hasNext()){
			ids[n]=""+(i.next()).getpId();
			System.out.println(ids[n]);
			n++;
		}
		MyListAdapter myListAdapter = new MyListAdapter(getActivity(), R.layout.purchased_item, ids);
		setListAdapter(myListAdapter);
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		//returns the inflated layout which contains the listview
		return inflater.inflate(R.layout.fragment_choose_purchase, container, false);
	}
		
	
		 
	 public class MyListAdapter extends ArrayAdapter<String> {
	  
		 Context myContext;
		
		  public MyListAdapter(Context context, int textViewResourceId,
		    String[] objects) {
		   super(context, textViewResourceId, objects);
		   myContext = context;
		  }
		
		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
			   //return super.getView(position, convertView, parent);
			   
			   LayoutInflater inflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			   
			   //Get Layout of An Item and Store it in a view
			   View row=inflater.inflate(R.layout.purchased_item, parent, false);
			   //get the elements of that view and set them accordingly
			   TextView header=(TextView)row.findViewById(R.id.tv_pItem_header);
			   TextView det1=(TextView)row.findViewById(R.id.tv_pitem_det1);
			   TextView det2=(TextView)row.findViewById(R.id.tv_pitem_det2);
			   //int pId=Integer.parseInt(ids[position]);
			   localResourcePurchase curr=pList.get(position);
			   header.setText(DbQuery.findResourceName(db, dbh,curr.getResourceId()));
			   det1.setText("Quantity:"+curr.getQty()+" "+curr.getQuantifier());
			   det2.setText("Cost:$"+curr.getCost());
			   ImageView icon=(ImageView)row.findViewById(R.id.icon_pitem_next);
			   
			   //when called by ViewNavigation we dont want the next icon
			   if(type==null)
				   icon.setImageResource(R.drawable.icon_money2);
			   else
				   icon.setImageResource(R.drawable.icon_next);
			   
			   return row;
		  }
	 }
	 
	 @Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			if(type!=null){//when called by ViewNavigation we dont need to register a click for anything
			 	Toast.makeText(getActivity(), getListView().getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
				
				Fragment newFragment =new FragmentPurchaseUse();
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				Bundle b=new Bundle();
				if(curr!=null)
					b.putParcelable("cycleMain", curr);
				b.putString("pId",getListView().getItemAtPosition(position).toString());//passes the id of the purchase
				b.putString("cycleId",""+cycleId);// passes the id of the cycle
				newFragment.setArguments(b);
				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack
				transaction.replace(R.id.useExpenseFrag, newFragment);
				transaction.addToBackStack(null);
				db.close();
				// Commit the transaction
				transaction.commit();
			}
		}

}