package by.test2rest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.test2rest.internet.get.Post;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoViewHolder>{

    private List<Post> postList = new ArrayList<>();
    public final String LOG = "text";

    public void setPostList(List<Post> postList) {
        this.postList = postList;
        Log.e(LOG, "setPostList");
    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        Log.e(LOG, "OnCreateViewHolder");
        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder holder, int position) {
        Log.e(LOG, "OnBindViewHolder");
        Post post = postList.get(position);

        holder.textView.setText(post.getTitle());
        Log.e("text", post.getTitle());

    }

    @Override
    public int getItemCount() {
        Log.e(LOG, "getItemCount");
        return postList.size();
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        public InfoViewHolder(View itemView) {
            super(itemView);
            Log.e(LOG, "InfoViewHolder");
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
