package com.syl.toolbox.upload;

import android.content.Intent;
import android.util.Log;

import com.syl.toolbox.services.UploadService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    public static final int HTTP_CONNECTION_TIMEOUT_DEFAULT = 3 * 1000;  // 3s
    public static final int HTTP_READ_TIMEOUT_DEFAULT = 3 * 1000;  // 3s
    public static final int FILE_BUFFER_SIZE = 4 * 1024;  // 4KB

    private HttpURLConnection mConnection;
    private OutputStream mOutputStream;
    private InputStream mInputStream;

    private String mBoundary;
    private byte[] mBoundaryBytes;
    private byte[] mTrailerBytes;
    private long mTotalBodyLength;

    // These upload request info come from Intent
    private long mUploadId;
    private String mUrl;
    private String mMethod;
    private ArrayList<HTTPRequestHeader> mHeaders;
    private ArrayList<UploadFile> mFiles;
    private UploadNotificationConfig mNotificationConfig;

    public MultipartUploadTask(UploadService service, Intent intent) {
        super(service);

        getUploadRequestInfo(intent);
    }

    public void getUploadRequestInfo(Intent intent) {
        mUploadId = intent.getLongExtra(UploadService.UPLOAD_REQUEST_ID, 0);
        mUrl = intent.getStringExtra(UploadService.UPLOAD_REQUEST_URL);
        mMethod = intent.getStringExtra(UploadService.UPLOAD_REQUEST_METHOD);
        mHeaders = intent.getParcelableArrayListExtra(UploadService.UPLOAD_REQUEST_HEADERS);
        mFiles = intent.getParcelableArrayListExtra(UploadService.UPLOAD_REQUEST_FILES);
        mNotificationConfig = intent.getParcelableExtra(UploadService.UPLOAD_REQUEST_NOTIFICATION_CONFIG);
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
        Log.d(TAG, "getHTTPConnection # Url=" + mUrl + ", Method=" + mMethod);

        URL url = new URL(mUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(mMethod);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setConnectTimeout(HTTP_CONNECTION_TIMEOUT_DEFAULT);
        connection.setReadTimeout(HTTP_READ_TIMEOUT_DEFAULT);

        return connection;
    }

    /**
     * 设置Multipart HTTP Request Headers
     */
    private void setRequestHeaders() {
        final String contentTypeKey = "Content-Type";
        final String contentTypeValue = "multipart/form-data; boundary=" + mBoundary;
        final String contentLengthKey = "Content-Length";
        final String contentLengthValue = String.valueOf(mTotalBodyLength);

//        mConnection.setRequestProperty("Connection", "keep-alive");
        mConnection.setRequestProperty(contentTypeKey, contentTypeValue);
        mConnection.setRequestProperty(contentLengthKey, contentLengthValue);

        Log.d(TAG, "setRequestHeaders # " + contentTypeKey + " " + contentTypeValue);
        Log.d(TAG, "setRequestHeaders # " + contentLengthKey + " " + contentLengthValue);

        ArrayList<HTTPRequestHeader> headers = mHeaders;
        for(HTTPRequestHeader header : headers) {
            mConnection.setRequestProperty(header.getKey(), header.getValue());
            Log.d(TAG, "setRequestHeaders # " + header.getKey() + " " + header.getValue());
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
        ArrayList<UploadFile> files = mFiles;

        for(UploadFile file : files) {
            Log.d(TAG, "writeBody # "+file.getUploadFile().getAbsolutePath());
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

        byte[] buffer = new byte[FILE_BUFFER_SIZE];
        int read;
        long sended = 0;

        while ((read = inputStream.read(buffer, 0, buffer.length)) > 0) {
            mOutputStream.write(buffer, 0, read);

            sended += read;
            int percent = (int) (((double)sended / mTotalBodyLength) * 100);

            Log.d(TAG, "writeFile # uploading... " + percent + "%");

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

        List<UploadFile> files = mFiles;

        for(UploadFile file : files) {
            len += mBoundaryBytes.length;
            len += ((MultipartUploadFile) file).getMultipartUploadHeader().length();
            len += file.getUploadFile().length();
        }

        len += mTrailerBytes.length;

        Log.d(TAG, "getTotalBodyLength # len=" + len);

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
