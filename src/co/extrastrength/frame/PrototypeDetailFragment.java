package co.extrastrength.frame;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import co.extrastrength.frame.dummy.DummyContent;

/**
 * A fragment representing a single Prototype detail screen. This fragment is
 * either contained in a {@link PrototypeListActivity} in two-pane mode (on
 * tablets) or a {@link PrototypeDetailActivity} on handsets.
 */
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
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mUrl = getArguments().getString(ARG_ITEM_ID);
			
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_prototype_detail,
				container, false);
		System.out.println("URL " + mUrl);
		if (mUrl != null) {
			WebView web = (WebView)rootView.findViewById(R.id.webView);
			web.getSettings().setJavaScriptEnabled(true);
			web.loadUrl(mUrl);
		}

		return rootView;
	}
}
