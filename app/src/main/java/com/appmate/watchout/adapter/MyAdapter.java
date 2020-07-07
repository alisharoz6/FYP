//package com.appmate.watchout.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.devclan.ertugrul_android.R;
//import com.devclan.ertugrul_android.activity.EpisodeActivity;
//import com.devclan.ertugrul_android.model.Season;
//import com.devclan.ertugrul_android.util.AppUtil;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.MyViewHolder> {
//
//    private Context mContext;
//    private List<Season> seasonsList;
//    private boolean isFilter;
//    private String row_index = "";
//    private ArrayList<MyViewHolder> holders = new ArrayList<>();
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        public ImageView imageView;
//        public LinearLayout ll_brand;
//        public CardView cardView;
//        public TextView tvTitle;
//        public boolean isSelected;
//
//        public MyViewHolder(View view) {
//            super(view);
//            imageView = view.findViewById(R.id.iv_title);
//            cardView = view.findViewById(R.id.cardView);
//            tvTitle = view.findViewById(R.id.tv_title);
////            if(isFilter){
////                ll_brand = view.findViewById(R.id.ll_brand);
////            }
//        }
//    }
//
//    public SeasonAdapter(Context context , List<Season> seasons, boolean filterScreen) {
//        this.mContext = context;
//        this.seasonsList = seasons;
//        this.isFilter = filterScreen;
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView;
////        if(isFilter){
//            itemView = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_cardview, parent, false);
////        }
////        else{
////            itemView = LayoutInflater.from(parent.getContext())
////                    .inflate(R.layout.layout_brand_image, parent, false);
////        }
//        return new MyViewHolder(itemView);
//    }
//
//
//    public void loadImage(String imageTitle, MyViewHolder holder){
//        switch (imageTitle){
//            case "Season One":  Glide.with(mContext).load(AppUtil.getImage(mContext,"season_one_bg")).into(holder.imageView); break;
//            case "Season Two":  Glide.with(mContext).load(AppUtil.getImage(mContext,"season_two_bg")).into(holder.imageView); break;
//            case "Season Three":  Glide.with(mContext).load(AppUtil.getImage(mContext,"season_three_bg2")).into(holder.imageView); break;
//            case "Season Four": Glide.with(mContext).load(AppUtil.getImage(mContext,"season_four_bg")).into(holder.imageView); break;
//            case "Season Five":  Glide.with(mContext).load(AppUtil.getImage(mContext,"season_five_bg")).into(holder.imageView); break;
//        }
//    }
//    @Override
//    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        if(!holders.contains(holder) && holders.size() != seasonsList.size()){
//            holders.add(holder);
//        }
//        final Season season = seasonsList.get(position);
//
//        loadImage(season.getSeasonName(),holder);
//        holder.tvTitle.setText(season.getSeasonName());
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if(season.isSelected()){
////                    season.setSelected(false);
////                }
////                else{
////                    season.setSelected(true);
////                }
//////                    row_index = brand.getName();
////                notifyDataSetChanged();
//                Intent intent = new Intent(mContext, EpisodeActivity.class);
//                intent.putExtra("episodeData", (Serializable) season.getEpisodes());
//                mContext.startActivity(intent);
//            }
//        });
////        if(isFilter) {
//
////            if (season.isSelected()){
////                Log.d("Clicked ","Selected Position :: ");
//////                holders.get(row_index).ll_brand.setBackgroundResource(R.drawable.bg_curved_grey_transparent);
//////                holders.get(row_index).isSelected = false;
////                ((MyViewHolder) holder).ll_brand.setBackgroundResource(R.drawable.bg_curved_selected);
////                ((MyViewHolder) holder).imageView.setColorFilter(mContext.getResources().getColor(R.color.white));
////            } else {
////                Log.d("Clicked ","UnSelected Position :: ");
//////                holders.get(row_index).isSelected = true;
////                ((MyViewHolder) holder).ll_brand.setBackgroundResource(R.drawable.bg_curved_grey_transparent);
////                ((MyViewHolder) holder).imageView.setColorFilter(mContext.getResources().getColor(R.color.colorTitleFragmentOne));
//////                holders.get(row_index).ll_brand.setBackgroundResource(R.drawable.bg_curved_selected);
////            }
////        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return seasonsList.size();
//    }
//}
