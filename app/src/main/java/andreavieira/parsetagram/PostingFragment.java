package andreavieira.parsetagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;

import andreavieira.parsetagram.model.Post;

public class PostingFragment extends Fragment {

    private static final String imagePath = "ufi_heart_icon.xml";
    private EditText descriptionInput;
    private Button camButton;
    private Button postButton;
    private ImageView postPic;
    public static final int REQUEST_IMAGE_CAPTURE = 1;


    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;

    public Bitmap imageBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        descriptionInput = view.findViewById(R.id.description_et);
        // createButton = view.findViewById(R.id.create_btn);
        // refreshButton = view.findViewById(R.id.refresh_btn);
        camButton = view.findViewById(R.id.cam_btn);
        postButton = view.findViewById(R.id.post_btn);
        postPic = view.findViewById(R.id.ivPostPic);

        //postButton.setVisibility(View.INVISIBLE);
        //postPic.setVisibility(View.INVISIBLE);
        //descriptionInput.setVisibility(View.INVISIBLE);


        camButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(PostingFragment.this.getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
        });

        postButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
            String message = descriptionInput.getText().toString();
            ParseUser user = ParseUser.getCurrentUser();

            // ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            // imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            // String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), imageBitmap, "Title", null);
            // Log.d("CameraFragment", "Path: " + path);
            // return Uri.parse(path);


            // ParseFile parseFile = null;
            // createPost(message, parseFile, user);
        }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == -1) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            postPic.setImageBitmap(imageBitmap);

            Uri orgUri = data.getData();
            Log.d("PostingFragment", "URI: " + orgUri);

            postPic.setVisibility(View.VISIBLE);
            descriptionInput.setVisibility(View.VISIBLE);
            camButton.setVisibility(View.INVISIBLE);
            postButton.setVisibility(View.VISIBLE);
        }
    }

    private void createPost(final String description, final ParseFile imageFile, final ParseUser user) {
        Log.d("MainActivity","New Post is saved");
        Post newPost = Post.newInstance(user, imageFile, description);
    }
}

