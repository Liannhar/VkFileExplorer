package com.example.vk

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity(){

    private val openDocumentTree = registerForActivityResult(ActivityResultContracts.OpenDocumentTree()){ uri ->
        uri?.let {
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            applicationContext.contentResolver.takePersistableUriPermission(uri, takeFlags)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("uri",uri )
            startActivity( intent)
            finish()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)
        openDocumentTree.launch(null)
    }
}