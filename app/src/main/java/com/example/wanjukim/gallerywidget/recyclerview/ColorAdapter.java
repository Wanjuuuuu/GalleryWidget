package com.example.wanjukim.gallerywidget.recyclerview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wanjukim.gallerywidget.R;
import com.example.wanjukim.gallerywidget.activities.TextMenuActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wanju Kim on 2017-08-31.
 */

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {
    private Context mcontext;
    private LayoutInflater minflater;
    private List<Color> colorList=new ArrayList<>();
    private ColorClickListener mclickListener;

    public ColorAdapter(Context context){
        mcontext=context;
        minflater=LayoutInflater.from(context);
    }

    public void setOnItemClickListener(ColorClickListener listener){
        mclickListener=listener;
    }

    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ColorViewHolder(minflater.inflate(R.layout.color_recycle_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(ColorViewHolder holder, int position) {
        Color color=colorList.get(position);
        holder.bind(color);
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public void addColorList(List<Color> colorList){
        this.colorList.addAll(colorList);
    }

    class ColorViewHolder extends RecyclerView.ViewHolder {
        private ImageView square;
        private ImageView selected;
        private Color color;

        ColorViewHolder(final View itemView) {
            super(itemView);
            square = (ImageView) itemView.findViewById(R.id.color_square); // recyclerView
            selected=(ImageView)itemView.findViewById(R.id.color_background); // when being selected

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mclickListener.onClick(color); // selected color

                    for(Color color:colorList)
                        color.setFlag(false);

                    color.setFlag(true);
                    notifyDataSetChanged();
                }
            });
        }

        private void bind(Color color){
            this.color=color;
            square.setBackgroundColor(color.getColor()); // colorFilter does not work

            if(color.isFlag())
                selected.setBackgroundColor(ContextCompat.getColor(mcontext,R.color.gray));
            else
                selected.setBackgroundColor(ContextCompat.getColor(mcontext,R.color.backgroundDefault));
        }
    }

    public interface ColorClickListener{
        public void onClick(Color color);
    }
}
