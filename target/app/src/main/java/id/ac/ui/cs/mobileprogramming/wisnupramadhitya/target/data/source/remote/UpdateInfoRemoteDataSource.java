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

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.UpdateInfo;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.UpdateInfoDataSource;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.WebServiceRequestQueue;

public class UpdateInfoRemoteDataSource implements UpdateInfoDataSource {
    private static final String ENDPOINT = "https://public-api.wordpress.com/rest/v1.1/sites/targetapp.tech.blog/posts/";

    private static UpdateInfoRemoteDataSource instance;

    private WebServiceRequestQueue mRequestQueue;

    private final MutableLiveData<List<UpdateInfo>> mListMutableLiveData;

    public UpdateInfoRemoteDataSource(WebServiceRequestQueue requestQueuel) {
        mRequestQueue = requestQueuel;
        mListMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public LiveData<List<UpdateInfo>> getUpdateInfos() {
        Response.Listener<JSONObject> listener = response -> {
            try {
                final String posts = response.getString("posts");
                List<UpdateInfo> updateInfos = new Gson()
                        .fromJson(posts, new TypeToken<List<UpdateInfo>>(){}.getType());
                mListMutableLiveData.postValue(updateInfos);
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

    public static synchronized UpdateInfoRemoteDataSource getInstance(@NonNull WebServiceRequestQueue requestQueue) {
        if(instance == null) {
            instance = new UpdateInfoRemoteDataSource(requestQueue);
        }
        return instance;
    }
}
