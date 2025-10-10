package lod.thelegendoftides.items;

import legend.core.memory.Method;
import legend.game.Scus94491BpeSegment_8002;
import legend.game.characters.Element;
import legend.game.combat.bent.BattleEntity27c;
import legend.game.i18n.I18n;
import legend.game.inventory.ItemStack;
import legend.game.inventory.UseItemResponse;
import legend.game.scripting.ScriptState;
import legend.lodmod.LodMod;
import lod.thelegendoftides.TlotFish;

import static legend.game.SItem.getXpToNextLevel;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;

public class BlackBassItem extends FishItem {
  public BlackBassItem(final int price) {
    super(TlotFish.BLACK_BASS, price);
  }

  @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return location == UsageLocation.MENU;
  }

  @Override
  @Method(0x80022d88L)
  public void useInMenu(final ItemStack stack, final UseItemResponse response, final int charId) {
    response._00 = 9;
    int xp = gameState_800babc8.charData_32c[charId].xp_00;
    if(xp <= 999999) {
      xp = xp + 100;
    } else {
      xp = 999999;
    }
    gameState_800babc8.charData_32c[charId].xp_00 = xp;

    if(gameState_800babc8.charData_32c[charId].xp_00 >= getXpToNextLevel(charId) && gameState_800babc8.charData_32c[charId].level_12 < 60) {
      gameState_800babc8.charData_32c[charId].level_12++;
      }
  }
}
