package lod.thelegendoftides.screens;

import legend.core.platform.input.InputAction;
import legend.game.combat.ui.UiBox;
import legend.game.i18n.I18n;
import legend.game.inventory.Equipment;
import legend.game.inventory.ItemStack;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import legend.game.types.Renderable58;
import lod.thelegendoftides.Fish;
import lod.thelegendoftides.TlotFish;
import org.jetbrains.annotations.NotNull;

import static legend.core.GameEngine.CONFIG;
import static legend.game.SItem.UI_WHITE_CENTERED;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.Scus94491BpeSegment_800b.itemOverflow;
import static legend.game.Text.renderText;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_CONFIRM;
import static legend.game.types.Renderable58.FLAG_DELETE_AFTER_RENDER;
import static lod.thelegendoftides.Tlot.CATCH_FLAGS_CONFIG;
import static lod.thelegendoftides.Tlot.TLOT_FLAGS_OTHER;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class FishAcquiredScreen extends MenuScreen {
  private final UiBox contentBox;
  private final Fish fish;
  private final Runnable setCaughtScreenUnloaded;

  private String acquiredMessage = "acquired_fish";

  public FishAcquiredScreen(final Fish fish, final Runnable setCaughtScreenUnloaded) {
    this.fish = fish;
    this.setCaughtScreenUnloaded = setCaughtScreenUnloaded;
    this.contentBox = new UiBox("Fish Capture Screen BG", 80, 60, 160, 120);

    if(fish.legendaryIndex != -1) {
      final long newFlags = CONFIG.getConfig(CATCH_FLAGS_CONFIG.get()) | (1 << fish.legendaryIndex);
      CONFIG.setConfig(CATCH_FLAGS_CONFIG.get(), newFlags);
    }

    if(fish.getReward() instanceof final ItemStack itemReward) {
      if(fish.getRegistryId() == TlotFish.AZEEL_GLADIATOR.getId()) {
        final long currentToOldLocationFlag = (CONFIG.getConfig(TLOT_FLAGS_OTHER.get()) & 0x1f) << 12;
        if((0x100 & CONFIG.getConfig(TLOT_FLAGS_OTHER.get())) == 0x100) {
          CONFIG.setConfig(TLOT_FLAGS_OTHER.get(), 0x200 | currentToOldLocationFlag);
          this.acquiredMessage = "azeel_escaped";
          return;
        } else if((0x200 & CONFIG.getConfig(TLOT_FLAGS_OTHER.get())) == 0x200) {
          CONFIG.setConfig(TLOT_FLAGS_OTHER.get(), 0x300 | currentToOldLocationFlag);
          this.acquiredMessage = "azeel_acquired";
        } else {
          CONFIG.setConfig(TLOT_FLAGS_OTHER.get(), 0x100 | currentToOldLocationFlag);
          this.acquiredMessage = "azeel_escaped";
          return;
        }
      }
      final ItemStack remainingItems = gameState_800babc8.items_2e9.give(itemReward);
      if(remainingItems.getSize() != 0) {
        itemOverflow.add(remainingItems);
      }
     } else if(fish.getReward() instanceof final Equipment equipmentReward) {
       gameState_800babc8.equipment_1e8.add(equipmentReward);
       // TODO handle overflow
       // if(!giveEquipment(equipmentReward)) {
       //   equipmentOverflow.add(equipmentReward);
       //   this.isRemainingItems = true;
       // }
     }
  }

  @Override
  protected void render() {
    this.contentBox.render();

    final Renderable58 renderable = this.fish.icon.render(160, 110, FLAG_DELETE_AFTER_RENDER);
    renderable.z_3c = 10.0f;
    renderable.widthScale = 4.0f;
    renderable.heightScale_38 = 4.0f;

    renderText((I18n.translate(getTranslationKey(this.acquiredMessage))), 160.0f, 60, UI_WHITE_CENTERED);
    renderText((I18n.translate(this.fish)), 160.0f, 165, UI_WHITE_CENTERED);
  }


  @Override
  protected InputPropagation inputActionPressed(@NotNull final InputAction action, final boolean repeat) {
      if(action == INPUT_ACTION_MENU_CONFIRM.get() && !repeat) {
          this.deferAction(this::unload);
      }
      return InputPropagation.HANDLED;
    }


  public void unload() {
    this.getStack().popScreen();
    this.contentBox.delete();
    this.setCaughtScreenUnloaded.run();
  }
}
