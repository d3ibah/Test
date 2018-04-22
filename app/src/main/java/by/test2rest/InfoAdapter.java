package by.test2rest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import by.test2rest.internet.get.Photo;
import by.test2rest.internet.get.Post;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoViewHolder>{

    private List<Post> postList = new ArrayList<>();
    private List<Photo> photoList = new ArrayList<>();
    private Photo photo;
    private String imageUrl;

    private ClickListener listenerInterface;

    public InfoAdapter(ClickListener listenerInterface) {
        this.listenerInterface = listenerInterface;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }

    public Photo getPhotoByPosition (int position){
        return photoList.get(position);
    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder holder, final int position) {
        final Post post = postList.get(position);

        holder.textView.setText(post.getTitle());
        Glide.with(holder.itemView.getContext())
                .load(getImageUrl(position))
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerInterface.onClick(getPhotoByPosition(position), post);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    // construct image url
    public String getImageUrl(int position) {
        String photoSize;

        photo = photoList.get(position);
        photoSize = "q";
        imageUrl = "https://farm" + photo.getFarm() + ".staticflickr.com/" + photo.getServer()
                + "/" + photo.getId() + "_" + photo.getSecret() + "_" + photoSize + ".jpg";
        return imageUrl;
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        public InfoViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
