package com.pjt.andrdgambas.LOGIN;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;

public class SJ_InsNetworkTask extends AsyncTask<Integer, String, Void> {
    Context context;
    String mAddr;
    ProgressDialog progressDialog;

    // 생성자 만들어지... 2개만 쓴다고 했으니깐.
    // 생성자.
    public SJ_InsNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        try {
            URL url = new URL(mAddr); // 타고 나가야지.
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); // cast.
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) { // 실행 됨.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
