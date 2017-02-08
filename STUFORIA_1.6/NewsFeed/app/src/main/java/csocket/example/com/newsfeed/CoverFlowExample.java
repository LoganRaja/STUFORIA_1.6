package csocket.example.com.newsfeed;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class CoverFlowExample extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cover_flow_example);
		CoverFlow coverFlow = (CoverFlow) findViewById(R.id.cf_coverflow);

		CoverImageAdapter coverImageAdapter = new CoverImageAdapter(this);

		coverFlow.setAdapter(coverImageAdapter);
		coverFlow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				Toast.makeText(CoverFlowExample.this, String.valueOf(position),
						Toast.LENGTH_SHORT).show();
			}
		});

	}
}