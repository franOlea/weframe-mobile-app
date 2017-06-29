package weframe.com.weframeandroidclient.picture.local.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import weframe.com.weframeandroidclient.R;

public class PictureViewHolder extends RecyclerView.ViewHolder {

    private final ImageView imageView;
    private final Button removeButton;
    private final Button uploadButton;

    private File file;

    public PictureViewHolder(View view) {
        super(view);
        this.imageView = (ImageView) view.findViewById(R.id.img);
        this.removeButton = (Button) view.findViewById(R.id.removeButton);
        this.uploadButton = (Button) view.findViewById(R.id.uploadButton);
    }

    public void setFile(final File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public Button getUploadButton() {
        return uploadButton;
    }
}
