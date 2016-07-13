 * 创建CrimePagerActivity类
 * 定义包含ViewPager的视图层级结构
 * 在CrimePagerActivity类中关联使用ViewPager及其Adapter

>创建ViewPager视图

 ```xml
 <?xml version="1.0" encoding="utf-8"?>
 <android.support.v4.view.ViewPager
     xmlns:android="http://schemas.android.com/apk/res/android"
     android:id="@+id/activity_crime_pager_view_pager"
     android:layout_width="match_parent"
     android:layout_height="match_parent"/>
 ```

### ViewPager 与 PagerAdapter

ViewPager同样需要PagerAdapter的支持。Google提供了PagerAdapter的子类`FragmentStatePagerAdapter`,它能协助处理许多细节问题。
`FragmentStatePagerAdapter`提供了两个方法：`getCount()` 和 `getItem(Int)`。调用 `getItem(Int)` 方法，获取并显示crime数组中指定位置的Crime时，它会返回配置过的CrimeFragment来完成显示的任务。

  * `getCount()`：返回数组列表中包含的列表项的数目
  * `getItem(Int)`：首先获取数据集中指定位置的Crime实例，然后利用该Crime实例的ID创建并返回一个有效配置的CrimeFragment

### FragmentStatePagerAdapter 与 FragmentPagerAdapter
`FragmentPagerAdapter`是另外一种可用的 PagerAdapter ，其用法与`FragmentStatePagerAdapter` 基本一致。唯一的区别在于：卸载不再需要的fragment时，各自采用的处理方法有所不同。

`FragmentStatePagerAdapter`会销毁不需要的 fragment 。事务提交后，activity  的 FragmentManager 中的 fragment 会彻底移除。`FragmentStatePagerAdapter`类名中的 “state” 表明：在销毁 fragment 时，可在`onSaveInstateState(Bundle)`方法中保存 fragment 的 Bundle 信息，用户切换回来时，保存的实例状态可用来恢复生成新的 fragment 。

对`FragmentPagerAdapter`来说，对于不再需要的 fragment ，`FragmentPagerAdapter`会选择调用事务的`detch(Fragment)`方法来处理它，而非`remove(Fragment)`方法，也就是，`FragmentPagerAdapter`只是销毁了 fragment 的视图，fragment 的实例还保留在`FragmentManager` 中。因此，`FragmentPagerAdapter`创建的 fragment 永远不会被销毁。

使用哪种 adapter 取决于应用的要求。如果用户需要大量的固定的 fragment ，则`FragmentStatePagerAdapter`更节省空间。弱智需要少量固定的 fragment ，则`FragmentPagerAdapter`是一个安全,合适的选择。
