package com.taonce.myjetpack.data.binding.observable

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.taonce.myjetpack.BR

class ObservableBean : BaseObservable() {

    @get:Bindable
    var name: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var age: Int = -1
        set(value) {
            field = value
            notifyPropertyChanged(BR.age)
        }
}

