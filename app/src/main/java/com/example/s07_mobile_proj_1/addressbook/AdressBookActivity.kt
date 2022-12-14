package com.example.s07_mobile_proj_1.addressbook

import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import com.evrencoskun.tableview.TableView
import androidx.appcompat.app.AppCompatActivity
import com.example.s07_mobile_proj_1.R

class AdressBookActivity : AppCompatActivity() {
    private val adapter: MyTableViewAdapter = MyTableViewAdapter()
    private val chs = arrayListOf<ColumnHeader>()
    private val rhs = arrayListOf<RowHeader>()
    private val cellList = arrayListOf<ArrayList<Cell>>()

    class Contact(val id: String, val name: String) {
        var numbers: ArrayList<String> = arrayListOf()
        var emails: ArrayList<String> = arrayListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adress_book)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        chs.add(ColumnHeader("Name"))
        chs.add(ColumnHeader("Phone Number"))
        chs.add(ColumnHeader("Email address"))

        for (contact in fetchContacts()) {
            rhs.add(RowHeader(contact.id))
            cellList.add(
                arrayListOf(
                    Cell(contact.name),
                    Cell(if (contact.numbers.isEmpty()) '-' else contact.numbers[0]),
                    Cell(if (contact.emails.isEmpty()) '-' else contact.emails[0])
                )
            )
        }

        val tv = this.findViewById<TableView>(R.id.content_container)

        tv.setAdapter(adapter)
        adapter.setAllItems(chs.toList(), rhs.toList(), cellList.toList())

        findViewById<Button>(R.id.button_set_filter).setOnClickListener {
            rhs.clear()
            cellList.clear()
            for (contact in fetchContacts().filter {
                if (it.numbers.size == 0) return@filter false

                return@filter it.numbers[0].contains("00", true)
            }) {
                rhs.add(RowHeader(contact.id))
                cellList.add(
                    arrayListOf(
                        Cell(contact.name),
                        Cell(if (contact.numbers.isEmpty()) '-' else contact.numbers[0]),
                        Cell(if (contact.emails.isEmpty()) '-' else contact.emails[0])
                    )
                )
            }

            adapter.setAllItems(chs.toList(), rhs.toList(), cellList.toList())
            tv.invalidate()
            tv.refreshDrawableState()
        }

        findViewById<Button>(R.id.button_reset_contacts_filter).setOnClickListener {
            rhs.clear()
            cellList.clear()
            for (contact in fetchContacts()) {
                rhs.add(RowHeader(contact.id))
                cellList.add(
                    arrayListOf(
                        Cell(contact.name),
                        Cell(if (contact.numbers.isEmpty()) '-' else contact.numbers[0]),
                        Cell(if (contact.emails.isEmpty()) '-' else contact.emails[0])
                    )
                )
            }

            adapter.setAllItems(chs.toList(), rhs.toList(), cellList.toList())
            tv.invalidate()
            tv.refreshDrawableState()
        }

    }



    private fun getContactsList(): ArrayList<Contact> {
        val contactsList = ArrayList<Contact>()
        val cr = contentResolver
        val contactsCursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null)

        if (contactsCursor != null && contactsCursor.count > 0) {
            val idIndex = contactsCursor.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = contactsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)

            while (contactsCursor.moveToNext()) {
                val id = contactsCursor.getString(idIndex)
                val name = contactsCursor.getString(nameIndex)
                if (name != null) {
                    contactsList.add(Contact(id, name))
                }
            }
            contactsCursor.close()
        }
        return contactsList
    }

    private fun getContactNumbers(): HashMap<String, ArrayList<String>> {
        val contactsNumberMap = HashMap<String, ArrayList<String>>()
        val phoneCursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (phoneCursor != null && phoneCursor.count > 0) {
            val contactIdIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val numberIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            while (phoneCursor.moveToNext()) {
                val contactId = phoneCursor.getString(contactIdIndex)
                val number: String = phoneCursor.getString(numberIndex)
                //check if the map contains key or not, if not then create a new array list with number
                if (contactsNumberMap.containsKey(contactId)) {
                    contactsNumberMap[contactId]?.add(number)
                } else {
                    contactsNumberMap[contactId] = arrayListOf(number)
                }
            }
            //contact contains all the number of a particular contact
            phoneCursor.close()
        }
        return contactsNumberMap
    }

    private fun getContactEmails(): HashMap<String, ArrayList<String>> {
        val contactsEmails = HashMap<String, ArrayList<String>>()
        val phoneCursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (phoneCursor != null && phoneCursor.count > 0) {
            val contactIdIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID)
            val emailIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
            while (phoneCursor.moveToNext()) {
                val contactId = phoneCursor.getString(contactIdIndex)
                val email: String = phoneCursor.getString(emailIndex)
                //check if the map contains key or not, if not then create a new array list with number
                if (contactsEmails.containsKey(contactId)) {
                    contactsEmails[contactId]?.add(email)
                } else {
                    contactsEmails[contactId] = arrayListOf(email)
                }
            }
            //contact contains all the number of a particular contact
            phoneCursor.close()
        }
        return contactsEmails
    }

    private fun fetchContacts(): ArrayList<Contact> {
        val contactsList = this.getContactsList()
        val contactNumbers = getContactNumbers()
        val contactEmails = getContactEmails()

        contactsList.forEach {
            contactNumbers[it.id]?.let { numbers ->
                it.numbers = numbers
            }
            contactEmails[it.id]?.let { emails ->
                it.emails = emails
            }
        }

        return contactsList
    }
}


// setContentView(R.layout.activity_adress_book)