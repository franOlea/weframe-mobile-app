package weframe.com.weframeandroidclient;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
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
import android.widget.Toast;

import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import weframe.com.weframeandroidclient.picture.RemotePicturesManager;
import weframe.com.weframeandroidclient.picture.local.LocalPicturesManager;
import weframe.com.weframeandroidclient.picture.local.model.Picture;
import weframe.com.weframeandroidclient.picture.local.PictureGalleryAdapter;

public class MainActivity extends AppCompatActivity {
    private Boolean exit = false;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private PictureGalleryAdapter adapter;
    private LocalPicturesManager localPicturesManager;
    private RemotePicturesManager remotePicturesManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initTabs();
        initPictureManagers();
        initAdapter();
        initLocalGallery();
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
    }

    private void initAdapter() {
        adapter = new PictureGalleryAdapter(getApplicationContext(), new ArrayList<Picture>(), remotePicturesManager);
    }

    private void initPictureManagers() {
        localPicturesManager = new LocalPicturesManager(this);
        remotePicturesManager = new RemotePicturesManager();
    }

    private void initLocalGallery() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        triggerLocalGallery();
    }

    private void doOnTabChanged(String tabId) {
        Log.i(this.getClass().getName(), "tabId: " + tabId);
        if(tabId.equals("Imagenes locales")) {
            triggerLocalGallery();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                File photoFile = localPicturesManager.createImageFile();
                Uri photoURI = FileProvider.getUriForFile(
                        this,
                        "weframe.com.weframeandroidclient",
                        photoFile
                );
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException ex) {
                Log.e("ERROR CREATING FILE", "There was an error creating the image file.", ex);
            }
        }
    }

    private void triggerLocalGallery() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                final List<Picture> list = localPicturesManager.loadLocalPictures();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateData(list);
                    }
                });
                return null;
            }
        }.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.i("CAMERA INTENT RESULT", "Camera picture taken and stored. Refreshing list...");
            triggerLocalGallery();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
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

    @Override
    public void onBackPressed() {
        if(exit) {
            finish();
        } else {
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3000);
        }
    }
}
