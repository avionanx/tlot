package lod.thelegendoftides.items;

import legend.core.memory.Method;
import legend.game.characters.Element;
import legend.game.combat.bent.BattleEntity27c;
import legend.game.inventory.ItemStack;
import legend.game.inventory.UseItemResponse;
import legend.game.modding.events.inventory.GatherAttackItemsEvent;
import legend.game.scripting.FlowControl;
import legend.game.scripting.ScriptState;
import legend.lodmod.LodMod;
import lod.thelegendoftides.TlotFish;
import lod.thelegendoftides.TlotItems;

import static legend.core.GameEngine.EVENTS;
import static legend.game.SItem.addHp;
import static legend.game.combat.Battle.seed_800fa754;

public class DorminItem extends FishItem {
  public DorminItem() {
    super(TlotFish.DORMIN);
  }

  @Override
  int getUnitPrice() {
   return 200;
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
    return LodMod.DARK_ELEMENT.get();
  }

  @Override
  public boolean isRepeat(final ItemStack stack) {
    return true;
  }

  @Override
  public FlowControl useInBattle(final ItemStack stack, final ScriptState<BattleEntity27c> user, final int targetBentIndex) {
    final ItemStack[] stacks = {
      new ItemStack(TlotItems.AURCIS.get()),
      new ItemStack(TlotItems.RYUJIN.get()),
      new ItemStack(TlotItems.ENDINESS_SERPENT.get()),
      new ItemStack(TlotItems.ABANCEX.get()),
    };
    final ItemStack selected = stacks[seed_800fa754.nextInt(stacks.length)];

    user.innerStruct_00.item_d4 = selected;
    user.registryIds[0] = selected.getItem().getRegistryId();

    return selected.useInBattle(user, selected.canTarget(TargetType.ALL) ? -1 : targetBentIndex);
  }
}
