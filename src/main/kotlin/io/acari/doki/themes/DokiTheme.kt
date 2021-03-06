package io.acari.doki.themes

import io.acari.doki.config.ThemeConfig
import io.acari.doki.stickers.CurrentSticker
import io.acari.doki.util.toColor
import io.acari.doki.util.toOptional
import java.awt.Color
import java.util.*

class Stickers(
  val default: String,
  val secondary: String?
)

class DokiThemeDefinition(
  val name: String,
  val displayName: String?,
  val dark: Boolean,
  val stickers: Stickers,
  val group: String,
  val colors: Map<String, Any>,
  val ui: Map<String, Any>
)

class DokiTheme(private val uiTheme: DokiThemeDefinition) {

  init {
    validateThemeDefinition()
  }

  val groupName: String
    get() = uiTheme.group
  val isDark: Boolean
    get() = uiTheme.dark

  val name: String
    get() = uiTheme.name

  val displayName: String
    get() = uiTheme.displayName ?: throw IllegalStateException(
      """
|${name}'s theme.json requires "displayName" to be defined""".trimMargin()
    )


  fun getStickerPath(): Optional<String> {
    return when (ThemeConfig.instance.currentSticker) {
      CurrentSticker.DEFAULT -> uiTheme.stickers.default
      CurrentSticker.SECONDARY -> uiTheme.stickers.secondary ?: uiTheme.stickers.default
    }.toOptional()
  }

  fun getSticker(): Optional<String> =
    getStickerPath()
      .map { it.substring(it.lastIndexOf("/") + 1) }

  val nonProjectFileScopeColor: String
    get() = uiTheme.colors["nonProjectFileScopeColor"] as? String
      ?: throw IllegalStateException("Expected 'colors.nonProjectFileScopeColor' to be present in $name theme json")

  val testScopeColor: String
    get() = uiTheme.colors["testScopeColor"] as? String
      ?: throw IllegalStateException("Expected 'colors.testScopeColor' to be present in theme $name json.")

  val contrastColor: Color
    get() = (uiTheme.colors["contrastColor"] as? String)?.toColor()
      ?: throw IllegalStateException("Expected 'colors.contrastColor' to be present in theme $name json.")


  companion object {
    val ACCENT_COLOR = "Doki.Accent.color"
    //todo: read from json...
    val requiredNamedColors = listOf(
      ACCENT_COLOR,
      "Doki.startColor",
      "Doki.stopColor"
    )
  }

  private fun validateThemeDefinition() {
    nonProjectFileScopeColor
    testScopeColor
    displayName
    try {
      if (!uiTheme.stickers.default.matches("^/stickers/.+\\.png\$".toRegex())) {
        throw NullPointerException()
      }
    } catch (e: NullPointerException) {
      throw IllegalStateException(
        """
|${name}'s theme.json requires 'stickers.default' to match '^/stickers/.+\\.png\$' 
|(eg /stickers/literature/just_monika.png)""".trimMargin()
      )
    }

    if (uiTheme.stickers.secondary?.matches("^/stickers/.+\\.png\$".toRegex()) == false) {
      throw IllegalStateException(
        """
|${name}'s theme.json requires 'stickers.secondary' to match '^/stickers/.+\\.png\$' 
|if provided (eg /stickers/literature/just_monika.png) """.trimMargin()
      )
    }

    requiredNamedColors.forEach { requiredColor ->
      val requiredNamedColor = uiTheme.ui[requiredColor] as? String
      try {
        if (requiredNamedColor!!.startsWith("#")) {
          requiredNamedColor.toColor()
        } else {
          (uiTheme.colors[requiredNamedColor] as String).toColor()
        }
      } catch (e: Throwable) {
        throw IllegalStateException(
          """
          Expected "$requiredColor" to be present in "colors" as a valid hex color in ${name}'s theme json.
           "$requiredNamedColor" is not a valid hex color or reference to a defined color
        """.trimIndent()
        )
      }
    }
  }
}