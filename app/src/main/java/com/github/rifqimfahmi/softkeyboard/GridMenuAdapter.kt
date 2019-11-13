package com.github.rifqimfahmi.softkeyboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GridMenuAdapter: RecyclerView.Adapter<GridMenuAdapter.MenuViewHolder>() {

    private val menus = arrayListOf(
        Menu("Voucher"),
        Menu("Voucher"),
        Menu("Voucher"),
        Menu("Voucher"),
        Menu("Voucher"),
        Menu("Voucher"),
        Menu("Voucher")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder.create(parent, viewType)
    }

    override fun getItemCount(): Int {
        return menus.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menus[position])
    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(menu: Menu) {

        }

        companion object {
            val LAYOUT = R.layout.item_menu

            fun create(parent: ViewGroup, viewType: Int): MenuViewHolder {
                return MenuViewHolder(LayoutInflater.from(parent.context).inflate(LAYOUT, parent, false))
            }
        }
    }

    data class Menu(private val name: String) {

    }
}