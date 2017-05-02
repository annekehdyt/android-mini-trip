package ash.example.mini_trip_project;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class TripList extends Activity implements OnItemClickListener {

	TextView tv; 

	ArrayList<ChildItem> items;
	ArrayList<ParentItem> items_parent;
	TripListAdapter adapter;
	ListView tripList;
	Intent data;
	int test;
	
	String dest;
	String loc;
	String date;
	String time;
	String title;
	int img;
	int position;
	int key;
	
	DBHelper dbHelper;
	Cursor cursor;
	
	SimpleCursorAdapter mAdapter;
	SQLiteDatabase db;
	String sql;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trip_list);
		
		/**
		 * Get the data from previous activity
		 */
		
		data = getIntent();
		test = data.getIntExtra("paramIndex", 0);
		
		/**
		 * Set the primary Destination
		 * by set text from title in the previous
		 * activity
		 */
		
		tv = (TextView)findViewById(R.id.trip_text);
		
		
		/**
		 * 1. Item
		 * 2. Adapter
		 * 3. Adapter View
		 * 
		 * Each register as costum adapter and
		 * set the database data to copy to
		 * the item list
		 */
		
		items = new ArrayList<ChildItem>();  
		items_parent = new ArrayList<ParentItem>();
		
		adapter = new TripListAdapter(this, 
				R.layout.trip_item,
				items);
		
		dbHelper = new DBHelper(this, 
        		"trips.db", 
        		null, 
        		1);
		
		/**
		 * Get the writeable database
		 * to get the data from table
		 */
		db = dbHelper.getWritableDatabase();
		
		
		sql = String.format("SELECT * FROM Trip WHERE _id = '%d';", test);
		cursor = db.rawQuery(sql, null);
		
		cursor.moveToNext();
		title = cursor.getString(cursor.getColumnIndex("destination"));
		
		tv.setText(title);
		
		sql = String.format(
				"SELECT * FROM TripContent WHERE title = '%d';", test);	
		
		if(cursor != null){
			cursor.close();
			cursor = null;
		}
		
		cursor = db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			items.add(new ChildItem(
					cursor.getInt(0),
					cursor.getInt(1), 
					cursor.getString(2), 
					cursor.getString(3), 
					cursor.getString(4), 
					cursor.getString(5)));
		}
		
		
		
		
		tripList = (ListView)findViewById(R.id.tripList_view);
		tripList.setAdapter(adapter);
		tripList.setOnItemClickListener(this);
		this.registerForContextMenu(tripList);
		dbHelper.close();
	}
	
	/**
     * Create the Option Menu
     * Appear when menu button clicked
     * Register the Menu inflater
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = new MenuInflater(this);
		menuInflater.inflate(R.menu.menu_child, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
     * OptionItemSelected Menu
     * When the menu is clicked 
     */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.child_item_new:
			Intent intentNew = new Intent(this, NewTripChild.class);
			startActivityForResult(intentNew, 1);
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	/**
     * Create the Context Menu
     * And add each menu Item
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	
    	menu.setHeaderTitle("Select Action");
    	menu.add(0, 1, 0, "Edit");
    	menu.add(0, 2, 0, "Delete");
    }
    
    /**
     * ContextItemSelected Menu
     * When the menu is clicked long
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
    	
    	switch(item.getItemId()){
    	case 1:

    		/**
    		 * onContextItemSelected
    		 * *****
    		 * Edit
    		 * *****
    		 * Move to the Editor activity
    		 * to Edit each content of the item
    		 */
    		
    		Intent intent = new Intent(this, ChildEdit.class);
    		intent.putExtra("paramKey", items.get(menuInfo.position).mKey);
    		intent.putExtra("paramTitle", title);
    		intent.putExtra("paramDest", items.get(menuInfo.position).mDestination);
    		intent.putExtra("paramPlace", items.get(menuInfo.position).mPlace);
    		intent.putExtra("paramDate", items.get(menuInfo.position).mDate);
    		intent.putExtra("paramTime", items.get(menuInfo.position).mTime);

    		startActivityForResult(intent,2);
    		
    		break;
    	case 2:
    		
    		/**
    		 * onContextItemSelected
    		 * *****
    		 * Delete
    		 * *****
    		 * Delete the clicked item
    		 * Via Context Item Menu 
    		 */
    		
    		ChildItem list = (ChildItem)tripList.getAdapter().getItem(menuInfo.position);
    		cursor.moveToPosition(menuInfo.position);
    		db = dbHelper.getWritableDatabase();
    		int id = cursor.getInt(cursor.getColumnIndex("_id"));
    		
    		sql = String.format(
    				"DELETE FROM TripContent " +
    			    "WHERE _id = '%d';", id);
    		db.execSQL(sql);
    		
    		sql = String.format("SELECT * FROM trip order by destination");
    		
    		if(cursor != null){
				cursor.close();
				cursor = null;
			}
    		
			cursor = db.rawQuery(sql, null);
    		
        	items.remove(list);
        	adapter.notifyDataSetChanged();
    		break;
    	
    	}
		return true; 
    }
    
    

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		/**
		 * onActivity Result
		 * ******
		 * Add New
		 * ******
		 * Get the value of each item
		 * that has been added
		 */
		
		if(requestCode == 1){
			if(resultCode == RESULT_OK){
				dest = data.getStringExtra("paramDest");
				loc = data.getStringExtra("paramLoc");
				date = data.getStringExtra("paramDate");
				time = data.getStringExtra("paramTime");
			
				
				db = dbHelper.getWritableDatabase();
	    		sql = String.format(
	    				"INSERT INTO TripContent VALUES(null, '%d', '%s', '%s', '%s', '%s')", 
	    				test, dest, loc, date, time);
	    		db.execSQL(sql);
	    		
	    		sql = String.format("SELECT * FROM TripContent WHERE title='%d'", test);
	    		
	    		if(cursor != null){
					cursor.close();
					cursor = null;
				}
	    		
	    		cursor = db.rawQuery(sql, null);
		
	    		items.clear();
	    		while(cursor.moveToNext()){
	    			items.add(new ChildItem(
	    					cursor.getInt(0), 
	    					cursor.getInt(1), 
	    					cursor.getString(2), 
	    					cursor.getString(3), 
	    					cursor.getString(4), 
	    					cursor.getString(5)));
	    		}

	    		adapter.notifyDataSetChanged();
	    		dbHelper.close();
				Toast.makeText(this, "Add new item", Toast.LENGTH_SHORT).show();
			}
		}else if(requestCode == 2){
			
			/**
			 * onActivity Result
			 * ******
			 * Edit
			 * ******
			 * Get the value of each item
			 * that has been edited
			 */
			
			if(resultCode == RESULT_OK){
    			
    			
				dest = data.getStringExtra("paramDest");
				loc = data.getStringExtra("paramLoc");
				date = data.getStringExtra("paramDate");
				time = data.getStringExtra("paramTime");
				key = data.getIntExtra("paramKey", 0);
	    		
	    		
	    		db = dbHelper.getWritableDatabase();
	    		sql = String.format(
						"UPDATE TripContent " +
						"SET destination='%s', location='%s', date='%s', time='%s'" +
						"WHERE _id='%d'",
	    				dest, loc, date, time, key);
	    		db.execSQL(sql);
	    		
	    		sql = String.format("SELECT * FROM TripContent WHERE title='%d';", test);

	    		
	    		if(cursor != null){
					cursor.close();
					cursor = null;
				}
	    		
	    		cursor = db.rawQuery(sql, null);

	    		items.clear();
	    		while(cursor.moveToNext()){
	    			items.add(new ChildItem(
	    					cursor.getInt(0), 
	    					cursor.getInt(1), 
	    					cursor.getString(2), 
	    					cursor.getString(3), 
	    					cursor.getString(4), 
	    					cursor.getString(5)));
	    		}
	    		 
		    	adapter.notifyDataSetChanged();
		    	dbHelper.close();
		    	Toast.makeText(this, "Edit Item", Toast.LENGTH_SHORT).show();
	    		
	    		
    		}else{
				Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	/**
	 * Clicked item content will be
	 * appear in the Trip Content class
	 */
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		Intent intent = new Intent(this, TripContent.class);
		intent.putExtra("paramTitle", title);
		intent.putExtra("paramDest", items.get(arg2).mDestination);
		intent.putExtra("paramLoc", items.get(arg2).mPlace);
		intent.putExtra("paramDate", items.get(arg2).mDate);
		intent.putExtra("paramTime", items.get(arg2).mTime);
		startActivityForResult(intent, 3);
		
	}
		
}
