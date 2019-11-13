package com.github.rifqimfahmi.softkeyboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_menu.view.*

class GridMenuAdapter: RecyclerView.Adapter<GridMenuAdapter.MenuViewHolder>() {

    private val menus = arrayListOf(
        Menu("Produk", R.drawable.ic_group_2),
        Menu("Gambar", R.drawable.gambar),
        Menu("PDF", R.drawable.pdf),
        Menu("Invoice", R.drawable.invoice),
        Menu("Voucher", R.drawable.voucher)
    )

    private val data = ArrayList<Menu>().apply {
        addAll(menus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder.create(parent, viewType)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun updateDataCount(value: Int) {
        data.clear()
        val number = menus.size - 1
        for (i in 0 until value) {
            val randomIndex = (0..number).random()
            data.add(menus[randomIndex])
        }
        notifyDataSetChanged()
    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(menu: Menu) {
            with(itemView) {
                tvTitle.text = menu.name
                ivIcon.setImageDrawable(ContextCompat.getDrawable(context, menu.drawable))
            }
        }

        companion object {
            val LAYOUT = R.layout.item_menu

            fun create(parent: ViewGroup, viewType: Int): MenuViewHolder {
                return MenuViewHolder(LayoutInflater.from(parent.context).inflate(LAYOUT, parent, false))
            }
        }
    }

    data class Menu(val name: String, @DrawableRes val drawable: Int) {

    }
}