package com.gmail.mtec.sistemas.agendabootcamp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gmail.mtec.sistemas.agendabootcamp.R
import com.gmail.mtec.sistemas.agendabootcamp.model.ContactModel

class ContactsAdapter(
    private val contactList:ArrayList<ContactModel>

) :RecyclerView.Adapter<ContactsAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsAdapter.MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_view,parent,false)

        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: ContactsAdapter.MyViewHolder, position: Int) {
        val item = contactList[position]//usar direto aqui para adicionar as informacoes no layout
        //holder.tvName.text = item.name

        holder.bindItem(contactList[position])//passar a manipulação para o MyViewHolder


    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName:TextView = itemView.findViewById(R.id.tvContactName)
        val tvPhoneNumber:TextView = itemView.findViewById(R.id.tvContactPhoneNumber)

        fun bindItem(contact: ContactModel){

            tvName.text = contact.name
            tvPhoneNumber.text = contact.phoneNumber
        }

    }


}