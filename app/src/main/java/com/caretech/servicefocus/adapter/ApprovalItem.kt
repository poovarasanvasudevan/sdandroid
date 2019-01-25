package com.caretech.servicefocus.adapter

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.caretech.servicefocus.R
import com.caretech.servicefocus.core.badge
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFilterable
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.flexibleadapter.utils.FlexibleUtils
import eu.davidea.viewholders.FlexibleViewHolder

data class ApprovalItem(val title: String, val description: String) : AbstractFlexibleItem<ApprovalItem.ViewHolder>(), IFilterable<String> {
    override fun filter(constraint: String): Boolean {
        return title.contains(constraint) || description.contains(constraint)
    }
    override fun getLayoutRes(): Int = R.layout.approval_item
    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>): ViewHolder = ViewHolder(view, adapter)

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>, holder: ViewHolder, position: Int, payloads: MutableList<Any>?) {

        val search = adapter.getFilter(String::class.java)

        if (search != null && (search.isNotEmpty() || search.isNotBlank())) {
            FlexibleUtils.highlightText(holder.title, title, search)
            FlexibleUtils.highlightText(holder.description, description, search)
        } else {

            holder.title.text = title
            holder.description.text = description
            holder.iconPanel.removeAllViews()
            holder.iconPanel.addView(holder.iconPanel.context.badge("APP", Color.RED))
        }
    }

    class ViewHolder(view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter) {
        var title: AppCompatTextView = view.findViewById(R.id.aTitle)
        var description: AppCompatTextView = view.findViewById(R.id.aDescription)
        var iconPanel: LinearLayout = view.findViewById(R.id.iconPanel)
    }
}