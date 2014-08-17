package co.extrastrength.frame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;

public class PrototypeDetailActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_prototype_detail);

		if (savedInstanceState == null) {
			Bundle arguments = new Bundle();
			arguments.putString(
					PrototypeDetailFragment.ARG_ITEM_ID,
					getIntent().getStringExtra(
							PrototypeDetailFragment.ARG_ITEM_ID));
			PrototypeDetailFragment fragment = new PrototypeDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.prototype_detail_container, fragment).commit();
			
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		

	}
	
	void back() {
		NavUtils.navigateUpTo(this, new Intent(this,
				PrototypeListActivity.class));
	}
}
