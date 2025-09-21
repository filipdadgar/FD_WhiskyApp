package com.filipdadgar.fd_whiskyapp

import android.os.Bundle
import android.widget.Spinner
import android.widget.TextView
import android.widget.ImageView
import android.widget.ArrayAdapter
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.resultTextView)) { v, insets ->
			val systemBars = insets.getInsets(Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}

		val whiskies = resources.getStringArray(R.array.whiskies)
		val prices = resources.getStringArray(R.array.prices)
		val artists = resources.getStringArray(R.array.artists)
		val sizes = resources.getStringArray(R.array.sizes)

		val resultTextView = findViewById<TextView>(R.id.resultTextView)
		val whiskySpinner: Spinner = findViewById(R.id.whiskySpinner)
		val artistSpinner: Spinner = findViewById(R.id.artistSpinner)
		val sizeSpinner: Spinner = findViewById(R.id.sizeSpinner)
		val whiskyImageView: ImageView = findViewById(R.id.whiskyImageView)
		val artistImageView: ImageView = findViewById(R.id.artistImageView)

		val whiskyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, whiskies)
		whiskyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		whiskySpinner.adapter = whiskyAdapter

		val artistAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, artists)
		artistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		artistSpinner.adapter = artistAdapter

		val sizeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sizes)
		sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		sizeSpinner.adapter = sizeAdapter

		fun printInfo(tView: TextView, whiskyIndex: Int, artistIndex: Int, sizeIndex: Int) {
			val whiskies = resources.getStringArray(R.array.whiskies)
			val prices = resources.getStringArray(R.array.prices)
			val artists = resources.getStringArray(R.array.artists)
			val sizes = resources.getStringArray(R.array.sizes)
			if (whiskyIndex > 0) {
				val whisky = whiskies[whiskyIndex]
				val pricePerCl = prices[whiskyIndex].toInt()
				val artist = artists[artistIndex]
				val sizeCl = sizes[sizeIndex].toInt()
				val totalPrice = pricePerCl * sizeCl
				tView.text = "Du har valt $whisky.\nPris per cl: $pricePerCl kr.\nStorlek: $sizeCl cl.\nTotalpris: $totalPrice kr.\nArtist: $artist."
				// Set images if available
				val whiskyImageRes = resources.getIdentifier("whisky_${whiskyIndex}", "drawable", packageName)
				if (whiskyImageRes != 0) whiskyImageView.setImageResource(whiskyImageRes)
				else whiskyImageView.setImageDrawable(null)
				val artistImageRes = resources.getIdentifier("artist_${artistIndex}", "drawable", packageName)
				if (artistImageRes != 0) artistImageView.setImageResource(artistImageRes)
				else artistImageView.setImageDrawable(null)
			} else {
				tView.text = getString(R.string.printText)
				whiskyImageView.setImageDrawable(null)
				artistImageView.setImageDrawable(null)
			}
		}

		whiskySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onNothingSelected(parent: AdapterView<*>?) {}
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				printInfo(resultTextView, position, artistSpinner.selectedItemPosition, sizeSpinner.selectedItemPosition)
			}
		}
		artistSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onNothingSelected(parent: AdapterView<*>?) {}
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				printInfo(resultTextView, whiskySpinner.selectedItemPosition, position, sizeSpinner.selectedItemPosition)
			}
		}
		sizeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onNothingSelected(parent: AdapterView<*>?) {}
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				printInfo(resultTextView, whiskySpinner.selectedItemPosition, artistSpinner.selectedItemPosition, position)
			}
		}
	}
}