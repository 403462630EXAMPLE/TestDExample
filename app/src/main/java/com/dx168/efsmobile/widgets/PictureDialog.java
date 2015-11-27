package com.dx168.efsmobile.widgets;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidao.efsmobile.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by rjhy on 15-1-15.
 */
public class PictureDialog extends Dialog {
    private static final String TAG = "PictureDialog";
    @InjectView(R.id.tv_save)
    TextView saveVew;
    @InjectView(R.id.tv_close)
    TextView closeView;
    @InjectView(R.id.iv_picture)
    ImageView pictureView;
    @InjectView(R.id.pb_image_progress)
    ProgressBar imageProgress;

    private String lastImageUrl = "";
    private ProgressDialog progressDialog;

    private PhotoViewAttacher attacher;

    public PictureDialog(Context context) {
        super(context, android.R.style.Theme_Light);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_picture);
        ButterKnife.inject(this);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);

        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        saveVew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                savePicture();
            }
        });
    }

    private void savePicture() {
        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "ytxmobile" + File.separator);
        dir.mkdirs();
        File file = new File(dir, UUID.randomUUID() + getSuffix());
        Ion.with(getContext())
                .load(lastImageUrl)
                .write(file)
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File result) {
                        if (e == null && result != null) {
                            addImageToGallery(file.getAbsolutePath());
                            Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private String getSuffix() {
        if (lastImageUrl.contains(".gif")) {
            return ".gif";
        } else if (lastImageUrl.contains(".jpg") || lastImageUrl.contains(".jpeg")) {
            return ".jpg";
        } else {
            return ".png";
        }
    }

    public void addImageToGallery(String filePath) {
        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    public void show(String imageUrl) {
        show();
        Log.d(TAG, "--imageUrl:" + imageUrl);
        if (lastImageUrl == null || !lastImageUrl.equals(imageUrl)) {
            pictureView.setImageBitmap(null);
        }
        imageProgress.setVisibility(View.VISIBLE);
        lastImageUrl = imageUrl.replaceAll(" ", "%20");
        Ion.with(getContext())
                .load(lastImageUrl)
                .withBitmap()
                .error(R.drawable.default_image)
                .centerInside()
                .intoImageView(pictureView)
                .setCallback(new FutureCallback<ImageView>() {
                    @Override
                    public void onCompleted(Exception e, ImageView result) {
                        attacher = new PhotoViewAttacher(pictureView);
                        attacher.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        imageProgress.setVisibility(View.GONE);
                    }
                });
    }
}
