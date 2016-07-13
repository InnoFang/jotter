<h1> SQLite数据库存储

<h2> 创建数据库

`openOrCreateDatabase(···)` 和 `databaseList()` 方法是Android提供的Context底层方法，可以用来打开数据库文件并将其转换为 SQLiteDatabase 实例。

不过，实际开发中，建议总是遵循以下步骤：
  + 确认目标数据库实际存在
  + 如果不存在，首先创建数据库，然后创建数据库表以及必需的初始化数据
  + 如果存在，打开并确认数据是否为最新版本
  + 如果是旧版本，就运行相关代码升级到最新版本

Android为我们提供的SQLiteOpenHelper类可以帮我们处理这些。首先 SQLiteOpenHelper 类是抽象类，有两个抽象方法 `onCreate()` 和 `onUpgrade()`，我们需要重写这两个方法并且分别在这两个方法中去实现创建和升级数据库的逻辑。

SQLiteOpenHelper 中有两个构造方法可供重写，一般使用参数少一点的那个构造方法即可。这个构造方法接收四个参数：
  + 第一个参数是 Context，有它才能对数据库进行操作。
  + 第二个参数是数据库名，创建数据库时使用的就是这里指定的名称。
  + 第三个参数允许我们在查询数据的时候返回一个自定义的Cursor，一般都是传入null
  + 第四个参数表示当前数据库的版本号，可用于对数据库进行升级操作

SQLiteOpenHelper 中还有两个非常重要的实例方法， `getReadableDatabase()` 和 `getWritableDatabase()`。这两个方法都乐意创建或打开一个现有的数据库（如果数据库已存在就打开，否则创建一个新的数据库），并且返回一个可对数据库进行读写操作的对象。不同的是，当数据库不可写入的时候（如果磁盘空间已满）`getReadableDatabase()` 方法返回的对象将以只读方式去打开数据库，而`getWritableDatabase()`方法则会抛出异常。

如果我们想创建一个名为 Student.db 的数据库，然后在这个数据库中新建一张Student表，表中有 学号(主键)，名字，性别等列。

  _**Eg:**_
  ```SQLite
    create table Bool(
      id integer primary key autoincrement,
      name text,
      gender text
    )
  ```
SQLite内部只支持 null，integer，real(浮点数)，text(文本)和 blob(二进制)这五种数据类型。另外上述见表语句中我们还使用了 primary key 将 id 列设为主键，并用autoincrement 关键字表示 id 列是自增长。

又因为SQLite允许存入数据时忽略底层数据列实际的数据类型，因此在编写建表语句时可以省略数据列后面的类型声明，所以下面的写法也是对的，

_**Eg:**_
```SQLite
  create table Bool(
    id integer primary key autoincrement,
    name,
    gender
    )
```
所以，我们创建一个 MyDatebaseHelper 类，继承自 SQLiteOpenHelper ，并实现它的两个抽象方法，及一个构造方法，完成建表与执行SQL语句。

_**Eg:**_

```java
public class MyDatebaseHelper extends SQLiteOpenHelper {

    public static final String CREAT_STUDENT = "create table Bool(" +
      "id integer primary key autoincrement, " +
      "name, " +
      "gender " +
      ")";

    public CrimeBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.exec(CREAT_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

```

<h2> SQLite数据库的 “增” “删” “改” “查”
<h3> “增” —— 添加数据

    	public long insert(String table, String nullColumnHack, ContentValues values)

该方法接收三个参数：
  + 第一个参数是表名
  + 第二个参数用于在未指定添加数据的情况下给某些可为空的列自赋值null，一般直接传入null即可
  + 第三个参数是一个 ContentValues 对象，它提供了一系列的 put() 方法重载，用于向 ContentValues 中添加数据，只需要将表中的每个列名以及相应的待添加数据传入即可。

_**Eg:**_

```java
  private MyDatebaseHelper dbHelper;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        dbHelper = new MyDatebaseHelper(this, "Student.db", null, 1);
        ···
        //  点击按钮添加数据
          onClick(View v){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            //  开始组装第一组数据
            values.put("id", "001");
            values.put("name", "Jacob");
            values.put("gender", "male");
            db.insert("Student", null, values);  //  插入第一条数据
            values.clear();  //  若想在下面继续添加数据，这一定要调用该方法
            //  开始组装第二组数据
            values.put("id", "002");
            values.put("name", "Jane");
            values.put("gender", "female");
            db.insert("Student", null, values);  //  插入第二条数据
          }
        ···
```
<h3> “删” —— 删除数据

    public int delete(String table, String whereClause, String[] whereArgs)

该方法接收三个参数：
  + 第一个参数是表名
  + 第二，三个参数 用于约束删除某一行或某几行的数据，不指定的话默认就是删除所有行，在第二个参数中我们可以使用占位符 “?”

_**Eg:**_ （以 *Student.db* 为例）

> 删除 "name" 为 "Jacob" 的数据

```java
  db.dele("Student", "name like ?", new String[]{"Jacob"});
```

> 删除所有 "name" 以 "J" 开头的数据

```java
  db.dele("Student", "name like ?", new String[]{"J_"});
```

> 删除所有 "id" 为 "002" 的数据

```java
  db.dele("Student", "id = ?", new String[]{"002"});
```    

> 删除所有 "id" 大于 "001" 的数据

```java
  db.dele("Student", "id > ?", new String[]{"001"});
```   

<h3> “改” —— 更新数据

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs)


该方法接收四个参数：
  + 第一个参数是表名
  + 第二个参数是 ContentValues 对象，要把更新数据在这里组装进去
  + 第三，四个参数 用于约束更新某一行或某几行的数据，不指定的话默认就是更新所有行，在第二个参数中我们可以使用占位符 “?”

_**Eg:**_
```java
    private MyDatebaseHelper dbHelper;
    @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_fragment);
          dbHelper = new MyDatebaseHelper(this, "Student.db", null, 1);
          ···
          //  点击按钮更新数据，将 "id" 为 "002" 的数据的 "name" 改为 "Mary"
            onClick(View v){
              SQLiteDatabase db = dbHelper.getWritableDatabase();
              ContentValues values = new ContentValues();
              values.put("name", "Mary");
              db.insert("Student", values, "id = ?", new String[]{"002"});
            }
          ···
```

<h3> “查” —— 查找数据

SQLite的查询方法有很多，而且参数都很多，这里我们讲一个参数最少却常用的

    public Cursor query(String table, String[] columns, String selection,
        String[] selectionArgs, String groupBy, String having,
        String orderBy)


query() 方法参数 | 对应 SQL 部分 | 描述
---|---|---
table | from table_name | 指定查询的表明
columns | select column1,column2 | 指定查询的列名
selection | where column = value | 指定 where 的约束条件
selectionArgs | - | 为 where 中的占位符提供具体的值
groupBy | group by column | 指定需要 group by 的列
having | having column = value | 对 group by 后的结果进一步约束
orderBy | order by column1,column2 | 指定查询结果的排序方式


如果我们想查询数据库的全部数据，只需要传入表名即可；如果我们想查询单个数据，传入表名后，再在第三，四个参数上指定约束条件即可，也就是说我们没必要指定所有的参数

_**Eg:**_
```java
    private MyDatebaseHelper dbHelper;
    @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_fragment);
          dbHelper = new MyDatebaseHelper(this, "Student.db", null, 1);
          ···
          //  点击按钮更新数据，将 "id" 为 "002" 的数据的 "name" 改为 "Mary"
            onClick(View v){
              SQLiteDatabase db = dbHelper.getWritableDatabase();
              Cursor cursor = db.query("Student
                      null,  //  Columns - null selects all columns
                      "id = ?",
                      new String[]{"002"},
                      null,  //  groupBy
                      null,  //  having
                      null   //  orderBy
              );
              cursor.moveToFirst();
              String id = getString(getColumnIndex("id"));
              String name = getString(getColumnIndex("name"));
              String gender = getString(getColumnIndex("gender"));
              Log.d("MainActivity", "Student's id is " + id);
              Log.d("MainActivity", "Student's name is " + name);
              Log.d("MainActivity", "Student's gender is " + gender);
              cursor.close();
            }
          ···
```
