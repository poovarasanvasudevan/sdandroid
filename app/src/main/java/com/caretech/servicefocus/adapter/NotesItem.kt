package com.caretech.servicefocus.adapter

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.caretech.servicefocus.R
import com.caretech.servicefocus.core.classes.Notes
import com.caretech.servicefocus.view.FavoriteButton
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractHeaderItem
import eu.davidea.flexibleadapter.items.IFilterable
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.flexibleadapter.utils.FlexibleUtils
import eu.davidea.viewholders.FlexibleViewHolder

data class NotesItem(val note: Notes) : AbstractHeaderItem<NotesItem.ViewHolder>(), IFilterable<String> {
    override fun filter(constraint: String?): Boolean {
        if (note.getTitle() != null && note.getTitle()!!.contains(constraint.toString())) {
            return true
        }
        if (note.getDescription() != null && note.getDescription()!!.contains(constraint.toString())) {
            return true
        }
        return false
    }

    override fun getLayoutRes(): Int = R.layout.notes_item

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>): ViewHolder = ViewHolder(view, adapter)

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>, holder: ViewHolder, position: Int, payloads: MutableList<Any>?) {

        if(adapter.hasFilter()) {
            val filter = adapter.getFilter(String::class.java)
            FlexibleUtils.highlightText(holder.heading,note.getTitle() , filter)
            FlexibleUtils.highlightText(holder.description,note.getDescription() , filter)

        } else {
            holder.heading.text = note.getTitle()
            holder.description.text = note.getDescription()
        }

        holder.faviorite.isFavorite = note.getFavorite()

        holder.faviorite.setOnFavoriteChangeListener(object : FavoriteButton.OnFavoriteChangeListener {
            override fun onFavoriteChanged(buttonView: FavoriteButton, favorite: Boolean) {
                if(favorite != note.getFavorite()) {
                    note.setFavorite(favorite)
                    note.pinInBackground {  }
                }
            }
        })
    }

    class ViewHolder(view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter) {
        var heading: AppCompatTextView = view.findViewById(R.id.nTitle)
        var description: AppCompatTextView = view.findViewById(R.id.nDescription)
        var faviorite: FavoriteButton = view.findViewById(R.id.faviorite)
    }
}