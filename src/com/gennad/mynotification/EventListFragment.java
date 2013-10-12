package com.gennad.mynotification;


import java.util.Date;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
public class EventListFragment extends ListFragment implements LoaderCallbacks<Cursor>    {
	SimpleCursorAdapter mAdapter;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);	
		Bundle b = this.getArguments();	
		getLoaderManager().initLoader(0, b, this);
		mAdapter = new SimpleCursorAdapter(getActivity(), 
				android.R.layout.simple_list_item_2, 
				null, //cursor 
				new String[] {CalendarContract.Events.TITLE, CalendarContract.Events.RRULE},
				new int[] { android.R.id.text1,  android.R.id.text2,}, 0); 
		setListAdapter(mAdapter);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		long calendarId = args.getLong("calendarId");
		CursorLoader cursorLoader = new CursorLoader(getActivity(),
				Events.CONTENT_URI, 
				null, Events.CALENDAR_ID +"= '" + calendarId +"' AND " + Events.DTEND + "> " + new Date().getTime() 
				+" OR " + Events.CALENDAR_ID +"= '" + calendarId +"' AND " + Events.RRULE + " NOT NULL", null, null);	
		return cursorLoader;

	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mAdapter.swapCursor(cursor);
	}
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);		
	}
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {


	}
	
} 
