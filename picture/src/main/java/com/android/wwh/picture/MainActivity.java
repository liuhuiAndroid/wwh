package com.android.wwh.picture;

import android.Manifest;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.wwh.library.log.Logger;
import com.android.wwh.picture.disklrucache.DiskLruCacheActivity;
import com.android.wwh.picture.handlerimageloader.HandlerImageLoaderActivity;
import com.android.wwh.picture.networkimageloader.NetworkImageLoaderActivity;
import com.android.wwh.picture.photowall.PhotoWallActivity;
import com.android.wwh.picture.photowallfalls.PhotoWallFallsActivity;
import com.android.wwh.picture.photowallfullversion.PhotoWallFullVersionActivity;
import com.android.wwh.picture.pictureselector.imageloader.PictureSelectorActivity;
import com.android.wwh.picture.rxglide.RxImageLoaderActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 主要代码是关于Android高效加载大图、多图解决方案，有效避免程序OOM
 * 并给其他研究问题提供入口
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUECT_CODE_SDCARD = 2;

    @BindView(R.id.imageView)
    ImageView mImagView;

    private LruCache<String, Bitmap> mLruCache;
    private RxPermissions mRxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRxPermissions = new RxPermissions(this);

        testMaxMemory();
        testBitmap();
        testLruCache();
    }

    public void android_photo_wall(View view) {
        startActivity(new Intent(MainActivity.this, PhotoWallActivity.class));
    }

    public void android_photo_wall_falls(View view) {
        // 申请权限
        mRxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean granted) throws Exception {
                        if (granted) { // Always true pre-M
                            startActivity(new Intent(MainActivity.this, PhotoWallFallsActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "没有SdCard权限!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void android_disklrucache(View view) {
        // 申请权限
        mRxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean granted) throws Exception {
                        if (granted) { // Always true pre-M
                            startActivity(new Intent(MainActivity.this, DiskLruCacheActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "没有SdCard权限!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void android_photo_wall_full_version(View view) {
        // 申请权限
        mRxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean granted) throws Exception {
                        if (granted) { // Always true pre-M
                            startActivity(new Intent(MainActivity.this, PhotoWallFullVersionActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "没有SdCard权限!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void android_handler_image_loader(View view) {
        // 申请权限
        mRxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean granted) throws Exception {
                        if (granted) { // Always true pre-M
                            startActivity(new Intent(MainActivity.this, HandlerImageLoaderActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "没有SdCard权限!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void android_picture_selector(View view) {
        // 申请权限
        mRxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean granted) throws Exception {
                        if (granted) { // Always true pre-M
                            startActivity(new Intent(MainActivity.this, PictureSelectorActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "没有SdCard权限!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void android_network_imageloader(View view) {
        // 申请权限
        mRxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean granted) throws Exception {
                        if (granted) { // Always true pre-M
                            startActivity(new Intent(MainActivity.this, NetworkImageLoaderActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "没有SdCard权限!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void android_rx_image_loader(View view){
        // 申请权限
        mRxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean granted) throws Exception {
                        if (granted) { // Always true pre-M
                            startActivity(new Intent(MainActivity.this, RxImageLoaderActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "没有SdCard权限!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 使用内存缓存技术来对图片进行缓存，让你的应用程序在加载很多图片的时候可以提高响应速度和流畅性
     * LruCache:把最近使用的对象用强引用存储在 LinkedHashMap 中，并且把最近最少使用的对象在缓存值达到预设定值之前从内存中移除
     */
    private void testLruCache() {
        // 获取到可用内存的最大值，使用内存超出这值会引起OutOfMemory异常。
        // LruCache通过构造函数传入缓存值，以KB为单位。
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 使用最大可用内存值的1/8作为缓存的大小。
        int cacheSize = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // bitmap.getRowBytes() * bitmap.getHeight() = bitmap.getByteCount()
                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                return bitmap.getByteCount() / 1024;
            }
        };
        loadBitmap(R.mipmap.ic_launcher, mImagView);
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mLruCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mLruCache.get(key);
    }

    public void loadBitmap(int resId, ImageView imageView) {
        final String imageKey = String.valueOf(resId);
        // 首先会在 LruCache 的缓存中进行检查
        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            // 如果找到了相应的键值，则会立刻更新ImageView
            imageView.setImageBitmap(bitmap);
        } else {
            // 否则开启一个后台线程来加载这张图片
            BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            task.execute(resId);
        }
    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private ImageView mImageView;

        public BitmapWorkerTask(ImageView imageView) {
            mImageView = imageView;
        }

        // 在后台加载图片。
        @Override
        protected Bitmap doInBackground(Integer... params) {
            final Bitmap bitmap = decodeSampledBitmapFromResource(
                    getResources(), params[0], 100, 100);
            // 把新加载的图片的键值对放到缓存中
            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mImageView.setImageBitmap(bitmap);
        }
    }

    /**
     * 将任意一张图片压缩成100*100的缩略图，并在ImageView上展示
     */
    private void testBitmap() {
        Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), R.mipmap.myimage, 400, 300);
        Logger.i("bitmap：" + bitmap.getWidth() + "," + bitmap.getHeight()); // bitmap：450,600
        //        mImage.setImageBitmap(bitmap);
    }

    /**
     * 查看每个应用程序最高可用内存是多少
     */
    private void testMaxMemory() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / (1024 * 1024));
        Logger.i("Max memory is " + maxMemory + "M"); // 我的手机一直是384M
    }

    /**
     * 计算出合适的inSampleSize值
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 使用BitmapFactory来对图片进行压缩
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

}
