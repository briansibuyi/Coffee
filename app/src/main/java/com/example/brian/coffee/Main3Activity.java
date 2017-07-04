package com.example.brian.coffee;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.brian.coffee.R.id.Cream;
import static com.example.brian.coffee.R.id.vanilla;


public class Main3Activity extends AppCompatActivity {

    public static String addCream;
    public static String addvanilla;
    public static String mycream;

    int quantity = 0;
    //Spinner spinner;
    ArrayList<String> items = new ArrayList<String>();
    ArrayAdapter<String> adpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //spinner = (Spinner)findViewById(R.id.spinner);
        //ArrayAdapter<CharSequence> adpter = ArrayAdapter.createFromResource(this, R.array.coffees, android.R.layout.simple_spinner_item);
        // spinner.setAdapter(adpter);



    }
    /*Increment method*/
    public void increment(View view)
    {
        if(quantity == 50)
        {
            return;
        }
        quantity = quantity + 1;
        display(quantity);

    }

    /*decrement method*/
    public void decrement(View view)
    {
        if(quantity == 1)
        {
            return;
        }
        quantity = quantity - 1;
        display(quantity);

    }



    /*Submit method id called when a button is clicked na dispaly */
    public void submitOrder(View view) {


        /*Name inputing*/
        EditText namefield = (EditText) findViewById(R.id.name);//cast
        String myname = namefield.getText().toString();//converting to string

        if (namefield.getText().toString().length() == 0) {

            namefield.setError("Please insert you name");

        } else {


        /*chocho check box*/
            CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.chocho);
            boolean haswhippedCream = whippedCreamCheckBox.isChecked();

            if (haswhippedCream) {
                //Add Chocolate Topping "+addWhippedCream;
                addCream = "Yes With Topping ";
            } else {
                addCream = "No Topping";
            }

        /*Cream check box*/
            CheckBox myCream = (CheckBox) findViewById(Cream);
            boolean myaddcream = myCream.isChecked();

            if (myaddcream) {
                //Add Chocolate Topping "+addWhippedCream;
                mycream = "Yes With Topping ";
            } else {
                mycream = "No Topping";
            }


            //Spinner
            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            String mySp = spinner.getSelectedItem().toString();

            if (spinner.getSelectedItem().toString().trim().equals("Select Coffee")) {
                Toast.makeText(getApplicationContext(), "Please Select A Coffee", Toast.LENGTH_SHORT).show();
            } else {


        /*Vanilla check box*/
                CheckBox vanilla = (CheckBox) findViewById(R.id.vanilla);
                boolean myaddvanilla = vanilla.isChecked();

                if (myaddvanilla) {
                    //Add Chocolate Topping "+addWhippedCream;
                    addvanilla = "Yes With Topping ";
                } else {
                    addvanilla = "No Topping";
                }

        /*Order processing*/
                int price = calculatePrice(haswhippedCream, myaddcream, myaddvanilla);
                String priceMessage = createOrderSummary(myname, mySp, price, addCream, mycream, addvanilla);
                //displayMessage(priceMessage);

        /*calling email intent/form*/
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Coffe Order :" + myname);
                intent.putExtra(Intent.EXTRA_TEXT, priceMessage
                );
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        }
    }
    private void display(int number){
        TextView quantityTextbview = (TextView)findViewById(R.id.textView6);
        quantityTextbview.setText(""+ number);
    }
    /*This method display the price after an order*/
    private  void displayPrice(int number)
    {
        TextView priceTextview = (TextView)findViewById(R.id.price_text_view);
        priceTextview.setText(NumberFormat.getCurrencyInstance().format(number));
    }


    /*This method display the massege after an onder*/
//    private  void   displayMessage(String message)
//    {
//        /*referencing the textview*/
//        TextView messsageprice = (TextView)findViewById(R.id.price_text_view);
//        messsageprice.setText(message);
//    }


    /*@return total price*/
    /*@pramm to check if user wants WhiteCream topping or not*/
    /*@pramm to check if user wants addCream topping or not*/
    /*@pramm to check if user wants vanilla topping or not*/

    private int calculatePrice(boolean haswhippedCream ,boolean addcream,boolean addvanilla)
    {
        /*price of 1 Cup of coffee*/
        int baseprice = 5;

        /*@adding R3 if a user wants Chocholate topping*/
        if(haswhippedCream){
            baseprice = baseprice + 3;
        }

         /*@adding R2 if a user wants White cream topping*/
        if(addcream){
            baseprice = baseprice + 2;
        }

         /*@adding R2 if a user wants Vanilla topping*/
        if(addvanilla){
            baseprice = baseprice + 2;
        }
         /*Calculating the total order price by multiplying by quantity*/
        return quantity * baseprice;


    }

    public  void getCoffee(){
        Spinner sp = (Spinner)findViewById(R.id.spinner);
        sp.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
    }

    /*Order summary*/

    private String createOrderSummary(String myname,String sp, int price,String addCream,String mycream, String addvanilla)
    {
        String priceMessage = "Name: "+myname;
        priceMessage +="\ncoffee type: "+ sp;
        priceMessage +="\nAdd Chocolate Topping: " +addCream;
        priceMessage +="\nAdd white Cream Topping: "+mycream;
        priceMessage +="\nAdd Vanilla Topping :"+addvanilla;
        priceMessage +="\nQuantity :"+quantity;
        priceMessage +="\nTotal: R "+price;
        priceMessage +="\nThank You!!!";
        priceMessage +="\n";
        priceMessage +="\n";
        return  priceMessage;

    }


}