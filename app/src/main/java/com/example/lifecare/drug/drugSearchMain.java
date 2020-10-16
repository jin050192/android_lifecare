package com.example.lifecare.drug;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifecare.R;
import com.example.lifecare.push.token;

public class drugSearchMain extends AppCompatActivity {

    Button drugSearch;
    Button photoSearch;
    Button btn_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);

//        bitOption = new BitmapFactory.Options();
//        bitOption.inSampleSize = 4;

        drugSearch =(Button)findViewById(R.id.btn_drugSearch);
        photoSearch =(Button)findViewById(R.id.btn_photoSearch);
        btn_token =(Button)findViewById(R.id.btn_token);

        drugSearch.setOnClickListener(drugClickListener);
        photoSearch.setOnClickListener(drugClickListener);
        btn_token.setOnClickListener(drugClickListener);

    }

    View.OnClickListener drugClickListener = new
            View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch ((v.getId())) {
                        case R.id.btn_drugSearch:
                            Intent intent  = new
                                    Intent(drugSearchMain.this, drugSearch.class);
                            startActivity(intent);
                            break;

                        case R.id.btn_photoSearch:
                            intent  = new
                                Intent(drugSearchMain.this, drugPhoto.class);
                            startActivity(intent);
                            break;
                        case R.id.btn_token:
                            intent  = new
                                Intent(drugSearchMain.this, token.class);
                            startActivity(intent);
                            break;
                    }
                }
            };


//    //RequestCode
//    final static int PICK_IMAGE = 1; //갤러리에서 사진선택
//    final static int CAPTURE_IMAGE = 2;  //카메라로찍은 사진선택
//
//    static final String TAG = "ProfileActivityTAG";
//    private String mCurrentPhotoPath; //카메라로 찍은 사진 저장할 루트경로
//    private Bitmap img; //비트맵 사진
//    private ImageView imageView;
//    private String mTmpDownloadImageUri; //Shared에서 받아올떄 String형이라 임시로 받아오는데 사용
//    private BitmapFactory.Options bitOption;
//
//
//    //사진찍기 or 앨범에서 가져오기 선택 다이얼로그
//    private void photoDialogRadio() {
//        final CharSequence[] PhotoModels = {"갤러리에서 가져오기", "카메라로 촬영 후 가져오기"};
//        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
//
//        alt_bld.setTitle("약 사진 설정");
//        alt_bld.setSingleChoiceItems(PhotoModels, -1, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int item) {
//                Toast.makeText(drugSearchMain.this,PhotoModels[item] + "가 선택되었습니다.", Toast.LENGTH_SHORT).show();
//                if (item == 0) { //갤러리 호출
//                    Intent intent = new Intent();
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(intent, PICK_IMAGE);
//
//                } else if (item == 1) { //사직 찍으러가기
////                   takePictureFromCameraIntent();
//                    Intent intent  = new
//                    Intent(drugSearchMain.this, drugPhoto.class);
//                    startActivity(intent);
//                }
//            }
//        });
//        AlertDialog alert = alt_bld.create();
//        alert.show();
//    }
//
//    @Override //갤러리에서 이미지 불러온 후
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // Check which request we're responding to
//        super.onActivityResult(requestCode, resultCode, data);
////        imageView = findViewById(R.id.v_outDrug);
//        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
//            // Gallery에서 선택한 이미지 데이터 얻기
//            Uri uri = data.getData();
//             try {
//
//                img = BitmapFactory.decodeStream(
//                        getContentResolver().openInputStream(uri), null, bitOption);
//                img = Bitmap.createBitmap(img);
//
//                // Image 생성 주소값으로 출력할 outPutDurgPhoto 로 보내기
//                Intent intent  = new
//                        Intent(drugSearchMain.this, selectGalley.class);
//                intent.putExtra("data", uri);
//                startActivity(intent);
//
//                Log.d(TAG, "갤러리 inputStream: " + data.getData());
//                Log.d(TAG, "갤러리 사진decodeStream: " + img);
//
//                mTmpDownloadImageUri = null;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (requestCode == CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {
//            if (resultCode == RESULT_OK) {
//                try {
//                    File file = new File(mCurrentPhotoPath);
//                    InputStream in = getContentResolver().openInputStream(Uri.fromFile(file));
//                    img = BitmapFactory.decodeStream(in);
//                    imageView.setImageBitmap(img);
//                    in.close();
//
//                    mTmpDownloadImageUri = null;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

//
//    //카메라로 촬영한 이미지를파일로 저장해주는
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "LifeCare_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//
//
//    //카메라 인텐트실행 함수
//    private void takePictureFromCameraIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.lifecare.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, CAPTURE_IMAGE);
//            }
//        }
//    }

}
