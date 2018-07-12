package andreavieira.parsetagram.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import andreavieira.parsetagram.R;
import andreavieira.parsetagram.model.Post;

public class PostingFragment extends Fragment {

    private static final String imagePath = "ufi_heart_icon.xml";
    private EditText descriptionInput;
    private Button camButton;
    private Button postButton;
    private ImageView postPic;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    String message;
    ParseUser user;


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

        postButton.setVisibility(View.INVISIBLE);
        postPic.setVisibility(View.INVISIBLE);
        descriptionInput.setVisibility(View.INVISIBLE);


        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photoFile = getPhotoFileUri(photoFileName);

                // wrap File object into a content provider
                // required for API >= 24
                // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
                Uri fileProvider = FileProvider.getUriForFile(getActivity(), "andreavieira.parsetagram", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
            if (takePictureIntent.resolveActivity(PostingFragment.this.getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
            message = descriptionInput.getText().toString();
            user = ParseUser.getCurrentUser();

                final File file = getPhotoFileUri(photoFileName);
                final ParseFile parseFile = new ParseFile(file);

                parseFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            createPost(message, parseFile, user);
                            Log.d("CameraFragment", "parse file successfully saved in background");
                        } else {
                            Log.d("CameraFragment", "parse file failed to saved in background");
                            e.printStackTrace();
                        }
                    }
                });
                // ByteArrayOutputStream
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
//            imageBitmap = (Bitmap) extras.get("data");
            String path = photoFile.getAbsolutePath();
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            postPic.setImageBitmap(bitmap);

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

        postButton.setVisibility(View.INVISIBLE);
        postPic.setVisibility(View.INVISIBLE);
        descriptionInput.setVisibility(View.INVISIBLE);
        camButton.setVisibility(View.VISIBLE);
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }
}

