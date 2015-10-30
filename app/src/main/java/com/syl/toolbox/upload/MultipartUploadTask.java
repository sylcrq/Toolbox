package com.syl.toolbox.upload;

import android.os.Build;
import android.util.Log;

import com.syl.toolbox.services.UploadService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Multipart Upload Task
 * Multipart上传任务
 *
 * Created by syl on 15/10/14.
 *
 * 1. get http connection
 * 2. set http header
 * such as:
 * Content-Type: multipart/form-data;
 * boundary=---------------------------9051914041544843365972754266
 * Content-Length: 554
 * 3. write body
 *
 * -----------------------------9051914041544843365972754266
 * Content-Disposition: form-data; name="text"
 *
 * text default
 * -----------------------------9051914041544843365972754266
 * Content-Disposition: form-data; name="file1"; filename="a.txt"
 * Content-Type: text/plain
 *
 * Content of a.txt.
 *
 * -----------------------------9051914041544843365972754266
 * Content-Disposition: form-data; name="file2"; filename="a.html"
 * Content-Type: text/html
 *
 * <!DOCTYPE html><title>Content of a.html.</title>
 *
 * -----------------------------9051914041544843365972754266--
 *
 * 4. get response code
 *
 *
 */
public class MultipartUploadTask extends UploadTask {

    public static final String TAG = MultipartUploadTask.class.getSimpleName();

    public static final String METHOD_POST = "POST";
    public static final String NEW_LINE = "\r\n";
    public static final String TWO_HYPHENS = "--";
    public static final int HTTP_UPLOAD_OK = 200;

    private HttpURLConnection mConnection;
    private OutputStream mOutputStream;
    private InputStream mInputStream;

    private String mBoundary;
    private byte[] mBoundaryBytes;
    private byte[] mTrailerBytes;
    private long mTotalBodyLength;

    public MultipartUploadTask(MultipartUploadRequest request, UploadService service) {
        super(request, service);
    }

    @Override
    public void upload() {

        try {
            mUploadService.sendUploadStart();

            mBoundary = getBoundary();
            mBoundaryBytes = getBoundaryBytes();
            mTrailerBytes = getTrailerBytes();
            mTotalBodyLength = getTotalBodyLength();

            // 1. Get HTTP Connection
//            Log.d(TAG, "getHTTPConnection");
            mConnection = getHTTPConnection();

            // 2. Set Multipart Upload HTTP Request Headers
//            Log.d(TAG, "setRequestHeaders");
            setRequestHeaders();

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                mConnection.setFixedLengthStreamingMode(mTotalBodyLength);
//            } else {
//                mConnection.setFixedLengthStreamingMode((int) mTotalBodyLength);
//            }

            // 3. Get HTTP OutputStream
//            Log.d(TAG, "getOutputStream");
            mOutputStream = mConnection.getOutputStream();

            // 4. Write Body
//            Log.d(TAG, "writeBody");
            writeBody();

            // 5. Get Response Code
//            Log.d(TAG, "getResponseCode");
            int code = mConnection.getResponseCode();
            if(code == HTTP_UPLOAD_OK) {
                Log.d(TAG, "upload ok !");
//                mInputStream = mConnection.getInputStream();
//
//                readResponse();
                mUploadService.sendUploadComplete();
            } else {
                Log.d(TAG, "upload failed ! code=" + code);
                mUploadService.sendUploadError();
            }
        } catch (IOException e) {
            e.printStackTrace();
            mUploadService.sendUploadError();
        } finally {
            // close
            closeInputStream();
            closeOutputStream();
            closeHTTPConnection();
        }
    }

    private void closeInputStream() {
        if(mInputStream != null) {
            try {
                mInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeOutputStream() {
        if(mOutputStream != null) {
            try {
                mOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeHTTPConnection() {
        if(mConnection != null) {
            mConnection.disconnect();
        }
    }

    /**
     * 获取HTTP Connection
     *
     * @return HttpURLConnection
     * @throws IOException
     */
    private HttpURLConnection getHTTPConnection() throws IOException {
        URL url = new URL(mUploadRequest.getRequestUrl());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(METHOD_POST);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        return connection;
    }

    /**
     * 设置Multipart HTTP Request Headers
     */
    private void setRequestHeaders() {
//        mConnection.setRequestProperty("Connection", "keep-alive");
        mConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + mBoundary);
        mConnection.setRequestProperty("Content-Length", String.valueOf(mTotalBodyLength));

        Map<String, String> headers = mUploadRequest.getRequestHeaders();
        Set<String> headerSet = headers.keySet();

        for (String key : headerSet) {
            mConnection.setRequestProperty(key, headers.get(key));
        }
    }


    private void readResponse() {
        // TODO:
//        mInputStream.read();
    }


    /**
     * Multipart Upload HTTP Request写body数据
     *
     * @throws IOException
     */
    private void writeBody() throws IOException {
        List<UploadFile> files = mUploadRequest.getRequestFiles();

        for(UploadFile file : files) {
            writeParameters((MultipartUploadFile) file);
            writeFile((MultipartUploadFile) file);
        }

        mOutputStream.write(mTrailerBytes, 0, mTrailerBytes.length);
    }

    private void writeParameters(MultipartUploadFile file) throws IOException {
        mOutputStream.write(mBoundaryBytes, 0, mBoundaryBytes.length);

        byte[] parameters = file.getMultipartUploadHeader().getBytes("US-ASCII");
        mOutputStream.write(parameters, 0, parameters.length);
    }

    private void writeFile(MultipartUploadFile file) throws IOException {
        InputStream inputStream = new FileInputStream(file.getUploadFile());

        byte[] buffer = new byte[4096];
        int read;
        long sended = 0;

        while ((read = inputStream.read(buffer, 0, buffer.length)) > 0) {
            mOutputStream.write(buffer, 0, read);

            sended += read;
            int percent = (int) (((double)sended / mTotalBodyLength) * 100);

            Log.d(TAG, "uploading... " + percent + "%");

            mUploadService.sendUploadProgress(percent);
        }
    }

    /**
     * Multipart Upload HTTP Request的body总长度
     *
     * @return long
     */
    private long getTotalBodyLength() {
        long len = 0;

        List<UploadFile> files = mUploadRequest.getRequestFiles();
        for(UploadFile file : files) {
            len += mBoundaryBytes.length;
            len += ((MultipartUploadFile) file).getMultipartUploadHeader().length();
            len += file.getUploadFile().length();
        }

        len += mTrailerBytes.length;

        return len;
    }

    private String getBoundary() {
//        final StringBuilder builder = new StringBuilder();
//        builder.append("---------------------------").append(System.currentTimeMillis());
//
//        return builder.toString();

        return "---------------------------" + System.currentTimeMillis();
    }

    private byte[] getBoundaryBytes() throws UnsupportedEncodingException {
//        final StringBuilder builder = new StringBuilder();
//        builder.append(NEW_LINE).append(TWO_HYPHENS).append(mBoundary).append(NEW_LINE);
//
//        return builder.toString().getBytes("US-ASCII");

        // "US-ASCII" ???
        return (NEW_LINE + TWO_HYPHENS + mBoundary + NEW_LINE).getBytes("US-ASCII");
    }

    private byte[] getTrailerBytes() throws UnsupportedEncodingException {
//        final StringBuilder builder = new StringBuilder();
//        builder.append(NEW_LINE).append(TWO_HYPHENS).append(mBoundary).append(TWO_HYPHENS).append(NEW_LINE);
//
//        return builder.toString().getBytes("US-ASCII");

        // "US-ASCII" ???
        return (NEW_LINE + TWO_HYPHENS + mBoundary + TWO_HYPHENS + NEW_LINE).getBytes("US-ASCII");
    }

}
