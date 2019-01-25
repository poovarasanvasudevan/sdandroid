package com.caretech.servicefocus.activity

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import co.zsmb.materialdrawerkt.draweritems.badgeable.secondaryItem
import co.zsmb.materialdrawerkt.draweritems.divider
import com.andrognito.flashbar.Flashbar
import com.caretech.servicefocus.R
import com.caretech.servicefocus.adapter.ApprovalItem
import com.caretech.servicefocus.core.*
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.startActivity
import com.mikepenz.actionitembadge.library.ActionItemBadgeAdder
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.ionicons_typeface_library.Ionicons
import com.mikepenz.materialdrawer.Drawer
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.common.FlexibleItemDecoration
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager
import org.json.JSONArray
import org.json.JSONObject
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class HomeActivity : BaseActivity() {

    companion object {
        lateinit var act: Activity
            private set

    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    override fun COMMON() {
        act = this@HomeActivity
        setSupportActionBar(toolBarView())
    }

    lateinit var result: Drawer
    lateinit var homeRv: RecyclerView
    val adapter = recyclerAdapter()

    override fun STOP() {

    }


    override fun layout(): Int = R.layout.activity_home
    override fun START() {

        homeRv = view().find(R.id.homeRv)

        adapter.clear()
        homeRv.layoutManager = SmoothScrollLinearLayoutManager(this@HomeActivity)
        homeRv.adapter = adapter
        homeRv.addItemDecoration(FlexibleItemDecoration(this@HomeActivity).withDefaultDivider())


        adapter.addItem(ApprovalItem("CTS SARF System Access Required", "Please Approve this request , Need Access to Email and Pidgin, and also Citrix access for other to communnicate"))
        adapter.addItem(ApprovalItem("CTS SARF System Access Required", "Please Approve this request , Need Access to Email and Pidgin, and also Citrix access for other to communnicate"))
        adapter.addItem(ApprovalItem("CTS SARF System Access Required", "Please Approve this request , Need Access to Email and Pidgin, and also Citrix access for other to communnicate"))
        adapter.addItem(ApprovalItem("CTS SARF System Access Required", "Please Approve this request , Need Access to Email and Pidgin, and also Citrix access for other to communnicate"))
        adapter.addItem(ApprovalItem("CTS SARF System Access Required", "Please Approve this request , Need Access to Email and Pidgin, and also Citrix access for other to communnicate"))
        adapter.addItem(ApprovalItem("CTS SARF System Access Required", "Please Approve this request , Need Access to Email and Pidgin, and also Citrix access for other to communnicate"))
        adapter.addItem(ApprovalItem("CTS SARF System Access Required", "Please Approve this request , Need Access to Email and Pidgin, and also Citrix access for other to communnicate"))
        adapter.addItem(ApprovalItem("CTS SARF System Access Required", "Please Approve this request , Need Access to Email and Pidgin, and also Citrix access for other to communnicate"))
        adapter.addItem(ApprovalItem("CTS SARF System Access Required", "Please Approve this request , Need Access to Email and Pidgin, and also Citrix access for other to communnicate"))
        adapter.addItem(ApprovalItem("CTS SARF System Access Required", "Please Approve this request , Need Access to Email and Pidgin, and also Citrix access for other to communnicate"))
        adapter.addItem(ApprovalItem("CTS SARF System Access Required", "Please Approve this request , Need Access to Email and Pidgin, and also Citrix access for other to communnicate"))
        adapter.addItem(ApprovalItem("CTS SARF System Access Required", "Please Approve this request , Need Access to Email and Pidgin, and also Citrix access for other to communnicate"))
        adapter.addItem(ApprovalItem("CTS SARF System Access Required", "Please Approve this request , Need Access to Email and Pidgin, and also Citrix access for other to communnicate"))
        adapter.addItem(ApprovalItem("CTS SARF System Access Required", "Please Approve this request , Need Access to Email and Pidgin, and also Citrix access for other to communnicate"))
        adapter.addItem(ApprovalItem("CTS SARF System Access Required", "Please Approve this request , Need Access to Email and Pidgin, and also Citrix access for other to communnicate"))

        adapter.mItemLongClickListener = FlexibleAdapter.OnItemLongClickListener { _ ->

            val dialog = BottomSheetBuilder(this@HomeActivity)
                    .addTitleItem("Actions")

                    .addItem(200, "Open", icon(GoogleMaterial.Icon.gmd_open_with, color = Color.DKGRAY, size = 8))
                    .addItem(201, "Approve", icon(GoogleMaterial.Icon.gmd_check, color = Color.DKGRAY, size = 8))
                    .addItem(202, "Deny", icon(GoogleMaterial.Icon.gmd_close, color = Color.DKGRAY, size = 8))

                    .setItemClickListener {
                        when (it.itemId) {
                            201 -> {
                                Flashbar.Builder(this)
                                        .gravity(Flashbar.Gravity.BOTTOM)
                                        .title("Approve in Progress")
                                        .primaryActionText("UNDO")
                                        .backgroundColorRes(R.color.md_black_1000)
                                        .primaryActionTapListener(object : Flashbar.OnActionTapListener {
                                            override fun onActionTapped(bar: Flashbar) {
                                                bar.dismiss()
                                            }
                                        })
                                        .build()
                                        .show()
                            }
                        }
                    }

                    .setMode(BottomSheetBuilder.MODE_LIST)
                    .setBackgroundColor(Color.WHITE)
                    .createDialog()

            dialog.show()
        }

        result = drawer {
            displayBelowStatusBar = false

            rootView = view().findViewById(R.id.mainContent)
            toolbar = toolBarView()
            actionBarDrawerToggleAnimated = true
            closeOnClick = true


            onItemClick { view, position, drawerItem ->
                val clickedItem = drawerItem.tag as JsonObject

                if (clickedItem.has("action") && clickedItem.get("action").asString.isNotEmpty()) {
                    result.closeDrawer()
                    parseAction(clickedItem)
                }
                true
            }

//            ParseConfig.getInBackground { config, e ->
//                if (e == null) {
                    val menuArray = JSONArray(readJSONFromAsset("test"))
                    (0 until menuArray.length()).forEach {
                        val singleMenu = menuArray.get(it) as JSONObject

                        when (singleMenu.getString("type")) {
                            "PRIMARY" -> {
                                primaryItem(singleMenu.getString("title")) {
                                    iconDrawable = icon(singleMenu.getString("icon"), Color.parseColor("#555555"), 28)

                                    if (singleMenu.has("description")) {
                                        description = singleMenu.getString("description")
                                    }
                                    if (singleMenu.has("badge")) {
                                        badge(singleMenu.getString("badge"))
                                    }

                                    identifier = it.toLong()
                                    tag = JsonParser().parse(singleMenu.toString())

                                }
                            }
                            "SECONDARY" -> {
                                secondaryItem(singleMenu.getString("title")) {
                                    iconDrawable = icon(singleMenu.getString("icon"), Color.parseColor("#555555"), 28)
                                    if (singleMenu.has("description")) {
                                        description = singleMenu.getString("description")
                                    }
                                    if (singleMenu.has("badge")) {
                                        badge(singleMenu.getString("badge"))
                                    }
                                }
                            }
                            "DIVIDER" -> divider()
                        }

                    }
              //  }
           // }

//            primaryItem(name = "Tickets") { iicon = GoogleMaterial.Icon.gmd_code }
//            primaryItem(name = "Approvals") {
//                iicon = GoogleMaterial.Icon.gmd_thumbs_up_down
//                badge("10")
//            }
//            primaryItem(name = "Vaction") { iicon = GoogleMaterial.Icon.gmd_beach_access }
//            primaryItem(name = "On-Call") { iicon = GoogleMaterial.Icon.gmd_local_phone }
//            primaryItem(name = "Notes") {
//                iicon = GoogleMaterial.Icon.gmd_note
//                identifier = 396L
//            }
//            primaryItem(name = "Watch List") { iicon = GoogleMaterial.Icon.gmd_hourglass_empty }
//            primaryItem(name = "Change Management") { iicon = GoogleMaterial.Icon.gmd_settings_applications }
//            primaryItem(name = "Vendor") { iicon = GoogleMaterial.Icon.gmd_group_work }
//            primaryItem(name = "Calendar") {
//                iicon = GoogleMaterial.Icon.gmd_perm_contact_calendar
//                identifier = 398L
//            }
//            divider()
//            primaryItem(name = "Guides") { iicon = GoogleMaterial.Icon.gmd_help }
//            primaryItem(name = "My Account") {
//                iicon = GoogleMaterial.Icon.gmd_account_circle
//                identifier = 399L
//            }
//            primaryItem(name = "Application Settings") {
//                iicon = GoogleMaterial.Icon.gmd_settings
//                identifier = 400L
//            }
//            primaryItem(name = "About") {
//                iicon = GoogleMaterial.Icon.gmd_store
//                identifier = 397L
//            }
//            primaryItem(name = "Logout") {
//                iicon = GoogleMaterial.Icon.gmd_exit_to_app
//                identifier = 395L
//            }

//            onItemClick { _, _, drawerItem ->
//                result.closeDrawer()
//                when (drawerItem.identifier) {
//                    400L -> startActivity<AccountSettingsActivity>()
//                    399L -> startActivity<MyAccountActivity>()
//                    398L -> startActivity<CalendarActivity>()
//                    397L -> startActivity<AboutActivity>()
//                    396L -> startActivity<NotesActivity>()
//                    395L -> {
//                        val dialog = indeterminateProgressDialog("Logging Out")
//                        dialog.show()
//                        ParseUser.logOutInBackground { e ->
//                            if (e == null) {
//                                dialog.dismiss()
//                                startActivity<MainActivity>()
//                                finish()
//                            }
//                        }
//                    }
//                }
//                true
//            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)

        ActionItemBadgeAdder()
                .act(this@HomeActivity)
                .menu(menu)
                .title("Notification")
                .itemDetails(0, 260, 1)
                .showAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                .add(Ionicons.Icon.ion_ios_bell, Color.WHITE, 1)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            260 -> startActivity<NotificationActivity>()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (result.isDrawerOpen) {
            result.closeDrawer()
        } else {
            super.onBackPressed()
        }
    }

}
