package com.mobiledev.topimpamatricks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by maiaphoebedylansamerjan on 4/16/16.
 */
public class OCRAsyncTask extends AsyncTask<String, Integer, JSONObject> {

    public static final String TAG = OCRAsyncTask.class.getSimpleName();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "AsyncTask has begun");
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        if (params.length == 0)
            return null;

        URL idol_ocr_service = null;
        try {
            idol_ocr_service = new URL("https://api.idolondemand.com/1/api/async/ocrdocument/v1");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            if (idol_ocr_service != null) {
                HttpURLConnection connection = (HttpURLConnection) idol_ocr_service.openConnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isCancelled())
            return null;


//        HttpPost httpPost = new HttpPost();
//        httpPost.setURI(uri);
//        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
//        reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//        reqEntity.addPart("apikey", new StringBody(apikey, ContentType.TEXT_PLAIN));
//        reqEntity.addBinaryBody("file", new File(mImageFullPathAndName);
//        reqEntity.addPart("mode", new StringBody("document_photo", ContentType.TEXT_PLAIN));
//        httpPost.setEntity(reqEntity.build());
//        HTTPClient httpClient = new DefaultHttpClient();
//        HttpResponse response = httpClient.execute(httpPost);

        return null;
    }

    @Override
    protected void onCancelled(JSONObject jsonObject) {
        super.onCancelled(jsonObject);
        Log.d(TAG, "AsyncTask cancelled");
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        if (jsonObject == null) {
            Log.e(TAG, "JSON is null");
        }
    }
}
