package com.taonce.myjetpack.livedata

import android.util.Log
import androidx.lifecycle.LiveData


class ExtendLiveData(content: String) : LiveData<String>() {
    init {
        setValue(content)
    }

    override fun onActive() {
        Log.d("taonce", "LiveData is onActive")
        super.onActive()
    }

    override fun onInactive() {
        Log.d("taonce", "LiveData is onInactive")
        super.onInactive()
    }

    public override fun setValue(value: String) {
        super.setValue(value)
    }
}
