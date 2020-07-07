package com.example.creanlol;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class CameraView extends Activity {
    private static final int PICK_IMAGE = 0;
    private static final int CAPTURE_IMAGE = 1;
    Bitmap image;

    private TessBaseAPI tessBaseAPI;
    private String datapath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cameraview);

        ImageView iv = (ImageView) findViewById(R.id.setImageView);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });
//        인식률이 너무 떨어진다. 그래서 그냥 이미지 디코딩을 위한 초기화 이미지를
//        직접 만든 민증 사진샘플로 초기화시켜서 샘플이미지에서 데이터를 추출하도록 함
        image = BitmapFactory.decodeResource(getResources(), R.drawable.asd);

        //언어 파일 경로
        datapath = getFilesDir()+"/tesseract";
        //트레이닝 데이터가 카피되어있는지 체크
        checkFile(new File(datapath + "/tesseract/"));
        //Tesseract API 사용할 언어
        String lang = "kor";
        tessBaseAPI = new TessBaseAPI();
        tessBaseAPI.init(datapath, lang);

        //완료버튼 눌렀을때
        Button bt = (Button) findViewById(R.id.finishbt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView OCRTextView = (TextView) findViewById(R.id.OCRTextView);
                String[] abc = OCRTextView.getText().toString().split("\n");
                Log.i("abc는", "이름 : "+abc[1]+"생년월일" + abc[2]);
                String[] sname = abc[1].split("\\(");
                String name = sname[0];
                String[] sage = abc[2].split("~");
                String age = sage[0];
                Log.i("네임은", sname[0]+"생년월일은 : "+sage[0]);
                SharedPreferences sp = getSharedPreferences("info",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name", name);
                editor.putString("age", age);
                editor.apply();
                finish();
            }
        });
    }
    //이미지에서 텍스트 추출
    public void processImage(View view) {
        String OCRresult = null;
        //테서렉트에 이미지 세팅
        tessBaseAPI.setImage(image);
        //이미지에서 text 얻어옴
        OCRresult = tessBaseAPI.getUTF8Text();
        //텍스트뷰에 인식한 글씨 세팅
        TextView OCRTextView = (TextView) findViewById(R.id.OCRTextView);
        OCRTextView.setText(OCRresult);
        Log.i("OCRresult", OCRresult);
    }
    private void copyFiles() {
        try{
            String filepath = datapath + "/tessdata/kor.traineddata";
            AssetManager assetManager = getAssets();
            InputStream instream = assetManager.open("tessdata/kor.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void checkFile(File dir) {
        //디렉토리가 없으면 디렉토리를 만들고 그후에 파일을 카피
        if(!dir.exists()&& dir.mkdirs()) {
            copyFiles();
        }
        //디렉토리가 있지만 파일이 없으면 파일카피 진행
        if(dir.exists()) {
            String datafilepath = datapath+ "/tessdata/kor.traineddata";
            File datafile = new File(datafilepath);
            if(!datafile.exists()) {
                copyFiles();
            }
        }
    }
    ////////////////////////////////////////////////////////////////////
    @Override
    //갤러리에서 이미지 불러온 후 행동
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
//                    File root = Environment.getExternalStorageDirectory();
//                    File cachePath = new File(root.getAbsolutePath() + "")
                    in.close();
                    // 이미지 표시
                    ImageView imageView = (ImageView) findViewById(R.id.setImageView);
                    imageView.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == CAPTURE_IMAGE) {
            if (requestCode == CAPTURE_IMAGE && resultCode == Activity.RESULT_OK && data.hasExtra("data")) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) {
                    ImageView imageView = (ImageView) findViewById(R.id.setImageView);
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
    private void checkPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(CameraView.this, "권한 허가", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(CameraView.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("카메라 권한이 필요합니다.")
                .setDeniedMessage("[설정] > [권한] 에서 카메라 사용 권한을 허용할 수 있습니다.")
                .setPermissions(Manifest.permission.CAMERA)
                .check();

    }

}