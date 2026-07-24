package lod.thelegendoftides.items;

import legend.core.memory.Method;
import legend.game.Menus;
import legend.game.characters.Element;
import legend.game.combat.SBtld;
import legend.game.combat.bent.BattleEntity27c;
import legend.game.combat.encounters.Encounter;
import legend.game.combat.types.EnemyDrop;
import legend.game.inventory.ItemStack;
import legend.game.inventory.UseItemResponse;
import legend.game.inventory.WhichMenu;
import legend.game.scripting.ScriptState;
import legend.game.submap.SMap;
import legend.game.submap.SubmapState;
import legend.lodmod.LodMod;
import lod.thelegendoftides.TlotFish;
import lod.thelegendoftides.TlotItems;
import org.joml.Vector3f;

import static legend.game.EngineStates.currentEngineState_8004dd04;
import static legend.game.SItem.addHp;
import static legend.game.SItem.menuStack;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.Scus94491BpeSegment_800b.itemOverflow;
import static legend.game.Scus94491BpeSegment_800b.itemsDroppedByEnemies_800bc928;

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

  @Override
  @Method(0x80022d88L)
  public void useInMenu(final ItemStack stack, final UseItemResponse response, final int charId) {
    SBtld.startEncounter(new Encounter(19, 0, 31, 31, 53, 90, 71, 75, 65535, 255, new Encounter.Monster(344, new Vector3f(-5248.000000f, 0.000000f, 128.000000f)), new Encounter.Monster(335, new Vector3f(-5376.000000f, 0.000000f, 3072.000000f)), new Encounter.Monster(335, new Vector3f(-5248.000000f, 0.000000f, -2944.000000f)), new Encounter.Monster(335, new Vector3f(-2944.000000f, 0.000000f, 2432.000000f)), new Encounter.Monster(335, new Vector3f(-3072.000000f, 0.000000f, -2304.000000f))), 18);
    response.success("You should save\nyour game");
    menuStack.popScreen();
    Menus.whichMenu_800bdc38 = WhichMenu.UNLOAD;
    final ItemStack item = new ItemStack(TlotItems.DORMIN.get(), 1);
    itemsDroppedByEnemies_800bc928.add(new EnemyDrop(item, () -> gameState_800babc8.items_2e9.give(item).isEmpty(), () -> itemOverflow.add(new ItemStack(item))));
    ((SMap)currentEngineState_8004dd04).smapLoadingStage_800cb430 = SubmapState.TRANSITION_TO_COMBAT_19;
  }
}
