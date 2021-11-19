package com.compact.picker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.provider.MediaStore;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;

public class
TakePicture extends ActivityResultContract<Void, Bitmap> {
    private Context context;

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Void input) {
        this.context = context;
        return actionChooser();
    }

    @Override
    public Bitmap parseResult(int resultCode, @Nullable Intent intent) {
        try {
            if (intent == null || resultCode != Activity.RESULT_OK) return null;
            else if (intent.hasExtra("data")) {
                return intent.getParcelableExtra("data");
            } else if (null != intent.getData()) {
                return MediaStore.Images.Media.getBitmap(context.getContentResolver(), intent.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Intent actionChooser() {
        Intent chooserIntent = Intent.createChooser(actionPick(), "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, Arrays.asList(
                actionGetContent(), actionImageCapture()
        ).toArray(new Parcelable[]{}));
        return chooserIntent;
    }

    private Intent actionImageCapture() {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    private Intent actionGetContent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        return intent;
    }

    @SuppressLint("IntentReset")
    private Intent actionPick() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        return intent;
    }
}
