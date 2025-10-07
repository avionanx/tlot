package lod.thelegendoftides.screens;

import legend.core.platform.input.InputAction;
import legend.game.combat.ui.UiBox;
import legend.game.i18n.I18n;
import legend.game.inventory.ItemStack;
import legend.game.inventory.WhichMenu;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import legend.game.inventory.screens.TooManyItemsScreen;
import legend.game.types.Renderable58;
import lod.thelegendoftides.Fish;
import org.jetbrains.annotations.NotNull;

import static legend.core.GameEngine.CONFIG;
import static legend.game.SItem.UI_WHITE;
import static legend.game.SItem.menuStack;
import static legend.game.Scus94491BpeSegment_8002.renderText;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.Scus94491BpeSegment_800b.itemOverflow;
import static legend.game.Scus94491BpeSegment_800b.whichMenu_800bdc38;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_CONFIRM;
import static legend.game.types.Renderable58.FLAG_DELETE_AFTER_RENDER;
import static lod.thelegendoftides.Tlot.CATCH_FLAGS;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class FishAcquiredScreen extends MenuScreen {
  private UiBox contentBox;
  private final Fish fish;
  private final Runnable setCaughtScreenUnloaded;
  private boolean isRemainingItems;
  private boolean isRenderingTooManyItemsScreen;

  public FishAcquiredScreen(final Fish fish, final Runnable setCaughtScreenUnloaded) {
    this.fish = fish;
    this.setCaughtScreenUnloaded = setCaughtScreenUnloaded;
    this.contentBox = new UiBox("Fish Capture Screen BG", 80, 60, 160, 120);

    final ItemStack remainingItems = gameState_800babc8.items_2e9.give(fish.getReward());
    if(remainingItems.getSize() != 0) {
      itemOverflow.add(remainingItems);
      this.isRemainingItems = true;
    }

    if(fish.legendaryIndex != -1) {
      final long newFlags = CONFIG.getConfig(CATCH_FLAGS.get()) | (1L << fish.legendaryIndex);
      CONFIG.setConfig(CATCH_FLAGS.get(), newFlags);
    }
  }

  @Override
  protected void render() {
    if(this.isRenderingTooManyItemsScreen) {
      if(whichMenu_800bdc38 == WhichMenu.NONE_0) {
        this.deferAction(this::unload);
      }
    } else {
      this.contentBox.render();

      final Renderable58 renderable = this.fish.icon.render(160, 110, FLAG_DELETE_AFTER_RENDER);
      renderable.z_3c = 10.0f;
      renderable.widthScale = 6.0f;
      renderable.heightScale_38 = 6.0f;

      renderText((I18n.translate(getTranslationKey("acquired_fish"))), 112.5f, 60, UI_WHITE);
      renderText((I18n.translate(this.fish)), 145.0f, 165, UI_WHITE);
    }
  }

  @Override
  protected InputPropagation inputActionPressed(@NotNull final InputAction action, final boolean repeat) {
    if(!this.isRenderingTooManyItemsScreen) {
      if(action == INPUT_ACTION_MENU_CONFIRM.get() && !repeat) {
        if(this.isRemainingItems) {
          this.deferAction(() -> {
            menuStack.pushScreen(new TooManyItemsScreen());
            whichMenu_800bdc38 = WhichMenu.RENDER_NEW_MENU;
            this.isRenderingTooManyItemsScreen = true;
            this.isRemainingItems = false;
          });
        } else {
          this.deferAction(this::unload);
        }
      }

      return InputPropagation.HANDLED;
    } else {
      return InputPropagation.PROPAGATE;
    }
  }

  public void unload() {
    this.getStack().popScreen();
    this.contentBox.delete();
    this.setCaughtScreenUnloaded.run();
  }
}
