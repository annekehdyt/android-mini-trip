package ash.example.mini_trip_project;

public class ParentItem {
	
	/**
	 * Data
	 * mDest		: Destination
	 * mEvent		: Event
	 * mDate		: Making date
	 * 
	 * Additional 
	 * mImage!
	 */
	int mKey;
	String mDest;
	String mEvent;
	String mDate;
	
	public ParentItem(int key, String dest, String event, String date) {
		mKey = key;
		mDest = dest;
		mEvent = event;
		mDate = date;
	}
}
