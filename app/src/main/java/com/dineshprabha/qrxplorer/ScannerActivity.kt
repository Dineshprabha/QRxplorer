package com.dineshprabha.qrxplorer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.dineshprabha.qrxplorer.databinding.ActivityScannerBinding
import com.google.common.util.concurrent.ListenableFuture

class ScannerActivity : AppCompatActivity() {

    private lateinit var binding : ActivityScannerBinding

    private lateinit var cameraSelector: CameraSelector
    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>
    private lateinit var processCameraProvider: ProcessCameraProvider
    private lateinit var cameraPreview: Preview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(
            {
            processCameraProvider = cameraProviderFuture.get()
                bindCameraPreview()
        }, ContextCompat.getMainExecutor(this)
        )

    }

    private fun bindCameraPreview(){
        cameraPreview = Preview.Builder().setTargetRotation(binding.previewCamera.display.rotation).build()

        cameraPreview.setSurfaceProvider(binding.previewCamera.surfaceProvider)

        processCameraProvider.bindToLifecycle(this, cameraSelector, cameraPreview)
    }

    companion object{
        fun startScanner(context: Context, onScan: () -> Unit){
            Intent(context, ScannerActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }
}