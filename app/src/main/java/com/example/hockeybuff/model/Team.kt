package com.example.hockeybuff.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(val name: String, val logo: String) : Parcelable
