package com.tkteam.reading.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tkteam.reading.ApplicationStateHolder;
import com.tkteam.reading.R;
import com.tkteam.reading.base.BaseFragment;
import com.tkteam.reading.base.event.ChangedFragmentEvent;
import com.tkteam.reading.dao.DatabaseHelper;
import com.tkteam.reading.dao.entites.Story;
import com.tkteam.reading.dao.entites.StoryCreate;
import com.tkteam.reading.dao.entites.User;
import com.tkteam.reading.service.StoryCreateService;
import com.tkteam.reading.service.StoryService;
import com.tkteam.reading.service.UserService;
import com.tkteam.reading.ui.adapter.CommonAdapter;
import com.tkteam.reading.ui.customview.AddOrEditCustomerDialog;
import com.tkteam.reading.ui.customview.CustomDialog;
import com.tkteam.reading.ui.event.AddUserEvent;
import com.tkteam.reading.ui.event.EditUserRespontEvent;
import com.tkteam.reading.ui.event.RequestEditUserEvent;
import com.tkteam.reading.ui.group.LessonGroup;
import com.tkteam.reading.ui.group.UserGroup;
import com.tkteam.reading.utils.FileUtils;
import com.tkteam.reading.utils.ProgressDialogHolder;
import com.tkteam.reading.utils.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by khiemvx on 5/26/2015.
 */
public class StartFragment extends BaseFragment {

    private static final int REQUEST_CODE_TAKE_PICTURE = 666;
    private static final int REQUEST_CODE_LOAD_PICTURE = 555;

    @InjectView(R.id.ivCreatedLesson)
    ImageView ivCreateLesson;
    @InjectView(R.id.ivManagerUsers)
    ImageView ivManagerUsers;
    @InjectView(R.id.ivSetting)
    ImageView ivSetting;
    @InjectView(R.id.ivProsgress)
    ImageView ivProsgress;
    @InjectView(R.id.ivGraphs)
    ImageView ivGraphs;
    @InjectView(R.id.tvUserActive)
    TextView tvUserActive;

    @InjectView(R.id.rbSystemLesson)
    RadioButton rbSystemLesson;
    @InjectView(R.id.rbCreateLesson)
    RadioButton rbCreateLesson;
    @InjectView(R.id.grid_view)
    GridView lvContent;

    CommonAdapter managerUserAdapter;
    AddOrEditCustomerDialog addOrEditCustomerDialog;
    ImageView ivPhotoUser;
    EditText etUserName;
    String fileName;
    List<UserGroup> userGroupList;

    @Override
    public int getLayout() {
        return R.layout.start_fragment;
    }

    @Override
    public void setupView() {
        tvUserActive.setText(ApplicationStateHolder.getInstance().getUserActiveName());
        onShowListSystemLesson();
    }

    @OnClick(R.id.rbCreateLesson)
    public void onShowListCreateLesson() {
        List<LessonGroup> lessonGroupList = LessonGroup.convertFromStoryCreate(getListStoreCreateInDB());
        CommonAdapter commonAdapter = new CommonAdapter(getActivity(), lessonGroupList);
        lvContent.setAdapter(commonAdapter);
    }

    @OnClick(R.id.rbSystemLesson)
    public void onShowListSystemLesson() {
        List<LessonGroup> lessonGroupList = LessonGroup.convertFromStory(getListStoreSystemInDB());
        CommonAdapter commonAdapter = new CommonAdapter(getActivity(), lessonGroupList);
        lvContent.setAdapter(commonAdapter);
    }

    private List<Story> getListStoreSystemInDB() {
        List<Story> storyList = new ArrayList<>();
        Cursor c = DatabaseHelper.getInstance(getActivity()).getReadableDatabase().rawQuery("SELECT * FROM  story", new String[]{});
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                storyList.add(StoryService.getInstance(getActivity()).convertDataToObject(c, new Story()));
            } while (c.moveToNext());
            c.close();
        }
        return storyList;
    }

    private List<StoryCreate> getListStoreCreateInDB() {
        List<StoryCreate> storyCreateList = new ArrayList<>();
        Cursor c = DatabaseHelper.getInstance(getActivity()).getReadableDatabase().rawQuery("SELECT * FROM  story_create", new String[]{});
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                storyCreateList.add(StoryCreateService.getInstance(getActivity()).convertDataToObject(c, new StoryCreate()));
            } while (c.moveToNext());
            c.close();
        }
        return storyCreateList;
    }

    @OnClick(R.id.ivManagerUsers)
    public void onManagerUser() {
        final CustomDialog dialog = new CustomDialog(getActivity(), R.layout.dialog_manager_users, R.style.dialog_anim_style);
        GridView gridView = (GridView) dialog.getRootView().findViewById(R.id.dialog_manager_users_gridViewUser);
        ImageView ivAddUser = (ImageView) dialog.getRootView().findViewById(R.id.dialog_manager_users_btAddUser);
        ImageView btDone = (ImageView) dialog.getRootView().findViewById(R.id.btDone);
        CheckBox ivEditUser = (CheckBox) dialog.getRootView().findViewById(R.id.dialog_manager_users_btEditUser);
        CheckBox ivDeleteUser = (CheckBox) dialog.getRootView().findViewById(R.id.dialog_manager_users_btDeleteUser);
        try {
            userGroupList = UserGroup.convertFromUser(UserService.getInstance(getActivity()).findAll());
            managerUserAdapter = new CommonAdapter(getActivity(), userGroupList);
            managerUserAdapter.setShowEditButton(false);
            managerUserAdapter.setShowDeleteButton(false);
            gridView.setAdapter(managerUserAdapter);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "Error when get from database");
        }
        ivAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrEditCustomerDialog = new AddOrEditCustomerDialog(getActivity(), R.layout.dialog_add_new_user, R.style.dialog_anim_style);
                ImageView ivBack = (ImageView) addOrEditCustomerDialog.getRootView().findViewById(R.id.dialog_add_new_user_ivBack);
                ImageView ivDone = (ImageView) addOrEditCustomerDialog.getRootView().findViewById(R.id.dialog_add_new_user_ivDone);
                ivPhotoUser = (ImageView) addOrEditCustomerDialog.getRootView().findViewById(R.id.dialog_add_new_user_ivPickPhoto);
                etUserName = (EditText) addOrEditCustomerDialog.getRootView().findViewById(R.id.dialog_add_new_user_etName);
                ivDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProgressDialogHolder.getInstance().showDialogWithoutMessage();
                        UUID userId = UUID.randomUUID();
                        User user = new User();
                        user.setName(StringUtils.trim(etUserName.getText().toString()));
                        user.setAvatarUrl(fileName);
                        user.setId(userId);
                        try {
                            UserService.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).createOrUpdate(user);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        EventBus.getDefault().post(new AddUserEvent(userId.toString()));
                        addOrEditCustomerDialog.dismiss();
                    }
                });
                ivBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addOrEditCustomerDialog.dismiss();
                    }
                });
                ivPhotoUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                });
                addOrEditCustomerDialog.show();
            }
        });
        ivEditUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                managerUserAdapter.setShowEditButton(isChecked);
                managerUserAdapter.notifyDataSetChanged();
            }
        });

        ivDeleteUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                managerUserAdapter.setShowDeleteButton(isChecked);
                managerUserAdapter.notifyDataSetChanged();
            }
        });
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.ivCreatedLesson)
    public void clickCreated() {
        EventBus.getDefault().post(new ChangedFragmentEvent(new ManageStoryFragment()));
    }

    @OnClick(R.id.ivSetting)
    public void clickSetting() {
        final CustomDialog dialog = new CustomDialog(getActivity(), R.layout.dialog_setting_app, R.style.dialog_anim_style);
        Switch switchVoiceOfWord = (Switch) dialog.getRootView().findViewById(R.id.switchVoiceOfWord);
        Switch switchVoiceOfQuestion = (Switch) dialog.getRootView().findViewById(R.id.switchVoiceOfQuestion);
        Switch switchVoiceOfStory = (Switch) dialog.getRootView().findViewById(R.id.switchVoiceOfStory);
        Switch switchAward = (Switch) dialog.getRootView().findViewById(R.id.switchAward);
        ImageView ivDone = (ImageView) dialog.getRootView().findViewById(R.id.setting_ivDone);
        RadioButton rbPracticeMode = (RadioButton) dialog.getRootView().findViewById(R.id.rbPracticeMode);
        RadioButton rbScoringMode = (RadioButton) dialog.getRootView().findViewById(R.id.rbScoringMode);
        final RadioGroup radioGroup = (RadioGroup) dialog.getRootView().findViewById(R.id.radioGroup);
        rbPracticeMode.setChecked(ApplicationStateHolder.getInstance().isPracticeMode());
        switchAward.setChecked(ApplicationStateHolder.getInstance().isAward());
        switchVoiceOfQuestion.setChecked(ApplicationStateHolder.getInstance().isVoiceOfQuestion());
        switchVoiceOfStory.setChecked(ApplicationStateHolder.getInstance().isReadStory());
        switchVoiceOfWord.setChecked(ApplicationStateHolder.getInstance().isVoiceOfWord());
        rbPracticeMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioGroup.setBackgroundResource(R.drawable.mode_scoring);
                    ApplicationStateHolder.getInstance().setPracticeMode(true);
                } else {
                    radioGroup.setBackgroundResource(R.drawable.mode_practice);
                    ApplicationStateHolder.getInstance().setPracticeMode(false);
                }
            }
        });
        rbScoringMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioGroup.setBackgroundResource(R.drawable.mode_practice);
                    ApplicationStateHolder.getInstance().setPracticeMode(false);
                } else {
                    radioGroup.setBackgroundResource(R.drawable.mode_scoring);
                    ApplicationStateHolder.getInstance().setPracticeMode(true);
                }
            }
        });
        switchAward.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ApplicationStateHolder.getInstance().setAward(true);
                } else
                    ApplicationStateHolder.getInstance().setAward(false);
            }
        });
        switchVoiceOfWord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ApplicationStateHolder.getInstance().setVoiceOfWord(true);
                else
                    ApplicationStateHolder.getInstance().setVoiceOfWord(false);
            }
        });
        switchVoiceOfStory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ApplicationStateHolder.getInstance().setReadStory(true);
                else
                    ApplicationStateHolder.getInstance().setReadStory(false);
            }
        });
        switchVoiceOfQuestion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ApplicationStateHolder.getInstance().setVoiceOfQuestion(true);
                else
                    ApplicationStateHolder.getInstance().setVoiceOfQuestion(false);
            }
        });
        ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.ivProsgress)
    public void clickProgress() {
        EventBus.getDefault().post(new ChangedFragmentEvent(new ProgressFragment()));
    }

    @OnClick(R.id.ivGraphs)
    public void clickGraphs() {
        EventBus.getDefault().post(new ChangedFragmentEvent(new ShowChartFragment()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && (requestCode == REQUEST_CODE_TAKE_PICTURE || requestCode == REQUEST_CODE_LOAD_PICTURE)) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivPhotoUser.setImageBitmap(imageBitmap);
            long nameFile = System.currentTimeMillis();
            fileName = FileUtils.bitmapToFile(getActivity().getApplicationContext(), imageBitmap);
        }
    }

    public void onEventMainThread(RequestEditUserEvent event) throws SQLException {
        final User user = DatabaseHelper.getInstance(getActivity()).getUserDao().queryForId(UUID.fromString(event.getUserId()));
        addOrEditCustomerDialog = new AddOrEditCustomerDialog(getActivity(), R.layout.dialog_add_new_user, R.style.dialog_anim_style);
        TextView tvAddOrEditUser = (TextView) addOrEditCustomerDialog.getRootView().findViewById(R.id.tvAddOrEditUser);
        ImageView ivBack = (ImageView) addOrEditCustomerDialog.getRootView().findViewById(R.id.dialog_add_new_user_ivBack);
        ImageView ivDone = (ImageView) addOrEditCustomerDialog.getRootView().findViewById(R.id.dialog_add_new_user_ivDone);
        ivPhotoUser = (ImageView) addOrEditCustomerDialog.getRootView().findViewById(R.id.dialog_add_new_user_ivPickPhoto);
        etUserName = (EditText) addOrEditCustomerDialog.getRootView().findViewById(R.id.dialog_add_new_user_etName);
        tvAddOrEditUser.setText("Edit User");
        etUserName.setText(user.getName());

        DisplayImageOptions options;
        ImageLoader imageLoader;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisc(false)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ApplicationStateHolder.getInstance().getMyActivity())
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(5)
                .threadPriority(Thread.MIN_PRIORITY + 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()).build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        if (StringUtils.isNotEmpty(user.getAvatarUrl()))
            ImageLoader.getInstance().displayImage("file://" + user.getAvatarUrl(), ivPhotoUser, options);
        ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(StringUtils.trim(etUserName.getText().toString()));
                user.setAvatarUrl(fileName);
                try {
                    UserService.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).createOrUpdate(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                EventBus.getDefault().post(new EditUserRespontEvent(user.getId().toString()));
                addOrEditCustomerDialog.dismiss();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrEditCustomerDialog.dismiss();
            }
        });
        ivPhotoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        addOrEditCustomerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
