package lod.thelegendoftides;

import legend.game.combat.Battle;
import legend.game.combat.SBtld;
import legend.game.combat.bent.PlayerBattleEntity;
import legend.game.combat.encounters.Encounter;
import legend.game.combat.environment.BattleCamera;
import legend.game.inventory.screens.MenuStack;
import legend.game.modding.events.RenderEvent;
import legend.game.modding.events.battle.BattleStartedEvent;
import legend.game.modding.events.gamestate.GameLoadedEvent;
import legend.game.modding.events.input.InputReleasedEvent;
import legend.game.modding.events.submap.SubmapEnvironmentTextureEvent;
import legend.game.submap.SMap;
import legend.game.submap.SubmapObject210;
import legend.game.submap.SubmapState;
import org.joml.Vector3f;
import org.legendofdragoon.modloader.Mod;
import org.legendofdragoon.modloader.events.EventListener;
import org.legendofdragoon.modloader.registries.RegistryId;

import static legend.core.GameEngine.EVENTS;
import static legend.game.Scus94491BpeSegment_8004.currentEngineState_8004dd04;
import static legend.game.Scus94491BpeSegment_800b.*;
import static legend.game.combat.bent.BattleEntity27c.FLAG_HIDE;
import static legend.lodmod.LodMod.INPUT_ACTION_SMAP_INTERACT;

@Mod(id = Main.MOD_ID, version = "3.0.0")
public class Main {
  public static final String MOD_ID = "thelegendoftides";
  private FishMeta meta;
  private FishLocationData currentCutFishingData;
  private boolean isFishEncounter = false;

  private final MenuStack menuStack = new MenuStack();
  private FishListScreen fishListScreen;

  public Main() {
    EVENTS.register(this);
  }

  public static RegistryId id(final String entryId) {
    return new RegistryId(MOD_ID, entryId);
  }

  @EventListener
  public void init(final GameLoadedEvent event) {
    this.meta = new FishMeta();
  }

  @EventListener
  public void checkFishingMap(final SubmapEnvironmentTextureEvent event) {
    if(this.fishListScreen != null) { this.fishListScreen.unload(); }
    this.menuStack.reset();
    this.currentCutFishingData = this.meta.getCutFish(event.submapCut);
    if(this.currentCutFishingData != null) {
      this.fishListScreen = new FishListScreen(this.meta, this.currentCutFishingData);
      this.menuStack.pushScreen(this.fishListScreen);
    }
  }

  @EventListener
  public void disableBentScriptsOnBattleStart(final BattleStartedEvent event) {
    if(!this.isFishEncounter) return;
    this.isFishEncounter = false;

    scriptStatePtrArr_800bc1c0[5].pause();
    scriptStatePtrArr_800bc1c0[6].pause();
    scriptStatePtrArr_800bc1c0[11].pause();
    scriptStatePtrArr_800bc1c0[11].storage_44[7] |= FLAG_HIDE;

    final Battle battle = ((Battle)currentEngineState_8004dd04);
    final PlayerBattleEntity player = (PlayerBattleEntity)scriptStatePtrArr_800bc1c0[6].innerStruct_00;
    BattleCamera camera = ((Battle)currentEngineState_8004dd04).camera_800c67f0;

    battle.battleInitialCameraMovementFinished_800c66a8 = true;
    camera.resetCameraMovement();
    camera.setViewpoint(-8000.0f, -2000.0f, -15000.0f);
    camera.setRefpoint(0.0f, 0.0f, -5000.0f);
    camera.flags_11c = 3;
    camera.refpointBobj_80 = player;
    player.model_148.shadowType_cc = 3;
    player.model_148.modelPartWithShadowIndex_cd = 8;
    player.model_148.shadowSize_10c.x = 0x1800 / (float)0x1000;
    player.model_148.shadowSize_10c.z = 0x1800 / (float)0x1000;
    player.model_148.coord2_14.coord.transfer.x = -2680;
    player.model_148.coord2_14.coord.transfer.y = 0;
    player.model_148.coord2_14.coord.transfer.z = -5200;
    player.model_148.coord2_14.transforms.rotate.y = 0;

    this.fishListScreen.battleTransitionFinished = true;
    this.menuStack.pushScreen(new BaitSelectionScreen(this.meta, this::handleBaitSelected));
  }

  @EventListener
  public void renderLoop(final RenderEvent event) {
    this.menuStack.render();
  }

  @EventListener
  public void inputReleased(final InputReleasedEvent event) {
    if(event.action == INPUT_ACTION_SMAP_INTERACT.get()
            && isOnFishingPrimitive(this.currentCutFishingData)
            && !gameState_800babc8.indicatorsDisabled_4e3) {

      this.isFishEncounter = true;
      this.fishListScreen.battleTransitionFinished = false;

      SBtld.startEncounter(new Encounter(1, 0, 0, 0, 0, 0, 0, 0, 133, 6, new Encounter.Monster(1, new Vector3f())), 13);
      ((SMap)currentEngineState_8004dd04).smapLoadingStage_800cb430 = SubmapState.TRANSITION_TO_COMBAT_19;
    }
  }

  private void handleBaitSelected(final String bait, final Runnable unloadBaitSelectionScreen) {
    unloadBaitSelectionScreen.run();

    if(bait == null) {
      postBattleAction_800bc974 = 5;
      return;
    }
    final Fish fish = this.meta.getRandomFishForBait(this.currentCutFishingData, bait);
    this.menuStack.pushScreen(new WaitingBiteScreen(this::handleQTESuccessful, this::handleQTEFail));
  }

  private void handleQTESuccessful() {
    this.menuStack.pushScreen(new AdditionOverlayScreen(((Battle)currentEngineState_8004dd04), (PlayerBattleEntity)scriptStatePtrArr_800bc1c0[6].innerStruct_00));
  }

  private void handleQTEFail() {
    this.menuStack.pushScreen(new BaitSelectionScreen(this.meta, this::handleBaitSelected));
  }

  public static boolean isOnFishingPrimitive(final FishLocationData locationData) {
    try {
      return ((SubmapObject210)scriptStatePtrArr_800bc1c0[10].innerStruct_00).collidedPrimitiveIndex_16c == locationData.collisionPrimitive();
    }catch(final Exception e) {
      return false;
    }
  }
}
