package com.gmail.mtec.sistemas.agendabootcamp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.mtec.sistemas.agendabootcamp.adapters.ContactsAdapter
import com.gmail.mtec.sistemas.agendabootcamp.databinding.ActivityContatosBinding
import com.gmail.mtec.sistemas.agendabootcamp.helper.OpenActivity
import com.gmail.mtec.sistemas.agendabootcamp.model.ContactModel


class ContatosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContatosBinding

    private val REQUEST_CONTACT = 1
    private lateinit var  adapter:ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContatosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermissions()



    }

    fun requestPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_CONTACT
            )

        } else {
            setContacts()

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CONTACT) {
            setContacts()
        }


    }

    @SuppressLint("Range")
    private fun setContacts() {
        val contactList: ArrayList<ContactModel> = ArrayList()

        //OBS: QUERY ja existente nos recursos do android para fazer varredura na lista de contatos
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (cursor != null){
            while (cursor.moveToNext()){

                 val name:String = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber:String = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                val contact = ContactModel(name,phoneNumber)
                contactList.add(contact)
            }


            cursor.close()
        }

        initRecyclerView(contactList)

    }

    private fun initRecyclerView(contactList: ArrayList<ContactModel>) {
        adapter = ContactsAdapter(contactList)
        val layout = LinearLayoutManager(this)
        binding.rvContactList.layoutManager = layout
        binding.rvContactList.adapter = adapter
    }
}