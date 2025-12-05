package com.app.agiliwell.ui.composables.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.agiliwell.R
import com.app.agiliwell.ui.theme.AgiliwellTheme

@Composable
fun OnboardingScreen(
    modifier: Modifier =Modifier,
    navigateToUserDetails : () -> Unit
) {
    Scaffold {padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 20.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 30.sp
                ),
                fontWeight = FontWeight(400),
                text = stringResource(R.string.onboarding_title)
            )
            Card(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 40.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
            ) {
                Image(
                    modifier = Modifier.padding(10.dp),
                    painter =  painterResource(id = R.drawable.refreshing_beverage),
                    contentDescription =null
                )
            }

            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 40.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp
                ),
                fontWeight = FontWeight(300),
                text = stringResource(R.string.onbaording_meesage)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = { navigateToUserDetails() },
                elevation = ButtonDefaults.elevatedButtonElevation( disabledElevation = 50.dp)
            ) {
                Text(text = stringResource(id = R.string.proceed))
                Icon(imageVector = Icons.AutoMirrored.Filled.NavigateNext, contentDescription = stringResource(
                    id = R.string.proceed
                ) )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun  PreviewWelcomeScreen(){
    AgiliwellTheme {
        OnboardingScreen(){
            
        }
    }
}