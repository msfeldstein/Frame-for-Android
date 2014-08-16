package co.extrastrength.frame;

import java.net.InetAddress;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

public class BonjourHelper {
	public static String SERVICE_TYPE = "_airframer._tcp.";
    public static String TAG = "airframer";
    NsdManager.DiscoveryListener mDiscoveryListener;
    NsdManager.ResolveListener mResolveListener;
    NsdManager mNsdManager;
    NsdServiceInfo mService;
    BonjourDelegate mDelegate;
    
    public interface BonjourDelegate {
    	public void foundService(String host, int port);
    }
    
    public BonjourHelper(Context c, BonjourDelegate delegate) {
    	mDelegate = delegate;
		initializeDiscoveryListener();
		initializeResolveListener();
		mNsdManager = (NsdManager)c.getSystemService(Context.NSD_SERVICE);
		mNsdManager.discoverServices(
		        SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
	}
    
	public void initializeDiscoveryListener() {

	    // Instantiate a new DiscoveryListener
	    mDiscoveryListener = new NsdManager.DiscoveryListener() {

	        //  Called as soon as service discovery begins.
	        @Override
	        public void onDiscoveryStarted(String regType) {
	            Log.d(TAG, "Service discovery started");
	        }

	        @Override
	        public void onServiceFound(NsdServiceInfo service) {
	            // A service was found!  Do something with it.
	            Log.d(TAG, "Service discovery success" + service);
	            if (service.getServiceType().indexOf("airframe") == -1) {
	                // Service type is the string containing the protocol and
	                // transport layer for this service.
	                Log.d(TAG, "Unknown Service Type: " + service.getServiceType());
	            } else {
	                // The name of the service tells the user what they'd be
	                // connecting to. It could be "Bob's Chat App".
	                Log.d(TAG, "Found: " + SERVICE_TYPE);
	                mNsdManager.resolveService(service, mResolveListener);
	            }
	        }

	        @Override
	        public void onServiceLost(NsdServiceInfo service) {
	            // When the network service is no longer available.
	            // Internal bookkeeping code goes here.
	            Log.e(TAG, "service lost" + service);
	        }

	        @Override
	        public void onDiscoveryStopped(String serviceType) {
	            Log.i(TAG, "Discovery stopped: " + serviceType);
	        }

	        @Override
	        public void onStartDiscoveryFailed(String serviceType, int errorCode) {
	            Log.e(TAG, "Discovery failed: Error code:" + errorCode);
	            mNsdManager.stopServiceDiscovery(this);
	        }

	        @Override
	        public void onStopDiscoveryFailed(String serviceType, int errorCode) {
	            Log.e(TAG, "Discovery failed: Error code:" + errorCode);
	            mNsdManager.stopServiceDiscovery(this);
	        }
	    };
	}
	

    public void initializeResolveListener() {
        mResolveListener = new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Called when the resolve fails.  Use the error code to debug.
                Log.e(TAG, "Resolve failed" + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.e(TAG, "Resolve Succeeded. " + serviceInfo);

                mService = serviceInfo;
                int port = mService.getPort();
                InetAddress host = mService.getHost();
                System.out.println("HOST: " + host + " + " + port);
                mDelegate.foundService(host.getHostAddress(), port);
            }
        };
    }
}
