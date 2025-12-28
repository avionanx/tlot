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
import static legend.game.SItem.addMp;
import static legend.game.SItem.addSp;

public class GrandRainbowTroutItem extends FishItem {
  public GrandRainbowTroutItem(final int price) {
    super(TlotFish.GRAND_RAINBOW_TROUT, price);
  }

  @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return true;
  }

  @Override
  public boolean canTarget(final ItemStack stack, final TargetType type) {
    return type == TargetType.ALLIES || type == TargetType.ALL;
  }

  @Override
  public Element getAttackElement(final ItemStack stack) {
    return LodMod.NO_ELEMENT.get();
  }

  @Override
  protected int getUseItemScriptEntrypoint() {
    return 2;
  }

  @Override
  @Method(0x80022d88L)
  public void useInMenu(final ItemStack stack, final UseItemResponse response, final int charId) {
    addHp(charId, 500);
    addMp(charId, 40);
    addSp(charId, 75);
    response.success();
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
