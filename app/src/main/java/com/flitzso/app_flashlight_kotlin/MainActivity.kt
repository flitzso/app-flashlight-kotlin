package com.flitzso.app_flashlight_kotlin

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

   lateinit var mFlashlightOnOff: Button

   val CAMERA_REQUEST = 123

    var hasFlash = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.CAMERA),CAMERA_REQUEST)

        hasFlash = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

        mFlashlightOnOff = findViewById(R.id.onOff)

        mFlashlightOnOff.setOnClickListener {

            if(hasFlash)
            {
                if(mFlashlightOnOff.text.toString().contains("ON"))
                {
                    mFlashlightOnOff.text = "OFF"

                    flashlightoff()
                }
                else
                {
                    mFlashlightOnOff.text = "ON"

                    flashlightOn()

                }
            }
            else
            {
                Toast.makeText(this@MainActivity,"No Flashlight available",Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun flashlightOn() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val cameraId = cameraManager.cameraIdList[0]
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, true) // Defina como true para ligar a lanterna
            }
        } catch (exp: Exception) {
            exp.printStackTrace()
        }
    }

    private fun flashlightoff() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val cameraId = cameraManager.cameraIdList[0]
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, false) // Defina como false para desligar a lanterna
            }
        } catch (exp: Exception) {
            exp.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hasFlash = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
                } else {
                    mFlashlightOnOff.isEnabled = false
                    Toast.makeText(this@MainActivity, "Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
     }
 }
