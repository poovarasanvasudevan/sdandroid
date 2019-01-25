package com.caretech.servicefocus.activity

import android.content.Context
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.caretech.servicefocus.R
import com.caretech.servicefocus.core.*
import com.caretech.servicefocus.core.ui.ImageTarget
import com.flipkart.android.proteus.*
import com.flipkart.android.proteus.exceptions.ProteusInflateException
import com.flipkart.android.proteus.value.Layout
import com.flipkart.android.proteus.value.ObjectValue
import com.flipkart.android.proteus.value.Value
import com.google.gson.reflect.TypeToken
import com.mcxiaoke.koi.log.logd
import org.jetbrains.anko.find
import org.json.JSONObject
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class FormActivity : BaseActivity() {

    lateinit var jsonLayoutInflater: ProteusLayoutInflater
    lateinit var styles: Styles
    lateinit var layout: Layout
    lateinit var layouts: Map<String, Layout>
    lateinit var jsonView: ProteusView
    lateinit var jsonManager: ProteusManager
    lateinit var data: ObjectValue
    lateinit var container: RelativeLayout
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private val styleManager = object : StyleManager() {
        override fun getStyles(): Styles? = styles
    }

    private val layoutManager = object : LayoutManager() {
        override fun getLayouts(): MutableMap<String, Layout> = layouts
    }

    private val loader = ProteusLayoutInflater.ImageLoader { view, url, callback ->
        Glide.with(this@FormActivity)
                .load(url)
                .into(ImageTarget(callback))
    }

    private val callback = object : ProteusLayoutInflater.Callback {

        override fun onUnknownViewType(context: ProteusContext, type: String, layout: Layout, data: ObjectValue, index: Int): ProteusView {
            throw ProteusInflateException("Unknown view type '$type' cannot be inflated")
        }

        override fun onEvent(event: String, value: Value, view: ProteusView) {
            logd("ProteusEvent", value.toString())
        }
    }

    override fun START() {

        try {
            //val layoutsJson: JsonObject = JsonParser().parse(readJSONFromAsset("layouts")).asJsonObject
            // val stylesJson: JsonObject = JsonParser().parse(readJSONFromAsset("styles")).asJsonObject
            // val notesJson: JsonObject = JsonParser().parse(readJSONFromAsset("notes")).asJsonObject

            jsonManager = ProteusManager();
            container = view().find(R.id.containers)


            val jsonContext = jsonManager.proteus.createContextBuilder(this)
                    .setLayoutManager(layoutManager)
                    .setCallback(callback)
                    .setImageLoader(loader)
                    .setStyleManager(styleManager)
                    .build()

            jsonLayoutInflater = jsonContext.getInflater();


//            val query = ParseQuery.getQuery<ParseObject>("Pages")
//            query.whereEqualTo("name", "ADD_NOTES");
//            query.getFirstInBackground { result, e ->
//                if (e == null) {
            try {
                val result =  JSONObject(readJSONFromAsset("notes"))
                data = layoutGson().fromJson(BaseApplication.context.readJSONFromAsset("data"), ObjectValue::class.java)
                layout = layoutGson().fromJson(result.getString("layout"), Layout::class.java)
                val layoutType = object : TypeToken<Map<String, Layout>>() {
                }.type

                layouts = layoutGson().fromJson(BaseApplication.context.readJSONFromAsset("layouts"), layoutType)
                styles = layoutGson().fromJson(BaseApplication.context.readJSONFromAsset("styles"), Styles::class.java)
                render()

            } catch (e: Exception) {
                openState().displayState("OOPS_MESSAGE")
            }

        } catch (e: Exception) {
            //e.printStackTrace()
            openState().displayState("OOPS_MESSAGE")
        }
    }

    fun render() {

        // remove the current view
        container.removeAllViews()

        // Inflate a new view using proteus
        val start = System.currentTimeMillis()
        jsonView = jsonLayoutInflater.inflate(layout, data, container, 0)
        println("inflate time: " + (System.currentTimeMillis() - start))

        // Add the inflated view to the container
        container.addView(jsonView.getAsView())
    }

    override fun STOP() {

    }

    override fun COMMON() {
        setSupportActionBar(toolBarView())
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Notes"
    }

    override fun layout(): Int = R.layout.activity_form

}
