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
      connection.setRequestMethod("GET");
      ```
      如果想要提交数据给服务器，只需要将HTTP请求的方法改成POST
      
      ```
      connection.setRequestMethod("POST");
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
      如果是想要提交数据给服务器，那么必须在获取输入流之前把要提交的数据写出即可。**注意**，每条数据都要以键值对的形式存在，数据与数据之间用 **&** 隔开，比如说我们想要向服务器提交用户名和密码，就可以这样写：
      
      ```
      DataOutputStream out = new DataOutputStream(Connection.getOutputStream());
      out.writeBytes("username=admin&password=123456");
      ```
  5. 最后调用`disconect()`方法将这个HTTP连接关闭，示例如下：
  
      ```
      connection.disconnect();
      ```
* **使用HttpClient**
  `HttpClient`是Apache提供给HTTP网络访问接口，从一开始的时候就被引入到了AndroidAPI中。它可以完成和`HttpURLConnection`几乎一模一样的效果，但两者之间爱你的用法有较大差别。
  1. 首先，因为`HttpClient`是一个接口，因此无法创建它的实例，通常情况下都会创建一个`DefaultHttpClient`的实例，示例如下：
  
      ```
      HttpClient httpClient = new DefaultHttpClient();
      ```
  2. 若要发起一条`GET`请求，就可以创建一个`HttpGet`对象，并传入目标的网络地址，然后调用`HttpClient`的`execute()`方法即可：
  
      ```
      HttpGet httpGet = new HttpGet("http://www.baidu.com");
      httpClient.execute(httpGet);
      ```
      如果是发起一条POST请求，需要创建一个`HttpPost`对象，并传入目标的网络地址，然后通过`NameValuePair`集合来存放待提交的参数，并将这个参数集合传入到一个`UrlEnCodedFormEntity`中，然后调用`HttpPost`的`setEntity()`方法将构建好的`UrlEncodedFormEntity`传入，再调用`HttpClient`的`execute()`方法，并将`HttpPost`对象传入即可，实例如下：
      
      ```
      HttpPost httpPost = new HttpPost("http://www.baidu.com");
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair("username","admin"));
      params.add(new BasicNameValuePair("password","123456"));
      UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"utf-8");
      httpPost.setEntity(entity);
      httpClient.execute(httpPost);
      ```
  3. 执行完`execute()`方法之后会返回一个`HttpPost`对象，服务器所返回的所有信息就会包含在这里面。通常情况下我们都会先取出服务器返回的状态码，如果等于200就说明请求和响应都成功了，如下所示：
      
      ```
      if(httpResponse.getStatusLine().getStatusCode() == 200) {
        //请求和响应都成功了
      }
      ```
  4. 接下来在这个if判断的内部取出服务返回的具体内容，可以调用`getEntity()`方法获取到一个`HttpEntity`实例，然后再用`EntityUtils.toString()`这个静态方法将`HttpEntity`转换为字符串即可，示例如下：
   
      ```
      HttpEntity entity = httpResponse.getEntity();
      String response = EntityUtils.toString(entity);
      ```
  
  **注意**，如果服务器返回的数据是带有中文的，直接调用`EntityUtils.toString()`方法进行转换会出现乱码的情况，这个时候只需要在转换的时候将字符集指定成 utf-8 就可以了，实例如下：
      
        String response = EntityUtils.toString(entity,"utf-8");
      
      
      
