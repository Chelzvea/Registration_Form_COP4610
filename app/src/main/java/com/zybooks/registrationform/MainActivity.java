package com.zybooks.registrationform;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                reset();
            }
        });


        //Submission button

        Button submitButton= findViewById(R.id.Submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view){
                // Perform validation checks
                if (checkInputs()) {
                    // If validation passes, show a success message and reset text color
                    Toast.makeText(getApplicationContext(), "Information stored in the database.", Toast.LENGTH_SHORT).show();
                    resetTextColor();
                }

                }
            });


        }




    // this handles the reset button
    private void reset() {

        // text fields
        EditText editMicrochipID = findViewById(R.id.EditMicrochipID);
        EditText editName = findViewById(R.id.EditName);
        EditText editEmail = findViewById(R.id.EditEmail);
        EditText editAccessCode = findViewById(R.id.EditAccessCode);

        editMicrochipID.setText("");
        editEmail.setText("");
        editName.setText("");
        editAccessCode.setText("");


        // check bock
        CheckBox NeuteredCheckBox = findViewById(R.id.CheckNeutered);
        NeuteredCheckBox.setChecked(false);

    }


private boolean checkInputs(){

    boolean isValid=true;

        String microchipId = ((EditText) findViewById(R.id.EditMicrochipID)).getText().toString();
        String accessCode = ((EditText) findViewById(R.id.EditAccessCode)).getText().toString();
        String confirmCode = ((EditText) findViewById(R.id.editConfirmCode)).getText().toString();
        String email = ((EditText) findViewById(R.id.EditEmail)).getText().toString();
     //   String breed = ((Spinner) findViewById(R.id.breedSpinner)).getSelectedItem().toString();





    // Check for empty or unselected fields
    if (microchipId.isEmpty() || accessCode.isEmpty() || confirmCode.isEmpty() || email.isEmpty()) { //|| breed.isEmpty()) {
        displayError("All fields are required.");
        isValid = false;
        setLabelTextColor(R.id.TextMicrochipID, Color.RED);
        setLabelTextColor(R.id.TextAccessCode, Color.RED);
        setLabelTextColor(R.id.textConfirmCode, Color.RED);
        setLabelTextColor(R.id.TextEmail, Color.RED);
    } else {
        setLabelTextColor(R.id.TextMicrochipID, Color.BLACK);
        setLabelTextColor(R.id.TextAccessCode, Color.BLACK);
        setLabelTextColor(R.id.textConfirmCode, Color.BLACK);
        setLabelTextColor(R.id.TextEmail, Color.BLACK);
        // Reset label text color to black for other fields
    }


    // Check if Access Code and Confirm Access Code match
    if (!accessCode.equals(confirmCode)) {
        displayError("Access Code and Confirm Access Code do not match.");
        isValid = false;
    }

    // Check for a valid email format
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        displayError("Invalid email format.");
        isValid = false;
    }

    // Check the <prefix> length and <domain type>
    String[] emailParts = email.split("@");
    if (emailParts.length != 2 || emailParts[0].length() < 3) {
        displayError("Invalid email format.");
        isValid = false;
    } else {
        String[] domainParts = emailParts[1].split("\\.");
        String domainType = domainParts[domainParts.length - 1];
        if (!domainType.equals("edu") && !domainType.equals("co") && !domainType.equals("com") && !domainType.equals("gal")) {
            displayError("Invalid domain type.");
            isValid = false;
        }
    }



    // Check Microchip ID format and existence in the array
    if (microchipId.length() < 5 || microchipId.length() > 15 || !microchipId.matches("^[a-zA-Z0-9]+$")) {
        displayError("Invalid Microchip ID format.");
        isValid = false;
    } else if (!Arrays.asList(getResources().getStringArray(R.array.chips)).contains(microchipId)) {
        displayError("Microchip ID does not exist in the database.");
        isValid = false;
    }

    return isValid;
}

    private void setLabelTextColor(int labelID, int red) {
            ((TextView) findViewById(labelID)).setTextColor(red);
        }
    

    private void displayError(String errorMessage) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }


    private void resetTextColor() {
        // Change the text color of the corresponding fields back to the default color
        ((EditText) findViewById(R.id.EditMicrochipID)).setTextColor(Color.BLACK);
        ((EditText) findViewById(R.id.EditAccessCode)).setTextColor(Color.BLACK);
        ((EditText) findViewById(R.id.editConfirmCode)).setTextColor(Color.BLACK);
        ((EditText) findViewById(R.id.EditEmail)).setTextColor(Color.BLACK);
        // Other field resets can be added here
    }

}// end of main




