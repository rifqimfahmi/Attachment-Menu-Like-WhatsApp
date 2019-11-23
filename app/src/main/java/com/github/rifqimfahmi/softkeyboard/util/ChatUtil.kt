package com.github.rifqimfahmi.softkeyboard.util

import com.github.rifqimfahmi.softkeyboard.adapter.Chat

object ChatUtil {
    fun createDummy(): List<Chat> {
        return arrayListOf(
            Chat("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean tristique massa ante, sit amet mollis sem fringilla a. Curabitur fringilla, lorem et commodo suscipit,!"),
            Chat("Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
            Chat("Praesent vel velit at neque scelerisque porttitor eu sed massa!"),
            Chat("Quisque varius porttitor varius. Vestibulum sodales tempor lectus. Pellentesque aliquet augue ac ante malesuada hendrerit!"),
            Chat("Suspendisse dapibus nulla ut turpis eleifend, scelerisque pharetra velit convallis. Praesent ultrices justo non augue volutpat imperdiet!"),
            Chat("Maecenas aliquam odio ac ultricies fringilla!"),
            Chat("Donec viverra sodales risus, id pellentesque sem accumsan ut!"),
            Chat("Ut euismod, est vitae vulputate mattis, neque lectus condimentum orci!"),
            Chat("Nam gravida, sapien et pulvinar egestas!"),
            Chat("Integer egestas arcu quis velit varius vulputate!"),
            Chat("Phasellus pellentesque metus vitae massa dapibus, sit amet dignissim orci efficitur. Praesent aliquam felis vel eleifend tincidunt!"),
            Chat("Integer egestas arcu quis velit varius vulputate. Phasellus pellentesque metus vitae massa dapibus, sit amet dignissim orci efficitur. Praesent aliquam felis vel eleifend tincidunt. Phasellus gravida porta mollis. Pellentesque posuere felis id lectus egestas, sed sollicitudin turpis egestas. Sed non sem quis purus ornare interdum. Donec ligula ex, cursus a porta quis, bibendum bibendum nisi.")
        )
    }

}