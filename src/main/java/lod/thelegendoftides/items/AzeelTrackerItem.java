package lod.thelegendoftides.items;

import legend.game.characters.Element;
import legend.game.combat.bent.BattleEntity27c;
import legend.game.inventory.ItemStack;
import legend.game.scripting.ScriptState;
import legend.lodmod.LodMod;
import lod.thelegendoftides.TlotFish;

import java.util.Random;

import static legend.core.GameEngine.CONFIG;
import static lod.thelegendoftides.Tlot.TLOT_FLAGS_OTHER;


public class AzeelTrackerItem extends FishItem {
  public AzeelTrackerItem(int price) {
    super(TlotFish.AZEEL_TRACKER, price);
  }

  @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return location == UsageLocation.BATTLE;
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
  public boolean isRepeat(final ItemStack stack) {
    return true;
  }

  @Override
  protected void useItemScriptLoaded(final ScriptState<BattleEntity27c> user, final int targetBentIndex) {
    this.selectNextAzeelLocation();
    user.setStor(28, targetBentIndex);
    user.setStor(30, user.index);
  }

  private void selectNextAzeelLocation() {
    final long currentFlag = CONFIG.getConfig(TLOT_FLAGS_OTHER.get());
    if((currentFlag & 0x1f) != 0x0 || (currentFlag & 0x300) == 0x300) return;

    final int visitedCount = this.getAzeelCatchCount();
    int random = new Random().nextInt(5 - visitedCount);
    int flag = 0x1000;
    while(random > -1) {
      if((currentFlag & flag) == flag) {
        flag <<= 1;
        if(flag == 0x20000) flag = 0x1000;
        continue;
      }
      if(random == 0) {
        break;
      }
      random--;
      flag <<= 1;
      if(flag == 0x20000) flag = 0x1000;
    }
    CONFIG.setConfig(TLOT_FLAGS_OTHER.get(), currentFlag | flag >> 12);
  }

  private int getAzeelCatchCount() {
    return Long.bitCount(CONFIG.getConfig(TLOT_FLAGS_OTHER.get()) >>> 12);
  }
}
