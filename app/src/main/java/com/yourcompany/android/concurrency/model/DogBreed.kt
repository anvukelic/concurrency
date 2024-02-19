package com.yourcompany.android.concurrency.model

import java.util.UUID

data class DogBreed(
    val id: String,
    val name: String,
    val thumbnailUrl: String,
)

val dogBreeds = listOf(
    DogBreed(id = UUID.randomUUID().toString(), name = "Samoyed", thumbnailUrl = ""),
    DogBreed(id = UUID.randomUUID().toString(), name = "German Shepherd", thumbnailUrl = ""),
    DogBreed(id = UUID.randomUUID().toString(), name = "Husky", thumbnailUrl = ""),
    DogBreed(id = UUID.randomUUID().toString(), name = "Corgi", thumbnailUrl = ""),
    DogBreed(id = UUID.randomUUID().toString(), name = "Shiba inu", thumbnailUrl = ""),
    DogBreed(id = UUID.randomUUID().toString(), name = "Akita inu", thumbnailUrl = ""),
    DogBreed(id = UUID.randomUUID().toString(), name = "Australian Shepherd", thumbnailUrl = ""),
    DogBreed(id = UUID.randomUUID().toString(), name = "Maltese", thumbnailUrl = ""),
    DogBreed(id = UUID.randomUUID().toString(), name = "Pomeranian", thumbnailUrl = ""),
    DogBreed(id = UUID.randomUUID().toString(), name = "Mix", thumbnailUrl = ""),
)
