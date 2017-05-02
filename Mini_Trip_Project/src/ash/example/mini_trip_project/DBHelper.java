package ash.example.mini_trip_project;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


public class DBHelper extends SQLiteOpenHelper {

	private static final String TRIP_TABLE_CREATE =
			"CREATE TABLE Trip(" +
			"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
		    "destination TEXT, " +
			"event TEXT, " +
			"date TEXT);";
	
	private static final String TRIPCONTENT_TABLE_CREATE =
			"CREATE TABLE TripContent(" +
		    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"title INTEGER, " +
			"destination TEXT, " +
			"location TEXT, " +
			"date TEXT, " +
			"time TEXT);";

	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL(TRIP_TABLE_CREATE);
		arg0.execSQL(TRIPCONTENT_TABLE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
