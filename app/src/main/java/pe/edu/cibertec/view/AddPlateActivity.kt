package pe.edu.cibertec.view

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pe.edu.cibertec.R
import pe.edu.cibertec.databinding.ActivityAddPlateBinding
import pe.edu.cibertec.dto.AddPlateReq
import pe.edu.cibertec.viewModel.AddPlateViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AddPlateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPlateBinding
    private val viewModel: AddPlateViewModel by viewModels()

    val selectMedia = registerForActivityResult(PickVisualMedia()){uri ->
        if(uri == null){
            println("------------>NULL")
        }else{
            binding.ivPrevisualization.setImageURI(uri)
            imgUri = uri

        }
    }

    lateinit var imgUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPlateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            btnAdd.setOnClickListener{
                val namePlate = RequestBody.create(MediaType.parse("txt/plain"), etName.text.toString())
                val description = RequestBody.create(MediaType.parse("txt/plain"), etDescription.text.toString())
                val price = RequestBody.create(MediaType.parse("txt/plain"), etPrice.text.toString())

                val imgFile = File(uriToFile(this@AddPlateActivity, imgUri)!!.path)
//                val requestBody = RequestBody.create(MediaType.parse("image/*"), imgFile)
                val requestBody = RequestBody.create(MediaType.parse(contentResolver.getType(imgUri)), imgFile)

                val photo = MultipartBody.Part.createFormData("photo", imgFile.name, requestBody)

                lifecycleScope.launch {
                    val res = viewModel.addPlate(
                        AddPlateReq(
                            namePlate = namePlate,
                            descriptionPlate = description,
                            price = price,
                            photo = photo
                        )
                    )

                    res?.let{
                        if(res.err) Toast.makeText(this@AddPlateActivity, res.msg, Toast.LENGTH_LONG).show()
                        else {
                            Toast.makeText(applicationContext, res.msg, Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@AddPlateActivity, MenuActivity::class.java))
                        }
                    }

                }
            }

            viewModel.state.observe(this@AddPlateActivity){
                pbAddPlate.visibility = if(it.loading) View.VISIBLE else View.GONE
            }

        }



    }

    override fun onResume() {
        super.onResume()

        binding.btnSelectImage.setOnClickListener{
            selectMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }
    }


    fun uriToFile(context: Context, uri: Uri): File? {
        return try {
            val file = File.createTempFile("temp", null, context.cacheDir)
            //val file = File(applicationContext.filesDir, uri.path)
            val inputStream = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            file
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}



