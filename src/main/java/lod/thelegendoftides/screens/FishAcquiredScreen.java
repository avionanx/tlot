package lod.thelegendoftides.screens;

import legend.core.platform.input.InputAction;
import legend.game.combat.ui.UiBox;
import legend.game.i18n.I18n;
import legend.game.inventory.Equipment;
import legend.game.inventory.ItemStack;
import legend.game.inventory.screens.FontOptions;
import legend.game.inventory.screens.HorizontalAlign;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import legend.game.inventory.screens.TextColour;
import legend.game.types.Renderable58;
import lod.thelegendoftides.Fish;
import org.jetbrains.annotations.NotNull;
import static legend.core.GameEngine.CONFIG;
import static legend.game.SItem.UI_WHITE;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.Scus94491BpeSegment_800b.itemOverflow;
import static legend.game.Text.renderText;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_CONFIRM;
import static legend.game.types.Renderable58.FLAG_DELETE_AFTER_RENDER;
import static lod.thelegendoftides.Tlot.CATCH_FLAGS_CONFIG;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class FishAcquiredScreen extends MenuScreen {
  private UiBox contentBox;
  private final Fish fish;
  private final Runnable setCaughtScreenUnloaded;
  private final FontOptions fontOptions = new FontOptions().colour(TextColour.WHITE).horizontalAlign(HorizontalAlign.CENTRE);

  public FishAcquiredScreen(final Fish fish, final Runnable setCaughtScreenUnloaded) {
    this.fish = fish;
    this.setCaughtScreenUnloaded = setCaughtScreenUnloaded;
    this.contentBox = new UiBox("Fish Capture Screen BG", 80, 60, 160, 120);

    if(fish.getReward() instanceof final ItemStack itemReward) {
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

    if(fish.legendaryIndex != -1) {
      final long newFlags = CONFIG.getConfig(CATCH_FLAGS_CONFIG.get()) | (1L << fish.legendaryIndex);
      CONFIG.setConfig(CATCH_FLAGS_CONFIG.get(), newFlags);
    }
  }

  @Override
  protected void render() {
    this.contentBox.render();

    final Renderable58 renderable = this.fish.icon.render(160, 110, FLAG_DELETE_AFTER_RENDER);
    renderable.z_3c = 10.0f;
    renderable.widthScale = 4.0f;
    renderable.heightScale_38 = 4.0f;

    renderText((I18n.translate(getTranslationKey("acquired_fish"))), 160.0f, 60, this.fontOptions);
    renderText((I18n.translate(this.fish)), 160.0f, 165, this.fontOptions);
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
