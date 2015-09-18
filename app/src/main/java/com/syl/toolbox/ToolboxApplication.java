package com.syl.toolbox;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * Created by shenyunlong on 2015/9/17.
 */
public class ToolboxApplication extends Application {

    public static final String TAG = ToolboxApplication.class.getSimpleName();

    public static final int THREAD_POOL_SIZE = 3;
    public static final int THREAD_PRIORITY = Thread.NORM_PRIORITY - 2;
    public static final int MEMORY_CACHE_SIZE = 2 * 1024 * 1024;  // 2MB
    public static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;   // 50MB

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate");

        initImageLoader(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        Log.d(TAG, "onTerminate");
    }

    /**
     * 初始化Universal Image Loader
     *
     * @param context
     */
    private void initImageLoader(Context context) {
        Log.d(TAG, "initImageLoader");

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);

        // thread
        config.threadPoolSize(THREAD_POOL_SIZE);
        config.threadPriority(THREAD_PRIORITY);
        config.denyCacheImageMultipleSizesInMemory();
        // memory cache
        config.memoryCache(new LruMemoryCache(MEMORY_CACHE_SIZE));
        config.memoryCacheSize(MEMORY_CACHE_SIZE);
        // disk cache
        config.diskCache(new UnlimitedDiskCache(StorageUtils.getCacheDirectory(context)));
        config.diskCacheSize(DISK_CACHE_SIZE);
        // enable debug log
        config.writeDebugLogs();

        ImageLoader.getInstance().init(config.build());
    }
}
