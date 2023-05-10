package com.example.vk

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.Date

class Adapter(
    private var files:List<DocumentFile>,
) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.item_name)
        private val sizeTextView: TextView = itemView.findViewById(R.id.item_size)
        private val dateTextView: TextView = itemView.findViewById(R.id.item_date)
        private val image: ImageView = itemView.findViewById(R.id.icon)
        private val card:LinearLayout=itemView.findViewById(R.id.card)
        private val share:CardView = itemView.findViewById(R.id.share)
        fun bind(file: DocumentFile) {
            nameTextView.text = file.name
            sizeTextView.text = file.length().toString()
            dateTextView.text = Date(file.lastModified()).toString()
            Log.i("AAAAAA",file.name.toString())
            when(file.type){
                "image/jpeg"->imageSet(file.uri)
                "image/png"->imageSet(file.uri)
                "image/jpg"->imageSet(file.uri)
                null->imageSetNull(file)
            }
            card.setOnClickListener {
                if (file.isDirectory)
                {
                    val intent = Intent(itemView.context, itemView.context::class.java)
                    intent.putExtra("uri",file.uri )
                    startActivity(itemView.context,intent,null)
                }
                else{
                    val openFileIntent = Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(file.uri, file.type)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    try {
                        startActivity(itemView.context,openFileIntent,null)
                    } catch (e: ActivityNotFoundException) {
                        Log.i("Error",e.toString())
                    }
                }
            }
            share.setOnClickListener {
                shareFile(file)
            }
        }

        private fun imageSetNull(file: DocumentFile) {
            if (file.isDirectory) image.setImageResource(R.drawable.baseline_folder_24)
            else image.setImageResource(R.drawable.question)
        }

        private fun imageSet(uri: Uri) {
            Glide.with(itemView.context).load(uri).into(image)
        }
        private fun shareFile(file: DocumentFile) {
            val documentUri: Uri = file.uri
            val intent = Intent(Intent.ACTION_SEND)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.putExtra(Intent.EXTRA_STREAM, documentUri)
            intent.type = file.type
            startActivity(itemView.context,Intent.createChooser(intent, "Share file"),null)

        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ViewHolder(itemView)
    }

    fun updateData(newListOfFiles:List<DocumentFile>) {
        files = newListOfFiles
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return files.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(files[position])
    }
}