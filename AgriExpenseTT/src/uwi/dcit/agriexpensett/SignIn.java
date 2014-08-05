package uwi.dcit.AgriExpenseTT;

import helper.CloudInterface;
import helper.DbHelper;
import helper.DbQuery;
import helper.Sync;

import java.util.ArrayList;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.agriexpensett.upaccendpoint.model.UpAcc;

public class SignIn {
	Context context;
	SQLiteDatabase db;
	DbHelper dbh;
	Activity activity;
	public SignIn(Activity activity,Context ctx){
		this.context=ctx;
		dbh=new DbHelper(context);
		db=dbh.getReadableDatabase();
		this.activity=activity;
	}
	public SignIn(SQLiteDatabase db,DbHelper dbh,Activity activity,Context ctx){
		this.context=ctx;
		this.db=db;
		this.dbh=dbh;
		this.activity=activity;
	}
	public void signIn(){
		UpAcc acc=isExisting();
		if(acc==null){
			accSetUp();
		}else{
			ContentValues cv=new ContentValues();
			if(acc.getSignedIn()==1){
				cv.put(DbHelper.UPDATE_ACCOUNT_SIGNEDIN, 0);
				((MainMenu)activity).toggleSignIn();
			}else if(acc.getSignedIn()==0){
				cv.put(DbHelper.UPDATE_ACCOUNT_SIGNEDIN, 1);
				((MainMenu)activity).toggleSignIn();
				initialSignIn(acc.getAcc());
			}
			db.update(DbHelper.TABLE_UPDATE_ACCOUNT, cv, DbHelper.UPDATE_ACCOUNT_ID+"=1",null);
		}
	}
	public void accSetUp(){
		System.out.println("sign in mc");
		ArrayList<String> accs=new ArrayList<String>();
		populateAcc(accs);
		if(accs.isEmpty()){
			Toast.makeText(context, "no accounts", Toast.LENGTH_SHORT).show();
			noAccs();
			return;
		}
			
		final CharSequence[] items= new CharSequence[accs.size()]; int i=0;
		for(String k:accs)
			items[i]=accs.get(i++);
		
	    AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    builder.setTitle("Select Account");
	    builder.setItems(items, new DialogInterface.OnClickListener() {
	        @Override
			public void onClick(DialogInterface dialog, int item) {
	            String namespace=convertString((items[item].toString()));
	            Toast.makeText(context, "signed in", Toast.LENGTH_SHORT).show();
	            initialSignIn(namespace);
	        }
	    }).show();
	}
	private void initialSignIn(final String namespace){
		
		class setup extends AsyncTask<Void, Void, UpAcc>{
			String namespace;
			public setup(String namespace){
				this.namespace=namespace;
			}
			@Override
			protected UpAcc doInBackground(Void... params) {
				CloudInterface cloudIF = new CloudInterface(context, db, dbh);
				UpAcc cloudAcc=cloudIF.getUpAcc(namespace);
				return cloudAcc;
			}

			@Override
			protected void onPostExecute(UpAcc cloudAcc) {
				Sync sync=new Sync(db, dbh, context);
				sync.start(namespace, cloudAcc);
				super.onPostExecute(cloudAcc);
			}
			
		}
		new setup(namespace).execute();
	}
	private void noAccs(){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setTitle("No accounts available");
		builder.setMessage("There are no accounts available to sign in with,"
				+ " please go to your phone's settings and create and account");
		builder.setNeutralButton("ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				
			}
		});
		builder.show();
	}
	
	private void populateAcc(ArrayList<String> accs){
		Account[] accounts = AccountManager.get(context).getAccounts();
		for(Account a:accounts){
		  accs.add(a.name);
		  System.out.println(a.name);
		}
	}
	private String convertString(String str){
		int len=str.length();
		String newStr="_";
		for(int i=0;i<len;i++){
			if(isChar(str.charAt(i)))
					newStr+=str.charAt(i);
			else if(str.charAt(i)=='@')
				break;
		}
		return newStr;
	}
	private boolean isChar(char c){
		if((c>='a'&&c<='z')||(c>='A'&&c<='Z'))
			return true;
		return false;
	}
	public UpAcc isExisting(){
		UpAcc acc=DbQuery.getUpAcc(db);
		if(acc.getAcc()==null || acc.getAcc().equals(""))
			return null;
		System.out.println("account exists !!!!!!!!");
		return acc;
	}
}	
