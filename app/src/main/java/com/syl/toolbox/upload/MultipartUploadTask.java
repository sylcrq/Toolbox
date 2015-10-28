package com.syl.toolbox.upload;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Multipart Upload Task
 * Multipart上传任务
 *
 * Created by syl on 15/10/14.
 */
public class MultipartUploadTask extends UploadTask {

    public static final String TAG = MultipartUploadTask.class.getSimpleName();

    public static final String METHOD_POST = "POST";
    public static final String NEW_LINE = "\r\n";
    public static final String TWO_HYPHENS = "--";

    private String mBoundary;
    private byte[] mBoundaryBytes;
    private byte[] mTrailerBytes;
    private long mTotalBodyLength;

    public MultipartUploadTask(MultipartUploadRequest request) {
        super(request);
    }

    @Override
    public void upload() {
        // TODO:
    }

    @Override
    public HttpURLConnection getHTTPConnection() throws IOException {
        URL url = new URL(uploadRequest.getRequestUrl());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(METHOD_POST);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        return connection;
    }

    @Override
    public void writeBody() {
        // TODO:
    }

    @Override
    public void readResponse() {
        // TODO:
    }

    /**
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
     */
//    public void upload() {
//        // TEST Url
//        final String url = mRequest.getUploadUrl();
//
//        try {
//            mBoundary = getBoundary();
//            mBoundaryBytes = getBoundaryBytes();
//            mTrailerBytes = getTrailerBytes();
//            mTotalBodyLength = getBodyLength();
//
//            mConnection = getHTTPConnection(url);
//
//            setRequestHeader(null);
//
//            mOutputStream = mConnection.getOutputStream();
//
//            writeBody();
//
//            int code = mConnection.getResponseCode();
//
//            if(code == 200) {
//                Log.d(TAG, "upload OK");
//
//                mInputStream = mConnection.getInputStream();
//                read();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            // TODO
//        }
//    }

//    private HttpURLConnection getHTTPConnection(String url) throws IOException {
//        URL url1 = new URL(url);
//        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
//
//        connection.setRequestMethod(METHOD_POST);
//        connection.setDoInput(true);
//        connection.setDoOutput(true);
//
//        return connection;
//    }

    private void setRequestHeaders() {
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Type", "multipart/form-data; " + mBoundary);
        connection.setRequestProperty("Content-Length", String.valueOf(mTotalBodyLength));

        Map<String, String> headers = uploadRequest.getRequestHeaders();
        Set<String> headerSet = headers.keySet();

        for (String key : headerSet) {
            connection.setRequestProperty(key, headers.get(key));
        }
    }

//    private void writeBody() throws IOException {
//        List<MultipartUploadFile> files = mRequest.getUploadFiles();
//
//        for(MultipartUploadFile file : files) {
//            writeRequestParameters(file);
//            writeFile(file);
//        }
//
//        mOutputStream.write(mTrailerBytes, 0, mTrailerBytes.length);
//    }

    private void writeRequestParameters(MultipartUploadFile file) throws IOException {
        outputStream.write(mBoundaryBytes, 0, mBoundaryBytes.length);

        byte[] parameters = file.getMultipartHeader().getBytes("US-ASCII");
        outputStream.write(parameters, 0, parameters.length);
    }

    private void writeFile(MultipartUploadFile file) throws IOException {
        File file1 = file.getFile();

        InputStream inputStream = new FileInputStream(file1);

        byte[] buffer = new byte[4096];
        int read = 0;

        while ((read = inputStream.read(buffer, 0, buffer.length)) > 0) {
            outputStream.write(buffer, 0, read);
        }
    }

//    private void read() {
//        // TODO:
//    }

    private long getBodyLength() {
        long len = 0;

        List<MultipartUploadFile> files = ((MultipartUploadRequest) uploadRequest).getUploadFiles();
        for(MultipartUploadFile file : files) {
            len += mBoundaryBytes.length;
            len += file.getMultipartHeader().length();
            len += file.getFile().length();
        }

        len += mTrailerBytes.length;

        return len;
    }

//    private long getRequestParameterLength() {
//
//    }
//
//    private long getFileLength() {
//
//    }

    private String getBoundary() {
        StringBuilder builder = new StringBuilder();
        builder.append("---------------------------").append(System.currentTimeMillis());

        return builder.toString();
    }

    private byte[] getBoundaryBytes() throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        builder.append(NEW_LINE).append(TWO_HYPHENS).append(mBoundary).append(NEW_LINE);

        // TODO: "US-ASCII" ???
        return builder.toString().getBytes("US-ASCII");
    }

    private byte[] getTrailerBytes() throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        builder.append(NEW_LINE).append(TWO_HYPHENS).append(mBoundary).append(TWO_HYPHENS).append(NEW_LINE);

        // TODO: "US-ASCII" ???
        return builder.toString().getBytes("US-ASCII");
    }

}
