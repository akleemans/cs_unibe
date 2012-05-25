package ch.eonum;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ZoomControls;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class HealthMapView extends MapView
{
	/**
	 * Describes the method that is invoked when MapView encounters changes.
	 */
	public interface OnChangeListener
	{
		public void onChange(MapView view, GeoPoint newCenter, GeoPoint oldCenter, int newZoom, int oldZoom);
	}

	private HealthMapView mapContext;
	private HealthMapView.OnChangeListener mapChangeListener = null;
	private GeoPoint lastCenterPosition;
	private int lastZoomLevel;
	private MapController controller;

	public HealthMapView(Context context, String apiKey)
	{
		super(context, apiKey);
		initialize();
	}

	public HealthMapView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initialize();
	}

	public HealthMapView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		initialize();
	}

	private void initialize()
	{
		this.mapContext = this;
		this.lastCenterPosition = this.getMapCenter();
		this.lastZoomLevel = this.getZoomLevel();
		this.setBuiltInZoomControls(true);
		this.controller = this.getController();
		ZoomControls control = (ZoomControls) this.getZoomButtonsController().getZoomControls();
		control.setOnZoomOutClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Logger.log("Zoomed out.");
				controller.zoomOut();
				performMapChanges();
			}
		});
		control.setOnZoomInClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Logger.log("Zoomed in.");
				controller.zoomIn();
				performMapChanges();
			}
		});
	}

	public void setOnChangeListener(HealthMapView.OnChangeListener listener)
	{
		mapChangeListener = listener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		try
		{
			super.onTouchEvent(event);
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			Logger.error("Error in super method", "A kitten's just been killed :( - " + e.toString());
			e.printStackTrace();
		}
		if (event.getAction() == MotionEvent.ACTION_UP && !getMapCenter().equals(this.lastCenterPosition)
			&& this.mapChangeListener != null)
		{
			Logger.log("Touch event triggered.");
			performMapChanges();
		}

		return true;
	}

	private void performMapChanges()
	{
		mapChangeListener.onChange(mapContext, getMapCenter(), lastCenterPosition, getZoomLevel(), lastZoomLevel);
		// Update map values
		lastCenterPosition = getMapCenter();
		lastZoomLevel = getZoomLevel();
	}
}
