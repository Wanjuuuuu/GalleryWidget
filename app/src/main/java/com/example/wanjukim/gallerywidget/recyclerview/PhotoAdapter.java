package com.example.wanjukim.gallerywidget.recyclerview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wanjukim.gallerywidget.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.R.id.parent;

/**
 * Created by Wanju Kim on 2017-07-30.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private Context mcontext;
    private LayoutInflater minflater;
    private List<Photo> photoList=new ArrayList<>();
    private PhotoClickListener mclickListener;

    public PhotoAdapter(Context context){
        mcontext = context;
        minflater=LayoutInflater.from(context);
    }

    public void setOnItemClickListener(PhotoClickListener listener){
        mclickListener=listener;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoViewHolder(minflater.inflate(R.layout.photo_recycle_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Photo photo=photoList.get(position);
        holder.bind(photo);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void addPhotoList(List<Photo> photoList){
        this.photoList.addAll(photoList);
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder{
        private ImageView photoImage;
        private Photo photo;

        PhotoViewHolder(final View itemView){
            super(itemView);
            photoImage=(ImageView)itemView.findViewById(R.id.photo_image);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mclickListener.onClickPhoto(photo);

                    for(Photo photo : photoList)
                        photo.setFlag(false);

                    photo.setFlag(true);
                    notifyDataSetChanged();
                }
            });
        }

        private void bind(Photo photo){
            this.photo=photo;
            Glide.with(mcontext).load(photo.getPath()).into(photoImage);

            if(photo.isFlag())
                itemView.setBackgroundColor(ContextCompat.getColor(mcontext,R.color.gray));

            else
                itemView.setBackgroundColor(ContextCompat.getColor(mcontext,R.color.white));
//            photoPath.setText(photo.getPath());
        }
    }

    public interface PhotoClickListener{
        public void onClickPhoto(Photo photo);
    }
}


