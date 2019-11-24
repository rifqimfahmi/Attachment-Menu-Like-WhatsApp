package com.github.rifqimfahmi.softkeyboard.util

import com.github.rifqimfahmi.softkeyboard.adapter.Chat

object ChatUtil {
    fun createDummy(): List<Chat> {
        return arrayListOf(
            Chat("Hola!"),
            Chat("Lorem ipsum dolor sit amet."),
            Chat("Praesent vel velit at neque scelerisque!"),
            Chat("Quisque varius porttitor varius. Vestibulum sodales tempor lectus. Pellentesque aliquet augue ac ante malesuada hendrerit!"),
            Chat("Suspendisse dapibus nulla"),
            Chat("Maecenas aliquam odio ac ultricies fringilla!"),
            Chat("Donec viverra sodales risus, id pellentesq!"),
            Chat("Ut euismod, est vitae vulputate mat."),
            Chat("Nam gravida, sapien et pulvinar egestas!"),
            Chat("Integer egestas arcu quis velit varius vulputate!"),
            Chat("Phasellus pellentesque metus vitae massa dapibus, sit amet dignissim orci efficitur. Praesent aliquam felis vel eleifend tincidunt!"),
            Chat("Integer egestas arcu quis velit varius vulputate. Phasellus pellentesque metus vitae massa dapibus, sit amet dignissim orci efficitur. Praesent aliquam felis vel eleifend tincidunt. Phasellus gravida porta mollis. Pellentesque posuere felis id lectus egestas, sed sollicitudin turpis egestas. Sed non sem quis purus ornare interdum. Donec ligula ex, cursus a porta quis, bibendum bibendum nisi.")
        )
    }

}