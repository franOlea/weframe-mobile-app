package weframe.com.weframeandroidclient.picture.local;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import weframe.com.weframeandroidclient.AsyncOperationRequestListener;
import weframe.com.weframeandroidclient.picture.RemotePicturesManager;
import weframe.com.weframeandroidclient.picture.local.model.PictureViewHolder;

public class UploadButtonPressedListener implements View.OnClickListener, AsyncOperationRequestListener {

    private final RemotePicturesManager remotePicturesManager;
    private final PictureGalleryAdapter pictureGalleryAdapter;
    private final PictureViewHolder pictureViewHolder;
    private final int index;
    private boolean working = false;

    public UploadButtonPressedListener(final RemotePicturesManager remotePicturesManager,
                                       final PictureGalleryAdapter pictureGalleryAdapter,
                                       final PictureViewHolder pictureViewHolder,
                                       final int index) {
        this.remotePicturesManager = remotePicturesManager;
        this.pictureGalleryAdapter = pictureGalleryAdapter;
        this.pictureViewHolder = pictureViewHolder;
        this.index = index;
    }

    @Override
    public void onClick(View v) {
        if(!working) {
            working = true;
            Log.d("UPLOAD_BUTTON_PRESSED " + index,
                    String.format("position = %d, holder_adapter_position = %d, size = %d",
                            index, pictureViewHolder.getAdapterPosition(), pictureGalleryAdapter.getItemCount()));
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Log.i("PICTURE UPLOADED", "Attempting to upload...");
                        onComplete(remotePicturesManager.uploadPicture(pictureViewHolder));
                        Log.i("PICTURE UPLOADED", "The picture was sent. ");
                    } catch (Exception e) {
                        onError(e);
                    } finally {
                        working = false;
                    }
                    return null;
                }
            }.execute();
        }
    }

    @Override
    public void onComplete(boolean success) {
        if(success) {
            displayToast("Imagen subida con exito.");
        } else {
            displayToast("La imagen no pudo ser subida.");
        }
    }

    @Override
    public void onError(Throwable throwable) {
        displayToast(throwable.getMessage());
    }

    private void displayToast(String message) {
        Log.i("PICTURE TOAST", message);
//        Toast toast = Toast.makeText(pictureGalleryAdapter.getContext(), message, Toast.LENGTH_SHORT);
//        toast.show();
    }
}