package lod.thelegendoftides.items;

import legend.game.characters.Element;
import legend.game.combat.bent.BattleEntity27c;
import legend.game.inventory.ItemStack;
import legend.game.scripting.ScriptState;
import legend.lodmod.LodMod;
import lod.thelegendoftides.TlotFish;

public class BottleOHorrors extends FishItem {
  public BottleOHorrors() {
    super(TlotFish.BOTTLE_O_HORRORS);
  }

  @Override
  int getUnitPrice() {
   return 444;
  }

 @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return location == UsageLocation.MENU;
  }
}
