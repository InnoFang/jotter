## TCP/IP 协议

 + TCP/IP是目前世界上应用最为广泛的协议，是以TCP和IP为基础的不同层次上多个协议的集合，也称：TCP/IP协议族或TCP/IP协议栈
 + TCP：Transmission Control Protocol  传输控制协议
 + IP：Internet Protocol 互联网协议




## IP地址

为实现网络中不同计算机之间的通信，每台机器都必须有一个唯一的表示--IP地址
 
IP地址格式：数字型，如：192.168.0.1。版本 IPV4，32位二进制

## 端口 

 + 用于区分不同应用程序
 + 端口号范围为0~65535，其中0~1023为系统保留（若需使用自定义端口号，建议使用1023之后的端口号）
 + IP地址和端口号组成了所谓的Socket，Socket是网路上运行的程序之间双向通信链路的终结点，是TCP和UDP的基础
+ + http:80    ftp:21    telnet:23

## Java中的网络支持

针对网络通信的不同层次，Java提供的网络功能有四大类：
 + InetAddress：用于表示网络上的硬件资源，表示互联网协议（IP）地址
 + URL： 统一资源定位符，表示Internet上某一资源的地址 通过URL可以直接读取或写入网络上的数据
 + Sockets：使用TCP协议实现网络通信的Socket相关类
 + Datagram：使用UDP协议，将数据保存在数据报中，通过网路进行通信


## InetAddress类

> 用于表示网络上的硬件资源，表示互联网协议（IP）地址 

```java
  /*获取本机的InetAddress实例*/
        InetAddress address = InetAddress.getLocalHost();
        System.out.println("计算机名：" + address.getHostName());
        System.out.println("IP地址：" + address.getHostAddress());

        /*直接获取IP地址的字节数组形式*/
        byte[] bytes = address.getAddress();
        System.out.println("字节数组形式的IP：" + Arrays.toString(bytes));
        /*直接输出InetAddress对象*/
        System.out.println(address);

        /*根据主机名获取InetAddress实例*/
        InetAddress address2 = InetAddress.getByName("Inno");
        /*根据IP地址获取InetAddress实例*/
//        InetAddress address2 = InetAddress.getByName("192.168.210.2");
        System.out.println("计算机名：" + address2.getHostName());
        System.out.println("IP地址：" + address2.getHostAddress());
```

## URL

>  统一资源定位符，表示Internet上某一资源的地址 

URL由两部分组成：
 + 协议名称
 + 资源名称
中间用冒号隔开，eg: http://innofang.github.io

```java
/*创建一个url实例*/
try {
    URL imooc = new URL("http://www.imooc.com");
    //?后面表示参数，#后面表示锚点
    URL url = new URL(imooc, "/index.html?username=Inno#test");
    System.out.println("协议：" + url.getProtocol());
    System.out.println("主机: " + url.getHost());
    // 如果未指定端口号，则使用默认的端口号，此时getPort()方法返回值为-1
    System.out.println("端口: " + url.getPort());
    System.out.println("文件路径: " + url.getPath());
    System.out.println("文件名: " + url.getFile());
    System.out.println("相对路径: " + url.getRef());
    System.out.println("查询字符串: " + url.getQuery());
} catch (MalformedURLException e) {
    e.printStackTrace();
}
```
## 使用URL读取网页内容

 +  通过URL对象的openStream()方法可以得到指定资源的输入流
 + 通过输入流可以读取，访问网络上的数据

```java
/*创建一个URL实例*/
URL url = new URL("http://www.baidu.com");
// 通过URL的openStream方法获取URL对象所表示的资源字节输入流
InputStream is = url.openStream();
// 将字节输入流转化为字符输入流
InputStreamReader isr = new InputStreamReader(is);
// 为字符输入流添加缓冲
BufferedReader br = new BufferedReader(isr);
String data = null;
while ( (data = br.readLine()) != null ){
    System.out.println(data);
}

br.close();
isr.close();
is.close();
```
## Socket 通信

TCP协议是面向连接，可靠的，有序的，以字节流的方式发送数据，基于TCP协议实现网络通信的类
 + 客户端Socket类
 + 服务端ServerSocket类

## Socket通信模型

<img src="http://a3.qpic.cn/psb?/V13lwktv2PBiIC/2oJutJeGO4R3HXDgjwL4vdqh7j8j8OKDzhOteVszbFI!/b/dAoBAAAAAAAA&bo=TAM3AgAAAAADB1g!&rf=viewer_4" />
  
## Socket通信实现步骤

 + 创建ServerSocket和Socket
 + 打开连接到Socket的输入/输出流
 + 按照协议对Socket进行读/写操作
 + 关闭输入输出流，关闭Socket

### 服务器端：
 + 创建ServerSocket对象，绑定监听端口
 + 通过accept() 方法监听客户端请求
 + 连接建立后，通过输入流读取客户端发送的请求信息
 + 通过输出流向客户端发送响应信息
 + 关闭相关资源

```java
public class Server {
    public static void main(String[] args) throws Exception{
    // 1.创建服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
    ServerSocket serverSocket = new ServerSocket(8888);
    // 2.调用accept()方法开始监听，等待客户端的连接
    System.out.println("***服务器即将启动，等待客户端的连接***");
    // 开始监听，等待客户端连接
    Socket socket = serverSocket.accept();
    // 3.获取输入流，并读取客户端信息
    InputStream is = socket.getInputStream();
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(isr);
    String info = null;
    while((info = br.readLine()) != null){
        System.out.println("我是服务端，客户端说：" + info);
    }
    socket.shutdownInput();// 关闭输入流

    // 4. 获取输出流，响应客户端的请求
    OutputStream os = socket.getOutputStream();
    PrintWriter pw = new PrintWriter(os);
    pw.write("欢迎你！");
    pw.flush();// 刷新缓存
    socket.shutdownOutput();

    // 5.关闭相关资源
    pw.close();
    os.close();
    br.close();
    isr.close();
    is.close();
    socket.close();
  }
}
```

### 客户端：
 + 创建Socket对象，指明需要连接的服务器的地址和端口号
 + 连接建立后，通过输出流向服务器端发送请求信息
 + 通过输入流获取服务器响应的信息
 + 关闭相关资源

```java
public class Client {
    public static void main(String[] args) throws Exception{
        // 1.创建客户端Socket，指定服务器端地址和端口
        Socket socket = new Socket("localhost", 8888);
        // 2.获取输出流，向服务器端发送信息
        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os);
        pw.write("用户名：admin;密码：123");
        pw.flush();
        socket.shutdownOutput();// 关闭输出流

        // 3.获取输入流，并读取服务器端的响应信息
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String info = null;
        while((info = br.readLine()) != null){
            System.out.println("我是客户端,服务端说：" + info);
        }

        // 4. 关闭资源
        br.close();
        is.close();
        pw.close();
        os.close();
        socket.close();

        /*
        对于同一个socket，如果关闭了输出流，则与该输出流关联的socket也会被关闭，
        所以一般不用关闭流，直接关闭socket即可
         */
    }
}
```

### 多线程服务器、 
应用多线程来实现服务器与多客户端之间的通信
基本步骤:    
 + 服务器端创建ServerSocket，循环调用accept()等待客户端连接
 + 客户端创建一个socket并请求和服务器短链接
 + 服务器端接受客户端请求，创建socket与该客户端建立专线连接
 + 建立连接的两个socket在一个单独的线程上对话
 + 服务器端继续等待新的连接

**创建ServerThread类**
```java
public class ServerThread extends Thread {
    // 和本线程相关的Socket
    Socket socket = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    // 线程执行的操作，响应客户端的请求
    public void run() {
        // 3.获取输入流，并读取客户端信息
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;
        try {
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("我是服务端，客户端说：" + info);
            }
            socket.shutdownInput();// 关闭输入流

            // 4. 获取输出流，响应客户端的请求
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.write("欢迎你！");
            pw.flush();// 刷新缓存
            socket.shutdownOutput();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                // 5.关闭相关资源
                if (pw != null)
                    pw.close();
                if (os != null)
                    os.close();
                if (br != null)
                    br.close();
                if (isr != null)
                    isr.close();
                if (is != null)
                    is.close();
                if (socket != null)
                    socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
```
**修改Server类**

```java
public class Server {
    public static void main(String[] args) throws Exception{
        // 1.创建服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
        ServerSocket serverSocket = new ServerSocket(8888);
        // 2.调用accept()方法开始监听，等待客户端的连接
        System.out.println("***服务器即将启动，等待客户端的连接***");
        Socket socket = null;
        int count = 0;  // 记录客户数量
        while(true){    // 服务器循环监听客户端的连接请求
            // 开始监听，等待客户端连接
            socket = serverSocket.accept();
            // 多线程通信
            ServerThread serverThread = new ServerThread(socket);
            // 设置线程的优先级，范围为[1,10]，默认为5
            serverThread.setPriority(4); // 未设置优先级可能会导致运行时速度非常慢，可降低优先级
            serverThread.start();// 启动线程

            count++;
            System.out.println("客户端数量:" + count);
            InetAddress address = socket.getInetAddress();
            System.out.println("当前客户端的IP：" + address.getHostAddress());
        }

        /*
        // 3.获取输入流，并读取客户端信息
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String info = null;
        while((info = br.readLine()) != null){
            System.out.println("我是服务端，客户端说：" + info);
        }
        socket.shutdownInput();// 关闭输入流

        // 4. 获取输出流，响应客户端的请求
        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os);
        pw.write("欢迎你！");
        pw.flush();// 刷新缓存
        socket.shutdownOutput();

        // 5.关闭相关资源
        pw.close();
        os.close();
        br.close();
        isr.close();
        is.close();
        socket.close();
        */
    }
}
```
## UDP编程

UDP协议以数据报作为数据传输的载体

进行数据传输时，首先需要将要传输的数据定义成数据报（Datagram），在数据报中指明数据索要到达的Socket（主机地址和端口号），然后再将数据报发送出去

### 相关操作类

 + DatagramPacket：表示数据报包
 + DatagramSocket：进行端到端通信的类

## UDP通信模型

### 服务器端实现步骤

 + 创建DatagramSocket，指定端口号
 + 创建DatagramPacket
 + 接收客户端发送的数据信息
 + 读取数据

**UDP服务器端类UDPServer实现**

```java
public class UDPServer {
    public static void main(String[] args) throws Exception{
        /*
        * 接受客户端发送的数据
        * */
        // 创建服务器端DatagramSocket，指定端口
        DatagramSocket socket = new DatagramSocket(8800);
        // 创建数据报包,用于接收客户端发送的数据
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        // 接受客户端发送的数据
        System.out.println("****服务器端已经启动，等待客户端发送数据****");
        socket.receive(packet); // 此方法在接收到数据报之前会一直阻塞
        // 读取数据
        String info = new String(data, 0, packet.getLength());
        System.out.println("我是服务器，客户端说：" + info);

        /*
        * 向客户端响应数据
        * */

        // 1.定义客户端的地址，端口号，数据
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        byte[] data2 = "欢迎你！".getBytes();

        // 2.创建数据报，包含响应的数据信息
        DatagramPacket packet1 = new DatagramPacket(data2, data2.length, address, port);
        // 3. 响应客户端
        socket.send(packet1);
        // 关闭资源
        socket.close();
    }
}
```

### 客户端实现步骤

 + 定义发送信息
 + 创建DatagramPacket，包含将要发送的信息
 + 创建DatagramSocket
 + 发送数据

**客户端类UDPClient实现**

```java
public class UDPClient {
    public static void main(String[] args) throws  Exception{

        /*
        * 向服务器端发送数据
        * */
        // 定义服务器的地址，端口号，数据
        InetAddress address = InetAddress.getByName("localhost");
        int port = 8800;
        byte[] data = "用户名：admin;密码：123".getBytes();
        // 创建数据报，包含发送的数据信息
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        // 创建DatagramSocket对象
        DatagramSocket socket = new DatagramSocket();
        // 向服务器端发送数据
        socket.send(packet);
        /*
        接收服务器端响应的数据
         */

        // 1.创建数据报，用于接收服务器端响应的数据
        byte[] data2 = new byte[1024];
        DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
        // 2.接收服务器端响应的数据
        socket.receive(packet2);
        // 3.读取数据
        String reply = new String(data2, 0, packet2.getLength());
        System.out.println("我是客户端，服务器说： " + reply);
        // 4. 关闭资源
        socket.close();
    }
}

```
