package com.dineshprabha.qrxplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.dineshprabha.qrxplorer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val cameraPermission = android.Manifest.permission.CAMERA
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission())
    { isGranted ->
        if (isGranted){
            startScanner()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamera.setOnClickListener {
            requestCameraAndStartScanner()
        }
    }

    private fun requestCameraAndStartScanner(){
        if (isPermissionGranted(cameraPermission)){
            //start scan
            startScanner()
        }else{
            requestCameraPermissions()
        }
    }

    private fun startScanner(){
        ScannerActivity.startScanner(this){

        }
    }

    private fun requestCameraPermissions() {
        when{
            shouldShowRequestPermissionRationale(cameraPermission) -> {
                cameraPermissionRequest {
                    openPermissionSettings()
                }
            }
            else -> {
                requestPermissionLauncher.launch(cameraPermission)
            }
        }
    }
}