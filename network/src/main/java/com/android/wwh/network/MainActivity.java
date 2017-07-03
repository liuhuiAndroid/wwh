package com.android.wwh.network;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.wwh.library.log.Logger;
import com.android.wwh.network.entity.Weather;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView mImageView;
    @BindView(R.id.networkImageView)
    NetworkImageView mNetworkImageView;
    private RequestQueue mQueue;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mQueue = Volley.newRequestQueue(MainActivity.this);
        mImageLoader = new ImageLoader(mQueue, new BitmapCache());

        testStringRequest();
        testJsonRequest();
        testImageRequest();
        testImageLoader();
        testNetworkImageView();
        testXMLRequest();
        testGsonRequest();
    }

    private void testGsonRequest() {
        GsonRequest<Weather> gsonRequest = new GsonRequest<Weather>("http://www.weather.com.cn/data/sk/101010100.html", Weather.class,
                new Response.Listener<Weather>() {
                    @Override
                    public void onResponse(Weather response) {
                        Weather.WeatherInfo weatherInfo = response.getWeatherinfo();
                        Logger.i("city is " + weatherInfo.getCity());
                        Logger.i("temp is " + weatherInfo.getTemp());
                        Logger.i("time is " + weatherInfo.getTime());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        mQueue.add(gsonRequest);
    }

    private void testXMLRequest() {
        XMLRequest xmlRequest = new XMLRequest("http://flash.weather.com.cn/wmaps/xml/china.xml", new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                try {
                    int eventType = response.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                String nodeName = response.getName();
                                if ("city".equals(nodeName)) {
                                    String attributeValue = response.getAttributeValue(1);
                                    Logger.i("pName is " + attributeValue);
                                }
                                break;
                        }
                        eventType = response.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.e(error.getMessage());
            }
        });
        mQueue.add(xmlRequest);
    }

    private void testNetworkImageView() {
        mNetworkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        mNetworkImageView.setErrorImageResId(R.mipmap.ic_launcher);
        mNetworkImageView.setImageUrl("https://pic1.zhimg.com/v2-b1a6055e70cac5fa50522d7b514fef74_b.jpg", mImageLoader);
    }

    private void testImageLoader() {
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(mImageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        mImageLoader.get("https://pic2.zhimg.com/v2-1da489501cf81b32c41d1896230c4839_b.jpg", imageListener);
    }

    private void testImageRequest() {
        ImageRequest imageRequest = new ImageRequest(
                "http://upload.jianshu.io/users/upload_avatars/1956963/0566da18-f6f3-4c80-b0ba-21af8d473650.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        mImageView.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mImageView.setImageResource(R.mipmap.ic_launcher);
            }
        });
        //        mQueue.add(imageRequest);
    }

    private void testJsonRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://test.91naju.com/najumain/api/index", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Logger.i("onResponse = " + response.toString());
                Logger.i("biu biu biu 射击");
                Logger.json(response.toString());
                Logger.i("biu biu biu 射击结束");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.i("onErrorResponse = " + error.getMessage(), error);
            }
        });
        mQueue.add(jsonObjectRequest);
    }

    private void testStringRequest() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://www.baidu.com", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Logger.i("onResponse = " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.i("onErrorResponse = " + error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("params1", "value1");
                map.put("params2", "value2");
                return map;
                //                return super.getParams();
            }
        };
        mQueue.add(stringRequest);
    }

}
