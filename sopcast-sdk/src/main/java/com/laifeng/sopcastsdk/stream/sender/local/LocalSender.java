package com.laifeng.sopcastsdk.stream.sender.local;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.laifeng.sopcastsdk.stream.sender.Sender;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: LocalSender
 * @Package com.laifeng.sopcastsdk.stream.sender.local
 * @Description:
 * @Author Jim
 * @Date 16/9/18
 * @Time 下午5:10
 * @Version
 */
public class LocalSender implements Sender{
    private File mTestFile;
    private FileOutputStream mOutStream;
    private static BufferedOutputStream mBuffer;

    @Override
    public void start() {
        String sdcardPath = Environment.getExternalStorageDirectory().toString();
        mTestFile = new File(sdcardPath+"/SopCast.flv");

        if(mTestFile.exists()){
            mTestFile.delete();
        }

        try {
            mTestFile.createNewFile();
            mOutStream = new FileOutputStream(mTestFile);
            mBuffer = new BufferedOutputStream(mOutStream);
        } catch (Exception e) {
            e.printStackTrace();
        }



// browsing http://localhost:5000 will return Hello!!!

    }


    public static void startHttpd() {

        AsyncHttpServer server = new AsyncHttpServer();
        server.get("/test", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                _request = request;
                _response = response;
            }
        });

// listen on port 5000
        server.listen(5000);
    }

    private static AsyncHttpServerRequest _request;
    private static AsyncHttpServerResponse _response;

    @Override
    public void onData(byte[] data, int type) {
        if (mBuffer != null){
            //                mBuffer.write(data);
//                mBuffer.flush();
            if (_request != null){

//                    response.writeHead();
//                    response.end();

                _response.send();
                Log.e("sender", "data" +data.length);
                ByteBufferList bufferList = new ByteBufferList(data);
                _response.write(bufferList);

//                    BufferedInputStream bInputStream = new BufferedInputStream(BaseApplication.appContext.getAssets().open(resourceName));
//                    _response.sendStream(bInputStream, bInputStream.available());

            }
        }
    }

    @Override
    public void stop() {
        if(mBuffer != null) {
            try {
                mBuffer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mBuffer = null;
        }
        if(mOutStream != null) {
            try {
                mOutStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mBuffer = null;
            mOutStream = null;
        }
    }
}
