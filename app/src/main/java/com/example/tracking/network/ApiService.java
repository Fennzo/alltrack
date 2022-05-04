package com.example.tracking.network;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tracking.models.Checkpoint;
import com.example.tracking.models.Tracking;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A service class for handling network requests.
 * Handles connection, calling app's needed APIs, and creating a singleton instance of itself
 */
public class ApiService {
    public static ApiService INSTANCE;
    private final RequestQueue requestQueue;
    private final Gson gson;

    private ApiService(Context context) {
        VolleyLog.DEBUG = true;
        // Bind Volley's lifecycle to application using application's context
        // Application's context lives for the rest of app's usage
        // Each request is sent to a queue and gets a chance for running when a thread is available
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        gson = new GsonBuilder().create();
    }

    public static synchronized ApiService getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ApiService(context);
        }
        return INSTANCE;
    }

    /**
     * Request a single tracking numbers's json data
     * @param trackingNum
     * @param slug
     * @param success
     * @param failure
     */
    public void getTracking(String trackingNum, String slug, Success<Tracking> success, Failure failure) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://api.aftership.com/v4/trackings/" + slug + "/" + trackingNum, null, response -> {
            Log.d("ApiService", "Slug: " + slug);
            try {

                JsonObject trackingJson = JsonParser.parseString(response.toString()).getAsJsonObject().getAsJsonObject("data").getAsJsonObject("tracking");
                Tracking tracking = gson.fromJson(trackingJson, Tracking.class);
                // Assign tracking number to each checkpoint, so we could retreive them based on tracking number
                // Web API doesn't provide the tracking number asssociated with each checkpoint
                for (int i = 0; i < tracking.checkpoints.size(); ++i) {
                    tracking.checkpoints.get(i).trackingNum = trackingNum;
                }
                success.onResponse(tracking);
            } catch (JsonParseException exception) {
                failure.onError(exception.getLocalizedMessage());
            }
        }, error -> {
            failure.onError(null);
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                headers.put("aftership-api-key", API_KEY);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20_000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    public void addTracking(String trackingNum, String memo, Success<String> success, Failure failure) {
        try {
            JSONObject jsonBody = new JSONObject();
            JSONObject trackingJson = new JSONObject();
            trackingJson.put("tracking_number", trackingNum);
            trackingJson.put("title", memo);
            jsonBody.put("tracking", trackingJson);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    "https://api.aftership.com/v4/trackings/",
                    jsonBody, response -> {
                try {
                    String slug = response.getJSONObject("data").getJSONObject("tracking").getString("slug");
                    success.onResponse(slug);
                } catch (JSONException exception) {
                    failure.onError(exception.getLocalizedMessage());
                }
            }, error -> {
                int statusCode = error.networkResponse.statusCode;
                failure.onError(String.valueOf(statusCode));
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json");
                    headers.put("aftership-api-key", API_KEY);
                    return headers;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(20_000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(request);
        } catch (JSONException exception) {
            failure.onError(exception.getLocalizedMessage());
        }
    }

    public void deleteTracking(String slug, String trackingNum, Success<String> onSuccess, Failure onFailure) {
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, "https://api.aftership.com/v4/trackings/" + slug + "/" + trackingNum, success -> {
            onSuccess.onResponse("OK");
        }, error -> {
            onFailure.onError(error.getLocalizedMessage());
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("aftership-api-key", API_KEY);
                return headers;
            }
        };
        requestQueue.add(deleteRequest);
    }

    private static final String BASE_URL = "https://api.aftership.com/v4/";
    // TODO: replace with your own valid api key from https://api.aftership.com
    private static final String API_KEY = "dccf9edc-a817-4cb3-b035-31039c8a313c";

    // Interface for clients handling success state of response
    public interface Success<T> {
        void onResponse(T data);
    }

    // Interface for clients handling error state of response, i.e. anything not 2XX
    public interface Failure {
        void onError(String message);
    }
}
