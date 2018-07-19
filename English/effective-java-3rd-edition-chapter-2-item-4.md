> Effective Java 3rd Edition 翻译单词学习

# 第 4 条：通过私有构造器强化不可实例化能力

## words and phrases

 + *Some people abuse them to avoid thinking **in terms of** objects.*

    - in terms of  :  根据;用…的话;就…而言;以…为单位
 
    > 因为有些人滥用它们以避免在对象的角度进行思考


 + *They can be used to group related methods on **primitive** values or arrays, **in the manner of** `java.lang.Math` or `java.util.Arrays`*
    
    - primitive : 原始的; 发展水平低的; 落后的; 原生的; 原始人; 早期的艺术家; 单纯的人：不世故的人; 自学的艺术
    - in the manner of : 照…的式样，做出…的样子

    > 它们可以用来以 java.lang.Math 或者 java.utill.Arrays 的方式对初始值或者数组上的相关方法进行分组


+ ***As of** Java 8, you can also put such methods in the interface, **assuming** it’s yours to modify.*

    - As of : 自…起，从…时起; 截至
    - assuming : 如果; 傲慢的，自负的; 僭越的; 假定; 取得( assume的现在分词); 假设(assume的ing形式); 

    > 自 Java 8 开始，你也可以把这些方法放到接口中，假设那是你的修改

+ *an instance would be **nonsensical***

    - nonsensical : 无意义的，荒谬的，愚蠢的; 不像话; 痴狂

    > 一个实例是没有意义的。

+ ***In the absence of** **explicit** constructors*

    - in the absence of : 缺乏…时，当…不在时
    - explicit :  明确的，清楚的; 直言的; 详述的; 不隐瞒的;  

    > 当缺少显示构造器时

+ *It is not **uncommon** to see **unintentionally** **instantiable** classes in published APIs.*
 
    - uncommon : 罕见的; 不寻常的; 非凡的，杰出的; 非常接近的
    - unintentionally : 无意之中; 非故意地，非存心地
    - instantiable : 可实例化

    > 在已发布的 API 中，无意中发现可实例化的类是并不罕见的。

+ ***Remainder** omitted*

    - remainder : 剩余物; 其他人员; 差数; 廉价出售的图书; 廉价出售; 剩余

    > 其余省略

+ *This idiom is **mildly** **counterintuitive** because the constructor is provided **expressly** so that it cannot be invoked*
 
    - mildly : 和善地; 轻微地; 说得委婉些; 说得好听一点
    - counterintuitive : 违反直觉的
    - expressly : 明确地; 明显地; 特别地; 特意地


+ *As a **side effect***

    - side effect : 副作用

    > 作为副作用