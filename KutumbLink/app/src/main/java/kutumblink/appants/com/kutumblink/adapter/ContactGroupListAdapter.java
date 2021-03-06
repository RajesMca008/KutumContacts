package kutumblink.appants.com.kutumblink.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.fragments.GroupContactsFragment;
import kutumblink.appants.com.kutumblink.model.GroupDo;

/**
 * Created by Vishnu on 18-05-2016.
 */
public class ContactGroupListAdapter extends BaseAdapter {

    ArrayList itemsList;
    private Context context;
    GroupDo adb;
    boolean[] checkBoxState;
    //Constructor to initialize values
    public ContactGroupListAdapter(Context context, ArrayList itemsList) {



        this.context = context;
        this.itemsList=itemsList;
        checkBoxState=new boolean[itemsList.size()];
    }

    @Override
    public int getCount() {

        // Number of times getView method call depends upon gridValues.length
        return itemsList.size();
    }


    @Override
    public Object getItem(int position) {
        return itemsList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }


    // Number of times getView method call depends upon gridValues.length

    public View getView(final int position, View convertView, ViewGroup parent) {

        //LayoutInflator to call external grid_item.xml file
        try {
            adb = (GroupDo) getItem(position);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        if (convertView == null) {

           // view = new View(context);

            // get layout from grid_item.xml
            convertView = inflater.inflate(R.layout.inflate_contactlist, null);
            TextView tv_contactName=(TextView)convertView.findViewById(R.id.tv_contactName);
            RelativeLayout rl_contacts=(RelativeLayout)convertView.findViewById(R.id.rl_contacts);
            final ImageView cb_conatacts=(ImageView)convertView.findViewById(R.id.cb_contacts);
            Button btn_phone=(Button)convertView.findViewById(R.id.btn_phone);
            final Button btn_email=(Button)convertView.findViewById(R.id.btn_email);
            tv_contactName.setText(""+adb.getGroup_Name());
            btn_phone.setVisibility(View.GONE);
            btn_email.setVisibility(View.GONE);



            rl_contacts.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    if(!checkBoxState[position]) {
                        checkBoxState[position] = true;
                        //Log.v("Email....","....EMAIL....Y."+GroupContactsFragment.arr_contacts.get(position).getConatactEmail());

                        cb_conatacts.setBackgroundResource(R.drawable.radio_btn_selected);
                        GroupContactsFragment.arr_group.get(position).setGroup_isSELECT(1);


                    } else {
                       // Log.v("Email....","....EMAIL....N."+GroupContactsFragment.arr_contacts.get(position).getConatactEmail());

                        cb_conatacts.setBackgroundResource(R.drawable.radio_btn);
                        checkBoxState[position] = false;
                        GroupContactsFragment.arr_group.get(position).setGroup_isSELECT(0);


                    }

                }
            });

            // set value into textview

        }



        return convertView;
    }


}
