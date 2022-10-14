package com.wisdomleaf.myassignment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class RecyclerAdapter(
    private val sourceList: ArrayList<ModalClass>, private val context: Context
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_view, parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.title.text = sourceList.get(position).title
        holder.description.text = sourceList.get(position).description
        Glide
            .with(context)
            .load(sourceList.get(position).imageView)
            .placeholder(R.mipmap.loading)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            var alertDialog: AlertDialog? = null
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setCancelable(false)
            alertDialogBuilder.setTitle("Description")
            val message =
                "Id : " + sourceList.get(position).title + "\nAuthor : " + sourceList.get(position).description
            alertDialogBuilder.setMessage(message)
            alertDialogBuilder.setPositiveButton("Okay", DialogInterface.OnClickListener { _, i ->
                alertDialog?.dismiss()
            })
            alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    override fun getItemCount(): Int {
        return sourceList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.txt_title)
        val description: TextView = itemView.findViewById(R.id.txt_descripion)
        val image: ImageView = itemView.findViewById(R.id.img)
    }

}