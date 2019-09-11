package com.kp.prosenjit.kect.Activity


import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.kp.prosenjit.kect.Adapter.PagerAdapter
import com.kp.prosenjit.kect.ButtomSheets.AppsBottomSheet
import com.kp.prosenjit.kect.ButtomSheets.ContactsButtomSheet
import com.kp.prosenjit.kect.ButtomSheets.ProgressButtomSheet
import com.kp.prosenjit.kect.Database.TinyDB
import com.kp.prosenjit.kect.R
import com.kp.prosenjit.kect.Model.Payment
import com.kp.prosenjit.kect.NetworkCall.*
import com.kp.prosenjit.kect.Registrarion.SiginInActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject



class MainActivity : Base() {

    private var appsBottomSheet: AppsBottomSheet?=null
    private var contactsBottomSheet: ContactsButtomSheet?=null
    private var progressBottomSheet: ProgressButtomSheet?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        ChangeStausBarColour(this@MainActivity,R.color.colorwhite)
        settingTabLayout()
        buttom_nav_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
    override fun onResume() {
        super.onResume()
        buttom_nav_view.menu.getItem(0).setCheckable(false)
        buttom_nav_view.menu.getItem(1).setCheckable(false)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val tinyDB=TinyDB(this@MainActivity)
                tinyDB.clear()
                val  intent= Intent(this@MainActivity, SiginInActivity::class.java).apply {  }
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("Requst code**",""+requestCode)
        if(requestCode== PAYMENT_REQUEST_CODE) {
            if (data != null) {
                Log.e("onActivityResult", "" + data.getStringExtra("response")!!)
                val res = data.getStringExtra("response")
                val search = "SUCCESS"
                if (res!!.toLowerCase().contains(search.toLowerCase())) {
                   // Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
                    appsBottomSheet!!.dismiss()
                    val parts = res.split("&")
                    val txnId=parts.get(0)
                    val txnRef=parts.get(3)
                    val report=txnId+"\n"+txnRef
                    ShowGifOnlyOkFinish(this@MainActivity,"Transaction Successful",report,R.drawable.checkmark)
                } else {
                    ShowGifOnlyOkFinish(this@MainActivity,"Transaction Failed","",R.drawable.info_x)
                    appsBottomSheet!!.dismiss()
                }
            }else{
                Log.e("Else##**","True")
                appsBottomSheet!!.dismiss()
            }
        }
    }




    private fun settingTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.str_activity))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.str_member_chat))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.str_gallary))
        tabLayout.tabGravity=TabLayout.GRAVITY_FILL

        val  adapter= PagerAdapter(this@MainActivity,supportFragmentManager,tabLayout.tabCount)
        view_pager.adapter=adapter

        view_pager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                view_pager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        buttom_nav_view.menu.getItem(0).setCheckable(true)
        when (menuItem.itemId) {
            R.id.navigation_contacts -> {
                progressBottomSheet = ProgressButtomSheet(this@MainActivity,"Contacts","")
                progressBottomSheet!!.show(supportFragmentManager, "Progress")
                FetchContacts()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_donation -> {
                 //SettingUpiPay()
                FetchUpiAppList()
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }


    private fun SettingUpiPay() {
        val payeeAddress = "prosenjitc8@oksbi"
        val payeeName = "Prosenjit Chowdhury"
        val transactionNote = "For child development"
        val amount = "1"
        val currencyUnit = "INR"
          val uri = Uri.parse(
              "upi://pay?pa=" + payeeAddress + "&pn=" + payeeName + "&tn=" + transactionNote +
    "&am=" + amount + "&cu=" + currencyUnit
          )

                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivityForResult(intent, PAYMENT_REQUEST_CODE)
    }

    private fun FetchUpiAppList() {
        val payment = Payment()
        payment.vpa="prosenjitc8@oksbi"
        payment.name="Santu Modal"
        payment.description="For Child Development"
        payment.amount="1.00"


        // Set Parameters for UPI
        val payUri = Uri.Builder()

        payUri.scheme("upi").authority("pay")
        payUri.appendQueryParameter("pa", payment.getVpa())
        payUri.appendQueryParameter("pn", payment.getName())
        payUri.appendQueryParameter("tn", payment.getDescription())
        payUri.appendQueryParameter("am", payment.getAmount())
        payUri.appendQueryParameter("cu", payment.getCurrency())

        //Build URI
        val uri = payUri.build()

        // Set Data Intent
        val paymentIntent = Intent(Intent.ACTION_VIEW)
        paymentIntent.data = uri

        // Check if app is installed or not
        if (paymentIntent.resolveActivity(packageManager) != null) {
            val intentList = packageManager.queryIntentActivities(paymentIntent, 0)
            showApps(intentList, paymentIntent)
        } else {
            Toast.makeText(this, "No UPI app found! Please Install to Proceed!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showApps(appsList: List<ResolveInfo>, paymentIntent: Intent) {
      //  Log.e("ListSizeMain**","==>"+appsList.size)
         appsBottomSheet = AppsBottomSheet(this@MainActivity,appsList, paymentIntent)
         appsBottomSheet!!.show(supportFragmentManager, "Pay Using")
    }

    private fun FetchContacts() {
        val parameter=JSONObject()
        NetworkUrlRequest(WebUrls.CONTACTS_URL,parameter,METHOD_GET,object : CustomResponse.Listener<String>{
            override fun onSuccessResponse(response: String) {
                try {
                    val jsonObject=JSONObject(response)
                    val error=jsonObject.getString(Base.ERROR_NAME)
                    try{
                        val message=jsonObject.getString(Base.RESULT_MSG)
                    }catch (e:Exception){}

                    if(error.equals(Base.ERROR_SUSSESS)){
                        val jsonArray=jsonObject.getJSONArray("USERS_DETAILS")
                        Log.e("ContactsArry","length**"+jsonArray.length()+"====>"+jsonArray)
                        progressBottomSheet!!.dismiss()
                        contactsBottomSheet = ContactsButtomSheet(this@MainActivity,this@MainActivity,jsonArray)
                        contactsBottomSheet!!.show(supportFragmentManager, "Contacts")
                    }else{
                        progressBottomSheet = ProgressButtomSheet(this@MainActivity,"Contacts","Something going wrong")
                        progressBottomSheet!!.show(supportFragmentManager, "Progress")
                    }



                }catch (e:Exception){
                    Log.e("Setting Adapter ex##",""+e)
                    progressBottomSheet = ProgressButtomSheet(this@MainActivity,"Contacts","Something going wrong")
                    progressBottomSheet!!.show(supportFragmentManager, "Progress")
                    contactsBottomSheet!!.dismiss()
                }

            }

            override fun onError(response: String) {
                progressBottomSheet = ProgressButtomSheet(this@MainActivity,"Contacts",response)
                progressBottomSheet!!.show(supportFragmentManager, "Progress")
                contactsBottomSheet!!.dismiss()
            }
        })

    }


}
