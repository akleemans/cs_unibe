package ch.eonum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Startup Activity which starts HealthActivity and then exits itself.
 * This class serves for testing purposes and could present
 * a startup screen in a later stage.
 */
public class StartActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Intent intent = new Intent(StartActivity.this, HealthActivity.class);
		startActivity(intent);
		finish();
	}
}
