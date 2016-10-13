package com.springs.springs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {
    public static final String[] TITLES= {"Hood","Full Sleeve Shirt","Shirt","Hood","Full Sleeve Shirt","Shirt"};
    public static final Integer[] IMAGES= {R.drawable.img1sample,R.drawable.img2sample,R.drawable.img3sample,R.drawable.img1sample,R.drawable.img2sample,R.drawable.img3sample};
    private static RecyclerView recyclerView;
    private static String navigateFrom;//String to get Intent Value

    private RecyclerView horizontal_recycler_view;
    private ArrayList<DetailedImage> horizontalList;
    private HorizontalAdapter horizontalAdapter;

    View v;

    CarouselView carouselView;
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            Drawable d = getResources().getDrawable(sampleImages[position]);
            // imageView.setImageResource(sampleImages[position]);
            imageView.setImageDrawable(d);

            //  imageView.setImageResource(sampleImages[position]);
        }
    };


    int[] sampleImages = {R.drawable.happysample,R.drawable.happysample,R.drawable.happysample,R.drawable.happysample,R.drawable.happysample};
    Context context;
    private android.support.v7.widget.Toolbar toolbar;



    public Home( ) {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home_fragment,container,false);
        carouselView = (CarouselView) v.findViewById(R.id.carouselView);

        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        horizontal_recycler_view= (RecyclerView) v.findViewById(R.id.horizontal_recycler_view);


        horizontalList=new ArrayList<>();
        for(int i = 0; i < 10; i+=2) {
            horizontalList.add(new DetailedImage("Book "+ (i+1), "Justin Pleyto", "This is book number "+(i+1)+"..", R.drawable.img1sample));
            horizontalList.add(new DetailedImage("Book "+ (i+2), "Baron Dim", "This is book number "+(i+2)+"..", R.drawable.img2sample));

        }

        horizontalAdapter=new HorizontalAdapter(horizontalList);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
        horizontal_recycler_view.setAdapter(horizontalAdapter);

        return v;
    }



    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<DetailedImage> horizontalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public ImageView image;
            public TextView author;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.titletext);
                image = (ImageView)view.findViewById(R.id.imageView3);
                author = (TextView) view.findViewById(R.id.authtext);

            }
        }


        public HorizontalAdapter(List<DetailedImage> horizontalList) {
            this.horizontalList = horizontalList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_item_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.title.setText(horizontalList.get(position).getTitle());
            holder.author.setText(horizontalList.get(position).getAuthor());

            Drawable d = ContextCompat.getDrawable(getContext(),horizontalList.get(position).getIcon());
            holder.image.setImageDrawable(d);


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
