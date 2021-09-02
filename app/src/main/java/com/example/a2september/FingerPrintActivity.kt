package com.example.a2september



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.a2september.databinding.ActivityFingerPrintBinding
import java.util.concurrent.Executor


class FingerPrintActivity : AppCompatActivity() {
    lateinit var executor: Executor
    lateinit var biometricPrompt: BiometricPrompt
    lateinit var promptInfo: BiometricPrompt.PromptInfo
    private var mBinding: ActivityFingerPrintBinding?=null
    private val mGetBinding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_finger_print)
        executor = ContextCompat.getMainExecutor(this)
        checkBiometricAvailable()
        biometricPrompt = BiometricPrompt(this,executor,object :BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(applicationContext,
                    errString, Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(applicationContext,
                    getString(R.string.biometric_succeeded_msg), Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, getString(R.string.biometric_failed_msg),
                    Toast.LENGTH_SHORT)
                    .show()
            }
        })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_title))
            .setSubtitle(getString(R.string.biometric_subtitle))
            .setNegativeButtonText(getString(R.string.biometric_negativebutton_text))
            .build()
        mGetBinding.apply {
            buttonAuthenticate.setOnClickListener {
                biometricPrompt.authenticate(promptInfo)
            }
        }
    }

    private fun checkBiometricAvailable(){
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                Log.d(getString(R.string.FINGERPRINT_TAG), getString(R.string.biometric_available_text))
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Log.e(getString(R.string.FINGERPRINT_TAG), getString(R.string.biometric_unavailable_text))
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Log.e(getString(R.string.FINGERPRINT_TAG), getString(R.string.biometric_error_text))
            else -> {
                //do nothing
            }
        }
    }

}