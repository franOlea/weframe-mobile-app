package weframe.com.weframeandroidclient.picture.local;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import weframe.com.weframeandroidclient.R;
import weframe.com.weframeandroidclient.picture.RemotePicturesManager;
import weframe.com.weframeandroidclient.picture.local.model.Picture;
import weframe.com.weframeandroidclient.picture.local.model.PictureViewHolder;

public class PictureGalleryAdapter extends RecyclerView.Adapter<PictureViewHolder> {

    private final Context context;
    private final List<Picture> list;
    private final RemotePicturesManager remotePicturesManager;

    public PictureGalleryAdapter(final Context context,
                                 final List<Picture> list,
                                 final RemotePicturesManager remotePicturesManager) {
        this.context = context;
        this.list = list;
        this.remotePicturesManager = remotePicturesManager;
    }

    public void updateData(List<Picture> updatedList) {
        Log.i(this.getClass().getName(), "Data update requested.");
        this.list.clear();
        this.list.addAll(updatedList);
        notifyDataSetChanged();
    }

    void remove(final PictureViewHolder pictureViewHolder) {
        this.list.remove(pictureViewHolder.getAdapterPosition());
        notifyItemRemoved(pictureViewHolder.getAdapterPosition());
        Log.i(this.getClass().getName(), "Picture view holder removed.");
    }

    @SuppressWarnings("unused")
    public void add(final Picture picture) {
        this.list.add(picture);
        notifyItemInserted(this.list.size() - 1);
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_thumbnail, viewGroup, false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PictureViewHolder viewHolder, final int i) {
        viewHolder.getImageView().setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.setFile(list.get(i).getFile());
        viewHolder.getImageView().setImageBitmap(getPictureByFile(list.get(i).getFile()));
        viewHolder.getRemoveButton().setOnClickListener(new DeleteButtonPressedListener(this, viewHolder, i));
        viewHolder.getUploadButton().setOnClickListener(new UploadButtonPressedListener(remotePicturesManager, this, viewHolder, i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private Bitmap getPictureByFile(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, bitmap.getWidth()/10, bitmap.getHeight()/10);
        return bitmap;
    }

    public Context getContext() {
        return context;
    }
}
