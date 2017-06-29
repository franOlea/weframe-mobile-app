package weframe.com.weframeandroidclient.picture;

import android.util.Log;
import android.view.View;

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

    }
}
