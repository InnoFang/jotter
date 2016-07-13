## fragment argument

每个fragments实例都可附带一个Bundle对象，该bundleb包含有键值对，我们可以像附加extra到Activity的intent中那样使用它们。一个键值对即一个argument。

要创建fragment argument，首先需要创建Bundle对象。然后，使用Bundle限定类型的“put”方法（类似于Intent的方法），将argument添加到bundle中，实例如下：
```java
  Bundle args = new Bundle();
  args.putSerializable(EXTRA_MY_OBJECT, myObject);
  args.putInt(EXTRA_MY_INT, myInt);
  args.putCharSequence(EXTRA_MY_STRING, myString);
```
#### 附加argument给fragment
**注意** 要附加argument bundle给fragment，需要调用`Fragment.setArguments(Bundle)`方法，而且，必须在fragment创建后，添加给Activity之前完成。

**方法** 添加名为`newInstance()`的静态方法给Fragment类。使用该方法，完成fragment实例及bundle对象的创建，然后将argument放入bundle中，最后附加fragment。托管activity需要fragment实例时，转而调用`newInstance()`方法，而非直接调用其构造方法。而且，为满足fragment创建argument的要求，activity可传入任何需要的参数给`newInstance()`方法。

#### 获取argument
fragment需要获取它的argument时，会先调用Fragment类的`getArguemnt()`方法，在调用Bundle的限定类型“get”方法，如`getSerializable(...)`方法
