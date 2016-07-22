package com.liuguangqiang.framework.utils.image;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    /**
     * Convert a Bitmap to a byte[].
     *
     * @param bm
     * @return
     */
    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bm.compress(CompressFormat.PNG, 100, out);
        return out.toByteArray();
    }

    /**
     * Convert a byte[] to a Bitmap.
     *
     * @param b
     * @return
     */
    public Bitmap bytesToBitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * Clip a bitmap and return the center part.
     *
     * @param source
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static Bitmap clipCenter(Bitmap source, int targetWidth, int targetHeight) {
        int startWidth = (source.getWidth() - targetWidth) / 2;
        int startHeight = ((source.getHeight() - targetHeight) / 2);
        Rect src =
                new Rect(startWidth, startHeight, startWidth + targetWidth, startHeight
                        + targetHeight);
        return clipBitmap(source, src);
    }

    /**
     * Clip a bitmap and return the bottom part.
     *
     * @param source
     * @param targetHeight
     * @return
     */
    public static Bitmap clipBottom(Bitmap source, int targetHeight) {
        int width = source.getWidth();
        int height = source.getHeight();
        int startWidth = 0;
        int startHeight = height - targetHeight;
        Rect src = new Rect(startWidth, startHeight, 0 + width, startHeight + targetHeight);
        return clipBitmap(source, src);
    }

    /**
     * Clip a Bitmap with a Rect.
     *
     * @param bmp
     * @param src
     * @return
     */
    private static Bitmap clipBitmap(Bitmap bmp, Rect src) {
        int width = src.width();
        int height = src.height();
        Rect des = new Rect(0, 0, width, height);
        Bitmap croppedImage = Bitmap.createBitmap(width, height, Config.RGB_565);
        Canvas canvas = new Canvas(croppedImage);
        canvas.drawBitmap(bmp, src, des, null);
        return croppedImage;
    }

    /**
     * Zoom a Bitmap.
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleImage(Bitmap bm, double newWidth, double newHeight) {
        float width = bm.getWidth();
        float height = bm.getHeight();
        Matrix matrix = new Matrix();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 限制宽度等比例压缩。
     *
     * @param bgimage
     * @param newWidth
     * @return
     */
    public static Bitmap scaleImageByWidth(Bitmap bgimage, float newWidth) {
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        float scale = ((float) newWidth) / width;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }

    /**
     * Return a Drawable form SD card.
     *
     * @param filePath
     * @return
     */
    public static Drawable getDrawableFromSDCard(String filePath) {
        File file = new File(filePath);
        Drawable drawable = null;
        if (file.exists()) {
            drawable = BitmapDrawable.createFromPath(filePath);
        }
        return drawable;
    }

    /**
     * Return a Bitmap from SD card.
     *
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapFromSDCard(String filePath) {
        File file = new File(filePath);
        Bitmap bm = null;
        if (file.exists()) {
            bm = BitmapFactory.decodeFile(filePath);
        }
        return bm;
    }

    /**
     * Return a Bitmap from the assets directory.
     *
     * @param context
     * @param fileName
     * @return
     */
    public static Bitmap getBitmapFromAssets(Context context, String fileName) {
        Bitmap bm;
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(fileName);
            bm = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return bm;
    }

    /**
     * Return a Drawable from the assets directory.
     *
     * @param context
     * @param fileName
     * @return Drawable.
     */
    public static Drawable getDrawableFromAssets(Context context, String fileName) {
        Drawable drawable;
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(fileName);
            drawable = Drawable.createFromStream(is, "");
            is.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return drawable;
    }

    /**
     * Return a Bitmap with round corner.
     *
     * @param mContext
     * @param resId
     * @param roundPixels
     * @return
     */
    public static Bitmap getRoundCornerImage(Context mContext, int resId, int roundPixels) {
        Bitmap bitmap;
        try {
            bitmap = ((BitmapDrawable) mContext.getResources().getDrawable(resId)).getBitmap();
            return getRoundCornerImage(bitmap, roundPixels);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Return a Bitmap with round corner.
     *
     * @param bitmap
     * @param roundPixels
     * @return
     */
    public static Bitmap getRoundCornerImage(Bitmap bitmap, int roundPixels) {
        Bitmap roundConcerImage;
        Canvas canvas;
        Paint paint;
        Rect rect;
        RectF rectF;
        try {
            roundConcerImage =
                    Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            canvas = new Canvas(roundConcerImage);
            paint = new Paint();
            paint.setColor(0xFFf1d9c3);
            rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            rectF = new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawRoundRect(rectF, roundPixels, roundPixels, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));
            canvas.drawBitmap(bitmap, null, rect, paint);
            return roundConcerImage;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Combine two bitmaps to a bitmap.
     *
     * @param background
     * @param foreground
     * @return
     */
    public static Bitmap combineBitmap(Bitmap background, Bitmap foreground) {
        Bitmap result;
        try {
            if (background == null) {
                return null;
            }
            int bgWidth = background.getWidth();
            int bgHeight = background.getHeight();
            int fgWidth = foreground.getWidth();
            int fgHeight = foreground.getHeight();
            result = Bitmap.createBitmap(bgWidth, bgHeight, Config.ARGB_8888);
            Canvas cv = new Canvas(result);
            cv.drawBitmap(background, 0, 0, null);
            cv.drawBitmap(foreground, (bgWidth - fgWidth) / 2, (bgHeight - fgHeight) / 2, null);
            cv.save(Canvas.ALL_SAVE_FLAG);
            // store
            cv.restore();
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Save a Bitmap to SD card.
     * <p>save as a png file.</>
     *
     * @param bitmap
     * @param savePath the path of save
     * @return
     */
    public static boolean saveBitmap(Bitmap bitmap, String savePath) {
        return saveBitmap(bitmap, savePath, 100, CompressFormat.JPEG);
    }

    /**
     * Save a Bitmap to SD card and change the quality.
     *
     * @param bitmap
     * @param savePath
     * @param quality
     * @param format
     * @return
     */
    public static boolean saveBitmap(Bitmap bitmap, String savePath, int quality,
                                     CompressFormat format) {
        try {
            File file = new File(savePath);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(format, quality, out)) {
                out.flush();
                out.close();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 压缩图片
     *
     * @param srcPath
     *            图片的存储路径
     * @param size
     *            压缩后的大小，单位为kb
     * @return
     */
    public static Bitmap compressedImage(String srcPath, int size) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap, size);// 压缩好比例大小后再进行质量压缩
    }

    private static Bitmap compressImage(Bitmap image, int size) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > size) { // 循环判断如果压缩后图片是否大于sizekb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 20;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

}
