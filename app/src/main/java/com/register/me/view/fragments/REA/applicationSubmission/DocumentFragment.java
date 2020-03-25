package com.register.me.view.fragments.REA.applicationSubmission;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.register.me.FileUtil;
import com.register.me.R;
import com.register.me.model.data.model.QandA;
import com.register.me.presenter.DocumentPresenter;
import com.register.me.view.BaseFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jennifer - AIT on 13-02-2020PM 03:05.
 */
public class DocumentFragment extends BaseFragment implements DocumentPresenter.IDocument {


    private ArrayList<QandA> questionList;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.card_upload)
    CardView upload;
    @Inject
    DocumentPresenter presenter;
    private String[] mimeTypes;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_upload_document;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext(), this);
        mimeTypes = getContext().getResources().getStringArray(R.array.mime_types);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @OnClick(R.id.card_upload)
    public void onClickUpload() {
        showFileChooser();
    }

    public void showFileChooser() {
   /*     Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
startActivityForResult(intent,1);*/
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(intent, 1);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        String uriString = uri.toString();
        File myFile = new File(uriString);
        String path = myFile.getAbsolutePath();
        String displayName = null;

        getDisplayname(uri, uriString, myFile, displayName);
        FileUtil fileUtil = new FileUtil();
        File f = fileUtil.getFile(getContext(),uri);
       String pathd =  fileUtil.getPath(getContext(),uri);
//        path = getFilePath(getContext(),uri);
        Log.d("path",path);

        try {
            byte[] fileContent  = Files.readAllBytes(f.toPath());
            Log.d("fileContent", String.valueOf(fileContent));

            String base = getStringFile(f);
            Log.d("base", base);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getStringFile(File f) {
        InputStream inputStream = null;
        String encodedFile= "", lastVal;
        try {
            inputStream = new FileInputStream(f.getAbsolutePath());

            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            encodedFile =  output.toString();
        }
        catch (FileNotFoundException e1 ) {
            e1.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;
        return lastVal;
    }

    private void getDisplayname(Uri uri, String uriString, File myFile, String displayName) {
        if (uriString.startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        } else if (uriString.startsWith("file://")) {
            displayName = myFile.getName();
        }
        Log.d("display",displayName);
    }


    String getFilePath(Context cntx, Uri uri) {
        Cursor cursor = null;
        try {
            String[] arr = { MediaStore.Images.Media.DATA };
            cursor = cntx.getContentResolver().query(uri,  arr, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String string = cursor.getString(column_index);
            return string;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    private String convertBase64String(Bitmap bitmap){
        String pdfString = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] pdfBytes = baos.toByteArray();
        pdfString = Base64.encodeToString(pdfBytes,Base64.NO_WRAP);
        return pdfString;

    }
    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e("this", "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
