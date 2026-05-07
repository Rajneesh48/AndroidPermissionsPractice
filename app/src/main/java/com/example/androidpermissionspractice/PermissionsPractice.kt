package com.example.androidpermissionspractice

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil3.compose.AsyncImage

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showSystemUi = true)
@Composable
fun Permissions(){
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val checkPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){
        if (it){
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    val pickGallery = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){
        imageUri = it
    }


    Column(modifier = Modifier.fillMaxSize()
        .padding(10.dp)
    , verticalArrangement = Arrangement.Center
    , horizontalAlignment = Alignment.CenterHorizontally) {

        if(imageUri == null){
            Image(painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "",
                modifier = Modifier.size(200.dp))
        }else {
            AsyncImage(
                model = imageUri,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                modifier = Modifier.size(200.dp),
                contentDescription = ""
            )
        }
        ElevatedButton(onClick = {
            if (checkpermission(context)){
                pickGallery.launch("image/*")
            }
            else{
                checkPermission.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
    })
         {
            Text(text = "OpenGallery")
        }
    }
}
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun checkpermission(context: Context): Boolean{
   return ContextCompat.checkSelfPermission(context,
       Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
}
