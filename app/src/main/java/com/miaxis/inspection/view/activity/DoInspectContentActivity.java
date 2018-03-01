package com.miaxis.inspection.view.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.ProblemPhotoAdapter;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.entity.InspectContent;
import com.miaxis.inspection.entity.InspectContentLog;
import com.miaxis.inspection.entity.InspectLog;
import com.miaxis.inspection.entity.ProblemPhoto;
import com.miaxis.inspection.entity.ProblemType;
import com.miaxis.inspection.model.local.greenDao.gen.InspectContentLogDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectLogDao;
import com.miaxis.inspection.model.local.greenDao.gen.ProblemPhotoDao;
import com.miaxis.inspection.utils.CommonUtil;
import com.miaxis.inspection.utils.PictureUtil;
import com.miaxis.inspection.utils.ResultType;
import com.miaxis.inspection.view.custom.BottomMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

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
    private Long inspectLogId;


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
                } catch (Exception e) {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        inspectLogId = getIntent().getLongExtra("inspectLogId", -1L);
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

    private void initListener(){

        menuListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                switch(view.getId()){
                    case R.id.btn_menu_1:
                        isCameraCapture=true;
                        bottomMenu.popupWindow.dismiss();
                        filePathCache = Environment.getExternalStorageDirectory()+"/problemPhoto/"+new Date().getTime()+".jpg";
                        File vFile=new File(filePathCache);
                        if(!vFile.exists()){
                            File vDirPath=vFile.getParentFile(); //new File(vFile.getParent());
                            vDirPath.mkdirs();
                        }
                        Uri uri=Uri.fromFile(vFile);
                        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        i.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                        startActivityForResult(i,photoNo);
                        break;
                    case R.id.btn_menu_2:
                        isCameraCapture=false;
                        bottomMenu.popupWindow.dismiss();
                        Intent intent=new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent,photoNo);
                        break;
                    case R.id.btn_menu_3:
                        bottomMenu.popupWindow.dismiss();
                        break;
                }
            }
        };
    }

    private void loadProblemType(){
        final List<ProblemType> problemTypeList = Inspection_App.getInstance().getDaoSession().getProblemTypeDao().loadAll();
        for (int i = 0; i < problemTypeList.size(); i++) {
            ProblemType type=problemTypeList.get(i);
            TextView tv=new TextView(this);
            tv.setText(type.getTypeName());
            tv.setTextColor(getResources().getColor(R.color.gray_dark));
            tv.setGravity(Gravity.CENTER);
            tv.setFocusable(true);
            tv.setClickable(true);

            GridLayout.Spec rowSpec=GridLayout.spec(i/3,1f);
            GridLayout.Spec columnSpec=GridLayout.spec(i%3,1f);
            GridLayout.LayoutParams params=new GridLayout.LayoutParams(rowSpec,columnSpec);
            params.height = 100;
            params.width = 0;
            params.setMargins(10,0,10,10);

            tv.setLayoutParams(params);
            tv.setBackground(getDrawable(R.drawable.orange_check_bg));
            final int finalI=i;
            tv.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    GridLayout parent=(GridLayout)view.getParent();
                    for(int i=0;i<parent.getChildCount();i++){
                        TextView child=(TextView)parent.getChildAt(i);
                        child.setSelected(false);
                        child.setTextColor(getResources().getColor(R.color.gray_dark));
                    }
                    TextView tv=((TextView)view);
                    tv.setTextColor(getResources().getColor(R.color.dark));
                    tv.setSelected(true);
                    selectedProblemType = problemTypeList.get(finalI);
                }
            });
            glProblemType.addView(tv);
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        if(isCameraCapture){
            if(resultCode==RESULT_OK){
                setupPhoto(new File(filePathCache));
            }
        }else{
            ContentResolver resolver=getContentResolver();
            if(resultCode==RESULT_OK){
                try{
                    // 获得图片的uri
                    Uri originalUri=data.getData();
                    setupPhoto(CommonUtil.getFileByUri(originalUri,this));
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    private void setupPhoto(File file){
        ProblemPhoto photo;

        if(photoList.size() == photoNo+1){
            photo = new ProblemPhoto();
            photoList.add(photo);
        }else{
            photo = photoList.get(photoList.size() - photoNo - 1);
        }
        photo.setPicUrl(file.getAbsolutePath());
        photoAdapter.notifyDataSetChanged();
    }

    private void initAddNewPhotoItem(){
        ProblemPhoto addNew=new ProblemPhoto();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.new_photo);
        addNew.setPicData(PictureUtil.bitmapToBytes(bitmap));
        photoList.add(addNew);
    }

    private void submit(){
        if(inspectLogId == -1L){
            Toast.makeText(this,"缺少日志id", Toast.LENGTH_SHORT).show();
            return;
        }
        InspectLogDao inspectLogDao= Inspection_App.getInstance().getDaoSession().getInspectLogDao();
        InspectLog inspectLog=inspectLogDao.queryBuilder().where(InspectLogDao.Properties.Id.eq(inspectLogId)).unique();
        if(inspectLog == null){
            Toast.makeText(this,"查询根检查日志失败",Toast.LENGTH_SHORT).show();
            return;
        }
        InspectContentLog contentLog=new InspectContentLog();
        contentLog.setInspectLogId(inspectLog.getId());
        contentLog.setInspectLogId(inspectLogId);
        contentLog.setOpDate(new Date());
        contentLog.setContentId(inspectContent.getId());
        contentLog.setResultType(selectedResultType);
        if(contentLog.getHasProblem()){
            contentLog.setProblemTypeId(selectedProblemType.getId());
            contentLog.setDescription(etProblemDescription.getText().toString());
        }
        InspectContentLogDao contentLogDao=Inspection_App.getInstance().getDaoSession().getInspectContentLogDao();
        contentLogDao.save(contentLog);
        Long contentLogId=contentLog.getId();
        ProblemPhotoDao problemPhotoDao=Inspection_App.getInstance().getDaoSession().getProblemPhotoDao();
        photoList.remove(0);
        for (int i=0;i<photoList.size();i++) {
            photoList.get(i).setContentLogId(contentLogId);
        }
        problemPhotoDao.saveInTx(photoList);

        inspectLog.setInspected(true);
        inspectLog.setOpDate(new Date());
        inspectLogDao.save(inspectLog);

        finish();

    }

}
