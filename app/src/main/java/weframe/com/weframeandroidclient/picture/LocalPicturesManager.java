package weframe.com.weframeandroidclient.picture;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LocalPicturesManager {

    private final Context context;

    public LocalPicturesManager(final Context context) {
        this.context = context;
    }

    public ArrayList<Picture> loadLocalPictures() {
        Log.i(this.getClass().getName(), "LOADING PICTURES...");
        ArrayList<Picture> pictures = new ArrayList<>();
        File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (dir != null) {
            File[] listOfFiles = dir.listFiles();
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    Log.i("LOCAL PICTURES", file.getAbsolutePath());
                    pictures.add(new Picture(file));
                }
            }
        }
        Log.i(this.getClass().getName(), String.format("PICTURES LOADED [%s]", pictures.size()));
        return pictures;
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }
}
