/*
 * Copyright (c) 2024 Kodeco Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.yourcompany.android.concurrency

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourcompany.android.concurrency.model.DogBreed
import com.yourcompany.android.concurrency.model.dogBreeds
import com.yourcompany.android.concurrency.ui.theme.ConcurrencyTheme
import com.yourcompany.android.concurrency.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Switch to AppTheme for displaying the activity
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContent {
            ConcurrencyTheme {
                SearchScreen()
            }
        }
    }
}

@Composable
private fun SearchScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<MainViewModel>()

    var searchTerm by remember { mutableStateOf("") }
    var shouldBlock by remember { mutableStateOf(true) }

    var isError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    var dogs by remember { mutableStateOf(emptyList<DogBreed>()) }

    LaunchedEffect(key1 = Unit) {
        viewModel.searchDogBreeds("") { result ->
            result
                .fold(
                    onSuccess = { dogBreeds ->
                        isError = false
                        dogs = dogBreeds
                    },
                    onFailure = { isError = true }
                )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(horizontal = 12.dp),
    ) {
        Spacer(modifier = Modifier.height(54.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchTerm,
            onValueChange = { newValue ->
                searchTerm = newValue.trim()
                viewModel.searchDogBreeds(searchTerm) { result ->
                    result
                        .fold(
                            onSuccess = { dogBreeds ->
                                isError = false
                                dogs = dogBreeds
                            },
                            onFailure = { isError = true }
                        )
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Crossfade(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            targetState = isLoading,
            label = "content",
        ) { state ->
            if (state) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            } else {
                if (isError) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = "Error")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(items = dogs, key = DogBreed::id) { dogBreed ->
                            Text(modifier = Modifier.height(50.dp), text = dogBreed.name, textAlign = TextAlign.Center, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}
