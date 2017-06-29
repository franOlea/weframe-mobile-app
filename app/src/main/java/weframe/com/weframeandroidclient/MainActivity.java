package weframe.com.weframeandroidclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import weframe.com.weframeandroidclient.picture.Picture;
import weframe.com.weframeandroidclient.picture.PictureGalleryAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initTabs();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initTabs() {
        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec localGalleryTab = tabs.newTabSpec("Imagenes locales");
        localGalleryTab.setContent(R.id.local_app_image_gallery);
        localGalleryTab.setIndicator("Locales");

        TabHost.TabSpec remoteGalleryTab = tabs.newTabSpec("Imagenes subidas");
        remoteGalleryTab.setContent(R.id.remote_image_gallery);
        remoteGalleryTab.setIndicator("Subidas"); // ContextCompat.getDrawable(this, android.R.drawable.ic_dialog_map)

        tabs.addTab(localGalleryTab);
        tabs.addTab(remoteGalleryTab);
        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                doOnTabChanged(tabId);
            }
        });

        tabs.setCurrentTab(0);
        loadLocalGallery();
    }

    private void doOnTabChanged(String tabId) {
        Log.i("TAG CHANGED", "tabId: " + tabId);
        if(tabId.equals("Imagenes locales")) {
            loadLocalGallery();
        }
    }

    private void loadLocalGallery() {
        Log.i("LOCAL GALLERY", "LOADING...");
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Picture> list = prepareData();
        PictureGalleryAdapter adapter = new PictureGalleryAdapter(getApplicationContext(), list);
        recyclerView.setAdapter(adapter);
        Log.i("LOCAL GALLERY", "LOADED");
    }

    private ArrayList<Picture> prepareData() {
        Log.i("LOCAL GALLERY", "LOADING DATA...");
        ArrayList<Picture> pictures = new ArrayList<>();
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (dir != null) {
            File[] listOfFiles = dir.listFiles();
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    Log.i("LOADING FILE", file.getAbsolutePath());
                    pictures.add(new Picture(file));
                }
            }
        }
        Log.i("LOCAL GALLERY", "DATA LOADED");
        return pictures;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settings:

                return true;
            case R.id.take_picture:
                dispatchTakePictureIntent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                File photoFile = createImageFile();
                Uri photoURI = FileProvider.getUriForFile(
                        this,
                        "weframe.com.weframeandroidclient",
                        photoFile
                );
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("ERROR CREATING FILE", "", ex);
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            if(imageBitmap != null) {
//                Log.i("CAMERA", "PICTRUE TAKEN SIZE " + imageBitmap.getByteCount());
//            }
////            mImageView.setImageBitmap(imageBitmap);
//        }
//    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
//        image.getAbsolutePath();
        return image;
    }
}
