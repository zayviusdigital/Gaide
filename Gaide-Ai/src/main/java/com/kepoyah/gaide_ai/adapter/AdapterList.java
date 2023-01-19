package com.kepoyah.gaide_ai.adapter;

import static maes.tech.intentanim.CustomIntent.customType;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.kepoyah.gaide_ai.R;
import com.kepoyah.gaide_ai.activity.Detail;
import com.kepoyah.gaide_ai.ads.admob.AdInterstitialAi;
import com.kepoyah.gaide_ai.ads.admob.AdNativeAi;
import com.kepoyah.gaide_ai.config.SettingsAi;
import com.kepoyah.gaide_ai.model.ModelList;
import com.zayviusdigital.artificialintelligence.listener.OnListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ModelList> itemsList;
    private final Context context;
    private final LayoutInflater mLayoutInflater;
    private static final int DEFAULT_VIEW_TYPE = 1;
    private static final int NATIVE_AD_VIEW_TYPE = 2;

    public AdapterList(List<ModelList> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == NATIVE_AD_VIEW_TYPE) {
            v = mLayoutInflater.inflate(R.layout.list_native, parent, false);
            return new NativeAdViewHolder(v);
        }
        v = mLayoutInflater.inflate(R.layout.list_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (!(holder instanceof ImageViewHolder)) {
            return;
        }
        ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
        if (!(itemsList.get(position) ==null)){
            imageViewHolder.title.setText(itemsList.get(position).getJudul());
            imageViewHolder.title.setSelected(true);
        }
        holder.itemView.setOnClickListener(v -> {
            AdInterstitialAi.SHOW((Activity) context, position, new OnListener() {
                @Override
                public void load() {
                    go_detail(position);
                }

                @Override
                public void succeed() {
                    go_detail(SettingsAi.position_detail_ai);
                }

                @Override
                public void failed() {
                    go_detail(position);
                }
            });
        });

    }

    private void go_detail(Integer position){
        Intent intent = new Intent(context, Detail.class);
        intent.putExtra("id", itemsList.get(position).getId());
        intent.putExtra("title",itemsList.get(position).getJudul());
        intent.putExtra("content", itemsList.get(position).getContent());
        context.startActivity(intent);
        customType(context,"fadein-to-fadeout");
    }

    @Override
    public int getItemViewType(int position) {
        if ((position+1)%3 == 0 && position!=0) {
            return NATIVE_AD_VIEW_TYPE;
        }
        return DEFAULT_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFilter(ArrayList<ModelList> filterModel) {
        itemsList = new ArrayList<>();
        itemsList.addAll(filterModel);
        notifyDataSetChanged();
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }

    }

    public class NativeAdViewHolder extends RecyclerView.ViewHolder {

        CardView admob;
        FrameLayout frameLayout;

        public NativeAdViewHolder(final View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.native_ad);
            admob = itemView.findViewById(R.id.native_ad_car);
            frameLayout.setVisibility(View.VISIBLE);
            AdNativeAi.GET((Activity) context, frameLayout, new OnListener() {
                @Override
                public void failed() {
                    super.failed();
                    admob.setVisibility(View.GONE);
                }
            });
        }
    }

}
