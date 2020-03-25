package com.register.me.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.widget.Toast;

import com.register.me.view.BaseActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DocumentPresenter {
    private Context context;

    public void init(Context context, IDocument listener) {
        this.context = context;
        ((BaseActivity)context).injector().inject(this);
    }



    public interface IDocument{

    }
}
