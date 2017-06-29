package weframe.com.weframeandroidclient.picture.local;

import android.util.Log;
import android.view.View;

import weframe.com.weframeandroidclient.picture.local.model.PictureViewHolder;

class DeleteButtonPressedListener implements View.OnClickListener {

    private final PictureGalleryAdapter pictureGalleryAdapter;
    private final PictureViewHolder pictureViewHolder;
    private final int index;

    DeleteButtonPressedListener(final PictureGalleryAdapter pictureGalleryAdapter,
                                       final PictureViewHolder pictureViewHolder,
                                       final int index) {
        this.pictureGalleryAdapter = pictureGalleryAdapter;
        this.pictureViewHolder = pictureViewHolder;
        this.index = index;
    }

    @Override
    public void onClick(View v) {
        Log.d("DELETED_BUTTON_PRESSED " + index,
                String.format("position = %d, holder_adapter_position = %d, size = %d",
                        index, pictureViewHolder.getAdapterPosition(), pictureGalleryAdapter.getItemCount()));
        pictureGalleryAdapter.remove(pictureViewHolder);
        if(pictureViewHolder.getFile() != null) {
            boolean succeess = pictureViewHolder.getFile().delete();
            Log.i("PICTURE_DELETED", "The picture file has been deleted " + succeess);
        } else {
            Log.i("PICTURE_NOT_DELETED", "The picture file has NOT been deleted ");
        }
    }
}
