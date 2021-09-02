package com.example.a2september

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.a2september.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private var mBinding:ActivityHomeBinding?=null
    private val binding1 get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_home)
        binding1.apply {
            buttonFingerprintnactivity.setOnClickListener {
                Intent(this@HomeActivity,FingerPrintActivity::class.java).also {
                    startActivity(it)
                }
            }
            buttonMotionactivity.setOnClickListener {
                Intent(this@HomeActivity,MainActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding=null
    }
}