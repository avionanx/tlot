package lod.thelegendoftides.screens;

import legend.core.QueuedModelStandard;
import legend.core.gte.MV;
import legend.core.platform.input.InputAction;
import legend.game.i18n.I18n;
import legend.game.inventory.Equipment;
import legend.game.inventory.ItemStack;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import legend.game.types.Renderable58;
import legend.game.ui.UiBox;
import lod.thelegendoftides.Fish;
import lod.thelegendoftides.TlotFish;
import lod.thelegendoftides.TlotLevelHelpers;
import org.jetbrains.annotations.NotNull;

import static legend.core.GameEngine.CONFIG;
import static legend.core.GameEngine.RENDERER;
import static legend.game.SItem.UI_WHITE_CENTERED;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.Scus94491BpeSegment_800b.itemOverflow;
import static legend.game.Text.renderText;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_CONFIRM;
import static legend.game.sound.Audio.playMenuSound;
import static legend.game.types.Renderable58.FLAG_DELETE_AFTER_RENDER;
import static lod.thelegendoftides.Tlot.CATCH_FLAGS_CONFIG;
import static lod.thelegendoftides.Tlot.TLOT_FISH_PRIME_CHARGES;
import static lod.thelegendoftides.Tlot.TLOT_FLAGS_OTHER;
import static lod.thelegendoftides.Tlot.TLOT_NUM_FISH_CAUGHT;
import static lod.thelegendoftides.Tlot.TLOT_XP;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class FishAcquiredScreen extends MenuScreen {
  private final UiBox contentBox;
  private final Fish fish;
  private final Runnable setCaughtScreenUnloaded;

  private String acquiredMessage = "acquired_fish";

  public FishAcquiredScreen(final Fish fish, final Runnable setCaughtScreenUnloaded) {
    this.fish = fish;
    this.setCaughtScreenUnloaded = setCaughtScreenUnloaded;
    this.contentBox = new UiBox(80, 60, 160, 120);

    if(fish.legendaryFlag != -1) {
      final long newFlags = CONFIG.getConfig(CATCH_FLAGS_CONFIG.get()) | (fish.legendaryFlag);
      CONFIG.setConfig(CATCH_FLAGS_CONFIG.get(), newFlags);
    }

    // Increment num caught by 1
    CONFIG.setConfig(TLOT_NUM_FISH_CAUGHT.get(), CONFIG.getConfig(TLOT_NUM_FISH_CAUGHT.get()) + 1);
    // Cap and increase charges by 0.1
    CONFIG.setConfig(TLOT_FISH_PRIME_CHARGES.get(),  Math.min(CONFIG.getConfig(TLOT_FISH_PRIME_CHARGES.get()) + 1, 50));

    // TODO render level up text
    final int oldLevel = TlotLevelHelpers.TLOT_GET_LEVEL();
    CONFIG.setConfig(TLOT_XP.get(), CONFIG.getConfig(TLOT_XP.get()) + fish.legendaryFlag == -1 ? fish.xp * gameState_800babc8.chapterIndex_98 : fish.xp);
    final int newLevel = TlotLevelHelpers.TLOT_GET_LEVEL();
    if(oldLevel != newLevel) {
      playMenuSound(9);
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

    //XP and levels and stuff
    final MV xpStuffMV = new MV();
    xpStuffMV.scaling(120.0f, 4.0f, 1.0f);
    xpStuffMV.transfer.set(160.0f, 200.0f, 11.0f);

    // TODO render max level
    RENDERER.queueOrthoModel(RENDERER.centredQuadOpaque, xpStuffMV, QueuedModelStandard.class).colour(0.0f, 0.0f, 0.0f);
    xpStuffMV.scaling(118.0f * TlotLevelHelpers.TLOT_GET_LEVEL_PROGRESS(), 2.0f, 1.0f);
    xpStuffMV.transfer.set(101.0f, 199.0f, 11.0f);
    RENDERER.queueOrthoModel(RENDERER.opaqueQuad, xpStuffMV, QueuedModelStandard.class).colour(0.4f, 0.5f, 0.8f);
    renderText(Integer.toString(TlotLevelHelpers.TLOT_GET_LEVEL()), 88.0f, 194.0f, UI_WHITE_CENTERED);
    renderText(Integer.toString(TlotLevelHelpers.TLOT_GET_LEVEL() + 1), 232.0f, 194.0f, UI_WHITE_CENTERED);
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
    this.setCaughtScreenUnloaded.run();
  }
}
