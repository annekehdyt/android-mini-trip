package ash.example.mini_trip_project;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TripListAdapter extends BaseAdapter {

	/**
	 * 1. Context
	 * 2. Layout
	 * 3. Data
	 */
	
	Context mContext;
	int mLayout;
	ArrayList<ChildItem> mItems;
	LayoutInflater mInflater;
	
	TextView mPlace;
	TextView mTime;
	
	public TripListAdapter(Context context, int layout, ArrayList<ChildItem> items) {
		mContext = context;
		mLayout = layout;
		mItems = items;
		
		mInflater = (LayoutInflater)context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
	}
	
	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int arg0) {
		return mItems.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		/**
		 * inflate the XML files and make the View object 
		 * when the arg1 View is null
		 */
		if(arg1 == null){
			arg1 = mInflater.inflate(mLayout, null);
		}
		
		mPlace = (TextView)arg1.findViewById(R.id.tripList_place);
		mTime = (TextView)arg1.findViewById(R.id.tripList_date);
		
		mPlace.setText(mItems.get(arg0).mPlace.toString());
		mTime.setText(mItems.get(arg0).mTime.toString());
		
		
		return arg1;
	}

}
