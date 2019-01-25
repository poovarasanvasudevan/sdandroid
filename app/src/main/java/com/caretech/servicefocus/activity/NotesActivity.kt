package com.caretech.servicefocus.activity

import android.content.Context
import android.graphics.Color
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import com.caretech.servicefocus.R
import com.caretech.servicefocus.adapter.NotesItem
import com.caretech.servicefocus.core.BaseActivity
import com.caretech.servicefocus.core.classes.Notes
import com.caretech.servicefocus.core.icon
import com.ferfalk.simplesearchview.SimpleSearchView
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder
import com.mcxiaoke.koi.ext.startActivity
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.ionicons_typeface_library.Ionicons
import com.parse.ParseQuery
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.common.FlexibleItemDecoration
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.yesButton
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class NotesActivity : BaseActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    lateinit var notesRV: RecyclerView
    val adapter = recyclerAdapter()
    var notes = mutableListOf<Notes>()
    override fun START() {

        notesRV.layoutManager = SmoothScrollLinearLayoutManager(this@NotesActivity)
        notesRV.adapter = adapter
        notesRV.addItemDecoration(FlexibleItemDecoration(this@NotesActivity).withDefaultDivider())


        val dialog = BottomSheetBuilder(this@NotesActivity)
                .addTitleItem("Pick Actions")
                .addItem(200, "Open", icon(FontAwesome.Icon.faw_envelope_open1, color = Color.DKGRAY, size = 8))
                .addItem(201, "Edit", icon(FontAwesome.Icon.faw_edit, color = Color.DKGRAY, size = 8))
                .addItem(202, "Delete", icon(GoogleMaterial.Icon.gmd_delete, color = Color.DKGRAY, size = 8))
                .addItem(203, "Mark as Favorites", icon(FontAwesome.Icon.faw_star, color = Color.DKGRAY, size = 8))


        adapter.mItemLongClickListener = FlexibleAdapter.OnItemLongClickListener { position ->
            dialog.setMode(BottomSheetBuilder.MODE_LIST)
            dialog.setBackgroundColor(Color.WHITE)

            dialog.setItemClickListener {
                when (it.itemId) {
                    200 -> {
                        val bundle = android.os.Bundle()
                        bundle.putParcelable("notes", notes.get(position))
                        startActivity<ViewNotesActivity>(bundle)
                    }
                    201 -> {
                        val bundle = android.os.Bundle()
                        bundle.putParcelable("notes", notes.get(position))
                        startActivity<AddNotesActivity>(bundle)
                    }
                }
            }
            dialog.createDialog().show()
        }

        adapter.mItemClickListener = FlexibleAdapter.OnItemClickListener { view, position ->
            val bundle = android.os.Bundle()
            bundle.putParcelable("notes", notes.get(position))
            startActivity<ViewNotesActivity>(bundle)
            true
        }

        loadAllNotes()

        searchView().setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return onQueryTextChange(query)
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (adapter.hasNewFilter(newText)) {
                    adapter.setFilter(newText)
                }

                return false
            }

            override fun onQueryTextCleared(): Boolean {
                return false
            }
        })
    }

    override fun STOP() {

    }


    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()

        loadAllNotes()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.notes_info_menu, menu)
        menu!!.findItem(R.id.addNotes).icon = icon(Ionicons.Icon.ion_android_add, android.graphics.Color.WHITE, 18)

        val item = menu.findItem(R.id.action_search);
        searchView().setMenuItem(item);


        return super.onCreateOptionsMenu(menu)
    }

    fun infoClicked() {
        alert(Appcompat, "This notes are private resource, not shared to any sources", "Notes") {
            yesButton {
                title = "ok"
                it.dismiss()
            }
        }.show()
    }

    fun addNotes() {
        startActivity<AddNotesActivity>()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            R.id.addNotes -> addNotes()
        }

        return super.onOptionsItemSelected(item)
    }


    fun loadAllNotes() {
        val query = ParseQuery.getQuery(Notes::class.java)
        query.fromLocalDatastore()
       // query.whereEqualTo("user", ParseUser.getCurrentUser())
        query.whereEqualTo("status", true)
        //query.orderByDescending("favorite")
        query.findInBackground { results, e ->
            if (e == null) {
                if (results.size > 0) {
                    adapter.clear()
                    notes = results
                    (0 until results.size).forEach {
                        adapter.addItem(NotesItem(results.get(it)))
                    }
                } else {
                    openState().displayState("NO_NOTES")
                    openState().setOnStateButtonClicked { startActivity<AddNotesActivity>() }
                }
            } else {
                openState().displayState("OOPS_MESSAGE")
            }
        }
    }

    override fun COMMON() {
        setSupportActionBar(toolBarView())
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Notes"

        notesRV = view().findViewById(R.id.notesView)

    }

    override fun layout(): Int = R.layout.activity_notes
}
