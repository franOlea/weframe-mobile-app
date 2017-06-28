package weframe.com.weframeandroidclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabs();
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
        
//        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String tabId) {
//                Log.i("AndroidTabsDemo", "Pestaña pulsada : " + tabId);
//                Toast.makeText(MainActivity.this, "Pestaña pulsada: " + tabId, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
