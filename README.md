本篇文章将从以下几点介绍 `DataBinding`：

1. 环境配置
2. 在 `Activity` 中使用 `DataBinding`
3. 在 `Fragment` 中使用 `DataBinding`
4. 在 `RecyclerView` 中使用 `DataBinding`
5. 在 `XML` 中使用表达式
6. 在 `XML` 中直接绑定方法
7. 结合可观察变量和可观察对象使用 `DataBinding`

正文开始，真香警告！(文章较长，建议准备一包瓜子...)



## 环境配置

使用 `DataBinding` 的前提需要完成以下两步：

1. 在工程的 `build.gradle` 下开启 `DataBinding` 选项：

   ```groovy
   android {
       // 注意dataBinding的大小写啊！！！
       dataBinding {
           enabled = true
       }
   }
   ```

2. 在工程的 `gradle.properties` 下启用新的数据绑定编译器：

   ```groovy
   android.databinding.enableV2=true
   ```


## Activity 中使用 DataBinding

环境配置好了之后，我们就可以使用 `DataBinding` 了，首先就拿 `Activity` 开刀吧，毕竟是最熟悉的陌生人 ^ - ^
1）布局
```xml
<?xml version="1.0" encoding="utf-8"?>

<layout>
    <data>
        // UserBean为数据类
        <variable name="userBean" type="com.taonce.myjetpack.data.binding.UserBean"/>
        // otherName仅仅是一个String对象
        <variable name="otherName" type="String"/>
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:tools="http://schemas.android.com/tools"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            tools:context=".data.binding.DataBindingActivity">

        <TextView android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:id="@+id/tv_first"
                  android:text="@{userBean.firstName}"
                  android:textSize="20sp"
                  android:gravity="center"
                  app:layout_constraintTop_toTopOf="parent"
                  app:layout_constraintLeft_toLeftOf="parent"/>

        <TextView android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:id="@+id/tv_last"
                  android:text="@{userBean.lastName + otherName}"
                  android:textSize="20sp"
                  android:gravity="center"
                  app:layout_constraintLeft_toLeftOf="parent"
                  app:layout_constraintTop_toBottomOf="@id/tv_first"/>

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

看完上面的布局文件，发现比平时写布局文件多了一些东西，我们来一个一个观察：

1. `XML` 最外层必须由 `layout` 标签包围；
2. 你需要使用什么样的数据，可以在 `data` 标签内通过 `variable` 来导入；
3. 使用数据时，只需要通过 `variable` 的 `name` 或者 `name` 的属性来获取内容即可。

**Tips：进行完这一步之后，一定要 `make` 一下工程，否则下一步无法进行**

2）绑定布局
`make` 工程之后，会自动为我们生成一个类，类名的规则是布局文件名 + `Binding`，比如布局文件名为：`activity_main`，那么生成的类名为：`ActivityMainBinding`，这个类我们接下来会使用到，是绑定布局的关键之处。

```kotlin
class DataBindingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 绑定布局从setContentView()变成了DataBindingUtil.setContentView()
        val binding: ActivityDataBindingBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_data_binding
        )
        binding.userBean = UserBean("Activity", "Taonce")
        binding.otherName = " Android"
    }
}
```

布局的绑定只是从 `setContentView( laytouId )` 换成了 `DataBindingUtil.setContentView( activity, layoutId)`，而且这个方法会返回一个 `ViewDataBinding` 对象，这里的 `ActivityDataBindingBinding` 是 `ViewDataBinding` 子类，我们可以通过 `ViewDataBinding` 设置布局所需要的数据。比如这里想要设置 `TextView` 的 `text` 属性，不用再 `setText( text )` 了，只需要设置 `binding.userBean` 和 `binding.otherName` 就好了。



## Fragment 中使用 DataBinding

知道了在 `Activity` 中如何使用 `DataBinding` 之后，`Fragment` 也是类似， 至少 `XML` 是不变的，我们只需要知道如何绑定就ok了：

```kotlin
class DataBindingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment中使用的是DataBindingUtil.inflate()来获取ViewDataBinding对象
        val binding = DataBindingUtil.inflate<FragmentDataBindingBinding>(
            inflater,
            R.layout.fragment_data_binding,
            container, false
        )
        binding.userBean = UserBean("Fragment", "")
        return binding.root
    }
}
```

`Fragment` 中通过 `DataBindingUtil.inflate()` 来获取 `ViewDataBinding` 对象，然后返回 `ViewDataBinding.root`，整个绑定就结束了！



## Adapter 中使用 DataBinding

在 `RecyclerView` 中使用 `DataBinding` 稍微比上面两种要复杂一点点，但是聪明的你，肯定是扫一眼就懂了。

1）`RecyclerView` 和 `Adapter` 绑定：在 `XML` 中设置 `adapter` 属性就完事了~

```kotlin
// XML
<layout>

    <data>
        <variable name="adapter" type="com.taonce.myjetpack.data.binding.list.ListAdapter"/>
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:tools="http://schemas.android.com/tools"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            tools:context=".data.binding.list.ListActivity">

        <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/rcv"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:adapter="@{adapter}"/>

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>

// Activity
binding.adapter = ListAdapter(
    this, listOf(
        UserBean("one", ""),
        UserBean("two", ""),
        UserBean("three", "")
    )
)
```

2）`Item` 的数据绑定：
生成 `Item` 布局文件：

```xml
<layout>

    <data>
        <variable name="userBean" type="com.taonce.myjetpack.data.binding.UserBean"/>
    </data>

    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                  android:orientation="vertical"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content">

        <TextView android:layout_width="match_parent" android:layout_height="wrap_content"
                  android:id="@+id/tv_item"
                  android:textSize="20sp"
                  android:layout_gravity="center"
                  android:text="@{userBean.firstName}"
                  android:gravity="center"/>

    </LinearLayout>
</layout>
```

接下来需要在 `Adapter` 中将 `Item` 所需要的数据传进来：

```kotlin
class ListAdapter(
    private val context: Context,
    val data: List<UserBean>
) : RecyclerView.Adapter<ListAdapter.Holder>() {
    // 绑定数据需要
    lateinit var binding: ItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // 获取ViewDataBinding对象
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item, parent, false
        )
        return Holder(binding.root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 根据position将数据和xml中的userBean绑定
        binding.userBean = data[position]
        // 立即改变数据
        binding.executePendingBindings()
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)
}
```

其实上面的注释已经说明了一切，就是把 `Holder` 需要做个工作给省去了，通过 `ViewDataBinding` 对象进行数据绑定，不需要再手动的去 `holder.tv.setText( text )` 了。



## DataBinding 中使用表达式

上面的案例一直都是 `XML` 中简单的绑定 `text` 属性，试想一下如果我们传入的值为空，那么岂不是很尴尬，所以 `DataBinding` 还给我们提供了多种表达式，以便更加灵活使用数据绑定，跟着例子一起来看看：

```xml
<?xml version="1.0" encoding="utf-8"?>

<layout>

    <data>
        <variable name="userBean" type="com.taonce.myjetpack.data.binding.UserBean"/>
        <variable name="age" type="Integer"/>
        <variable name="view" type="android.view.View"/>
        <variable name="ageLess20" type="String"/>
        <variable name="ageThan20" type="String"/>
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:tools="http://schemas.android.com/tools"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            tools:context=".data.binding.expression.ExpressionActivity">

        <TextView android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:textSize="20sp"
                  android:textColor="@color/colorAccent"
                  android:padding="10dp"
                  android:id="@+id/tv_one"
                  app:layout_constraintLeft_toLeftOf="parent"
                  app:layout_constraintTop_toTopOf="parent"
                  android:text="@{userBean.firstName ?? userBean.lastName}"/>

        <TextView android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:textSize="20sp"
                  android:textColor="@color/colorAccent"
                  android:padding="10dp"
                  android:id="@+id/tv_two"
                  app:layout_constraintTop_toBottomOf="@id/tv_one"
                  android:text='@{userBean.lastName.isEmpty() ? "lastName is null" : userBean.lastName}'/>

        <TextView android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:textSize="20sp"
                  android:textColor="@color/colorAccent"
                  android:padding="10dp"
                  android:id="@+id/tv_three"
                  app:layout_constraintTop_toBottomOf="@id/tv_two"
                  android:text="@{ageThan20}"
                  android:visibility="@{age > 20 ? view.VISIBLE : view.GONE}"/>

        <TextView android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:textSize="20sp"
                  android:textColor="@color/colorAccent"
                  android:padding="10dp"
                  android:id="@+id/tv_four"
                  app:layout_constraintTop_toBottomOf="@id/tv_two"
                  android:text="@{ageLess20}"
                  android:visibility="@{age &lt; 20 ? view.VISIBLE : view.GONE}"/>

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

上面的例子中，我们看到了几个新鲜的符号，如：

1. `userBean.firstName ?? userBean.lastName`：表示 `firstName` 不为空时选择 `firstName`，为空时则选择 `lastName`；
2. `userBean.lastName.isEmpty() ? "lastName is null" : userBean.lastName`：这个就是完完整整的一个三元操作符，相信大家在日常开发中都已经熟练使用过了；
3. `age > 20 ? view.VISIBLE : view.GONE`：这是判断的表达式，`age` 大于20的时候 `View` 显示，否则隐藏；
4. `age &lt; 20 ? view.VISIBLE : view.GONE`：咋一看，感觉好像没见过 `&lt;`，其实它是就是 `<` 符号，不过要求强制写成这样罢了。

除了上面例举的几个以外，还提供了许多操作符，下面一一列出来：

1. 数学表达式：`+ - / * %`
2. 字符串表达式：`+`
3. 逻辑表达式：`&& ||`
4. 二进制表达式：`& | ^`
5. 一元表达式：`+ - ! ~`
6. 位移表达式：`>> >>> <<`
7. 比较表达式：`== > < >= <=` (其中 `<` 需要写成 `&lt;`)
8. `instanceof`
9. `()`
10. 文字类型：character, String, numeric, `null`
11. 强转：`Cast`
12. 方法回调
13. 属性引用
14. 数组
15. 三元表达式：`? :`

具体的大家可以去 Android [官网](https://developer.android.com/topic/libraries/data-binding/expressions.html)上面去看，上面还有详细的案例。(狗头提醒：需要借助 tizi)



## DataBinding 绑定方法

方法的绑定分为两种，一种是带参数的方法，另外一种是不带参数的方法。其实处理起来都是很类似的，方法带参数的话，我们可以通过 `variable` 来导入参数，然后在 `Activity` 中赋值就行了。在看下面的例子之前，我们首先要知道，绑定方法的形式都是以 `lambda` 来实现的。

先看 `XML` 文件：

```xml
<?xml version="1.0" encoding="utf-8"?>

<layout>

    <data>
        <!--方法所属类，方法的定义不一定非要在Activity中，可以在任何类中，只需要再次定义就好-->
        <variable name="activity" type="com.taonce.myjetpack.data.binding.function.FunctionActivity"/>
        <!--通过variable把方法的参数传递过来-->
        <variable name="isToast" type="Boolean"/>
    </data>
    <androidx.constraintlayout.widget.ConstraintLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:tools="http://schemas.android.com/tools"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            tools:context=".data.binding.function.FunctionActivity">

        <Button android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:id="@+id/bt_one"
                app:layout_constraintTop_toTopOf="parent"
                android:text="不带参数的方法"
                android:textAllCaps="false"
                android:onClick="@{() -> activity.buttonClick()}"/>

        <Button android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:id="@+id/bt_two"
                app:layout_constraintTop_toBottomOf="@id/bt_one"
                android:text="带参数的方法"
                android:textAllCaps="false"
                android:onClick="@{() -> activity.toast(isToast)}"/>

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

再来看看具体的方法：

```kotlin
class FunctionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityFunctionBinding>(
            this, R.layout.activity_function
        )
        // 绑定xml中两个变量
        binding.activity = this
        binding.isToast = true
    }

    /*
    * 不带参数的方法
    * */
    fun buttonClick() {
        Toast.makeText(this, "button click", Toast.LENGTH_SHORT).show()
    }

    /*
    * 带参数的方法
    * */
    fun toast(isToast: Boolean) {
        if (isToast) {
            Toast.makeText(this, "isToast is true", Toast.LENGTH_SHORT).show()
        }
    }
}
```



## 结合可观察字段和可观察对象

可观察字段或可观察对象的意思就是，当这些字段或对象的值发生改变时，与之相关联的 UI 将会自动更新。Android 提供了以下几种可观察类：

- `ObservableBoolean`：布尔类型
- `ObservableByte`：字节类型
- `ObservableChar`：字符类型
- `ObservableShort`：短整型
- `ObservableInt`：整型
- `ObservableLong`：长整型
- `ObservableFloat`：单精度浮点型
- `ObservableDouble`：双精度浮点型
- `ObservableParcelable`：序列化类型

有人说，为什么没有 `String` 类型的，不存在的，`String` 虽然不是基本数据类型，但是还是得提供的，毕竟它的上场率可是登顶的，要想使用 `String` 需要借助 `ObservableField` 对象，一会来看下如何使用。说了这么多类型不可能一一举例了，在这就以 `String` 和 `Int` 为例说明下如何使用这些可观察字段吧。

`XML`：

```xml
<?xml version="1.0" encoding="utf-8"?>

<layout>
    <data>
        <import type="androidx.databinding.ObservableField"/>
        <!--因为<需要转义成&lt;-->
        <variable name="title" type="ObservableField&lt;String>"/>
        <variable name="activity" type="com.taonce.myjetpack.data.binding.observable.ObservableActivity"/>

    </data>
    <androidx.constraintlayout.widget.ConstraintLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:tools="http://schemas.android.com/tools"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            tools:context=".data.binding.observable.ObservableActivity">

        <TextView android:layout_width="match_parent" android:layout_height="wrap_content"
                  app:layout_constraintTop_toTopOf="parent"
                  android:id="@+id/tv_title"
                  android:text="@{title ?? title}"
                  android:textSize="20sp"
                  android:padding="10dp"
                  android:gravity="center"/>

        <Button android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:id="@+id/bt_observable"
                android:onClick="@{()->activity.changeContent()}"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintLeft_toLeftOf="parent"
                app:layout_constraintRight_toRightOf="parent"
                android:text="修改界面的值"
                android:textSize="20sp"
                android:padding="10dp"/>

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

`Activity`：

```kotlin
class ObservableActivity : AppCompatActivity() {
    lateinit var binding: ActivityObservableBinding
    private val title = ObservableField("ObservableActivity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_observable)
        binding.activity = this
        binding.title = title
    }

    fun changeContent() {
        // 可观察字段通过set()方法更新值
        title.set("ChangeTitle")
    }
}
```

在 `XML` 文件中绑定可观察字段后，在 `Activity` 通过该字段的 `set( value )` 方法来更新值并且通知界面更新 UI。

到这里有人会提出疑问，假如我绑定的不是上面的字段，而且某个数据类咋办，难道还是要通过手动去更新 UI ？不不不，官网也提供了方法可将数据类的变量转化为可观察对象，只要值改变，也会自动的更新 UI，一起看看吧。

```kotlin
class ObservableBean : BaseObservable() {

    // 必须使用 @get:Bindable 注解
    @get:Bindable
    var name: String = ""
        set(value) {
            field = value
            // 每当值set()后，通过notifyPropertyChanged()方法去指定更新
            // 可更新某个值，可以更新整个数据，取决于你BR后面的属性
            // BR.name 就代表只更新name相关的UI
            // BR._all 可更新所有的BR中字段相关联的UI
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var age: Int = -1
        set(value) {
            field = value
            notifyPropertyChanged(BR.age)
        }
}
```

代码中注释解释了一部分，这里再加上两点：

1. 在 Kotlin 中使用注解的话，必须在工程的 `build.gradle` 中加上 `apply plugin: 'kotlin-kapt'`
2. 每当在类中声明了一个加了注解的变量，都需要 `make` 一下工程，这样才会在 `BR` 类中生成对应的变量标志，不然系统找不到对应的变量

创建完成之后，就可以像之前绑定数据类一样操作了，跟之前的操作毫无区别，这里就不再过多解释了。

推荐阅读：
[KTX - 更简洁的开发Android]([https://www.jianshu.com/p/d935b4985a1c](https://www.jianshu.com/p/d935b4985a1c)
)

**如果本文章你发现有不正确或者不足之处，欢迎你在下方留言或者扫描下方的二维码留言也可！**

![](https://upload-images.jianshu.io/upload_images/6297937-911a72afff920ecf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)