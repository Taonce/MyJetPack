package com.taonce.myjetpack

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.PointF
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.util.LruCache
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.contentValuesOf
import androidx.core.content.edit
import androidx.core.content.getSystemService
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.scale
import androidx.core.graphics.toPoint
import androidx.core.location.component1
import androidx.core.location.component2
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.core.os.postDelayed
import androidx.core.util.lruCache
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_main.*

class KTXActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // sp
        val sp: SharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE)
        // 常规
        val editor = sp.edit()
        editor.putString("name", "taonce").apply()
        editor.putString("name", "tao").apply()
        // KTX
        sp.edit {
            putString("name", "taonce")
            putString("name", "tao")
        }

        // context的扩展函数
        // 常规获取系统服务
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        // KTX
        val windowManager1 = getSystemService<WindowManager>()

        // ContentValues
        // 常规
        val commonContentValues = ContentValues()
        commonContentValues.put("name", "taonce")
        commonContentValues.put("age", 20)
        // KTX
        val contentValues: ContentValues = contentValuesOf(
            // 只需要传入键值对
            Pair("name", "taonce"),
            "age" to 20
        )


        // Animator
        val animator =
            ObjectAnimator.ofFloat(tv, "alpha", 1.0f, 0.2f)
        // 常规
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        // KTX
        animator.doOnStart { }
        animator.doOnEnd { }
        animator.doOnCancel { }

        // point
        val pointF = PointF(1f, 1f)
        val point = pointF.toPoint()

        // bitmap
        val bitmap = createBitmap(200, 200)
        iv.setImageBitmap(bitmap)
        val scaleBitmap = bitmap.scale(50, 50)
        val bitmapDrawable = bitmap.toDrawable(this.resources)

        // uri
        val url = "D:\\AndroidProject"
        val uri = url.toUri()
        val file = uri.toFile()

        // handler
        val handler = Handler()
        // 常规
        handler.postDelayed({
            // runnable.run()
        }, 1000L)
        // KTX
        handler.postDelayed(1000L) {
            // runnable.run()
        }

        // lruCache
        // 常规
        val commonLruCache = object : LruCache<String, Bitmap>(50) {
            override fun sizeOf(key: String, value: Bitmap): Int {
                return (value.rowBytes) * (value.height) / 1024
            }
        }
        // KTX
        val lruCache: LruCache<String, Bitmap> = lruCache(50, sizeOf = { _, value ->
            return@lruCache (value.rowBytes) * (value.height) / 1024
        })

        // EditText
        val editText = EditText(this)
        // 常规
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        // KTX
        editText.doOnTextChanged { text, start, count, after ->
            print("text is $text, start index is $start, text len is $count, changed index is $after")
        }
        editText.doAfterTextChanged { }
        editText.doBeforeTextChanged { text, start, count, after -> }

        // Location
        val location = Location("")
        val (lat, lon) = location
    }
}
