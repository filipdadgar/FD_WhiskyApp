package com.filipdadgar.fd_whiskyapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.view.View
import android.widget.AdapterView
import com.filipdadgar.fd_whiskyapp.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type

class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		ViewCompat.setOnApplyWindowInsetsListener(binding.resultTextView) { v, insets ->
			val systemBars = insets.getInsets(Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}

		val whiskies = resources.getStringArray(R.array.whiskies)
		val prices = resources.getStringArray(R.array.prices)
		val artists = resources.getStringArray(R.array.artists)
		val sizes = resources.getStringArray(R.array.sizes)

		val whiskyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, whiskies)
		whiskyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		binding.whiskySpinner.adapter = whiskyAdapter

		val artistAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, artists)
		artistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		binding.artistSpinner.adapter = artistAdapter

		val sizeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sizes)
		sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		binding.sizeSpinner.adapter = sizeAdapter

		fun printInfo(whiskyIndex: Int, artistIndex: Int, sizeIndex: Int) {
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
				binding.resultTextView.text = "Du har valt $whisky.\nPris per cl: $pricePerCl kr.\nStorlek: $sizeCl cl.\nTotalpris: $totalPrice kr.\nArtist: $artist."
				// Convert names to valid resource names
				val whiskyResName = whisky.lowercase().replace(" ", "_").replace("-", "_")
				val artistResName = artist.lowercase().replace(" ", "_").replace("-", "_")
				val whiskyImageRes = resources.getIdentifier(whiskyResName, "drawable", packageName)
				if (whiskyImageRes != 0) binding.whiskyImageView.setImageResource(whiskyImageRes)
				else binding.whiskyImageView.setImageDrawable(null)
				val artistImageRes = resources.getIdentifier(artistResName, "drawable", packageName)
				if (artistImageRes != 0) binding.artistImageView.setImageResource(artistImageRes)
				else binding.artistImageView.setImageDrawable(null)
			} else {
				binding.resultTextView.text = getString(R.string.printText)
				binding.whiskyImageView.setImageDrawable(null)
				binding.artistImageView.setImageDrawable(null)
			}
		}

		binding.whiskySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onNothingSelected(parent: AdapterView<*>?) {}
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				printInfo(position, binding.artistSpinner.selectedItemPosition, binding.sizeSpinner.selectedItemPosition)
			}
		}
		binding.artistSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onNothingSelected(parent: AdapterView<*>?) {}
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				printInfo(binding.whiskySpinner.selectedItemPosition, position, binding.sizeSpinner.selectedItemPosition)
			}
		}
		binding.sizeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onNothingSelected(parent: AdapterView<*>?) {}
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				printInfo(binding.whiskySpinner.selectedItemPosition, binding.artistSpinner.selectedItemPosition, position)
			}
		}
	}
}