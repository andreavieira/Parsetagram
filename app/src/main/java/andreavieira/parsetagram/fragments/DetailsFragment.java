package andreavieira.parsetagram.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import andreavieira.parsetagram.R;
import andreavieira.parsetagram.glide.GlideApp;
import andreavieira.parsetagram.model.Post;

public class DetailsFragment extends Fragment {

    public ImageView ivPost;
    public TextView tvUsername;
    public TextView tvTopUsername;
    public TextView tvCaption;
    public TextView tvTimestamp;

    public ImageView ivLike;
    public ImageView ivUser;


    private static final String KEY_USER_PICTURE = "profileImage";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_post, container, false);
    }

    @Override
    public void onViewCreated(View itemView, Bundle savedInstanceState) {
        super.onViewCreated(itemView, savedInstanceState);
        String id = savedInstanceState.getString("PostId");

        // Initiate all the Views in DetailsFragment
        // Perform findViewById lookups
        ivPost = itemView.findViewById(R.id.post_iv);
        tvUsername = itemView.findViewById(R.id.user_tv);
        tvTimestamp = itemView.findViewById(R.id.timestamp_tv);
        ivLike = itemView.findViewById(R.id.like_iv);
        ivUser = itemView.findViewById(R.id.user_iv);

        Post.Query query = new Post.Query().withUser();
        query.getQuery(Post.class).getInBackground(id, new GetCallback<Post>() {
            @Override
            public void done(Post post, ParseException e) {
                // assign Views values in PostDetailsFragment
                try {
                    tvUsername.setText(post.getUser().fetchIfNeeded().getUsername());
                    tvCaption.setText(post.getCaption());
                    tvTimestamp.setText(post.getRelativeTimeAgo().toUpperCase());

                    GlideApp.with(getContext())
                            .load(post.getImage().getUrl())
                            .into(ivPost);

                    ParseUser postUser = post.getUser();
                    if (postUser.get(KEY_USER_PICTURE) != null) {
                        ParseFile photoFile = postUser.getParseFile(KEY_USER_PICTURE);
                        GlideApp.with(getContext()).load(photoFile.getUrl()).circleCrop()
                                .into(ivUser);
                    }
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

}

