## fragment间的数据传递<h6>以 `FirstFragment` 与 `ScondFragment` 之间的数据传递为例
- - -
**Fragment之间的数据传递同样需要借助 fragment argument 的知识**

* 从`FirstFragment` 传递数据到 `ScondFragment` （传递String类型为例）
  + 在`ScondFragment`中新建 `newInstance(String)`方法
  + 将String类型数据作为 argument 附加给 fragment

** _Eg：_**

>添加`newInstance()`方法 ( *`SecondFragment.java`* )

```java
  public class SecondFragment{
    private static final String ARG_STRING = "string";

    public static SecondFragment newInstance(String string){
      Bundle args = new Bundle();
      args.putString(ARG_STRING, string);

      SecondFragment fragment = new SecondFragment();
      fragment.setArguments(args);
      return fragment;
    }
  }
```

>传递数据并显示`ScondFragment` (  *`FirstFragment.java`* )

```java
  private static final String TAG_STRING = "string";

  @override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle saveInstanceState){
    ···
      public void onClick(View v){
        FragmentManager manager = getFragmentManager();
        String string = "Hello World"; // 要传递的数据
        SecondFragment first = SecondFragment.newInstance(string);
        fragment.show(manager, TAG_STRING);
      }
    ···
  }
```
**PS** 要将`SecondFragment`添加给 FragmentManager 管理并放置到屏幕上，可调用 fragment 实例的如下方法：

    public void show(FragmentManager manager, String tag)
    public void show(FragmentTransaction transaction, String tag)
string 参数可唯一标识 FragmentManager 队列中的 `SecondeFragment`。两个方法任你选：如传入 FragmentTransaction 参数, 你自己负责创建并提交事务；如传入 FragmentManager 参数，系统会自动创建并提交事务。


* 从 `ScondFragment` 返回数据给 `FirstFragment`
  - 设置目标fragment
    + 类似于 activity 间的关联，可将 `FirstFragment` 设置成 `SecondFragment`的目标 fragment 。即使是在`FirstFragment`和`SecondFragment`被销毁和重建后，操作系统也会重新关联它们。调用 `public void setTargetFragment(Fragment fragment, int requestCode)`，该方法有两个参数：目标fragment以及请求代码。需要时，目标fragment使用请求代码确认是哪个fragment在回传数据。我们可调用设置目标 fragment 的 fragment 的`getTargetFragment()`和`getTargetRequestCode()`方法获取它们。

  - 传递数据给目标fragment。处理由同一个Activity托管的两个fragment间的数据返回时，可借用`Fragment.onActivityResult(···)`方法。因此，直接调用目标fragment的`Fragment.onActivityResult(···)`方法，就能实现数据的回传。该方法恰好有我们需要的如下信息：
    * 请求代码：与传入`setTargetFragment(···)`方法相匹配，告诉目标 fragment 返回结果来自哪里
    * 结果代码：决定下一步该采取什么行动
    * Intent：包含extra数据
  我们可以在`SecondFragment`类中，新建`sendResult(···)`私有方法，创建intent并将数据作为extra附加到intent上，最后调用`SecondFragment.onActivityResult()`方法

** _Eg：_**

>设置目标fragment ( *`FirstFragment.java`* )

```java
private static final int REQUEST_STRING = 0;

@override
public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle saveInstanceState){
  ···
    public void onClick(View v){
      FragmentManager manager = getFragmentManager();
      String string = "Hello World"; // 要传递的数据
      SecondFragment first = SecondFragment.newInstance(string);
      fragment.setTargetFragment(FirstFragment.this, REQUEST_STRING);
      fragment.show(manager, TAG_STRING);
    }
  ···
}
```

>回调目标 fragment ( *`SecondFragment.java`* )

```java
  public class SecondFragment {
    public static final String EXTRA_STRING = "[package_name].string";

    ···

    private void sendResult(int resultCode, String string) {
      if (null == getTargetFragment()) {
          return;
      }

      Intent inetnt = new Intent();
      intent.putStringExtra(EXTRA_STRING, string);

      getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
  }
```
