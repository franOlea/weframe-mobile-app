package weframe.com.weframeandroidclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

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

        tabs.setCurrentTab(0);
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
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if(imageBitmap != null) {
                Log.i("CAMERA", "PICTRUE TAKEN SIZE " + imageBitmap.getByteCount());
            }
//            mImageView.setImageBitmap(imageBitmap);
        }
    }
}
