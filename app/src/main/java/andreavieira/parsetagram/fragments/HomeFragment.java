package andreavieira.parsetagram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import andreavieira.parsetagram.adapters.PostAdapter;
import andreavieira.parsetagram.R;
import andreavieira.parsetagram.model.Post;

public class HomeFragment extends Fragment {
    RecyclerView timelineRv;
    PostAdapter postAdapter;
    ArrayList<Post> posts;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false) Once the network request has completed successfully.
                swipeContainer.setRefreshing(true);
                fetchTimelineAsync(0);
            }
        });

        posts = new ArrayList<>();
        timelineRv = view.findViewById(R.id.timeline_rv);
        postAdapter = new PostAdapter(posts);
        timelineRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        timelineRv.setAdapter(postAdapter);

        loadTimeline();
    }

    private void loadTimeline() {

    }

    public void fetchTimelineAsync(int page) {

    }

    public void populateTimeline(){

    }

}
