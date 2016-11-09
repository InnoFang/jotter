> **Set 集合内的元素是无序的且不可重复**

## HashSet 类

>  HashSet 不是同步的，如果多个线程同时访问一个HashSet，假设有两个或者两个以上的线程同时修改了HashSet集合时，则必须通过代码来保证其同步

> 集合元素值可以为null

> 当向HashSet集合中存入一个元素时，HashSet会调用该对象的hashCode()方法来得到该对象的hashCode值，然后根据该HashCode值来决定该对象在HashSet中存储位置。如果有两个元素通过equals方法比较返回true，但它们的hashCode()方法返回值不相等，HashSet将会把它们存储在不同位置，也就可以添加成功

> 如果需要把某个类的对象保存到 HashSet 集合中，重写这个类的 equals() 方法和 hashCode() 方法时，应该尽量保证两个对象通过 equals() 方法比较返回 true 时， 它们的 hashCode() 方法返回值也相等

**为什么要尽量保证两个对象通过 equals() 方法比较返回 true 时， 它们的 hashCode() 方法返回值也相等？**

HashSet 中每个能存储元素的“槽位”(slot)通常称为“桶”(bucket)，如果有多个元素的hashCode值相同，但它们通过 equals() 方法比较返回 false ， 就需要在一个“桶”里放多个元素，这样会导致性能下降

重写 hashCode() 方法的基本规则 ： 
 + 在程序运行过程中，同一个对象多次调用 hashCode() 方法应该返回相同的值
 + 当两个对象通过 equals() 方法比较返回true时，这两个对象的 hashCode() 方法应返回相等的值
 + 对象中用作 equals() 方法比较标准的实例变量，都应该用于计算 hashCode 值

重写 hashCode() 一般步骤 ： 
  1).    把对象内每个有意义的实例变量（即每个参与 equals() 方法比较标准的实例变量）计算出一个int 类型的 hashCode 值，如下表：
实例变量类型 | 计算方式
--- | ---
boolean | hashCode = (f ? 0 : 1);
整数类型(byte, short, char, int)  | hashCode = (int)f;
long | hashCode = (int)(f ^ (f >>> 32));
float | hashCode = Float.floatToIntBits(f);
double | logn l = Double.doubleToLongBits(f);<br />hashCode = (int)^(1 >>> 32);
引用类型 | hashCode = f.hashCode();
  2).  用第一步中计算出来的多个hashCode值组合计算出一个hashCode值返回。
_**Eg:**_
  `return f1.hashCode() + (int)f2;
 为了避免直接相加残生偶然相等，可以通过个实例变量的hashCode值乘以任意一个质数后再相加
_**Eg:**_
  `return f1.hashCode() * 19 + (int)f2 * 31;

- -  -

## LinkedHashSet类

> 使用链表维护元素的次序，这样使得元素看起来是以插入的顺序保存的。因为需要维护元素的插入顺序，因此性能略低于HashSet 的性能，但在迭代访问 Set 里的全部元素时将有很好的性能，因为它以链表来维护内部顺序

_**Eg：**_

```java
Set<String> books = new LinkedHashSet<>();
        books.add("Head First Design Partten");
        books.add("Data Structure and Algorithm");
        books.add("Thinking in Java");
        System.out.println(books);
        
        /*删除 Head First Design Partten*/
        books.remove("Head First Design Partten");
        
        /*重新添加 Head First Design Partten*/
        books.add("Head First Design Partten");
        System.out.println(books);  
```

_**Output：:**_

`
[Head First Design Partten, Data Structure and Algorithm, Thinking in Java]
[Data Structure and Algorithm, Thinking in Java, Head First Design Partten]  
`

- - - 

## TreeSet类

> TreeSet是SortedSet接口的唯一实现，正如SortedSet名字所暗示的，TreeSet可以确保集合元素处于排序状态。

与前面HashSet集合相比，TreeSet还提供了如下几个额外的方法：
 + Object first()：返回集合中的第一个元素。
 +  Object last()：返回集合中的最末一个元素。
 + Object lower(Object e)：返回集合中位于指定元素之前的元素（即小于指定元素的最大元素，参考元素不需要是TreeSet的元素）。
 + Object higher(Object e)：返回集合中位于指定元素之后的元素（即大于指定元素的最小元素，参考元素不需要是TreeSet的元素）。
 + SortedSet subSet(fromElement, toElement)：返回此Set的子集合，范围从fromElement（包含）到toElement（不包含）。
 + SortedSet headSet(toElement)：返回此Set的子集，由小于toElement的元素组成。 
 +  SortedSet tailSet(fromElement)：返回此Set的子集，由大于或等于fromElement的元素组成。

_**Eg：**_
```java
//        Set<Integer> nums = new TreeSet<>(); /*TreeSet是SortedSet接口的实现类，下述方法Set没有，所以这样写会报错*/
        TreeSet<Integer> nums = new TreeSet<>();    
        nums.add(5);
        nums.add(2);
        nums.add(10);
        nums.add(-9);
        System.out.println(nums);                /*输出TreeSet*/
        System.out.println(nums.first());        /*输出集合第一个元素*/
        System.out.println(nums.last());            /*输出集合最后一个元素*/
        System.out.println(nums.headSet(4));    /*输出小于4的自己，不包含4*/
        System.out.println(nums.tailSet(5));      /*输出大于5的子集，包含5*/
        System.out.println(nums.subSet(-3, 4));/*输出大于等于-3，小于4的子集*/  
```
_**Output：**_
`
[-9, 2, 5, 10]
-9
10
[-9, 2]
[5, 10]
[2]  
`
TreeSet采用红黑树的数据结构对元素进行排序。TreeSet支持两种排序方法：自然排序和定制排序。
 + 自然排序：TreeSet会调用集合元素的compareTo(Object obj)方法来比较元素之间大小关系，然后将集合元素按升序排列，这种方式就是自然排列。
 + 定制排序：TreeSet借助于Comparator接口的帮助。该接口里包含一个的int compare(T o1, T o2)方法，该方法用于比较o1和o2的大小。

**为了保证程序过呢国家健壮，推荐不要修改放入HashSet和TreeSet集合中元素的关键实例变量**

- - -
## EnumSet 类
> EnumSet是一个专为枚举类设计的集合类，EnumSet中所有元素都必须是指定枚举类型的枚举值，该枚举类型在创建EnumSet时显式或隐式地指定。EnumSet的集合元素也是有序的，EnumSet以枚举值在Enum类的定义顺序来决定集合元素的顺序。
EnumSet在内部以位向量的形式存储，这种存储形式非常紧凑、高效，因此EnumSet对象占用内存很小，而且运行效率很好。尤其是当进行批量操作（如调用containsAll和 retainAll方法）时，如其参数也是EnumSet集合，则该批量操作的执行速度也非常快。
EnumSet集合不允许加入null元素。如果试图插入null元素，EnumSet将抛出 NullPointerException异常。如果仅仅只是试图测试是否出现null元素、或删除null元素都不会抛出异常，只是删除操作将返回false，因为没有任何null元素被删除。

_**Eg：**_
```java
public static void main(String[] args) {
        // 创建一个EnumSet集合，集合元素就是Season枚举类的全部枚举值
        EnumSet es1 = EnumSet.allOf(Season.class);
        System.out.println(es1); // 输出[SPRING,SUMMER,FALL,WINTER]
        // 创建一个EnumSet空集合，指定其集合元素是Season类的枚举值。
        EnumSet es2 = EnumSet.noneOf(Season.class);
        System.out.println(es2); // 输出[]
        // 手动添加两个元素
        es2.add(Season.WINTER);
        es2.add(Season.SPRING);
        System.out.println(es2); // 输出[SPRING,WINTER]
        // 以指定枚举值创建EnumSet集合
        EnumSet es3 = EnumSet.of(Season.SUMMER , Season.WINTER);
        System.out.println(es3); // 输出[SUMMER,WINTER]
        EnumSet es4 = EnumSet.range(Season.SUMMER , Season.WINTER);
        System.out.println(es4); // 输出[SUMMER,FALL,WINTER]
        // 新创建的EnumSet集合的元素和es4集合的元素有相同类型，
        // es5的集合元素 + es4集合元素 = Season枚举类的全部枚举值
        EnumSet es5 = EnumSet.complementOf(es4);
        System.out.println(es5); // 输出[SPRING]
    }  
```

_**Output：**_
`
[SPRING, SUMMER, FALL, WINTER]
[]
[SPRING, WINTER]
[SUMMER, WINTER]
[SUMMER, FALL, WINTER]
[SPRING]
`

- - -

## 各 Set 实现类的性能分析

    HashSet 的性能总是比 TreeSet好。因为TreeSet需要额外的红黑树算法来维护集合元素的次序，只有当需要一个保持排序的 Set 时，才应该使用 TreeSet，否则都应该使用 HashSet

    对于普通的插入，删除操作，LinkedHashSet 比 HashSet 要略微慢一点，这是由维护链表所带来的额外开销造成的，但由于有了链表，遍历LinkedHashSet会更快

    EnumSet是所有Set实现类中性能最好的，但它只能保存同一个枚举类的枚举值作为集合类型

**Set的三个实现类，HashSet，TreeSet和EnumSet都是线程线程不安全的**

若有线程同时访问一个Set集合，并且有大于一个Set集合修改了该Set集合，则必须手动保证该Set集合的同步性。通常可以通过Collections工具类的 synchronizedSortedSet方法来“包装”该set集合。此操作最好在创建时进行，以防止对Set集合的意外非同步访问。
_**Eg：**_
```java
Sorted s = Collections.synchronizedSortedSet(new TreeSet(...));
```
    
    
