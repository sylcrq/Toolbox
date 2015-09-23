package com.syl.toolbox.network;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Created by shenyunlong on 2015/9/23.
 */
public class MyNetwork {

    public static final String TAG = MyNetwork.class.getSimpleName();

    private static MyNetwork mInstance;

    private MyNetwork() {}

    /**
     * 单例
     *
     * @return
     */
    public static MyNetwork getInstance() {
        if(mInstance == null) {
            mInstance = new MyNetwork();
        }

        return mInstance;
    }

    /**
     * GET网络请求
     *
     * @param address
     */
    public void request4Get(String address, Map<String, String> header) {
        URL url;
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");  // Sets the request command which will be sent to the remote HTTP server.
            //connection.setRequestProperty("Accept-Encoding", "identity");  // Sets the value of the specified request header field.

            for(String key : header.keySet()) {
                connection.setRequestProperty(key, header.get(key));
            }

            //connection.getErrorStream();
            //connection.getHeaderFields();

            String method = connection.getRequestMethod();  // Returns the request method which will be used to make the request to the remote HTTP server.
            // **** DO CONNECT ****
            int respCode = connection.getResponseCode();  // Returns the response code returned by the remote HTTP server.
            String respMsg = connection.getResponseMessage();  // Returns the response message returned by the remote HTTP server.
            String encoding = connection.getContentEncoding();  // Returns the encoding used to transmit the response body over the network.

            Log.d(TAG, "method="+method+", respCode="+respCode+", respMsg="+respMsg+", encoding="+encoding);

            String urlHost = url.getHost();
            String connUrlHost = connection.getURL().getHost();

            Log.d(TAG, "urlHost="+urlHost+", connectionUrlHost="+connUrlHost);

            inputStream = connection.getInputStream();
            String ret = readStream(inputStream);

            Log.d(TAG, "request4Get: "+valueofString(ret));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(connection != null) {
                connection.disconnect();  // Releases this connection so that its resources may be either reused or closed.
            }
        }
    }

    /**
     * POST网络请求
     *
     * @param address
     */
    public void request4Post(String address, Map<String, String> header) {
        URL url;
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;

        final String args = "fromdevice=pc&clientip=10.10.10.0&detecttype=LocateRecognize&languagetype=CHN_ENG&imagetype=1&image=/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDABMNDxEPDBMREBEWFRMXHTAfHRsbHTsqLSMwRj5KSUU+RENNV29eTVJpU0NEYYRiaXN3fX59S12Jkoh5kW96fXj/2wBDARUWFh0ZHTkfHzl4UERQeHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHj/wAARCAAfACEDAREAAhEBAxEB/8QAGAABAQEBAQAAAAAAAAAAAAAAAAQDBQb/xAAjEAACAgICAgEFAAAAAAAAAAABAgADBBESIRMxBSIyQXGB/8QAFAEBAAAAAAAAAAAAAAAAAAAAAP/EABQRAQAAAAAAAAAAAAAAAAAAAAD/2gAMAwEAAhEDEQA/APawEBAQEBAgy8i8ZTVV3UY6V1eU2XoWDDZB19S646Gz39w9fkKsW1r8Wm2yo1PYis1be0JG9H9QNYCAgc35Cl3yuVuJZl0cB41rZQa32dt2y6OuOiOxo61vsLcVblxaVyXD3hFFjL6La7I/sDWAgICAgICB/9k=";

        try {
            url = new URL(address);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);  // Sets the flag indicating whether this URLConnection allows output.
            //connection.setFixedLengthStreamingMode(1024);  // Configures this connection to stream the request body with the known fixed byte count of contentLength
            //connection.setChunkedStreamingMode(1024);  // Stream a request body whose length is not known in advance.

            for(String key : header.keySet()) {
                connection.setRequestProperty(key, header.get(key));
            }

            // **** DO CONNECT ****
            outputStream = connection.getOutputStream();
            writeStream(outputStream, args.getBytes("UTF-8"));

            inputStream = connection.getInputStream();
            String ret = readStream(inputStream);

            Log.d(TAG, "request4Post: "+valueofString(ret));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                connection.disconnect();
            }
        }

    }

    public static String valueofString(String string) {
        if(string == null) {
            return "null";
        } else {
            return string;
        }
    }

    private String readStream(InputStream inputStream) throws IOException{
        if(inputStream == null) {
            return null;
        }

        // 读取String可以用下面这种方式
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        StringBuilder sb = new StringBuilder();

        while((line = reader.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    private void writeStream(OutputStream outputStream, byte[] data) throws IOException{
        if(outputStream == null) {
            return;
        }

        outputStream.write(data);
    }
}
