package com.raouf.movieapp.presontation.detailScreen



sealed interface DetailScreenEvent {
   data object PlayVideo : DetailScreenEvent
}