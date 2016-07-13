###WebView的用法
  首先修改*activity_main.xml*中的代码，简单的添加一个WebView的控件，让它占满屏幕并给它设置一个id
  ```
  <?xml version="1.0" encoding="utf-8"?>
  <LinearLayout
      xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:tools="http://schemas.android.com/tools"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      tools:context=".MainActivity">
      <WebView
          android:id="@+id/web_view"
          android:layout_width="match_parent"
          android:layout_height="match_parent"/>
  </LinearLayout>
  ```
  接着修改*MainActivity.java*的代码
  ```
  public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        
        webView = (WebView) findViewById (R.id.web_view);
        webView.getSettings ().setJavaScriptEnabled (true);
        webView.setWebViewClient (new WebViewClient ());
        webView.loadUrl ("http://www.baidu.com");
    }
}

  ```
  1. 首先用findViewById找到了WebView的实例
  2. 然后调用WebView的getgetSettings()方法去设置浏览器的属性，这里通过调用setJavaScriptEnabled()方法来让WebView支持JavaScript脚本
  3. 调用webView的setWebViewClient()方法，并且传入了一个WebViewClient的实例。其作用是：当我们从一个网页跳转到另一个网页时，我们希望目标网页仍然在当前WebView中显示，而不是打开系统浏览器
  4. 调用WebView的loadUrl()方法，并将网址传入
  
**注意**，因为使用到了网络功能，而访问网络是需要声明权限的，因此我们还得修改*AndroidManifest.xml*文件，并加入权限声明
  `<uses-permission android:name="android.permission.INTERNET"/>` 
效果图如下：
 <img src="https://raw.githubusercontent.com/InnF/HttpDemo/master/webviewtest/src/device-2016-05-24-151355.png" width = "300" height = "500" align=center />
