package lod.thelegendoftides.items;

import legend.game.characters.Element;
import legend.game.combat.bent.BattleEntity27c;
import legend.game.inventory.ItemStack;
import legend.game.scripting.ScriptState;
import legend.lodmod.LodMod;
import lod.thelegendoftides.TlotFish;

public class PetrichorItem extends FishItem {
  public PetrichorItem() {
    super(TlotFish.PETRICHOR);
  }

  @Override
  int getUnitPrice() {
   return 400;
  }

 @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return location == UsageLocation.BATTLE;
  }

  @Override
  public boolean canTarget(final ItemStack stack, final TargetType type) {
    return type == TargetType.ENEMIES;
  }

  @Override
  public Element getAttackElement(final ItemStack stack) {
    return LodMod.WATER_ELEMENT.get();
  }

  @Override
  protected int getUseItemScriptEntrypoint() {
    return 33;
  }

  @Override
  protected void useItemScriptLoaded(final ScriptState<BattleEntity27c> user, final int targetBentIndex) {
    user.setStor(28, targetBentIndex);
    user.setStor(30, user.index);
  }
}
