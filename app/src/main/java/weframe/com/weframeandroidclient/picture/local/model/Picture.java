package weframe.com.weframeandroidclient.picture.local.model;

import java.io.File;

public class Picture {

    private final File file;

    public Picture(final File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
