package com.miaxis.inspection.view.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.ProblemPhotoAdapter;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.entity.Config;
import com.miaxis.inspection.entity.InspectContent;
import com.miaxis.inspection.entity.InspectContentLog;
import com.miaxis.inspection.entity.InspectItem;
import com.miaxis.inspection.entity.ProblemPhoto;
import com.miaxis.inspection.entity.ProblemType;
import com.miaxis.inspection.entity.ResponseEntity;
import com.miaxis.inspection.entity.comm.CheckContentLog;
import com.miaxis.inspection.model.local.greenDao.gen.InspectContentLogDao;
import com.miaxis.inspection.model.local.greenDao.gen.ProblemPhotoDao;
import com.miaxis.inspection.model.remote.retrofit.LogNet;
import com.miaxis.inspection.utils.CommonUtil;
import com.miaxis.inspection.utils.DateUtil;
import com.miaxis.inspection.utils.PictureUtil;
import com.miaxis.inspection.utils.ResultType;
import com.miaxis.inspection.view.custom.BottomMenu;
import com.miaxis.inspection.view.custom.SimpleDialog;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoInspectContentActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_content_name)
    TextView tvContentName;
    @BindColor(R.color.red)
    int red;
    @BindColor(R.color.green_dark)
    int greenDark;
    @BindColor(R.color.blue_band_dark2)
    int blueDark2;
    @BindView(R.id.ll_result_type)
    LinearLayout llResultType;
    @BindView(R.id.gl_problem_type)
    GridLayout glProblemType;
    @BindView(R.id.sv_problem)
    ScrollView svProblem;
    @BindView(R.id.et_problem_description)
    EditText etProblemDescription;
    @BindView(R.id.rv_pic_description)
    RecyclerView rvPicDescription;
    @BindView(R.id.tv_resultType1)
    TextView tvResultType1;
    @BindView(R.id.tv_resultType2)
    TextView tvResultType2;
    @BindView(R.id.btn_video_record)
    Button btnVideoRecord;
    @BindView(R.id.btn_voice_record)
    Button btnVoiceRecord;
    @BindView(R.id.btn_video_play)
    Button btnVideoPlay;
    @BindView(R.id.tv_video_name)
    TextView tvVideoName;
    @BindView(R.id.btn_voice_play)
    Button btnVoicePlay;
    @BindView(R.id.tv_voice_name)
    TextView tvVoiceName;

    private InspectContent inspectContent;
    private View.OnClickListener menuListener;

    private String selectedResultType;
    private boolean hasProblem;

    private ProblemType selectedProblemType;

    private BottomMenu bottomMenu;
    private List<ProblemPhoto> photoList;
    private ProblemPhotoAdapter photoAdapter;
    private int photoNo;
    private boolean isCameraCapture;
    private String filePathCache;
    private String inspectPointLogCode;

    private static final int CAPTURE_PHOTO_ACTIVITY_REQUEST_CODE = 97;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 98;
    private static final int CAPTURE_VOICE_ACTIVITY_REQUEST_CODE = 99;
    private Uri videoUri;
    private String videoName;
    private File voiceFile;
    private Uri voiceUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_insepct_content);
        ButterKnife.bind(this);

        initData();
        initListener();
        initView();
        loadResultType();
        loadProblemType();
    }

    private void initToolBar() {
        toolbar.setTitle("检查内容");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_submit:
                try {
                    submit();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        inspectPointLogCode = getIntent().getStringExtra("pointLogCode");
        inspectContent = (InspectContent) getIntent().getSerializableExtra("content");
        photoList = new ArrayList<>();
        initAddNewPhotoItem();
        photoAdapter = new ProblemPhotoAdapter(photoList, this);
        photoAdapter.setListener(new ProblemPhotoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                photoNo = position;
                bottomMenu.show();
            }
        });
    }

    @Override
    protected void initView() {
        initToolBar();
        tvContentName.setText(inspectContent.getName());
        bottomMenu = new BottomMenu(this, menuListener);
        rvPicDescription.setAdapter(photoAdapter);
        rvPicDescription.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void loadResultType() {
        switch (inspectContent.getResultType()) {
            case ResultType.NORMAL_UNNORMAL:
                tvResultType1.setText("正常");
                tvResultType2.setText("异常");
                break;
            case ResultType.BROKEN_NOT_BROKEN:
                tvResultType1.setText("未损坏");
                tvResultType2.setText("损坏");
                break;
            case ResultType.CLEAR_NOT_CLEAR:
                tvResultType1.setText("清理");
                tvResultType2.setText("未清理");
                break;
        }
        tvResultType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasProblem = false;
                tvResultType1.setSelected(true);
                tvResultType1.setTextColor(getResources().getColor(R.color.green_dark));

                tvResultType2.setSelected(false);
                tvResultType2.setTextColor(getResources().getColor(R.color.gray_dark));

                svProblem.setVisibility(View.INVISIBLE);
                selectedResultType = tvResultType1.getText().toString();
            }
        });

        tvResultType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasProblem = true;
                tvResultType2.setSelected(true);
                tvResultType2.setTextColor(getResources().getColor(R.color.red));

                tvResultType1.setSelected(false);
                tvResultType1.setTextColor(getResources().getColor(R.color.gray_dark));

                svProblem.setVisibility(View.VISIBLE);
                selectedResultType = tvResultType2.getText().toString();
            }
        });
    }

    private void initListener() {

        menuListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_menu_1:
                        isCameraCapture = true;
                        bottomMenu.popupWindow.dismiss();
                        filePathCache = Environment.getExternalStorageDirectory() + "/inspection/problemPhoto/" + new Date().getTime() + ".jpg";
                        File vFile = new File(filePathCache);
                        if (!vFile.exists()) {
                            File vDirPath = vFile.getParentFile(); //new File(vFile.getParent());
                            vDirPath.mkdirs();
                        }
                        Uri uri = Uri.fromFile(vFile);
                        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(i, CAPTURE_PHOTO_ACTIVITY_REQUEST_CODE);
                        break;
                    case R.id.btn_menu_2:
                        isCameraCapture = false;
                        bottomMenu.popupWindow.dismiss();
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, CAPTURE_PHOTO_ACTIVITY_REQUEST_CODE);
                        break;
                    case R.id.btn_menu_3:
                        bottomMenu.popupWindow.dismiss();
                        break;
                }
            }
        };
    }

    private void loadProblemType() {
        final List<ProblemType> problemTypeList = Inspection_App.getInstance().getDaoSession().getProblemTypeDao().loadAll();
        for (int i = 0; i < problemTypeList.size(); i++) {
            ProblemType type = problemTypeList.get(i);
            TextView tv = new TextView(this);
            tv.setText(type.getTypeName());
            tv.setTextColor(getResources().getColor(R.color.gray_dark));
            tv.setGravity(Gravity.CENTER);
            tv.setFocusable(true);
            tv.setClickable(true);

            GridLayout.Spec rowSpec = GridLayout.spec(i / 3, 1f);
            GridLayout.Spec columnSpec = GridLayout.spec(i % 3, 1f);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
            params.height = 100;
            params.width = 0;
            params.setMargins(10, 0, 10, 10);

            tv.setLayoutParams(params);
            tv.setBackground(getDrawable(R.drawable.orange_check_bg));
            final int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GridLayout parent = (GridLayout) view.getParent();
                    for (int i = 0; i < parent.getChildCount(); i++) {
                        TextView child = (TextView) parent.getChildAt(i);
                        child.setSelected(false);
                        child.setTextColor(getResources().getColor(R.color.gray_dark));
                    }
                    TextView tv = ((TextView) view);
                    tv.setTextColor(getResources().getColor(R.color.dark));
                    tv.setSelected(true);
                    selectedProblemType = problemTypeList.get(finalI);
                }
            });
            glProblemType.addView(tv);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        switch (requestCode) {
            case CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    btnVideoPlay.setVisibility(View.VISIBLE);
                    tvVideoName.setVisibility(View.VISIBLE);
                    btnVideoRecord.setVisibility(View.GONE);
                    tvVideoName.setText(videoName);
                    btnVideoPlay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(DoInspectContentActivity.this, VideoPlayActivity.class);
                            i.putExtra("uri", data.getData());
                            startActivity(i);
                        }
                    });
                } else if (resultCode == RESULT_CANCELED) {
                } else {
                }
                break;
            case CAPTURE_PHOTO_ACTIVITY_REQUEST_CODE:
                if (isCameraCapture) {
                    if (resultCode == RESULT_OK) {
                        setupPhoto(new File(filePathCache));
                    }
                } else {
                    if (resultCode == RESULT_OK) {
                        try {
                            // 获得图片的uri
                            Uri originalUri = data.getData();
                            setupPhoto(CommonUtil.getFileByUri(originalUri, this));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                break;
            case CAPTURE_VOICE_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    voiceUri = data.getData();
                    voiceFile = CommonUtil.getFileByUri(voiceUri, this);
                    btnVoicePlay.setVisibility(View.VISIBLE);
                    tvVoiceName.setVisibility(View.VISIBLE);
                    btnVoiceRecord.setVisibility(View.GONE);
                    tvVoiceName.setText(voiceFile.getName());
                } else if (resultCode == RESULT_CANCELED) {
                } else {
                }
                break;
        }
    }

    private void setupPhoto(File file) {
        ProblemPhoto photo;
        if (photoList.size() == photoNo + 1) {
            photo = new ProblemPhoto();
            photoList.add(photo);
        } else {
            photo = photoList.get(photoList.size() - photoNo - 1);
        }
        photo.setPicUrl(file.getAbsolutePath());
        photoAdapter.notifyDataSetChanged();
    }

    private void initAddNewPhotoItem() {
        ProblemPhoto addNew = new ProblemPhoto();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.new_photo);
        addNew.setPicData(PictureUtil.bitmapToBytes(bitmap));
        photoList.add(addNew);
    }

    private void submit() throws UnsupportedEncodingException {
        if (inspectPointLogCode == null) {
            Toast.makeText(this, "缺少日志编号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!tvResultType1.isSelected() && !tvResultType2.isSelected()) {
            Toast.makeText(this, "请选择检查结果", Toast.LENGTH_SHORT).show();
            return;
        }
        InspectContentLog contentLog = new InspectContentLog();
        contentLog.setUploaded(false);
        contentLog.setPointLogCode(inspectPointLogCode);
        contentLog.setOpDate(new Date());
        contentLog.setContentId(inspectContent.getId());
        contentLog.setResultType(selectedResultType);
        contentLog.setHasProblem(hasProblem);
        if (contentLog.getHasProblem()) {
            contentLog.setProblemTypeId(selectedProblemType.getId());
            contentLog.setDescription(etProblemDescription.getText().toString());
        }
        InspectContentLogDao contentLogDao = Inspection_App.getInstance().getDaoSession().getInspectContentLogDao();
        contentLogDao.save(contentLog);

        if (contentLog.getHasProblem()) {
            Long contentLogId = contentLog.getId();
            ProblemPhotoDao problemPhotoDao = Inspection_App.getInstance().getDaoSession().getProblemPhotoDao();
            photoList.remove(0);    //去除
            for (int i = 0; i < photoList.size(); i++) {
                photoList.get(i).setContentLogId(contentLogId);
            }
            problemPhotoDao.saveInTx(photoList);
        }

        uploadContentLog(contentLog);
    }

    private void uploadContentLog(final InspectContentLog contentLog) throws UnsupportedEncodingException {
        Config config = Inspection_App.getInstance().getDaoSession().getConfigDao().load(1L);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();
        final LogNet net = retrofit.create(LogNet.class);

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        if (hasProblem) {
            for (int i = 0; i < photoList.size(); i++) {
                ProblemPhoto p = photoList.get(i);
                File mFile = new File(p.getPicUrl());
                RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/jpg"), mFile);
                builder.addFormDataPart("file", mFile.getName(), photoRequestBody);
                switch (i) {
                    case 0:
                        contentLog.setPhotoName1(mFile.getName());
                        break;
                    case 1:
                        contentLog.setPhotoName2(mFile.getName());
                        break;
                    case 2:
                        contentLog.setPhotoName3(mFile.getName());
                        break;
                    case 3:
                        contentLog.setPhotoName4(mFile.getName());
                        break;
                    case 4:
                        contentLog.setPhotoName5(mFile.getName());
                        break;
                }
            }

            if (videoUri != null) {
                File videoFile = CommonUtil.getFileByUri(videoUri, DoInspectContentActivity.this);
                RequestBody videoRequestBody = RequestBody.create(MediaType.parse("image/jpg"), videoFile);
                builder.addFormDataPart("file", videoFile.getName(), videoRequestBody);
                contentLog.setVideoName(videoFile.getName());
            }
            if (voiceUri != null) {
                RequestBody voiceRequestBody = RequestBody.create(MediaType.parse("image/jpg"), voiceFile);
                builder.addFormDataPart("file", voiceFile.getName(), voiceRequestBody);
                contentLog.setVoiceName(voiceFile.getName());
            }
        }
        List<MultipartBody.Part> parts = null;
        try {
            parts = builder.build().parts();
        } catch (Exception e) {
            parts = builder.addFormDataPart("t", "").build().parts();
        }

        net.uploadContentLog(fetchContentLog(contentLog), parts)
                .subscribeOn(Schedulers.newThread())
                .doOnNext(new Consumer<ResponseEntity<String>>() {
                    @Override
                    public void accept(ResponseEntity<String> responseEntity) throws Exception {
                        if (TextUtils.equals("200", responseEntity.getCode())) {
                            contentLog.setUploaded(true);
                        } else {
                            contentLog.setUploaded(false);
                        }
                        InspectContentLogDao contentLogDao = Inspection_App.getInstance().getDaoSession().getInspectContentLogDao();
                        contentLogDao.insertOrReplace(contentLog);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity responseEntity) throws Exception {
                        setResult(111);
                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("===", throwable.getMessage());
                        contentLog.setUploaded(false);
                        InspectContentLogDao contentLogDao = Inspection_App.getInstance().getDaoSession().getInspectContentLogDao();
                        contentLogDao.insertOrReplace(contentLog);
                        setResult(-111);
                        finish();
                    }
                });

    }

    private String fetchContentLog(InspectContentLog contentLog) throws UnsupportedEncodingException {
        CheckContentLog checkContentLog = new CheckContentLog();
        InspectContent inspectContent = contentLog.getInspectContent();
        InspectItem inspectItem = inspectContent.getInspectItem();
        checkContentLog.setProjectFormCode(inspectItem.getInspectFormCode());
        checkContentLog.setProjectCode(inspectItem.getCode());
        checkContentLog.setcPointLogCode(contentLog.getPointLogCode());
        checkContentLog.setOpDate(DateUtil.toHourMinString(contentLog.getOpDate()));
        checkContentLog.setOpUser(URLEncoder.encode(Inspection_App.getCurInspector().getOpUserName(),"utf-8"));
        checkContentLog.setProjectContent(URLEncoder.encode(inspectContent.getName(),"utf-8"));
        if (contentLog.getHasProblem()) {
            checkContentLog.setStatus(1);
            checkContentLog.setErrorType(Integer.valueOf(contentLog.getProblemType().getType() + ""));
            checkContentLog.setDescription(URLEncoder.encode(contentLog.getDescription(),"utf-8"));
            checkContentLog.setPicture0Url(contentLog.getPhotoName1());
            checkContentLog.setPicture1Url(contentLog.getPhotoName2());
            checkContentLog.setPicture2Url(contentLog.getPhotoName3());
            checkContentLog.setPicture3Url(contentLog.getPhotoName4());
            checkContentLog.setPicture4Url(contentLog.getPhotoName5());
            checkContentLog.setVideoUrl(contentLog.getVideoName());
            checkContentLog.setVoiceUrl(contentLog.getVoiceName());
        } else {
            checkContentLog.setStatus(0);
        }
        checkContentLog.setResult(contentLog.getResultType());
        return new Gson().toJson(checkContentLog);

    }

    @OnClick(R.id.btn_video_record)
    void onRecordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        try {
            videoUri = Uri.fromFile(createMediaFile()); // create a file to save the video
        } catch (IOException e) {
            e.printStackTrace();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);  // set the image file name
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
        // start the Video Capture Intent
        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
    }

    private File createMediaFile() throws IOException {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/inspection/problemVideo");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "VID_" + timeStamp;
        String suffix = ".mp4";
        videoName = imageFileName + suffix;
        File mediaFile = new File(mediaStorageDir + File.separator + videoName);
        return mediaFile;
    }

    @OnClick(R.id.btn_voice_record)
    void onRecordVoice() {
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, CAPTURE_VOICE_ACTIVITY_REQUEST_CODE);

    }

    @Override
    public void onBackPressed() {
        final SimpleDialog sd = new SimpleDialog();
        sd.setMessage("检查内容尚未提交，是否返回上级页面？");
        sd.setConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sd.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sd.dismiss();
            }
        });
        sd.show(getFragmentManager(), "back");
    }

    @OnClick(R.id.btn_voice_play)
    void onPlayVoice() {
//        if (tvVoiceName.getVisibility() == View.VISIBLE && voiceFile != null && TextUtils.isEmpty(tvVoiceName.getText().toString())) {
//            MediaPlayer mediaPlayer = new MediaPlayer();
//            try {
//                mediaPlayer.reset();
//                mediaPlayer.setDataSource(this, voiceUri);
//                mediaPlayer.prepare();
//                mediaPlayer.start();
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(DoInspectContentActivity.this, "播放录音时遇到错误！", Toast.LENGTH_LONG).show();
//            }
//        }
    }
}
