#####在Android上发送HTTP请求的方式一般有两种，`HttpURLConnection`和`HttpClient`，现在分别介绍这两种的用法#####
* **使用HttpURLConnection**
  1. 首先需要获取到`HttpURLConnection`的实例，一般只需new出一个URL对象，并传入目标的网络地址，然后调用一下openConnection()即可
  
    ```  
      URL url = new URL("http://www.baidu.com"); 
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    ```
  2. 得到了`HttpURLConnection`的实例之后，我们可以设置一下HTTP请求所使用的方法。常用的方法有两个
    * `GET` ：表示希望从服务器那里获取数据
    * `POST` ：表示希望提交数据给服务器
    
      写法如下：
      ```
      connection.setRequestMethod("GET);
      ```
  3. 接下来就可以进行一些自由的定制了，比如设置连接超时、读取超时的毫秒数，以及服务器希望得到一些消息头等，示例如下：
  
      ```
      connection.setcConnectionTimeout(8000);
      connection.setReadTimeout(8000);
      ```
  4. 之后再调用`getInputStream()`方法就可以获取到服务器返回的输入流了，然后再对输入流进行读取，示例如下：
  
      ```
      InputStream in = connection.getInputStream();
      ```
  5. 最后调用`disconect()`方法将这个HTTP连接关闭，示例如下：
  
      ```
      connection.disconnect();
      ```
    
