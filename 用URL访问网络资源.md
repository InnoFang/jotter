

####1.1 使用URL读取网络资源
* * *
URL类提供了多个构造器用于创建URL对象，一旦获得了，URL对象之后，就可以调用如下常用方法来访问该URL对应的资源了
>String getFile(): 获取次URL的资源名

>String getHost(): 获取此URL的主机名

>String getPath(): 获取此URL的路径部分

>int getPort(): 获取此URL的端口号

>String getProtocol(): 获取此URL的协议名称

>String getQuery(): 获取此URL的查询字符串部分

>URLConnection openConnection(): 返回一个URLConnection对象，它表示到URL所引用的远程对象的连接

>InputStream openStream(): 打开与此URL的连接，并返回一个用于读取该URL资源的InputStream

通过URL类读取远程资源，实例如下：

```Java
public class MainActivity extends AppCompatActivity {

    private ImageView show;//代表从网络上下载的图片
    private Bitmap bitmap;
    Handler handler = new Handler ()
    {
        @Override
        public void handleMessage (Message msg) {
            if(msg.what == 0x123)
            {
                //使用ImageView显示该图片
                show.setImageBitmap (bitmap);
            }
        }
    };

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        show = (ImageView) findViewById (R.id.show);
        new Thread ()
        {
            public void run()
            {
                try {
                    //定义一个URL对象
                    URL url = new URL ("http://www.crazyit.org/attachments/" +
                            "month_1008/20100812_7763e970f822325bfb019ELQVym8tw3A.png");
                    //打开该URL对应的资源的输入流
                    InputStream is = url.openStream ();
                    //从InputStream中解析出图片
                    bitmap = BitmapFactory.decodeStream (is);
                    //添加消息，通知UI组件显示该图片
                    handler.sendEmptyMessage (0x123);
                    is.close ();
                    //再次打开URL对应的资源的输入流
                    is = url.openStream ();
                    //打开手机文件对应的输出流
                    OutputStream os = openFileOutput ("crazyit.png",MODE_PRIVATE);
                    byte[] buff = new byte[1024];
                    int hasRead = 0;
                    //将URL对应的资源下载到本地
                    while((hasRead = is.read (buff)) > 0) {
                        os.write (buff, 0, hasRead);
                    }
                    is.close ();
                    os.close ();
                } catch (Exception e) {
                    e.printStackTrace ();
                }
            }
        }.start ();
    }
}
```


####1.2 使用URLConnection提交请求
