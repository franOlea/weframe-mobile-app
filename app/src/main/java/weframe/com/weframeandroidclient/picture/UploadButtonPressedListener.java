package weframe.com.weframeandroidclient.picture;

import android.util.Log;
import android.view.View;

class UploadButtonPressedListener implements View.OnClickListener {

    private final RemotePicturesManager remotePicturesManager;
    private final PictureGalleryAdapter pictureGalleryAdapter;
    private final PictureViewHolder pictureViewHolder;
    private final int index;
    private boolean working = false;

    UploadButtonPressedListener(final RemotePicturesManager remotePicturesManager,
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
            boolean success = remotePicturesManager.uploadPicture(pictureViewHolder);
            Log.i("PICTURE UPLOADED", "The picture was uploaded. " + success);
            working = false;
        }
    }
}