package io.acari.DDLC.themes.anthro

import com.chrisrm.ideaddlc.utils.MTAccents
import io.acari.DDLC.themes.AnthroTheme
import io.acari.DDLC.toStream
import java.util.stream.Stream
import javax.swing.plaf.ColorUIResource

class CleoTheme : AnthroTheme("cleo", "Cleo", false, "Cleo") {

  override fun getChibi(): String = "athro/cleo.png"

  override fun getNormalChibi(): String = "athro/cleo.png"

  override fun getBackgroundColorString(): String = "e6e6f1"

  //todo: remove these
  override fun getClubMember(): String = "sayori.png"

  override fun joyfulClubMember(): String = "sayori_joy.png"

  override fun getSecondaryBackgroundColorString(): String = "dcdbe9"

  override fun getSecondaryForegroundColorString(): String = "36363a"

  override fun getSelectionForegroundColorString(): String = "e7e7ed"

  override fun getSelectionBackgroundColorString(): String = "55545d"

  override fun getTreeSelectionBackgroundColorString(): String = "242528"

  override fun getTreeSelectionForegroundColorString(): String = "e7e7ed"

  override fun getForegroundColorString(): String = "1F2435"

  override fun getTextColorString(): String = "1F2435"

  override fun getInactiveColorString(): String = "7D7F86"

  override fun getMenuItemForegroundColor(): String = "252529"

  override fun getMenuBarSelectionForegroundColorString(): String = "e7e7ed"

  override fun getMenuBarSelectionBackgroundColorString(): String = "242528"

  override fun getNotificationsColorString(): String = "DCDDE8"

  override fun getHighlightColorString(): String = "DCE0F0"

  override fun getContrastColorString(): String = "CFCFD5"

  override fun getBorderColorString(): String = "d0cdd5"

  override fun getEditorTabColorString(): String = contrastColorString

  override fun getButtonBackgroundColor(): String = contrastColorString

  override fun getButtonForegroundColor(): String = "252427"

  override fun getAccentColor(): String = MTAccents.WATER.hexColor

  override fun getBackgroundColorResource(): ColorUIResource = ColorUIResource(0xe7e7ed)

  override fun getForegroundColorResource(): ColorUIResource = ColorUIResource(0x0F111A)

  override fun getDisabledColorString(): String = "A2B6CB"

  override fun getTableSelectedColorString(): String = "242528"

  override fun getStartColor(): String = "252d33"

  override fun getStopColor(): String = "6accfc"

  override fun getNonProjectFileScopeColor(): String = "EBEDD9"

  override fun getTestScope(): String = "C0EDCC"

  override fun getSelectedButtonForegroundColor(): String = selectionForegroundColorString

  override fun getSelectionForegroundResources(): Stream<String> {
    return super.getSelectionForegroundResources()
        .filter { it != "SearchEverywhere.Tab.selectedForeground" }
  }

  override fun getForegroundResources(): Stream<String> {
    return Stream.concat(super.getForegroundResources(),
        "SearchEverywhere.Tab.selectedForeground".toStream())
  }
}
