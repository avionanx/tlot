package lod.thelegendoftides.items;

import legend.core.memory.Method;
import legend.game.characters.Element;
import legend.game.combat.bent.BattleEntity27c;
import legend.game.inventory.ItemStack;
import legend.game.inventory.UseItemResponse;
import legend.game.scripting.ScriptState;
import legend.lodmod.LodMod;
import lod.thelegendoftides.TlotFish;

import static legend.game.SItem.addHp;

public class DorminItem extends FishItem {
  public DorminItem() {
    super(TlotFish.DORMIN);
  }

  @Override
  int getUnitPrice() {
   return 20;
  }

 @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return location == UsageLocation.BATTLE;
  }

  @Override
  public boolean canTarget(final ItemStack stack, final TargetType type) {
    return type == TargetType.ALLIES;
  }

  @Override
  public Element getAttackElement(final ItemStack stack) {
    return LodMod.DARK_ELEMENT.get();
  }

  @Override
  protected int getUseItemScriptEntrypoint() {
    return 34;
  }

  @Override
  public boolean alwaysHits(final ItemStack stack) {
    return true;
  }

  @Override
  protected void useItemScriptLoaded(final ScriptState<BattleEntity27c> user, final int targetBentIndex) {
    user.setStor(28, targetBentIndex);
    user.setStor(30, user.index);
  }
}
