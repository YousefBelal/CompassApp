package com.example.compassapp

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compassapp.ui.theme.CompassAppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sucho.placepicker.AddressData
import com.sucho.placepicker.Constants
import com.sucho.placepicker.Constants.GOOGLE_API_KEY
import com.sucho.placepicker.MapType
import com.sucho.placepicker.PlacePicker

val LocalActivity = staticCompositionLocalOf<Activity> {
    error("CompositionLocal LocalActivity not present")
}

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompassAppTheme {
                CompositionLocalProvider(LocalActivity provides this) {
                    // A surface container using the 'background' color from the theme
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Black)
                    ) {
                        start_sheet2()

                    }
                }
            }
            }
        }

    }


@Destination(start=true)
@ExperimentalMaterialApi
@Composable
fun mainB(){

    val scope = rememberCoroutineScope()

    val bottomSheetScaffoldState1 = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    BottomSheetScaffold(
        sheetShape= RoundedCornerShape(topEnd =40.dp , topStart =40.dp ),
        sheetBackgroundColor= Color(0xFF0D1920),
        scaffoldState = bottomSheetScaffoldState1,
        sheetPeekHeight = 56.dp,
        sheetContent = {
            LazyColumn() {
                item {
                    sheet_button()
                }
                item(){
                    SimpleRadioButtonComponent()
                }
                item {
                    addLocation_btn()
                }
            }
        }
    ) {
    }
}

@Composable
fun sheet_button(){
    Row(modifier=Modifier.padding(horizontal = 180.dp)) {
        Icon(painter = painterResource(id = R.drawable.ic_drag_handle), contentDescription = "",
            modifier= Modifier
                .size(40.dp),
            tint = Color.White
        )
    }
}

@Composable
fun SimpleRadioButtonComponent() {
    val radioOptions = listOf("AL Qibla ")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) }
                        )
                        .padding(horizontal = 32.dp)
                        .border(3.dp, Color(36, 83, 175), shape = RoundedCornerShape(10.dp)),
                    horizontalArrangement=Center,
                    verticalAlignment=CenterVertically
                ) {
                    val context = LocalContext.current
                    RadioButton(
                        selected = (text == selectedOption),
                        modifier = Modifier.padding(all = Dp(value = 6F)),
                        onClick = {
                            onOptionSelected(text)
                            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                        },
                        colors = RadioButtonDefaults.colors(Color.White)
                    )
                    Text(
                        text = text,
                        fontSize=16.sp,
                        color= Color.White,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun addLocation_btn(){
    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

                if (result.resultCode == Activity.RESULT_OK) {
                    val addressData =
                        result.data?.getParcelableExtra<AddressData>(Constants.ADDRESS_INTENT)
                }

        }
    val activity = LocalActivity.current

    Row(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 32.dp),
        horizontalArrangement=Center,
        verticalAlignment=CenterVertically
    ){
        Box(
            Modifier
                .width(40.dp)
                .height(40.dp)
                .background(color = Color(36, 83, 175), shape = RoundedCornerShape(10.dp))
                .clickable(
                    onClick = {

                        val intent = PlacePicker
                            .IntentBuilder()
                            .setLatLong(
                                40.748672,
                                -73.985628
                            )  // Initial Latitude and Longitude the Map will load into
                            .showLatLong(true)  // Show Coordinates in the Activity
                            .setMapZoom(12.0f)  // Map Zoom Level. Default: 14.0
                            .setAddressRequired(true) // Set If return only Coordinates if cannot fetch Address for the coordinates. Default: True
                            .hideMarkerShadow(true) // Hides the shadow under the map marker. Default: False
                            .setMarkerDrawable(R.drawable.mark) // Change the default Marker Image
                            .setMarkerImageImageColor(R.color.colorPrimary)
//                            .setFabColor(R.color.fabColor)
//                            .setPrimaryTextColor(R.color.primaryTextColor) // Change text color of Shortened Address
//                            .setSecondaryTextColor(R.color.secondaryTextColor) // Change text color of full Address
//                            .setBottomViewColor(R.color.bottomViewColor) // Change Address View Background Color (Default: White)
//                            .setMapRawResourceStyle(R.raw.map_style)  //Set Map Style (https://mapstyle.withgoogle.com/)
                            .setMapType(MapType.NORMAL)
//                            .setPlaceSearchBar(true, GOOGLE_API_KEY) //Activate GooglePlace Search Bar. Default is false/not activated. SearchBar is a chargeable feature by Google
                            .onlyCoordinates(true)  //Get only Coordinates from Place Picker
                            .build(activity)
                        startForResult.launch(intent)
                    }
                )
        ){
            Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = "",
                modifier= Modifier
                    .size(40.dp),
                tint = Color.White
            )

        }

    }
}

@ExperimentalMaterialApi
@Composable
fun start_sheet2(){
    val scope = rememberCoroutineScope()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    BottomSheetScaffold(
        sheetShape= RoundedCornerShape(topEnd =40.dp , topStart =40.dp ),
        sheetBackgroundColor= Color(0xFF0D1920),
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 56.dp,
        sheetContent = {
            LazyColumn() {
                item {
                    sheet_button()
                }
                item {
                    save_location_screen()
                }

            }
        }
    ) {
    }
}

@Composable
fun save_location_screen(){
    var name by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("21.389082") }
    var longitude by remember { mutableStateOf("39.857910") }
    val text_latitude by remember { mutableStateOf("Latitude") }
    val text_longitude by remember { mutableStateOf("Longitude") }
    var location by remember { mutableStateOf("Saeed, Tanta, EG") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(horizontal = 32.dp),
            horizontalArrangement=Center,
            verticalAlignment=CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .width(317.dp)
                    .height(60.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp,
                            bottomStart = 12.dp,
                            bottomEnd = 12.dp
                        )
                    )
                    .background(color = Color.White),
                value = name,
                onValueChange = { name = it
                },
                placeholder = {
                    Text(text = "Enter Location Label", style = TextStyle(fontSize = 18.sp, color = Color.Black))},
                colors=TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color(36, 83, 175),
                    focusedBorderColor = Color(36, 83, 175),
                    textColor = Color.Black, cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 12.dp)

            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .height(45.dp)
                .padding(horizontal = 32.dp),
                horizontalArrangement=Arrangement.SpaceAround,
                verticalAlignment=CenterVertically
        ){
            Text(
                text=latitude+"\n"+text_latitude,
                color=Color.Gray,
                fontSize=17.sp,
                fontWeight= FontWeight.Bold,
            )

            Text(
                text=longitude+"\n"+text_longitude,
                color=Color.Gray,
                fontSize=17.sp,
                fontWeight= FontWeight.Bold,
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .height(45.dp)
                .padding(horizontal = 32.dp),
            horizontalArrangement=Arrangement.Center,
            verticalAlignment=CenterVertically
        ){
            Text(
                text=location,
                color=Color.Gray,
                fontSize=24.sp,
                fontWeight= FontWeight.Normal,
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(horizontal = 32.dp),
            horizontalArrangement=Arrangement.Center,
            verticalAlignment=CenterVertically
        ){
            Button(
                modifier=Modifier.width(286.dp),
                onClick ={},
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(36,83,175)),
                contentPadding = PaddingValues(14.dp),
                shape = CircleShape,
                elevation = ButtonDefaults.elevation(6.dp),
                content = {
                    Text(
                        text = "Create new location",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                    )
        })

    }
}}


//@ExperimentalMaterialApi
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    CompassAppTheme {
//        mainB()
//    }
//}}