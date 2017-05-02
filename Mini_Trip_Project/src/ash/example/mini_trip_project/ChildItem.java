package ash.example.mini_trip_project;

public class ChildItem {

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
	int mTitle;
	String mDestination;
	String mPlace;
	String mDate;
	String mTime;
	
	public ChildItem(int key, int title, String destination, String place, String date, String time) {
		mKey = key;
		mDestination = destination;
		mPlace = place;
		mDate = date;
		mTime = time;
		
		
	}
}
