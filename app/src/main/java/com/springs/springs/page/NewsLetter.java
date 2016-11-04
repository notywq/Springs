package com.springs.springs.page;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.springs.springs.controller.OkHttpHandler;
import com.springs.springs.controller.RequestBuilder;
import com.springs.springs.model.DetailedImage;
import com.springs.springs.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class NewsLetter extends Fragment {

    private View v;
    private ArrayList<DetailedImage> news = new ArrayList<>();

    private RecyclerView vertical_recycler_view;
    private  VerticalAdapter verticalAdapter;
    private Activity main;
    private Context context;

    private OkHttpHandler okhandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        v = inflater.inflate(R.layout.newsletter_fragment, container, false);
        // Inflate the layout for this fragment
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

        okhandler = new OkHttpHandler(context,main,vertical_recycler_view,"news");
        okhandler.prepareEssentials(RequestBuilder.buildNewsURL(),news);
        okhandler.loadContent();
    }

    /*public void populateNews()
    {
        for(int i = 0; i < 10; i++) {
            news.add(new DetailedImage("Article "+ (i+1), "Author Load", "This is article number "+(i+1)+"..", R.drawable.newsicon));
        }

    }*/

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
}
