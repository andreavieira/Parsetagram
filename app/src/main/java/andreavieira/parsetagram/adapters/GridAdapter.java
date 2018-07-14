package andreavieira.parsetagram.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import andreavieira.parsetagram.R;
import andreavieira.parsetagram.activities.MainActivity;
import andreavieira.parsetagram.activities.RegisterActivity;
import andreavieira.parsetagram.fragments.DetailsFragment;
import andreavieira.parsetagram.fragments.UserFragment;
import andreavieira.parsetagram.model.Post;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private List<Post> myPosts;
    Context context;

    public GridAdapter(List<Post> posts) {
        myPosts = posts;
    }

    @NonNull
    @Override
    public GridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_mypost, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get data
        Post post = myPosts.get(position);

        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(holder.ivPost);
    }

    @Override
    public int getItemCount() {
        return myPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivPost;

        public ViewHolder(View itemView) {
            super(itemView);

            // Perform findViewById lookups
            ivPost = itemView.findViewById(R.id.mypost_iv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            // TODO - Make grid clickable for more detailed view
//            final Intent intent = new Intent(context, DetailsFragment.class);
//            //startActivity(intent);
//            //finish();
//            int position = getAdapterPosition();
//            // Make sure the position exists
//            if (position != RecyclerView.NO_POSITION) {
//                // Get the post at the position, this won't work if the class is static
//                Post post = myPosts.get(position);
//            }
        }

        public void clear() {
            myPosts.clear();
            notifyDataSetChanged();
        }
    }
}
