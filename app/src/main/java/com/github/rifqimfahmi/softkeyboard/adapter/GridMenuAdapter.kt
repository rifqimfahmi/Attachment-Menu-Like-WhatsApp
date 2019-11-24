package com.github.rifqimfahmi.softkeyboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.rifqimfahmi.softkeyboard.R
import kotlinx.android.synthetic.main.item_menu.view.*

class GridMenuAdapter : RecyclerView.Adapter<GridMenuAdapter.MenuViewHolder>() {

    var listener: GridMenuListener? = null

    private val menus = arrayListOf(
        Menu(
            "Document",
            R.drawable.ic_document
        ),
        Menu(
            "Camera",
            R.drawable.ic_camera
        ),
        Menu(
            "Gallery",
            R.drawable.ic_gallery
        ),
        Menu(
            "Audio",
            R.drawable.ic_volume
        ),
        Menu(
            "Location",
            R.drawable.ic_location
        ),
        Menu(
            "Contact",
            R.drawable.ic_contact
        )
    )

    interface GridMenuListener {
        fun dismissPopup()
    }

    private val data = ArrayList<Menu>().apply {
        addAll(menus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder.create(
            parent,
            viewType
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(data[position], listener)
    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            menu: Menu,
            listener: GridMenuListener?
        ) {
            with(itemView) {
                tvTitle.text = menu.name
                ivIcon.setImageDrawable(ContextCompat.getDrawable(context, menu.drawable))
                itemView.setOnClickListener {
                    Toast.makeText(it.context, "Menu ${menu.name} clicked", Toast.LENGTH_SHORT)
                        .show()
                    listener?.dismissPopup()
                }
            }
        }

        companion object {
            val LAYOUT = R.layout.item_menu

            fun create(parent: ViewGroup, viewType: Int): MenuViewHolder {
                return MenuViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        LAYOUT,
                        parent,
                        false
                    )
                )
            }
        }
    }

    data class Menu(val name: String, @DrawableRes val drawable: Int) {

    }
}