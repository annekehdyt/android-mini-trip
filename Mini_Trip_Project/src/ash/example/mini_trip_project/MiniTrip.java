package ash.example.mini_trip_project;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MiniTrip extends Activity implements OnItemClickListener {
	
	ArrayList<ParentItem> items;
	DestListAdapter adapter;
	ListView destList;
	
	String dest;
	String event;
	String date;
	
	int position;
	String sql;
	int key;
	
	String trip;
	DBHelper dbHelper;
	Cursor cursor;
	
	SimpleCursorAdapter mAdapter;
	SQLiteDatabase db;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_trip);
        
        /**
		 * 1. Item
		 * 2. Adapter
		 * 3. Adapter View
		 * 
		 * Each register as costum adapter and
		 * set the database data to copy to
		 * the item list
		 */
        
        items = new ArrayList<ParentItem>();
        
        adapter = new DestListAdapter(
        		this, 
        		R.layout.dest_item, 
        		items);
        
        
        dbHelper = new DBHelper(this, 
        		"trips.db", 
        		null, 
        		1);
        
        db = dbHelper.getWritableDatabase();
		sql = String.format(
				"SELECT * FROM Trip order by destination");
        
		if(cursor != null){
			cursor.close();
			cursor = null;
		}
		
		cursor = db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			items.add(new ParentItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
		}
		
		
        destList = (ListView)findViewById(R.id.listView1);
        destList.setAdapter(adapter);
        destList.setOnItemClickListener(this);
        
        this.registerForContextMenu(destList);
        
        dbHelper.close();
        
        
    }

	/**
     * Create the Option Menu
     * Appear when menu butten clicked
     * Register the Menu inflater
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
	/**
     * OptionItemSelected Menu
     * When the menu is clicked 
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	case R.id.item_new:
    		Intent intent = new Intent(this, NewTrip.class);
    		startActivityForResult(intent, 1);
    		break;
    	case R.id.item_about:
    		Intent intent2 = new Intent(this, About.class);
    		startActivity(intent2);
    		break;
    	case R.id.item_dev:
    		Intent intent3 = new Intent(this, Developer.class);
    		startActivity(intent3);
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
    	//return super.onContextItemSelected(item);
    	
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
    		
    		Intent intent = new Intent(this, ParentEdit.class);
    		intent.putExtra("paramKey", items.get(menuInfo.position).mKey);
    		intent.putExtra("paramDest", items.get(menuInfo.position).mDest);
    		intent.putExtra("paramEvent", items.get(menuInfo.position).mEvent);
    		position = menuInfo.position;
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
    		
    		ParentItem list = (ParentItem)destList.getAdapter().getItem(menuInfo.position);
    		cursor.moveToPosition(menuInfo.position);
    		db = dbHelper.getWritableDatabase();
    		int id = cursor.getInt(cursor.getColumnIndex("_id"));
    		//String dest = cursor.getString(cursor.getColumnIndex("destination"));
    		
    		sql = String.format(
    				"DELETE FROM TripContent " +
    			    "WHERE title='%d';", id);
    		db.execSQL(sql);
    		sql = String.format(
    				"DELETE FROM trip " +
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
    	
    	if(requestCode == 1){
    		
    		/**
    		 * onActivity Result
    		 * ******
    		 * Add New
    		 * ******
    		 * Get the value of each item
    		 * that has been added
    		 */
    		
	    	if(resultCode == RESULT_OK){
	    		dest = data.getStringExtra("paramDest");
	    		event = data.getStringExtra("paramEvent");
	    		date = data.getStringExtra("paramDate");
	    		
	    		db = dbHelper.getWritableDatabase();
	    		sql = String.format(
	    				"INSERT INTO Trip VALUES(null, '%s', '%s', '%s')", 
	    				dest, event, date);
	    		db.execSQL(sql);
	    		
	    		Toast.makeText(this, "Add new item", Toast.LENGTH_SHORT).show();
	    		
	    		sql = "SELECT * FROM Trip;";
	    		
	    		if(cursor != null){
					cursor.close();
					cursor = null;
				}
	    		
	    		cursor = db.rawQuery(sql, null);
	
	    		items.clear();
	    		while(cursor.moveToNext()){
	    			items.add(new ParentItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
	    		}
	    		adapter.notifyDataSetChanged();
	    		dbHelper.close();
	    	}else{
				Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
			}
    	} else if(requestCode == 2){
    		
    		/**
			 * onActivity Result
			 * ******
			 * Edit
			 * ******
			 * Get the value of each item
			 * that has been edited
			 */
    			
    		if(resultCode == RESULT_OK){

    			key = data.getIntExtra("paramKey", 0);
	    		dest = data.getStringExtra("paramDest");
	    		event = data.getStringExtra("paramEvent");
	    		date = data.getStringExtra("paramDate");
	    		
	    		
	    		db = dbHelper.getWritableDatabase();
	    		sql = String.format(
						"UPDATE trip " +
						"SET destination='%s', event='%s', date='%s'" +
						"WHERE _id='%d'",
	    				dest, event, date, key);
	    		db.execSQL(sql);
	    		
	    		sql = "SELECT * FROM Trip;";
	    		
	    		if(cursor != null){
					cursor.close();
					cursor = null;
				}
	    		
	    		cursor = db.rawQuery(sql, null);

	    		items.clear();
	    		while(cursor.moveToNext()){
	    			items.add(new ParentItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
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
	 * appear in the Trip List class
	 */
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this, TripList.class);
		
		intent.putExtra("paramIndex", items.get(arg2).mKey);
		startActivityForResult(intent, 1);
	}
	

}
