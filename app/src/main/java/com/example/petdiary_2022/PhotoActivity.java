package com.example.petdiary_2022;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    private Uri mlmageCaptureUri;
    private ImageView iv_UserPhoto;
    private int id_view;
    private String absoultePath;
    private MyGridViewAdapter adapter = new MyGridViewAdapter();
    final GridView gv = (GridView) findViewById(R.id.gridView);


    ArrayList<PhotoBear> photoIDs = new ArrayList<PhotoBear>();

    private final DatabaseReference root = FirebaseDatabase.getInstance().getReference("Photo");
    private final StorageReference reference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        /*
        photoIDs.add(new PhotoBear(,R.drawable.img1,"img1"+photoIDs.size()));
        photoIDs.add(new PhotoBear("2",R.drawable.img1,"img1"+photoIDs.size()));
        photoIDs.add(new PhotoBear("3",R.drawable.img1,"img1"+photoIDs.size()));
*/

        gv.setAdapter(adapter);

        Button btn_plus = (Button) this.findViewById(R.id.plusBtn);
        btn_plus.setOnClickListener(this::onClick);
        /*
        ?????? ??????????????? ????????? ?????????
        */

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View dialogView = (View) View.inflate(PhotoActivity.this, R.layout.dialog_photo, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(PhotoActivity.this);
                ImageView ivPhoto = (ImageView) dialogView.findViewById(R.id.iv_Photo);
                ivPhoto.setImageResource(position);
                dlg.setTitle("????????? ??????");
                dlg.setView(dialogView);
                dlg.setNegativeButton("??????", null);
                dlg.show();
            }
        });
    }
    /*
    ???????????? ?????? ????????????
     */
    public void doTakeAlbumAction(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);

    }

    /*
    ??????????????? ?????? ??????
     */
    public void doTakePhotoAction(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //????????? ????????? ????????? ????????? ??????
        String url = "tmp_" + String.valueOf(java.lang.System.currentTimeMillis())+".jpg";
        mlmageCaptureUri = FileProvider.getUriForFile (getApplicationContext(), "????????????.provider",
                new File (Environment.getExternalStorageDirectory(), url));

        intent.putExtra(MediaStore.EXTRA_OUTPUT,mlmageCaptureUri);
        uploadToFireBase((mlmageCaptureUri));
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode!=RESULT_OK)
            return ;

        switch(requestCode){
            case PICK_FROM_ALBUM:{
                mlmageCaptureUri=data.getData();

                uploadToFireBase((mlmageCaptureUri));
                Log.d("SmartWheel",mlmageCaptureUri.getPath().toString());
            }

            case PICK_FROM_CAMERA:{
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mlmageCaptureUri,"image/");

                intent.putExtra("outputX",200);
                intent.putExtra("outputY",200);
                intent.putExtra("aspectX",1);
                intent.putExtra("aspectY",1);
                intent.putExtra("scale",true);
                intent.putExtra("return-data",true);
                startActivityForResult(intent, CROP_FROM_IMAGE);
                break;
            }
            case CROP_FROM_IMAGE:{
                if(resultCode != RESULT_OK){
                    return ;
                }

                final Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                        "/SmartWheel/"+ java.lang.System.currentTimeMillis()+".jpg";

                if(extras != null){
                    Bitmap photo = extras.getParcelable("data");
                    iv_UserPhoto.setImageBitmap(photo);

                    storeCropImage(photo,filePath);
                    absoultePath =filePath;
                    break;


                }
                File f = new File(mlmageCaptureUri.getPath());
                bindInsert(f);
                if(f.exists()) {
                    f.delete();
                }
            }
        }
    }

    private void storeCropImage(Bitmap bitmap, String filePath) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel";
        File directory_SmartWheel = new File(dirPath);
        if(!directory_SmartWheel.exists()) // SmartWheel ??????????????? ????????? ????????? (?????? ???????????? ????????? ????????? ?????????.)
            directory_SmartWheel.mkdir();
        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(copyFile)));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {

        id_view = v.getId();
        if(v.getId() == R.id.plusBtn) {
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakePhotoAction();
                }
            };
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakeAlbumAction();
                }
            };
            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
            new AlertDialog.Builder(this)
                    .setTitle("???????????? ????????? ??????")
                    .setPositiveButton("????????????", cameraListener)
                    .setNeutralButton("????????????", albumListener)
                    .setNegativeButton("??????", cancelListener)
                    .show();
        }
        else if(v.getId() == R.id.delBtn){
            //final int position = gv.getCheckedItemPosition();

        }
    }

    private void bindInsert(File photoname) {
        // Item ??????
        photoIDs.add(new PhotoBear(photoname,photoIDs.size(),"photo" + photoIDs.size()));

        // Grid list ??????
        adapter.notifyDataSetChanged();
    }

    public class MyGridViewAdapter extends BaseAdapter {
        public int getCount() {
            return photoIDs.size();
        }


        public void addPhoto(PhotoBear photoID){
            photoIDs.add(photoID);
        }
        @Override
        public Object getItem(int position) {
            return photoIDs.get(position);
        }
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup viewGroup) {

            Context context = viewGroup.getContext();
            final PhotoBear photobear = photoIDs.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.gridview_list_item, viewGroup, false);
                ImageView iv_icon =(ImageView) convertView.findViewById(R.id.iv_icon);
                iv_icon.setImageResource(photobear.getResId());
            }

            else{
                View view = new View(context);
                view = (View) convertView;
            }
            return convertView;

        }
    }

    private void uploadToFireBase(Uri uri){
        StorageReference fileRef = reference.child(java.lang.System.currentTimeMillis() + "." +
                getFileExtension(uri));
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }


}



