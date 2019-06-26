package com.taonce.myjetpack.data.binding.observable

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.taonce.myjetpack.BR

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

