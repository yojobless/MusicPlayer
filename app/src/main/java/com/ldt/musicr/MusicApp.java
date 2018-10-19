package com.ldt.musicr;

import android.app.Application;

import com.ldt.musicr.permission.Nammu;
import com.ldt.musicr.utils.PreferencesUtility;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.L;

import java.io.IOException;
import java.io.InputStream;

public class MusicApp extends Application {
    private static MusicApp mInstance;

    public static synchronized MusicApp getInstance() {
        return mInstance;
    }
@Override
    public void onCreate() {
    super.onCreate();
    mInstance = this;
    ImageLoaderConfiguration localImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this).imageDownloader(new BaseImageDownloader(this) {
        PreferencesUtility prefs = PreferencesUtility.getInstance(MusicApp.this);

        @Override
        protected InputStream getStreamFromNetwork(String imageUri, Object extra) throws IOException {
            if (prefs.loadArtistImages()) return super.getStreamFromNetwork(imageUri, extra);
            throw new IOException();
        }
    }).build();

    ImageLoader.getInstance().init(localImageLoaderConfiguration);
    L.writeLogs(false);
    L.disableLogging();
    L.writeDebugLogs(false);
    Nammu.init(this);

}
}