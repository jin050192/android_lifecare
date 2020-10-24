package com.example.lifecare.food;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.lifecare.R;
import com.example.lifecare.VO.FoodVO;
import com.example.lifecare.drug.PermissionUtils;
import com.muddzdev.styleabletoast.StyleableToast;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FoodPhoto extends AppCompatActivity {

    public static final String FILE_NAME = "temp.jpg";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private static final int MAX_LABEL_RESULTS = 10;
    private static final int MAX_DIMENSION = 200;

    private static final int GALLERY_PERMISSIONS_REQUEST = 0;
    private static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_IMAGE_REQUEST = 3;

    private TextView mImageDetails;
    private ImageView mMainImage;

    Interpreter interpreter;
    float[][] modelOutput;
    //ByteBuffer modelOutput;

    private FoodVO foodVO;
    SimpleDateFormat format1 = new SimpleDateFormat( "yyyy-MM-dd");
    Date time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_photo);

        /*상단바 숨기기*/
        getSupportActionBar().hide();


        Button fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FoodPhoto.this);
            builder
                    .setMessage(R.string.dialog_select_prompt)
                    .setPositiveButton(R.string.dialog_select_gallery, (dialog, which) -> startGalleryChooser())
                    .setNegativeButton(R.string.dialog_select_camera, (dialog, which) -> startCamera());

            builder.create().show();
        });

        mImageDetails = findViewById(R.id.foodDetail);
        mMainImage = findViewById(R.id.main_image);
        foodVO= new FoodVO();

        time = new Date();
        interpreter = getTfliteInterpreter("your_model.tflite");
//        FirebaseCustomRemoteModel remoteModel =
//                new FirebaseCustomRemoteModel.Builder("your_model").build();
//        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
//                .requireWifi()
//                .build();
//        FirebaseModelManager.getInstance().download(remoteModel, conditions)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void v) {
//                        // Download complete. Depending on your app, you could enable
//                        // the ML feature, or switch from the local model to the remote
//                        // model, etc.
//                    }
//                });
//        FirebaseModelManager.getInstance().getLatestModelFile(remoteModel)
//                .addOnCompleteListener(new OnCompleteListener<File>() {
//                    @Override
//                    public void onComplete(@NonNull Task<File> task) {
//                        File modelFile = task.getResult();
//                        if (modelFile != null) {
//                            interpreter = new Interpreter(modelFile);
//                        }
//                    }
//                });

    }

    private Interpreter getTfliteInterpreter(String modelPath){
        try{
            return new Interpreter(loadModelFile(FoodPhoto.this, modelPath));
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("인터프리터 null");
        return null;

    }

    private MappedByteBuffer loadModelFile(Activity activity, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private void doInterpreter(Bitmap yourInputImage){
        Bitmap bitmap = Bitmap.createScaledBitmap(yourInputImage, 200, 200, true);
        int batchNum =0;
        float[][][][] input = new float[1][200][200][3];
        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {
                int pixel = bitmap.getPixel(x, y);
                input[batchNum][x][y][0] = Color.red(pixel) / 255.0f;
                System.out.println(Color.red(pixel) / 255.0f);
                input[batchNum][x][y][1] = Color.green(pixel) / 255.0f;
                input[batchNum][x][y][2] = Color.blue(pixel) / 255.0f;
            }
        }
        modelOutput=new float[1][101];
        System.out.println("======================model Interpreter : "+interpreter.toString());
        System.out.println("======================model modelOutput : "+modelOutput.toString());
        interpreter.run(input, modelOutput);
    }

    public void getLabel(){

        int maxNum = 0;
        String maxLabel="";
        float maxPossibility =0;

        String maxProtein ="";
        String maxCalcium = "";
        String maxFat = "";
        String maxCarbohydrate = "";
        String maxVitaminC = "";
        String maxKcal="";

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("custom_labels.txt")));
            for (int i = 0; i < 101; i++) {
                String line = reader.readLine();
                String[] foodInfo = line.split(" ");

                System.out.println(i);

                String label = foodInfo[1];
                String protein = foodInfo[2];
                String calcium = foodInfo[3];
                String fat = foodInfo[4];
                String carbohydrate = foodInfo[5];
                String vitaminC = foodInfo[6];
                String kcal = foodInfo[7];

                float probability = modelOutput[0][i];
                if(maxPossibility <probability){
                    maxNum = i;
                    maxPossibility = probability;
                    maxLabel=label;
                    maxProtein= protein;
                    maxCalcium=calcium;
                    maxFat = fat;
                    maxCarbohydrate=carbohydrate;
                    maxVitaminC=vitaminC;
                    maxKcal = kcal;
                }
                Log.i("TAG", String.format("%s: %1.3f", label, probability));
            }
        } catch (IOException e) {
        }

        StringBuilder sb = new StringBuilder();
        sb.append("선택된 음식 : " + maxLabel +"\n 확률 : " + String.format("%1.3f", maxPossibility*100)+"%\n");
        sb.append("단백질 : " + maxProtein +"g\t\t 칼슘 : " + maxCalcium+"mg\n");
        sb.append("지방 : " + maxFat +"g\t\t  탄수화물 : " + maxCarbohydrate+"g\n");
        sb.append("비타민C : " +maxVitaminC+"mg\n");
        sb.append("칼로리 : "+maxKcal+"kcal");

        SpannableStringBuilder ssb = new SpannableStringBuilder(sb.toString());
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#4c00b8")), sb.toString().indexOf(": ")+1, sb.toString().indexOf("확률"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new AbsoluteSizeSpan(160), sb.toString().indexOf(": "), sb.toString().indexOf("확률"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#F44336")), sb.toString().indexOf("칼로리"), sb.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new AbsoluteSizeSpan(160), sb.toString().indexOf("칼로리"), sb.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        foodVO.setDate(format1.format(time));
        foodVO.setFoodName(maxLabel);
        foodVO.setKcal(maxKcal);
        foodVO.setCarbo(maxCarbohydrate);
        foodVO.setProtein(maxProtein);
        foodVO.setFat(maxFat);
        foodVO.setFoodNum(maxNum);
        int lid = getResources().getIdentifier("food_"+ String.format("%03d",maxNum), "drawable", this.getPackageName());
        System.out.println("======================================="+"food_"+ String.format("%03d",maxNum));
        System.out.println("======================================"+lid);
        foodVO.setFoodImg(lid);

        mImageDetails.setText(ssb);
    }


    public void startGalleryChooser() {
        Toast.makeText(FoodPhoto.this, "갤러리가 선택되었습니다.", Toast.LENGTH_LONG).show();
        if (PermissionForFood.requestPermission(this, GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "사진 선택"),
                    GALLERY_IMAGE_REQUEST);
        }
    }

    public void startCamera() {
        Toast.makeText(FoodPhoto.this, "카메라 선택되었습니다.", Toast.LENGTH_LONG).show();
        if (PermissionForFood.requestPermission(
                this,
                CAMERA_PERMISSIONS_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
        }
    }

    public File getCameraFile() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(dir, FILE_NAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            uploadImage(data.getData());
        } else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            uploadImage(photoUri);
        }
    }


    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, CAMERA_PERMISSIONS_REQUEST, grantResults)) {
                    startCamera();
                }
                break;
            case GALLERY_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, GALLERY_PERMISSIONS_REQUEST, grantResults)) {
                    startGalleryChooser();
                }
                break;
        }
    }

    public void uploadImage(Uri uri) {
        if (uri != null) {
            try {
                // scale the image to save on bandwidth
                Bitmap bitmap =
                        scaleBitmapDown(
                                MediaStore.Images.Media.getBitmap(getContentResolver(), uri),
                                MAX_DIMENSION);

                doInterpreter(bitmap);
                getLabel();
                mMainImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                Log.d("사진 선택에 실패 하였습니다. " , e.getMessage());
                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d("사진을 가져올 수 없습니다.", "사진을 가져올 수 없습니다.");
            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }
    }


    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    public void addFood(View w){
        if(foodVO.getFoodName()==null){
            StyleableToast.makeText(FoodPhoto.this, "음식사진을 아직 등록하지 않으셨습니다.", Toast.LENGTH_LONG, R.style.mytoast).show();
            return;
        }

        DbOpenHelper mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();
        mDbOpenHelper.insertColumn(foodVO);

        StyleableToast.makeText(FoodPhoto.this, foodVO.getFoodName()+"음식을 등록하셨습니다.", Toast.LENGTH_LONG, R.style.mytoast).show();
    }

    public void todayFoodButton(View w){
        Intent intent = new Intent(FoodPhoto.this, FoodToday.class);
        startActivity(intent);
    }
}
