package com.springs.springs.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.springs.springs.R;
import com.springs.springs.model.DetailedImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Baron Dimaranan on 04/11/2016.
 */

public class OkHttpHandler
{
    public static OkHttpClient client = new OkHttpClient();
    private String response;
    private JSONArray arr;
    private Activity activity;
    private Context context;
    private Request request;
    private HttpUrl okurl;
    private ArrayList<DetailedImage> tgtList;
    private View v;
    private String purpose;

    public void prepareEssentials(HttpUrl okurl, ArrayList<DetailedImage> list)
    {
        this.okurl = okurl;
        this.tgtList = list;
    }

    public ArrayList<DetailedImage> getTgtList() {
        return tgtList;
    }

    public void setTgtList(ArrayList<DetailedImage> tgt) {
        this.tgtList = tgt;
    }

    public static OkHttpClient getClient() {
        return client;
    }

    public static void setClient(OkHttpClient client) {
        OkHttpHandler.client = client;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public JSONArray getArr() {
        return arr;
    }

    public void setArr(JSONArray arr) {
        this.arr = arr;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public HttpUrl getOkurl() {
        return okurl;
    }

    public void setOkurl(HttpUrl okurl) {
        this.okurl = okurl;
    }


    public OkHttpHandler(Context context, Activity activity,View v,String purpose)
    {
        this.context = context;
        this.activity = activity;
        this.v = v;
        this.purpose = purpose;

    }

    public void loadContent() {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                /**
                 * Progress Dialog for User Interaction
                 */
                dialog = new ProgressDialog(activity);
                dialog.setTitle("Initializing..");
                dialog.setMessage("Loading your view..");
                dialog.show();
            }


            @Override
            protected Void doInBackground(Void... params)
            {
                try {

                    client.newCall(ApiCallOkHTTP.GET(client, okurl)).enqueue(new Callback()
                    {

                        @Override
                        public void onFailure(Call call, IOException e)
                        {

                            Log.d("failokhttp","client callback failed!");
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, final Response oresponse) throws IOException
                        {
                            response = oresponse.body().string();
                            Log.d("Response", response);
                            try
                            {
                                tgtList.clear();
                                arr  = new JSONArray(response);

                                for (int i = 0; i < arr.length(); i++)
                                {
                                    JSONObject o = arr.getJSONObject(i);

                                    grabForPurpose(o, purpose);

                                    Log.d("res","I got the title!: " +tgtList.get(i).getTitle());
                                }


                            }
                            catch(JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                new Handler().postDelayed(new Runnable()
                {
                  @Override
                  public void run()
                  {
                      dialog.dismiss();
                  }
                }, getAsyncWait(purpose));

            }
        }.execute();
    }

    private void grabForPurpose(JSONObject o,String purpose)
    {
        DetailedImage d = new DetailedImage();
        try {
            if (purpose.equals("news"))
            {
                d.setId(o.getInt("Id"));
                d.setTitle(o.getString("Title"));
                d.setAuthor(o.getString("Author"));
                d.setDescription(o.getString("Content"));
                d.setIconurl(o.getString("ImageUrl"));
                d.setDate(parseJsonDate(o.getString("Date")));
                d.setIcon(R.drawable.newsicon);

                tgtList.add(d);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView r;
                        r = (RecyclerView) v;

                        r.getAdapter().notifyDataSetChanged();

                    }
                });
            }
            else if(purpose.equals("home"))
            {
                d.setId(o.getInt("Id"));
                d.setTitle(o.getString("Name"));
                d.setAuthor(o.getString("Author"));
                d.setIconurl(o.getString("ImageUrl"));
                d.setIcon(R.drawable.newsicon);

                tgtList.add(d);
                activity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        RecyclerView r;
                        r = (RecyclerView) v;
                        r.getAdapter().notifyDataSetChanged();
                    }
                });
                Log.d("res","I got the Date: " +d.getTitle());
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

    }

    public String parseJsonDate(String jsonDate) {

        Date fullDate = new Date( Long.parseLong(jsonDate.substring(6,jsonDate.length()-2)));
        String twoDigitMonth = (fullDate.getMonth() + 1) + ""; if (twoDigitMonth.length() == 1) twoDigitMonth = "0" + twoDigitMonth;

        String twoDigitDate = fullDate.getDate() + ""; if (twoDigitDate.length() == 1) twoDigitDate = "0" + twoDigitDate;

        return twoDigitMonth + "/" + twoDigitDate + "/" + (fullDate.getYear() + 1900);
    }

    public int getAsyncWait(String purpose)
    {
        if(purpose.equals("home"))
            return 200;

        return 300;

    }
}
