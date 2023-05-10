package com.example.vk

import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val uri = intent.getParcelableExtra("uri") ?: Uri.EMPTY
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        val files = listFilesAndFolders(uri)
        files.sortedBy { it.name }
        val adapter = Adapter(files)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        sortButton(files, adapter)
    }

    private fun sortButton(files:MutableList<DocumentFile>, adapter: Adapter) {
        var nameSortAsc = true
        var sizeSortAsc = true
        var dateSortAsc = true
        var expansionSortAsc = true
        findViewById<LinearLayout>(R.id.name_sort).setOnClickListener {
            nameSortAsc = if (!nameSortAsc) {
                files.sortBy { it.name }
                adapter.updateData(files)
                true
            } else {
                files.sortByDescending { it.name }
                adapter.updateData(files)
                false
            }

        }
        findViewById<LinearLayout>(R.id.size_sort).setOnClickListener {
            sizeSortAsc = if (sizeSortAsc) {
                files.sortBy { it.length() }
                adapter.updateData(files)
                false
            } else {
                files.sortByDescending { it.length() }
                adapter.updateData(files)
                true
            }
        }
        findViewById<LinearLayout>(R.id.date_sort).setOnClickListener {
            dateSortAsc = if (dateSortAsc) {
                files.sortBy { it.lastModified() }
                adapter.updateData(files)
                false
            } else {
                files.sortByDescending { it.lastModified() }
                adapter.updateData(files)
                true
            }
        }
        findViewById<LinearLayout>(R.id.expansion_sort).setOnClickListener {
            expansionSortAsc = if (expansionSortAsc) {
                files.sortBy { it.type }
                adapter.updateData(files)
                false
            } else {
                files.sortByDescending { it.type }
                adapter.updateData(files)
                true
            }
        }
    }

    private fun listFilesAndFolders(uri: Uri): MutableList<DocumentFile> {
        val documentFile = DocumentFile.fromTreeUri(applicationContext, uri)
        val filesAndFolders = mutableListOf<DocumentFile>()
        run breaking@ {
            documentFile?.listFiles()?.forEach { file ->
                filesAndFolders.add(file)
                if(uri==file.uri) {
                    filesAndFolders.clear()
                    file.listFiles().forEach {filesAndFolders.add(it) }
                    return@breaking
                }
            }
        }

       /* lifecycleScope.launch {
            addHash(filesAndFolders)
        }*/

        return filesAndFolders
    }

    /*private fun addHash(files:MutableList<DocumentFile>){

        files.forEach {
            val inputStream: InputStream? = contentResolver.openInputStream(it.uri)
            val md = MessageDigest.getInstance("SHA-256")
            val buffer = ByteArray(8192)
            var bytesRead = inputStream?.read(buffer)
            while (bytesRead != -1 && bytesRead != null && inputStream != null) {
                md.update(buffer, 0, bytesRead)
                bytesRead = inputStream.read(buffer)
            }
            inputStream?.close()
            val bytes = md.digest()
            val sb = StringBuilder()
            for (i in bytes.indices) {
                sb.append(((bytes[i] and 0xff.toByte()) + 0x100).toString(16).substring(1))
            }
        }

    }*/
}