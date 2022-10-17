package com.proxima_b.rootcheck

import android.content.DialogInterface
import android.graphics.Paint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.proxima_b.rootcheck.util.*


class MainActivity : AppCompatActivity() {

    var deviceBrand: String= ""
    var deviceModel: String = ""
    var osRelease: String=""
    private lateinit var dm: TextView
    private lateinit var av: TextView
    private lateinit var ava: TextView
    private lateinit var rs: TextView
    private lateinit var root: TextView
    private lateinit var buttonChec: TextView
    private lateinit var circlePhoneBackground: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dm = findViewById(R.id.textDeviceModel)
        av = findViewById(R.id.textAndroidVersion)
        ava = findViewById(R.id.textVersionApp)
        rs = findViewById(R.id.textRootStatus)
        root = findViewById(R.id.root)
        buttonChec = findViewById(R.id.buttonCheck)
        circlePhoneBackground = findViewById(R.id.circlePhoneBackground)
        // Load info
        getDeviceInfo()

        // Set info
        dm.text = "$deviceBrand $deviceModel"
        av.text = "Android Version: " + osRelease
        ava.text = "(" + AppInfo.version + ")"

        rs.text = "Unchecked"
        root.paintFlags = root.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        root.setOnClickListener {

            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("What is root ?")
            builder.setMessage(R.string.mroot)
            builder.setCancelable(false)


            builder.setPositiveButton("OK"
            ) { dialog, which -> dialog.cancel() }
            builder.show()
        }

        buttonChec.setOnClickListener {
            try {
                checkRoot()
            } catch (e: Exception) {
                Toast.makeText(this, "An error occurred. " + e.message, Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun getDeviceInfo() {
        deviceBrand = android.os.Build.MANUFACTURER
        deviceModel = android.os.Build.MODEL
        osRelease = android.os.Build.VERSION.RELEASE
    }

    private fun checkRoot() {

        // Check if device is rooted
        val boolMethodOne = RootChecker.checkRootMethodOne()
        val boolMethodTwo = RootChecker.checkRootMethodTwo()
        val boolMethodThree = RootChecker.checkRootMethodThree()

        if (boolMethodOne || boolMethodTwo || boolMethodThree) {
            circlePhoneBackground.setBackgroundResource(R.drawable.circle_no_rooted)
            rs.text = "# Rooted!"

            rs.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorNoRooted))


        } else {
            circlePhoneBackground.setBackgroundResource(R.drawable.circle_rooted)
            rs.text = "Not Rooted!"
            rs.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorRooted))
        }
    }
}
