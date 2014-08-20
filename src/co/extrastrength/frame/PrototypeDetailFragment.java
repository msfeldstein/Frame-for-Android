package co.extrastrength.frame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;

public class PrototypeDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private String mUrl;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public PrototypeDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			mUrl = getArguments().getString(ARG_ITEM_ID);
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_prototype_detail,
				container, false);
		if (mUrl != null) {
			WebView web = (WebView)rootView.findViewById(R.id.webView);
			web.getSettings().setJavaScriptEnabled(true);
			web.getSettings().setSupportZoom(false);
			web.loadUrl(mUrl);
			
			web.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					back();
					return false;
				}
			});
		}

		return rootView;
	}
	void back() {
		NavUtils.navigateUpTo(getActivity(), new Intent(getActivity(),
				PrototypeListActivity.class));
	}
}
