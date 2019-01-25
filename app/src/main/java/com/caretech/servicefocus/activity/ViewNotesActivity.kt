package com.caretech.servicefocus.activity

import android.content.Context
import android.graphics.Color
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatTextView
import com.caretech.servicefocus.R
import com.caretech.servicefocus.core.BaseActivity
import com.caretech.servicefocus.core.classes.Notes
import com.caretech.servicefocus.core.icon
import com.caretech.servicefocus.view.FavoriteButton
import com.mcxiaoke.koi.ext.startActivity
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import org.jetbrains.anko.find
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class ViewNotesActivity : BaseActivity() {

    lateinit var note : Notes

    override fun START() {
        view().find<AppCompatTextView>(R.id.nVTitle).text = note.getTitle()
        view().find<AppCompatTextView>(R.id.nvDesc).text = note.getDescription()
        view().find<FavoriteButton>(R.id.nvFav).isFavorite = note.getFavorite()
    }

    override fun STOP() {
    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    override fun COMMON() {
        setSupportActionBar(toolBarView())
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Notes"

        note = intent.getParcelableExtra("notes")

    }

    override fun layout(): Int = R.layout.activity_view_notes

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.view_notes_menu, menu)
        menu!!.findItem(R.id.editNotes).icon = icon(FontAwesome.Icon.faw_edit, Color.WHITE, 18)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.editNotes -> {
                val bundle = android.os.Bundle()
                bundle.putParcelable("notes", note)
                startActivity<AddNotesActivity>(bundle)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
