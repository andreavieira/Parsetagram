package andreavieira.parsetagram.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import andreavieira.parsetagram.glide.GlideApp;
import andreavieira.parsetagram.R;
import andreavieira.parsetagram.model.Post;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> timelinePosts;
    Context context;

    public PostAdapter(List<Post> posts) {
        timelinePosts = posts;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        // Get data
        Post post = timelinePosts.get(position);

        // Populate views accordingly to data:

        // Get usernane
        holder.tvUser.setText(post.getUser().getUsername());
        // Get users profile image
        GlideApp.with(context)
                .load(post.getProfileImage().getUrl())
                .circleCrop()
                .into(holder.ivUser);
        // Get post image
        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(holder.ivPost);
        // Get likes
        //viewHolder.tvLikes.setText(post.getLikes());
        // Get caption
        holder.tvCaption.setText(post.getCaption());
        // Get timestamp
        holder.tvDate.setText(post.getRelativeTimeAgo());

    }

    @Override
    public int getItemCount() {
        return timelinePosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivPost;
        public ImageView ivUser;
        public TextView tvUser;
        public TextView tvCaption;
        public TextView tvLikes;
        public TextView tvDate;
        public ImageView ivLike;
        public ImageView ivComment;

        public ViewHolder(View itemView) {
            super(itemView);

            // Perform findViewById lookups
            ivPost = itemView.findViewById(R.id.post_iv);
            ivUser = itemView.findViewById(R.id.user_iv);
            tvUser = itemView.findViewById(R.id.user_tv);
            tvCaption = itemView.findViewById(R.id.caption_tv);
            tvLikes = itemView.findViewById(R.id.likes_tv);
            tvDate = itemView.findViewById(R.id.date_tv);
            ivLike = itemView.findViewById(R.id.like_iv);
            ivComment = itemView.findViewById(R.id.post_iv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {

        }
    }

    public void clear() {
        timelinePosts.clear();
        notifyDataSetChanged();
    }
}
