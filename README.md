环境配置
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

`Activity` 中使用 `DataBinding`

环境配置好了之后，我们就可以使用 `DataBinding` 了，首先就拿 `Activity` 开刀吧，毕竟是最熟悉的陌生人^-^

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

布局的绑定只是从 `setContentView( laytouId )` 换成了 `DataBindingUtil.setContentView( activity, layoutId)`，而且这个方法会返回一个 `ViewDataBinding` 对象，这里的 `ActivityDataBindingBinding` 是 `ViewDataBinding` 子类，我们可以通过 `ViewDataBinding` 设置布局所需要的数据。比如这里想要设置 `TextView` 的 `text` 属性，不用再 `setText( text )` 了，只需要设置 `binding.userBena` 就好了。



`Fragment` 中使用 `DataBinding`

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



`Adapter` 中使用 `DataBinding`

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



`DataBinding` 中使用表达式





`DataBinding` 绑定方法
结合可观察字段和可观察数据类

