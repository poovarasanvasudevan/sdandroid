package com.caretech.servicefocus.activity

import android.content.Context
import android.graphics.Color
import android.view.Menu
import android.view.MenuItem
import com.andrognito.flashbar.Flashbar
import com.caretech.servicefocus.R
import com.caretech.servicefocus.core.BaseActivity
import com.caretech.servicefocus.core.classes.Notes
import com.caretech.servicefocus.core.icon
import com.google.android.material.textfield.TextInputEditText
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import org.jetbrains.anko.toast
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AddNotesActivity : BaseActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    lateinit var notesTitle: TextInputEditText
    lateinit var notesDescription: TextInputEditText
    var notes: Notes? = null

    override fun START() {
        if (intent.hasExtra("notes")) {
            notes = intent.getParcelableExtra("notes")

            if(notes != null) {
                notesTitle.setText(notes!!.getTitle())
                notesDescription.setText(notes!!.getDescription())
            }
        }
    }

    override fun STOP() {

    }

    override fun COMMON() {
        setSupportActionBar(toolBarView())
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "New Notes"

        notesTitle = view().findViewById(R.id.notesTitle)
        notesDescription = view().findViewById(R.id.notesDescription)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_notes_menu, menu)
        menu!!.findItem(R.id.saveNotes).icon = icon(GoogleMaterial.Icon.gmd_save, Color.WHITE, 18)

        return super.onCreateOptionsMenu(menu)
    }

    fun saveNotes() {
        if (notesTitle.text.toString().isEmpty() || notesTitle.text.toString().isBlank()) {
            notesTitle.setError("Title is Required")
            return
        }

        if (notesDescription.text.toString().isEmpty() || notesDescription.text.toString().isBlank()) {
            notesDescription.setError("Description is Required")
            return
        }


        val bar = Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .showProgress(Flashbar.ProgressPosition.LEFT)
                .title("Saving Notes")
                .backgroundColorRes(R.color.colorPrimary)
                .build()

        bar.show()
        var notesObject : Notes? = null
        if(notes == null) {
            notesObject = Notes()
           // notesObject.setUser(ParseUser.getCurrentUser())
            notesObject.setStatus(true)
            notesObject.setFavorite(false)
        } else {
            notesObject = notes
        }
        notesObject!!.setTitle(notesTitle.text.toString().trim())
        notesObject.setDescription(notesDescription.text.toString().trim())

        notesObject.pinInBackground { e ->
            if (e == null) {
                if (bar.isShowing())
                    bar.dismiss()

                onBackPressed()
            } else {

                toast(e.localizedMessage)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            R.id.saveNotes -> {
                saveNotes()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun layout(): Int = R.layout.activity_add_notes

}
