package com.tkteam.reading.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.tkteam.reading.R;
import com.tkteam.reading.base.BaseFragment;
import com.tkteam.reading.base.event.ChangedFragmentEvent;
import com.tkteam.reading.dao.entites.StoryCreate;
import com.tkteam.reading.service.StoryCreateService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Khiemvx on 6/6/2015.
 */
public class CreateStoryFragment extends BaseFragment {

    private static final int REQUEST_CODE_TAKE_PICTURE = 1111;
    private static final int REQUEST_CODE_LOAD_PICTURE = 2222;
    @InjectView(R.id.etTitle)
    EditText etTitle;
    @InjectView(R.id.etContent)
    EditText etContent;
    @InjectView(R.id.created_fragment_ivNext)
    ImageView ivNext;
    @InjectView(R.id.ivStory)
    ImageView ivStory;
    @InjectView(R.id.created_fragment_ivBack)
    ImageView ivBack;
    String fileName;

    @Override
    public int getLayout() {
        return R.layout.created_story_fragment;
    }

    @Override
    public void setupView() {

    }

    @OnClick(R.id.ivStory)
    public void selectImageForStory() {
        final CharSequence[] items = {
                "Take photo", "Choose existing photo", "Cancel"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Change profile picture");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PICTURE);
                    }
                } else if (item == 1) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_LOAD_PICTURE);
                } else if (item == 2) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void intentForCropImage(Intent takePictureIntent) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.story_attach_image_background, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;

        takePictureIntent.putExtra("crop", "true");
        takePictureIntent.putExtra("aspectX", 1);
        takePictureIntent.putExtra("aspectY", 1);
        takePictureIntent.putExtra("outputX", imageHeight);
        takePictureIntent.putExtra("outputY", imageWidth);
        takePictureIntent.putExtra("return-data", true);
    }

    @OnClick(R.id.created_fragment_ivNext)
    public void clickStart() {
        StoryCreate storyCreate = new StoryCreate();
        UUID storyId = UUID.randomUUID();
        storyCreate.setTitle(etTitle.getText().toString());
        storyCreate.setContent(etContent.getText().toString());
        storyCreate.setId(storyId);
        storyCreate.setThumb_image(fileName);
        Log.i("UUID", "UUID StoryCreate: " + storyId);
        try {
            StoryCreateService.getInstance(getActivity()).createOrUpdate(storyCreate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        EventBus.getDefault().post(new ChangedFragmentEvent(new CreateQuestionFragment(storyId)));
    }

    @OnClick(R.id.created_fragment_ivBack)
    public void onBack() {
        getActivity().onBackPressed();
    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && (requestCode == REQUEST_CODE_TAKE_PICTURE || requestCode == REQUEST_CODE_LOAD_PICTURE)) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivStory.setImageBitmap(imageBitmap);
            long nameFile = System.currentTimeMillis();
            fileName = bitmapToFile(getActivity().getApplicationContext(), imageBitmap);
        }
    }

    public String bitmapToFile(Context context, Bitmap data) {
        String imageFilePath = String.valueOf(context.getCacheDir());
        String pathImage = "";
        String imageName = String.valueOf(System.currentTimeMillis()) + ".png";
        File file = new File(imageFilePath);
        try {
            if (!file.exists()) {
                file.mkdir();
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            data.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();
            File outputFile = new File(file, imageName);
            FileOutputStream outputStr = new FileOutputStream(outputFile);
            outputStr.write(bitmapdata);
            outputStr.flush();
            outputStr.close();
            pathImage = outputFile.getAbsolutePath();
        } catch (IOException e) {

        }
        return pathImage;
    }
}
