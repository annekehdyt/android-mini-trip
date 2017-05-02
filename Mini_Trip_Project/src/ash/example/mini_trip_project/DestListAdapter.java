package ash.example.mini_trip_project;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DestListAdapter extends BaseAdapter {

	/**
	 * 1. Context
	 * 2. Layout
	 * 3. Data
	 */
	
	Context mContext;
	int mLayout;
	ArrayList<ParentItem> mItems;
	LayoutInflater mInflater;
	
	TextView mTitle;
	TextView mEvent;
	TextView mTime;
	
	public DestListAdapter(Context context, int layout, ArrayList<ParentItem> items) {
		mContext = context;
		mLayout = layout;
		mItems = items;
		
		mInflater = (LayoutInflater)mContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
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
		
		mTitle = (TextView)arg1.findViewById(R.id.textView1);
		mEvent = (TextView)arg1.findViewById(R.id.textView2);
		mTime = (TextView)arg1.findViewById(R.id.textView3);
		
		mTitle.setText(mItems.get(arg0).mDest);
		mEvent.setText(mItems.get(arg0).mEvent);
		mTime.setText(mItems.get(arg0).mDate);
		
		return arg1;
	}

}
