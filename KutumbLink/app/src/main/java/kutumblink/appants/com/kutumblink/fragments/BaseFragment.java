package kutumblink.appants.com.kutumblink.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.utils.Constants;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DatabaseHandler dbHandler;
    private OnFragmentInteractionListener mListener;

    public HomeActivity activity=null;
    protected FragmentActivity mActivity;

    public static LinearLayout ll_actions=null;

    public BaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BaseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseFragment newInstance(String param1, String param2) {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dbHandler=new DatabaseHandler(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity=(HomeActivity)context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    protected void makeToast(String text) {
        Toast toast = Toast.makeText(getActivity().getApplicationContext(),text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




        protected  void showConfirmDialog(String title, String message, final boolean needGoBack) {
            android.support.v7.app.AlertDialog.Builder builder = new  android.support.v7.app.AlertDialog.Builder(getContext());


           // builder.setIcon(R.mipmap.ic_launcher);
            if(title!=null && !title.equals(""))
         builder.setTitle(title);
            /*LayoutInflater inflater = (mActivity).getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.dialog_customeview,null));*/
        StringBuffer sb = new StringBuffer(message);


        builder.setMessage(sb.toString()).setCancelable(false);
        builder.setPositiveButton(Html.fromHtml("<font color='#0AAEEF'>OK</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        //getFragmentManager().popBackStack();

                        if(mActivity!=null) {
                            View view = mActivity.getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }


                            if (needGoBack) {
                                mActivity.onBackPressed();
                            }
                        }
                    }

                }

        );



        builder.show();
    }





    protected  void showConfirmDialogActions(String title,String message) {
        android.support.v7.app.AlertDialog.Builder builder = new  android.support.v7.app.AlertDialog.Builder(getContext());

       // builder.setIcon(R.mipmap.ic_launcher);
       // builder.setTitle(title);
        StringBuffer sb = new StringBuffer(message);
        //LayoutInflater inflater = (mActivity).getLayoutInflater();
        //builder.setView(inflater.inflate(R.layout.dialog_customeview,null));

        builder.setMessage(sb.toString()).setCancelable(false);
        builder.setPositiveButton(Html.fromHtml("<font color='#0AAEEF'>OK</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //getFragmentManager().popBackStack();
                        if(mActivity!=null) {
                            View view = mActivity.getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                        }
                      //  getActivity().onBackPressed();
                    }
                }

        );

        builder.show();
    }





    public void showConfirmOptionsDialog(String title, String message, final int type, final String params) {
        AlertDialog.Builder builder = new  AlertDialog.Builder(getContext());


         builder.setTitle(title);
        //builder.setIcon(R.mipmap.ic_launcher);
        StringBuffer sb = new StringBuffer(message);
        //LayoutInflater inflater = (mActivity).getLayoutInflater();
        //builder.setView(inflater.inflate(R.layout.dialog_customeview,null));


        builder.setMessage(sb.toString()).setCancelable(false);
        builder.setPositiveButton(Html.fromHtml("<font color='#0AAEEF'>OK</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(type==1){
                            Constants.NAV_GROUPS=100;
                            dbHandler.DeleteTable(dbHandler.TABLE_GROUP, "G_NAME='" + params+ "'");
                            dbHandler.DeleteTable("TBL_PHONE_CONTACTS","Phone_Contact_Gid='"+params+"'");
                            FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
                            FragmentTransaction ft=fragmentManager.beginTransaction();
                            ft.replace(R.id.main_container, new GroupsMainFragment());
                            ft.commit();
                            //showConfirmDialog("","Contacts(s) copied successfully.",false);
                        }else if(type==2){
                            dbHandler.DeleteTable(DatabaseHandler.TABLE_EVENTS, "evt_title='" + params + "'");
                            FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            ft.replace(R.id.main_container, new EventsMainFragment());
                            ft.commit();
                        }else if(type==3){
                            for(int i=0;i<GroupContactsFragment.arr_contacts.size();i++){
                                if(GroupContactsFragment.arr_contacts.get(i).getIS_CONTACT_SELECTED()==1){
                                    dbHandler.DeleteTable("TBL_PHONE_CONTACTS", "Phone_Contact_Gid='" + Constants.GROUP_NAME + "' AND Phone_Contact_ID='"+GroupContactsFragment.arr_contacts.get(i).getConatactId()+"'");

                                }


                            }
                            showConfirmDialog("",getString(R.string.remove_successfully),true);

                           /* FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            ft.replace(R.id.main_container, new GroupContactsFragment());
                            ft.commit();

                            Constants.NAV_GROUPS = 100;*/
                        }


                    }
                }

        );
        builder.setNegativeButton(Html.fromHtml("<font color='#0AAEEF'>CANCEL</font>"), null);
        AlertDialog dialog =builder.show();
        /*TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);*/




    }


    public void ShowCustomOptionDialog(String title, String message, final int type, final String params)
    {

        // custom dialog
        final Dialog dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.dialog_customeview);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        TextView textView = (TextView) dialog.findViewById(R.id.title);
        textView.setText(title);

        TextView messageView = (TextView) dialog.findViewById(R.id.message);
        messageView.setText(message);

        Button dialogButton = (Button) dialog.findViewById(R.id.okay);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (FragmentActivity) activity;
    }





}
