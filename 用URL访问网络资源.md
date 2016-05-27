

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
* * *
URL的openConnection()方法将返回一个URLConnection对象，该对象表示应用程序和URL之间的通信连接。程序可以通过URLConnection实例向该URL发送请求，读取URL引用的资源

通常创建一个和URL的连接，并发送请求、读取此URL引用的资源需要如下几个步骤
    1. 通过调用URL对象的openConnection()方法来创建URLConnection对象
    2. 设置URLConnection的参数和普通请求属性
    3. 如果只是发送GET方式的请求，那么使用connect方法建立和远程资源之间的实际连接即可；如果需要POST方式的请求，则需要获取URLConnection实例对应的输出流来发送请求参数
    4. 远程资源变为可用，程序可以访问远程资源的头字段，或通过输入流读取远程资源的数据
    
在建立和远程资源的实际连接之前，程序可以通过如下方法来设置请求头字段
>setAllowUserInteraction: 设置该URLConnection的allowUserInteraction请求头字段的值
>setDoInput: 设置该URLConnection的doInput请求头字段的值
>setDoOutput: 设置该URLConnection的doOutput请求头字段的值
>setIfModifiedSince: 设置该URLConnection的ifModifiedSince请求头字段的值
>setUseCaches: 设置该URLConnection的useCaches请求头字段的值
>setRequestProperty(String key, String value): 设置该URLConnection的key请求头字段的值为value，示例如下：
```Java
conn.setRequestProperty("accept","*/*");
```
>addRequestProperty(String key, String value): 为该URLConnection的key请求头字段增加value值，该方法并不会覆盖原请求头字段的值，而是将新值追加到原请求头字段中

当远程资源可用之后，程序可以使用一下方法来访问头字段和内容
>Object getcontent(): 获取该URLConnection的内容
>String getHeaderField(String name): 获取指定响应头字段的值
>getInputStream(): 返回URLConnection对应的输入流，用于获取URLConnection响应的内容
>getOutputStream(): 返回URLConnection对应的输出流，用于向URLConnection发送请求参数
**注意** 如果既要使用输入流读取URLConnection响应的内容，也要使用输出流发出请求参数，一定要先使用输出流，再使用输入流

getHeaderField()方法用于根据相应头字段来返回对应的值。而某些头字段由于经常需要访问，所以Java提供了以下方法来访问特定响应头字段的值
>getContentEcoding(): 获取content-econding响应头字段的值
>getContentLength(): 获取content-length响应头字段的值
>getContentType(): 获取content-type响应头字段的值
>getDate(): 获取date响应头字段的值
>getExpiration(): 获取expires响应头字段的值
>getLastModified(): 获取last-madified响应头字段的值

发送GET、POST请求的工具类，实例如下：

```Java

```
