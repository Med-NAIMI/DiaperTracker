package com.example.dbdiapertracker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.dbdiapertracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //data binding object that will hold views
    lateinit var binding:ActivityMainBinding

    //declaring references to editable layouts
    lateinit var tracksTextView: TextView
    lateinit var counterTextView: TextView
    lateinit var timeEditText: EditText

    //neded to check isChecked
    lateinit var dirtyRadioButton: RadioButton
    lateinit var dryRadioButton: RadioButton
    lateinit var wetRadioButton: RadioButton

    //variable accessile via layout.xml
    var type="Get it done!"

    var diaperCounter=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        //inflating layout binding
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        //Declaring the variable in layout.xml to binding
        binding.myActivity=this

        //references to buttons
        val addButton: Button =binding.mainActivityBtAddDiaper
        val clearButton: Button =binding.mainActivityBtClearDiapers
        dirtyRadioButton=binding.mainActivityRbDirty
        dryRadioButton=binding.mainActivityRbDry
        wetRadioButton=binding.mainActivityRbWet

        //instantiating references to editable UIs
        tracksTextView=binding.mainActivityTvTracks
        counterTextView=binding.mainActivityTvDiapersCounter
        timeEditText=binding.mainActivityEtTime

        //Triggering button's events
        addButton.setOnClickListener { addEntry() }
        clearButton.setOnClickListener { clearEntries() }
    }
    private fun addEntry() {
        //build our entries

        //time
        var changeDiaperAt="00:00"
        var stringTimeEditText=timeEditText.text.toString()


        if(stringTimeEditText!=""){
            changeDiaperAt=stringTimeEditText
        }

        //type
        var tracksText:String
        if(dirtyRadioButton.isChecked()){
            tracksText="Dirty diaper changed at $changeDiaperAt"
            type="Dirty"

        }else if(dryRadioButton.isChecked()){
            tracksText="Dry diaper changed at $changeDiaperAt"
            type="Dry"
        }else{
            tracksText="Wet diaper changed at $changeDiaperAt"
            type="Wet"
        }
        //invalidate all binding expressions
        binding.apply { invalidateAll() }

        //incrementing counter
        diaperCounter++

        //updating the UI
        updateUI(tracksText)
    }

    private fun updateUI(newTrack: String) {
        var oldTracks=tracksTextView.text.toString()
        tracksTextView.text="$oldTracks \n $newTrack"

        //Counter
        counterTextView.text="The numer of changed diapers is: $diaperCounter"

        //hide keyboard
        hideKayboard()

        //reset time input
        timeEditText.setText("")

    }

    private fun hideKayboard() {
        val imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(dryRadioButton.windowToken,0)
    }


    private fun clearEntries() {
        tracksTextView.text=""
        counterTextView.text="The number of changed diapers is: 0"
        dryRadioButton.setChecked(true)
        diaperCounter=0
        type="Start!"
        binding.apply { invalidateAll() }
    }

}