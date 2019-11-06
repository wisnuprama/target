package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.remote;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.RecentInfo;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.UpdateInfoDataSource;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.volley.WebServiceRequestQueue;

public class RecentInfoRemoteDataSource implements UpdateInfoDataSource {
    private static final String ENDPOINT = "https://public-api.wordpress.com/rest/v1.1/sites/targetapp.tech.blog/posts/";

    private static RecentInfoRemoteDataSource instance;

    private WebServiceRequestQueue mRequestQueue;

    private final MutableLiveData<List<RecentInfo>> mListMutableLiveData;

    public RecentInfoRemoteDataSource(WebServiceRequestQueue requestQueuel) {
        mRequestQueue = requestQueuel;
        mListMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public LiveData<List<RecentInfo>> getUpdateInfos() {
        Response.Listener<JSONObject> listener = response -> {
            try {
                final String posts = response.getString("posts");
                List<RecentInfo> recentInfos = new Gson()
                        .fromJson(posts, new TypeToken<List<RecentInfo>>(){}.getType());
                mListMutableLiveData.postValue(recentInfos);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };
        Response.ErrorListener errorListener = error -> error.printStackTrace();

        mRequestQueue.addToRequestQueue(new JsonObjectRequest(Request.Method.GET,
                                                              ENDPOINT,
                                                              null,
                                                              listener,
                                                              errorListener));
        return mListMutableLiveData;
    }

    public static synchronized RecentInfoRemoteDataSource getInstance(@NonNull WebServiceRequestQueue requestQueue) {
        if(instance == null) {
            instance = new RecentInfoRemoteDataSource(requestQueue);
        }
        return instance;
    }
}
