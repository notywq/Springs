package com.springs.springs.page;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.springs.springs.controller.ApiCallOkHTTP;
import com.springs.springs.controller.RequestBuilder;
import com.springs.springs.model.DetailedImage;
import com.springs.springs.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class NewsLetter extends Fragment {

    private View v;
    private ArrayList<DetailedImage> news = new ArrayList<>();
    private LayoutInflater v2;

    private RecyclerView vertical_recycler_view;
    private  VerticalAdapter verticalAdapter;
    private OkHttpClient client;
    private String response;
    private JSONArray ob;
    private JSONArray arr;
    private FragmentActivity main;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.newsletter_fragment, container, false);
        // Inflate the layout for this fragment
        v2 = inflater;





        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        vertical_recycler_view= (RecyclerView) v.findViewById(R.id.newsView);
        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        vertical_recycler_view.setLayoutManager(verticalLayoutmanager);
        //populateNews();
        verticalAdapter = new VerticalAdapter(getActivity(),news);
        vertical_recycler_view.setAdapter(verticalAdapter);
        main = getActivity();
        context = getContext();
        loadOkHTTP();
    }

    public void loadOkHTTP() {
        client = new OkHttpClient();
        //Picasso picasso = new Picasso.Builder(getContext()).downloader(new OkHttp3Downloader(client)).build();
        loadContent();
    }

    public String parseJsonDate(String jsonDate) {

        Date fullDate = new Date( Long.parseLong(jsonDate.substring(6,jsonDate.length()-2)));
        String twoDigitMonth = (fullDate.getMonth() + 1) + ""; if (twoDigitMonth.length() == 1) twoDigitMonth = "0" + twoDigitMonth;

        String twoDigitDate = fullDate.getDate() + ""; if (twoDigitDate.length() == 1) twoDigitDate = "0" + twoDigitDate;
        String currentDate = twoDigitMonth + "/" + twoDigitDate + "/" + (fullDate.getYear() + 1900);

        return currentDate;
    }

    private void loadContent() {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                /**
                 * Progress Dialog for User Interaction
                 */
                dialog = new ProgressDialog(main);
                dialog.setTitle("Loading..");
                dialog.setMessage("Grabbing News Articles..");
                dialog.show();
            }


            @Override
            protected Void doInBackground(Void... params)
            {
                try {

                    client.newCall(ApiCallOkHTTP.GET(client, RequestBuilder.buildNewsURL())).enqueue(new Callback()
                    {

                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, final Response oresponse) throws IOException
                        {
                                response = oresponse.body().string();
                                Log.d("Response", response);
                                try
                                {
                                    news.clear();
                                    //ob  = new JSONArray(response);
                                    arr  = new JSONArray(response);

                                    for (int i = 0; i < arr.length(); i++)
                                    {
                                        JSONObject o = arr.getJSONObject(i);
                                        DetailedImage d = new DetailedImage();
                                        d.setId(o.getInt("Id"));
                                        d.setTitle(o.getString("Title"));
                                        d.setAuthor(o.getString("Author"));
                                        d.setDescription(o.getString("Content"));
                                        d.setIconurl(o.getString("ImageUrl"));
                                        d.setDate(parseJsonDate(o.getString("Date")));
                                        d.setIcon(R.drawable.newsicon);
                                        news.add(d);
                                        main.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                vertical_recycler_view.getAdapter().notifyDataSetChanged();

                                            }
                                        });
                                        Log.d("res","I got the Date: " +d.getIconurl());
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

                new Handler().postDelayed(new Runnable() {
                                              @Override
                                              public void run()
                                              {
                                                  dialog.dismiss();
                                              }
                                          },
                        400);

                /**
                 * Checking if List size if more than zero then
                 * Update ListView
                 */

            }
        }.execute();
    }

    public void populateNews()
    {
        for(int i = 0; i < 10; i++) {
            news.add(new DetailedImage("Article "+ (i+1), "Author Load", "This is article number "+(i+1)+"..", R.drawable.newsicon));
        }

    }

    public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder> {

        private ArrayList<DetailedImage> verticalList;
        private Activity acontext;

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            public TextView title;
            public TextView author;
            public TextView desc;
            public ImageView img;

            MyViewHolder(View view) {
                super(view);
                img = (ImageView) view.findViewById(R.id.item_icon);
                title = (TextView) view.findViewById(R.id.item_txtnewstitle);
                author = (TextView) view.findViewById(R.id.item_txtauthor);
                desc = (TextView) view.findViewById(R.id.item_txtdesc);
                view.setOnClickListener(this);

            }
            @Override
            public void onClick(View v)
            {
                // Clicked on item
                Toast.makeText(getActivity(), "Clicked on position: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        }


        VerticalAdapter(Activity context, ArrayList<DetailedImage> vlist) {
            this.verticalList = vlist;
            this.acontext = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_item_view, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.title.setText(verticalList.get(position).getTitle());
            holder.author.setText(verticalList.get(position).getAuthor());
            holder.desc.setText(verticalList.get(position).getDescription());
            Log.d("res","I got the IconURL with Pic: " +verticalList.get(position).getIconurl());
           // Picasso.with(context).load(verticalList.get(position).getIconurl()).into(holder.img);
            /*Picasso.with(context).load(verticalList.get(position).getIconurl()).fit().centerCrop()
                    .placeholder(R.drawable.newsicon)
                    .error(R.drawable.newsicon)
                    .into(holder.img);
*/
            Picasso.with(acontext).load("http://s.imgur.com/images/logo-1200-630.jpg")
                   // .placeholder(R.drawable.newsicon)
                    .resize(400,400)
                    .centerInside()
                    .error(R.drawable.newsicon)
                    .into(holder.img);



            //Drawable d = ContextCompat.getDrawable(getContext(),verticalList.get(position).getIcon());
           // holder.img.setImageDrawable(d);

            /*
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),holder.title.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });
            */
        }

        @Override
        public int getItemCount() {
            return verticalList.size();
        }


    }

    /*
    private void populateListView() {
        ArrayAdapter<DetailedImage> adapter = new MyListAdapter();
        ListView list = (ListView)v.findViewById(R.id.newsView);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView)v.findViewById(R.id.newsView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                DetailedImage currDetailedImage = news.get(position);
                String message = "Article " + (position+1)
                        + " selected, Desc: " + currDetailedImage.getDescription();
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<DetailedImage> {
        MyListAdapter() {
            super(getActivity(), R.layout.news_item_view, news);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = v2.inflate(R.layout.news_item_view, parent, false);
            }


            DetailedImage currentA = news.get(position);

            // Fill the view
            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentA.getIcon());


            TextView makeText = (TextView) itemView.findViewById(R.id.item_txtnewstitle);
            makeText.setText(currentA.getTitle());


            TextView yearText = (TextView) itemView.findViewById(R.id.item_txtauthor);
            yearText.setText(currentA.getAuthor());


            TextView condionText = (TextView) itemView.findViewById(R.id.item_txtdesc);
            condionText.setText(currentA.getDescription());

            return itemView;
        }
    }
    */


}
