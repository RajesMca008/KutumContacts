package kutumblink.appants.com.kutumblink.fragments;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.onegravity.contactpicker.contact.Contact;
import com.onegravity.contactpicker.contact.ContactDescription;
import com.onegravity.contactpicker.contact.ContactSortOrder;
import com.onegravity.contactpicker.core.ContactPickerActivity;
import com.onegravity.contactpicker.picture.ContactPictureType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.model.EventsDo;
import kutumblink.appants.com.kutumblink.utils.Constants;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditEventsFragment extends Fragment {

    private static final int REQUEST_CONTACT = 0;
    int INSERT_CONTACT_REQUEST = 2;

    public EditEventsFragment() {
        // Required empty public constructor
    }

    public static EditText event_title_text;
    TextView tv_date, tv_time, tv_eventTitle, tv_desc, tv_sel_contactlist, tv_sortorder;
    Button btn_save;
    DatabaseHandler dbHandler;

    ArrayList<EventsDo> arrEvt = new ArrayList<>();
    String contactsInfo = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dbHandler = new DatabaseHandler(getActivity());
        View view = inflater.inflate(R.layout.fragment_edit_events, container, false);
        event_title_text = (EditText) view.findViewById(R.id.event_title_text);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_eventTitle = (TextView) view.findViewById(R.id.photo_title_text);
        tv_desc = (TextView) view.findViewById(R.id.photo_link_text);
        tv_sel_contactlist = (TextView) view.findViewById(R.id.select_contactstext);
        tv_sortorder = (TextView) view.findViewById(R.id.sort_order_text);
        btn_save = (Button) view.findViewById(R.id.save_btn_id);


        //  if(Constants.EVENT_OPERATIONS.equalsIgnoreCase("EDIT")){
        Bundle args = getArguments();

        if (args != null) {
            event_title_text.setText(args.getString("time"));
            tv_eventTitle.setText(args.getString("title"));
            tv_desc.setText(args.getString("desc"));
            contactsInfo = args.getString("contacts");
        }

        try {

            JSONArray jArr = new JSONArray(contactsInfo);

            for (int i = 0; i < jArr.length(); i++) {

                JSONObject jobj = jArr.getJSONObject(i);

                EventsDo evtDo = new EventsDo();
                evtDo.setEvtContacts(jobj.getString(DatabaseHandler.PHONE_CONTACT_ID));
                arrEvt.add(evtDo);
            }


            // contactsInfo=jo


        } catch (JSONException e) {

        }

        //  }


        final Collection<Long> selectContats = new ArrayList<Long>();
        tv_sel_contactlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < arrEvt.size(); i++) {
                    selectContats.add(Long.parseLong(arrEvt.get(i).getEvtContacts()));
                }
                Intent intent = new Intent(getActivity(), ContactPickerActivity.class)
                        // .putExtra(ContactPickerActivity.EXTRA_THEME, mDarkTheme ? R.style.Theme_Dark : R.style.Theme_Light)
                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                        .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                        //  .putExtra(ContactPickerActivity.EXTRA_PRESELECTED_CONTACTS,  GroupsMainFragment.arr_group)
                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.ADDRESS.name())
                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                        .putExtra(ContactPickerActivity.EXTRA_ONLY_CONTACTS_WITH_PHONE, true)

                        .putExtra(ContactPickerActivity.EXTRA_PRESELECTED_CONTACTS, (Serializable) selectContats)

                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());
                //.putExtra(ContactPickerActivity.SELECTED_CONTACTS, GroupContactsFragment.arr_contacts);

                startActivityForResult(intent, REQUEST_CONTACT);


            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  if (tv_eventTitle.getText().toString().length() != 0 && tv_desc.getText().toString().length() != 0) {
                try {


                    ContentValues cv = new ContentValues();
                    cv.put(DatabaseHandler.EVT_TITLE, tv_eventTitle.getText().toString());
                    cv.put(DatabaseHandler.EVT_DESC, tv_desc.getText().toString());

                    cv.put(DatabaseHandler.EVT_CREATED_ON, event_title_text.getText().toString());
                    // dbHandler.UpdateTable(DatabaseHandler.TABLE_EVENTS,cv,"evt_title='"+Constants.EVENTS_OLD_NAME+"'");
                    dbHandler.insert(DatabaseHandler.TABLE_EVENTS, cv);
                } catch (Exception e) {

                }

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container, new EventsMainFragment()).commit();
              /*  } else {
                    Toast.makeText(getActivity(), "Please enter the Title and description", Toast.LENGTH_LONG).show();
                }*/
            }
        });

        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.DatePickerFragment mDatePicker = new HomeActivity.DatePickerFragment();
                mDatePicker.show(getActivity().getFragmentManager(), "Select date");


            }
        });
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeActivity.TimePicker mTimePicker = new HomeActivity.TimePicker();
                mTimePicker.show(getActivity().getFragmentManager(), "Select time");
            }
        });

     /*   event_title_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });*/


        HomeActivity.ib_back.setBackgroundResource(R.mipmap.left_arrow);
        if(Constants.EVENT_OPERATIONS.equalsIgnoreCase("EDIT")){
            HomeActivity.tv_title.setText("Edit Details");
        }else {
            HomeActivity.tv_title.setText("Events");
        }
        HomeActivity.ib_back_next.setText("Edit Details");
        HomeActivity.ib_menu.setBackgroundColor(Color.TRANSPARENT);

        if (Constants.EVENT_OPERATIONS.equalsIgnoreCase("SAVE")) {
            HomeActivity.ib_menu.setText("");
        } else {
            HomeActivity.ib_menu.setText("Save");
        }



        HomeActivity.ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ContentValues cv = new ContentValues();
                cv.put(DatabaseHandler.EVT_TITLE, tv_eventTitle.getText().toString());
                cv.put(DatabaseHandler.EVT_DESC, tv_desc.getText().toString());

                cv.put(DatabaseHandler.EVT_CREATED_ON, event_title_text.getText().toString());
                dbHandler.UpdateTable(DatabaseHandler.TABLE_EVENTS, cv, "evt_title='" + Constants.EVENTS_OLD_NAME + "'");
                EventsMainFragment groupContacts = new EventsMainFragment(); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                fragmentTransaction.replace(R.id.main_container, groupContacts);
                fragmentTransaction.addToBackStack("edit_event");
                fragmentTransaction.commit();

            }
        });


        HomeActivity.ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.EVENT_OPERATIONS.equalsIgnoreCase("SAVE")) {
                    EventsMainFragment groupContacts = new EventsMainFragment(); //New means creating adding.
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("contacts", contactsInfo);
                    args.putString("desc", tv_desc.getText().toString());
                    args.putString("title", tv_eventTitle.getText().toString());
                    args.putString("time", event_title_text.getText().toString());
                    groupContacts.setArguments(args);

                    fragmentTransaction.replace(R.id.main_container, groupContacts);
                    fragmentTransaction.addToBackStack("edit_event");
                    fragmentTransaction.commit();
                } else {
                    EventActionsFragment groupContacts = new EventActionsFragment(); //New means creating adding.
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("contacts", contactsInfo);
                    args.putString("desc", tv_desc.getText().toString());
                    args.putString("title", tv_eventTitle.getText().toString());
                    args.putString("time", event_title_text.getText().toString());
                    groupContacts.setArguments(args);

                    fragmentTransaction.replace(R.id.main_container, groupContacts);
                    fragmentTransaction.addToBackStack("edit_event");
                    fragmentTransaction.commit();
                }
            }
        });

        HomeActivity.ib_back_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.EVENT_OPERATIONS.equalsIgnoreCase("SAVE")) {
                    EventsMainFragment groupContacts = new EventsMainFragment(); //New means creating adding.
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, groupContacts);
                    fragmentTransaction.addToBackStack("edit_event");
                    fragmentTransaction.commit();
                } else {
                    EventActionsFragment groupContacts = new EventActionsFragment(); //New means creating adding.
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, groupContacts);
                    fragmentTransaction.addToBackStack("edit_event");
                    fragmentTransaction.commit();
                }
            }
        });


        return view;
    }

    List<Contact> contacts;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CONTACT && resultCode == Activity.RESULT_OK &&
                data != null && data.hasExtra(ContactPickerActivity.RESULT_CONTACT_DATA)) {

            contacts = (List<Contact>) data.getSerializableExtra(ContactPickerActivity.RESULT_CONTACT_DATA);

            if (Constants.EVENT_OPERATIONS.equalsIgnoreCase("EDIT")) {


                try {

                    JSONArray jsonArray = new JSONArray();
                    for (Contact contact : contacts) {
                        JSONObject jobj = new JSONObject();
                        jobj.put(DatabaseHandler.PHONE_CONTACT_ID, "" + contact.getId());
                        jobj.put(DatabaseHandler.PHONE_CONTACT_NUMBER, "" + contact.getPhone(0));
                        jobj.put(DatabaseHandler.PHONE_CONTACT_EMAIL, "" + contact.getEmail(0));
                        jobj.put(DatabaseHandler.PHONE_CONTACT_NAME, "" + contact.getDisplayName());
                        jsonArray.put(jobj);

                    }

                    Log.v("DATA....", "DATA.....EDIT..." + jsonArray.toString());
                    ContentValues cv = new ContentValues();
                    cv.put(DatabaseHandler.EVT_TITLE, tv_eventTitle.getText().toString());
                    cv.put(DatabaseHandler.EVT_DESC, tv_desc.getText().toString());
                    cv.put(DatabaseHandler.EVT_CONTACTS, jsonArray.toString());
                    cv.put(DatabaseHandler.EVT_CREATED_ON, event_title_text.getText().toString());
                    dbHandler.UpdateTable(DatabaseHandler.TABLE_EVENTS, cv, " evt_title='" + Constants.EVENTS_OLD_NAME + "'");

                } catch (Exception e) {

                }

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container, new EventsMainFragment()).commit();

            } else if (Constants.EVENT_OPERATIONS.equalsIgnoreCase("SAVE")) {
                try {

                    JSONArray jsonArray = new JSONArray();
                    for (Contact contact : contacts) {
                        JSONObject jobj = new JSONObject();
                        jobj.put(DatabaseHandler.PHONE_CONTACT_ID, "" + contact.getId());
                        jobj.put(DatabaseHandler.PHONE_CONTACT_NUMBER, "" + contact.getPhone(0));
                        jobj.put(DatabaseHandler.PHONE_CONTACT_EMAIL, "" + contact.getEmail(0));
                        jobj.put(DatabaseHandler.PHONE_CONTACT_NAME, "" + contact.getDisplayName());

                        jsonArray.put(jobj);

                        Log.v("DATA....", "DATA.....SAVE..CONTACTID...." + contact.getId());
                    }
                    ContentValues cv = new ContentValues();
                    cv.put(DatabaseHandler.EVT_TITLE, tv_eventTitle.getText().toString());
                    cv.put(DatabaseHandler.EVT_DESC, tv_desc.getText().toString());
                    cv.put(DatabaseHandler.EVT_CONTACTS, jsonArray.toString());
                    cv.put(DatabaseHandler.EVT_CREATED_ON, event_title_text.getText().toString());
                    dbHandler.insert(DatabaseHandler.TABLE_EVENTS, cv);
                    Log.v("DATA....", "DATA.....SAVE..." + jsonArray.toString());
                } catch (Exception e) {

                }

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container, new EventsMainFragment()).commit();

            }


        } else if (requestCode == INSERT_CONTACT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {


            } else if (resultCode == Activity.RESULT_CANCELED) {
                //    Toast.makeText(getActivity(),"Contact Added Succesfully",Toast.LENGTH_SHORT).show();
            }

        }
    }
}
