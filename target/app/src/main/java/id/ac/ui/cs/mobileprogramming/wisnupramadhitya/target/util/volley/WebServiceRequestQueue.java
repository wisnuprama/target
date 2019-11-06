package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Singleton for request queue so the queue can be used in all app component.
 */
public class WebServiceRequestQueue {

    private static WebServiceRequestQueue instance;

    private RequestQueue mRequestQueue;
    private Context mContext;

    public WebServiceRequestQueue(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized WebServiceRequestQueue getInstance(Context context) {
        if (instance == null) {
            instance = new WebServiceRequestQueue(context);
        }
        return instance;
    }

    /**
     * Get the request queue, if null then create a new instance.
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Add request to queue.
     * @param req
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
