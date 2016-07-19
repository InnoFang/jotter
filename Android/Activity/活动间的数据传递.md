活动间的跳转，通常伴随着数据的传递，以实现更多的用途。

>下面以`FirstActivity`和`SecondActivity`两个Activity为例，进行讲解：
我们知道用Intent来实现活动间的跳转，那么启动活动的方法有两种：

1.  `startActivity(Intent intent);`
2. `startActivityForResult(Intent intent, int requestCode);`

>两个方法的区别是：第一个方法是能在启动活动时，将数据从`FirstActivity`传给`SecondActivity`，但无法获得`SecondActivity`传回给`FirstActivity`的数据；第二个方法则可以通过重写`onActivityResult(int requestCode, int resultCode, Intent data);`获得从`SecondActivity`传回的数据。所以要实现两个活动之间的数据相互传递，那么应该选择`startActivityForResult(Intent intent, int requestCode);`方法

当然，活动间传递数据还需要利用`Intent`类的`putExtra(String name, value)`方法，
我们通过传入类似键值对的参数来传递数据，**注意两点**

1.  第一个参数传入extra的定义键，我们通常是创建一个静态字符串常量并以	_包名+值_	的形式赋值
例如：
```Java
public static final String EXTRA_KEY = "com.example.administrator.Test.key";
```
使用包名修饰extra数据信息的好处是，可以避免来自不同应用的extra间发生命名冲突
2. value是根据自己需要传递的数据类型来决定
例如
```Java
Intent data = new Intent ();
boolean valueBoolean = true;
data.putExtra (EXTRA_KEY_BOOLEAN, valueBoolean);//传递布尔值
int valueInt = 123;
data.putExtra (EXTRA_KEY_INT, valueInt);//传递整型
//...
```


然后我们就可以将extra附加到intent上
```Java
Intent data = new Intent ();
boolean valueBoolean = true;
data.putExtra (EXTRA_KEY_BOOLEAN, valueBoolean);//传递布尔值
data.putExtra (EXTRA_KEY_BOOLEAN, valueBoolean);//传递布尔值
int valueInt = 123;
data.putExtra (EXTRA_KEY_INT, valueInt);//传递整型
startActivity(data);
```
但是我们可以换一种更好的写法，我们将处理extra信息的实现细节封装在`SecondActivity`里面，因为关于extra信息的实现细节`FirstActivity`无需知道，他只需要传递相应的值即可，而应用的其它代码就更不需要知道这些细节了，具体的实现方法是在SecondActivity内创建一个静态方法，并在方法里面封装相应的逻辑，实例如下：
>SecondActivity中的newIntent方法的逻辑实现
```Java
public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_KEY_BOOLEAN = "com.example.administrator.Test.value_true";
    public static final String EXTRA_KEY_INT = "com.example.administrator.Test.value_123";

    ...
	public static void newIntent(Context context, boolean valueBoolean, int valueInt) {
		Intent i = new Intent(context, SecondActivity.class);
		i.putExtra(EXTRA_KEY_BOOLEAN, valueBoolean);
		i.putExtra(EXTRA_KEY_INT, valueInt);
		return i;
	}

}
```
那么接下来在`FirstActivity`中就可以调用该方法了
>在FirstActivity中调用newIntent方法
```Java
	...
Button.setOnClickListener(new View.OnClickListener{

	public void onClick(View v){
		Intent i = SecondActivity.newIntent(FirstActivity.this, true, 123);
		startActivity(i);
	}
});
	...
```
这样写，代码会更简洁与直观，并且便于维护和查看。
最后，在SecondActivity中获取数据就完成了从FirstActivity到SecondActivity的数据传递 	 
```Java
public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_KEY_BOOLEAN = "com.example.administrator.Test.value_true";
    public static final String EXTRA_KEY_INT = "com.example.administrator.Test.value_123";

    private boolean valueBoolean;
    private int valueInt;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_second);

        valueBoolean = getIntent ().getBooleanExtra (EXTRA_KEY_BOOLEAN, false);
        valueInt = getIntent().getIntExtra (EXTRA_KEY_INT,0);
}
```
>**注意**，上面代码中的Activity.getIntent()方法返回了由`startActivity(i);`转发的Intent对象
`getBooleanExtra (EXTRA_KEY_BOOLEAN, false);`方法就是获取相应键的值，第一个参数就是键，第二个参数是一个默认值。
`getIntExtra (EXTRA_KEY_INT,0);`的道理一样。


如果`FirstActivity`想从`SecondActivity`中获得返回数据，那么就需要将上面的
`startActivity(Intent intent);`改为
`startActivityForResult(Intent intent, int requestCode);`并且重写`onActivityResult(int requestCode, int resultCode, Intent data);`方法。
解决了从`FirstActivity`到`SecondActivity`的数据传递，那么接下来就是如何从`SecondActivity`将数据传出，并且在`FirstActivity`中获取返回数据

**首先**，就是将之前的`startActivity(i)`，改为`startActivityForResult(i, 0)`那个0就是传入的请求码，请求码只要是一个唯一值即可，其作用是在有多个Activity要传递数据时，可以根据请求码来辨别是哪一个Activity

**然后**，就是跟之前一样创建一个Intent对象，并且附上extra，但是有一个区别就是，还需要调用`setResult(int resultCode, Intent data)`方法，第一个参数是结果码
一般来说，参数result code可以是以下两个预定义常量中的任何一个

 *   Activity.RESULT_OK;
 *   Activity.REULT_CANCELED;   

(如果需要自己定义结果码，还可以使用另一个常量：RESULT_FIRST_USER。)
如果不调用`setResult(int resultCode, Intent data)`方法，那么在单击后退键后，总是会返回`Activity.REULT_CANCELED;`的结果码。
我们同样将逻辑封装起来
>在SecondActivity中封装返回数据的逻辑
```Java
public static final String EXTRA_KEY_BACK_BOOLEAN = "com.example.administrator.Test.value_back";

private void actionResult(boolean valueBoolean) {
	Intent data = new Intent();
	data.putExtra(EXTRA_KEY_BACK_BOOLEAN, valueBoolean);
	setResult(RESULT_OK, data);
}

```
`actionResult(boolean valueBoolan)`方法的参数是需要传递的值，可以根据情况进行增删。
然后就可以在适当的位置调用该方法，例如在点击回退键是返回数据：

```Java
public void onBackPressed(){
    actionResult(true);
    finish();
}
```

**最后**，处理返回结果，即重写`onActivityResult`方法
实例如下：
```Java
protected void onActivityResult(int requestCode,int resultCode,Intent data){
    switch(requestCode){
        case 0:
        if(resultCode == RESULT_OK){
            boolean result = data.getBooleanExtra(EXTRA_KEY_BACK_BOOLEAN,false);
            if (result) {
            	//TO-DO
            }else{
            	//TO-DO
            }
        }
        break;
    default:
    }
}

```
数据的返回完成！

**总结**
	1. 若是只是从`FirstActivituy`传递数据给`SecondActivity`，则使用`startActivity(Intent intent)`方法，若是还需要得到`SecondActivity`返回的数据，则应使用`startActivityForResult(Intent intent, int requestCode)`方法
	2. 传递数据需要利用Intent类里的`putExtra()`方法，根据情况传递相对应的键值，若是返回数据，则还需要调用`setResult()`方法，并传入相应的结果码和Intent对象。
	3. 将传递数据的逻辑进行封装
	4. 在处理返回数据时，需要重写`onActivityResult(int requestCode, int resultCode, Intent data);`方法，并根据requestCodeq(请求码)判断是哪一个Activity返回的数据，判断resultCode(结果码)并作出相应的操作
