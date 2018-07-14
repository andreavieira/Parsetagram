package andreavieira.parsetagram.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import andreavieira.parsetagram.activities.LoginActivity;
import andreavieira.parsetagram.R;
import andreavieira.parsetagram.adapters.GridAdapter;
import andreavieira.parsetagram.adapters.PostAdapter;
import andreavieira.parsetagram.glide.GlideApp;
import andreavieira.parsetagram.model.Post;

public class UserFragment extends Fragment {
    Button logoutButton;
    TextView username;
    ImageView profPic;
    ArrayList<Post> posts;
    RecyclerView gridRv;
    GridAdapter gridAdapter;
    private SwipeRefreshLayout swipeContainer;

    private static final String KEY_PROFILE = "profile";
    ParseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
//        swipeContainer = view.findViewById(R.id.swipeContainer);
//        // Setup refresh listener which triggers new data loading
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Your code to refresh the list here.
//                // Make sure you call swipeContainer.setRefreshing(false) Once the network request has completed successfully.
//                swipeContainer.setRefreshing(true);
//                fetchTimelineAsync(0);
//            }
//        });

        logoutButton = view.findViewById(R.id.logout_btn);
        username = view.findViewById(R.id.profile_tv);
        profPic = view.findViewById(R.id.profile_iv);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        //gridRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //gridRv.setAdapter(gridAdapter);

        user = ParseUser.getCurrentUser();

        username.setText(user.getUsername());

        if(user.get(KEY_PROFILE) != null) {
            ParseFile photoFile = user.getParseFile(KEY_PROFILE);
            GlideApp.with(getContext()).load(photoFile.getUrl()).circleCrop()
                    .into(profPic);
        }

        posts = new ArrayList<>();
        gridRv = view.findViewById(R.id.profile_rv);
        gridAdapter = new GridAdapter(posts);
        gridRv.setLayoutManager(new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false));
        gridRv.setAdapter(gridAdapter);

        loadTimeline();
    }

    private void loadTimeline() {
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i += 1) {
                        Log.d("UserFragment", "Post[" + i + "] = "
                                + objects.get(i).getCaption()
                                + "\nusername = " + objects.get(i).getUser().getUsername());
                        // checking if grabbing right info and post object unwraps the user
                        posts.add(0, objects.get(i));
                        gridAdapter.notifyItemInserted(0);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

//    public void fetchTimelineAsync(int page) {
//        gridAdapter.clear();
//        loadTimeline();
//        swipeContainer.setRefreshing(false);
//    }
}
