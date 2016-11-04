package com.springs.springs.page;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.springs.springs.R;
import com.springs.springs.controller.ApiCallOkHTTP;
import com.springs.springs.controller.OkHttpHandler;
import com.springs.springs.controller.RequestBuilder;
import com.springs.springs.model.DetailedImage;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class Home extends Fragment {


    private RecyclerView horizontal_recycler_view;
    private ArrayList<DetailedImage> horizontalList;
    private ArrayList<DetailedImage> carouselList;
    private HorizontalAdapter horizontalAdapter;

    int[] sampleImages = {R.drawable.happysample,R.drawable.happysample,R.drawable.happysample,R.drawable.happysample,R.drawable.happysample};
    private android.support.v7.widget.Toolbar toolbar;

    private OkHttpClient client;
    private String response;
    private JSONArray ev_arr;

    private FragmentActivity main;
    private Context context;
    ImageListener imageListener;
    private OkHttpHandler okhandler;
    View v;
    CarouselView carouselView;

    public Home( )
    {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home_fragment,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        carouselView = (CarouselView) v.findViewById(R.id.carouselView);

        horizontal_recycler_view= (RecyclerView) v.findViewById(R.id.horizontal_recycler_view);

        carouselList = new ArrayList<>();
        horizontalList=new ArrayList<>();

        horizontalAdapter=new HorizontalAdapter(getActivity(),horizontalList);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
        horizontal_recycler_view.setAdapter(horizontalAdapter);

        main = getActivity();
        context = getContext();

        okhandler = new OkHttpHandler(context,main,horizontal_recycler_view,"home");
        loadEvents();
        okhandler.prepareEssentials(RequestBuilder.buildBooksURL(1),horizontalList);
        okhandler.loadContent();


    }


    private void loadEvents()
    {

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
                dialog.setMessage("Grabbing Events..");
                dialog.show();
            }


            @Override
            protected Void doInBackground(Void... params)
            {
                try {
                    client = new OkHttpClient();
                    client.newCall(ApiCallOkHTTP.GET(client, RequestBuilder.buildEventsURL())).enqueue(new Callback()
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
                                //ob  = new JSONArray(response);
                                ev_arr  = new JSONArray(response);

                                for (int i = 0; i < ev_arr.length(); i++)
                                {
                                    JSONObject o = ev_arr.getJSONObject(i);
                                    DetailedImage d = new DetailedImage();
                                    d.setId(o.getInt("Id"));
                                    d.setTitle(o.getString("Name"));
                                    d.setDescription(o.getString("Description"));
                                    d.setIconurl(o.getString("ImageUrl"));
                                    d.setDate(okhandler.parseJsonDate(o.getString("Date")));
                                    d.setIcon(R.drawable.newsicon);
                                    carouselList.add(d);

                                    Log.d("eve","I got the Event: " +d.getTitle());
                                }
                                Log.d("img","I got the img: " +carouselList.get(0).getTitle());

                                        main.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageListener = new ImageListener() {
                                                    @Override
                                                    public void setImageForPosition(int position, ImageView imageView) {

                                                        //rawable d = ResourcesCompat.getDrawable(getResources(),sampleImages[position],null);
                                                        // imageView.setImageResource(sampleImages[position]);
                                                        //imageView.setImageDrawable(d);
                                                        Picasso.with(main).load(carouselList.get(position).getIconurl())
                                                                // .placeholder(R.drawable.newsicon)
                                                                .resize(400,300)
                                                                .error(R.drawable.newsicon)
                                                                .into(imageView);

                                                        //  imageView.setImageResource(sampleImages[position]);
                                                    }
                                                };
                                                carouselView.setImageListener(imageListener);
                                                carouselView.setPageCount(carouselList.size());//carouselList.size());
                                            }
                                        });
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
                }, 200);

                /**
                 * Checking if List size if more than zero then
                 * Update ListView
                 */

            }
        }.execute();

    }


    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<DetailedImage> horizontalList;
        private Activity acontext;

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            ImageView image;
            TextView author;

            MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.titletext);
                image = (ImageView)view.findViewById(R.id.imageView3);
                author = (TextView) view.findViewById(R.id.authtext);

            }
        }


        HorizontalAdapter(Activity acontext, List<DetailedImage> horizontalList) {
            this.horizontalList = horizontalList;
            this.acontext = acontext;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_item_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            if(horizontalList.get(position).getTitle().length() > 8)
            {
                String temp = horizontalList.get(position).getTitle().substring(0,7) + "...";
                holder.title.setText(temp);
            }

            else
                holder.title.setText(horizontalList.get(position).getTitle());
            holder.author.setText(horizontalList.get(position).getAuthor());

            Drawable d = ContextCompat.getDrawable(getContext(),horizontalList.get(position).getIcon());
            holder.image.setImageDrawable(d);

            Picasso.with(acontext).load(horizontalList.get(position).getIconurl())
                    // .placeholder(R.drawable.newsicon)
                    .resize(400,400)
                    .centerInside()
                    .error(R.drawable.newsicon)
                    .into(holder.image);


            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),holder.title.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }

}
