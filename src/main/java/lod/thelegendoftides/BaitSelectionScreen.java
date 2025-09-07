package lod.thelegendoftides;

import legend.core.platform.Window;
import legend.game.combat.ui.UiBox;
import legend.game.i18n.I18n;
import legend.game.inventory.ItemStack;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import legend.game.inventory.screens.TextColour;
import legend.game.inventory.screens.controls.Button;
import legend.game.modding.events.inventory.Inventory;
import lod.thelegendoftides.items.BaitItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;

import static legend.core.GameEngine.RENDERER;
import static legend.game.SItem.UI_WHITE;
import static legend.game.Scus94491BpeSegment_8002.playMenuSound;
import static legend.game.Scus94491BpeSegment_8002.renderText;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_BACK;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_DOWN;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_UP;
import static lod.thelegendoftides.Tlot.getExtraWidth;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class BaitSelectionScreen extends MenuScreen {
  private final List<Button> menuButtons = new ArrayList<>();
  private UiBox headerBox;
  private UiBox contentBox;
  private UiBox hotkeyBox;

  private int extraWidth;

  public BaitSelectionScreen(final Inventory inv, final BiConsumer<Bait, Runnable> callback) {
    this.extraWidth = (int)getExtraWidth();

    final List<ItemStack> baits = new ArrayList<>();
    for(final ItemStack stack : inv) {
      if(stack.getItem() instanceof BaitItem) {
        baits.add(stack);
      }
    }

    baits.sort(Comparator.comparing((ItemStack stack) -> ((BaitItem)stack.getItem()).getBait(stack).quality).reversed());

    for(int i = 0; i < baits.size(); i++) {
      final ItemStack baitStack = baits.get(i);
      final BaitItem baitItem = (BaitItem)baitStack.getItem();
      final Bait bait = baitItem.getBait(baitStack);

      this.addButton(I18n.translate(bait), () -> {
        playMenuSound(2);
        this.deferAction(() -> {
          baitItem.consumeBait(baitStack);
          inv.removeIfEmpty(baitStack);
          callback.accept(bait, this::unload);
        });
      });
    }

    this.headerBox = new UiBox("Bait List Header", 20 - this.extraWidth / 2, 18, 100, 14);
    this.contentBox = new UiBox("Bait List Content", 20 - this.extraWidth / 2, 40, 100, 80);
    this.hotkeyBox = new UiBox("Hotkey BG", 8 - this.extraWidth / 2, 226, 304 + this.extraWidth, 10);

    if(baits.isEmpty()) {
      this.addButton(I18n.translate(getTranslationKey("message_no_bait")), () -> {});
    }

    this.setFocus(this.menuButtons.getFirst());

    this.addHotkey(I18n.translate(getTranslationKey("hotkey_bait_cancel")), INPUT_ACTION_MENU_BACK, () -> {
      playMenuSound(3);
      this.deferAction(() -> callback.accept(null, this::unload));
    });

    RENDERER.events().onResize(this::onResized);
  }

  private void onResized(final Window window, final int x, final int y) {
    this.headerBox.delete();
    this.contentBox.delete();
    this.hotkeyBox.delete();

    this.extraWidth = (int)getExtraWidth();

    for(int i = 0; i < this.menuButtons.size(); i++) {
      this.menuButtons.get(i).setPos(30 - this.extraWidth / 2, 40 + i * 14);
    }

    this.headerBox = new UiBox("Bait List Header", 20 - this.extraWidth / 2, 18, 100, 14);
    this.contentBox = new UiBox("Bait List Content", 20 - this.extraWidth / 2, 40, 100, 80);
    this.hotkeyBox = new UiBox("Hotkey BG", 8 - this.extraWidth / 2, 226, 304 + this.extraWidth, 10);
  }

  @Override
  protected void render() {
    this.headerBox.render();
    this.contentBox.render();
    this.hotkeyBox.render();
    renderText((I18n.translate(getTranslationKey("bait_list"))), 40 - this.extraWidth / 2, 20, UI_WHITE);
  }

  private void unload() {
    this.getStack().popScreen();
    this.headerBox.delete();
    this.contentBox.delete();
    this.hotkeyBox.delete();

    RENDERER.events().removeOnResize(this::onResized);
  }

  private void addButton(final String text, final Runnable onClick) {
    final int index = this.menuButtons.size();
    final Button button = this.addControl(new Button(text));

    button.setPos(30 - this.extraWidth / 2, 40 + index * 14);
    button.setZ(1);
    button.setWidth(80);
    button.onHoverIn(() -> {
      playMenuSound(1);
      this.setFocus(button);
    });
    button.setTextColour(TextColour.GREY);
    button.onLostFocus(() -> button.setTextColour(TextColour.GREY));
    button.onGotFocus(() -> button.setTextColour(TextColour.WHITE));

    button.onPressed(onClick::run);
    this.menuButtons.add(button);
    button.onInputActionPressed((action, repeat) -> {
      if(action == INPUT_ACTION_MENU_DOWN.get()) {
        for(int i = 1; i < this.menuButtons.size(); i++) {
          final Button otherButton = this.menuButtons.get(Math.floorMod(index + i, this.menuButtons.size()));

          if(!otherButton.isDisabled() && otherButton.isVisible()) {
            playMenuSound(1);
            this.setFocus(otherButton);
            break;
          }
        }
        return InputPropagation.HANDLED;
      } else if(action == INPUT_ACTION_MENU_UP.get()) {
        for(int i = 1; i < this.menuButtons.size(); i++) {
          final Button otherButton = this.menuButtons.get(Math.floorMod(index - i, this.menuButtons.size()));

          if(!otherButton.isDisabled() && otherButton.isVisible()) {
            playMenuSound(1);
            this.setFocus(otherButton);
            break;
          }
        }
        return InputPropagation.HANDLED;
      }
      return InputPropagation.PROPAGATE;
    });
  }

  // Want to show fish list while this screen is active
  @Override
  protected boolean propagateRender() {
    return true;
  }
}
