package lod.thelegendoftides;

import legend.core.gte.MV;
import legend.game.combat.Battle;
import legend.game.combat.SBtld;
import legend.game.combat.bent.PlayerBattleEntity;
import legend.game.combat.encounters.Encounter;
import legend.game.combat.environment.BattleCamera;
import legend.game.combat.types.AdditionHits80;
import legend.game.inventory.screens.MenuStack;
import legend.game.modding.events.RenderEvent;
import legend.game.modding.events.battle.BattleStartedEvent;
import legend.game.modding.events.gamestate.GameLoadedEvent;
import legend.game.modding.events.input.InputReleasedEvent;
import legend.game.modding.events.submap.SubmapEnvironmentTextureEvent;
import legend.game.submap.SMap;
import legend.game.submap.SubmapObject210;
import legend.game.submap.SubmapState;
import legend.game.types.TmdAnimationFile;
import org.joml.Vector3f;
import org.legendofdragoon.modloader.Mod;
import org.legendofdragoon.modloader.events.EventListener;
import org.legendofdragoon.modloader.registries.RegistryId;

import java.util.Random;

import static legend.core.GameEngine.EVENTS;
import static legend.game.Scus94491BpeSegment.*;
import static legend.game.Scus94491BpeSegment_8003.GsGetLw;
import static legend.game.Scus94491BpeSegment_8004.additionCounts_8004f5c0;
import static legend.game.Scus94491BpeSegment_8004.currentEngineState_8004dd04;
import static legend.game.Scus94491BpeSegment_8006.battleState_8006e398;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.Scus94491BpeSegment_800b.postBattleAction_800bc974;
import static legend.game.Scus94491BpeSegment_800b.scriptStatePtrArr_800bc1c0;
import static legend.game.combat.SBtld.loadAdditions;
import static legend.game.combat.bent.BattleEntity27c.FLAG_HIDE;
import static legend.lodmod.LodMod.INPUT_ACTION_SMAP_INTERACT;

@Mod(id = Main.MOD_ID, version = "3.0.0")
public class Main {
  public static final String MOD_ID = "thelegendoftides";

  private final Random rand = new Random();

  private FishMeta meta;
  private FishLocationData currentCutFishingData;
  private boolean isFishEncounter = false;

  private final MenuStack menuStack = new MenuStack();
  private FishListScreen fishListScreen;

  private FishingState state = FishingState.NOT_FISHING;
  private FishingRod fishingRod;
  private Battle battle;
  private PlayerBattleEntity player;
  private String bait;

  private int loadingAnimIndex = -1;
  private int animationFrames;

  private float bobberVerticalAcceleration;
  private int castingTicks;

  /** The length of each bob */
  private static final long BOB_TIME = 250_000_000L;
  /** The interval between groups of bobs (a +-20% random factor is also applied */
  private static final long BOB_INTERVAL = 1_000_000_000L;
  /** The number of bobs in each interval */
  private static final int BOB_COUNT = 2;
  private final Vector3f nibbleStartPos = new Vector3f();
  private final Vector3f nibbleEndPos = new Vector3f();
  private long nextBobTime;
  private long nextBobInterval;
  private int bobCount;

  private AdditionOverlayScreen additionScreen;
  private AdditionHits80 activeAddition;
  private int additionTicks;

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

    this.battle = ((Battle)currentEngineState_8004dd04);
    this.player = (PlayerBattleEntity)scriptStatePtrArr_800bc1c0[6].innerStruct_00;
    BattleCamera camera = ((Battle)currentEngineState_8004dd04).camera_800c67f0;

    this.battle.battleInitialCameraMovementFinished_800c66a8 = true;
    camera.resetCameraMovement();
    camera.setViewpoint(-8000.0f, -2000.0f, -15000.0f);
    camera.setRefpoint(0.0f, 0.0f, -5000.0f);
    camera.flags_11c = 3;
    camera.refpointBobj_80 = player;
    this.player.model_148.shadowType_cc = 3;
    this.player.model_148.modelPartWithShadowIndex_cd = 8;
    this.player.model_148.shadowSize_10c.x = 0x1800 / (float)0x1000;
    this.player.model_148.shadowSize_10c.z = 0x1800 / (float)0x1000;
    this.player.model_148.coord2_14.coord.transfer.x = -2680;
    this.player.model_148.coord2_14.coord.transfer.y = 0;
    this.player.model_148.coord2_14.coord.transfer.z = -5200;
    this.player.model_148.coord2_14.transforms.rotate.y = 0;

    // Hide player's weapon
    this.player.model_148.partInvisible_f4 |= 0x1L << this.player.getWeaponModelPart();

    this.fishListScreen.isFishListScreenDisabled = false;

    if(this.fishingRod != null) {
      throw new IllegalStateException("Need to clean up after fishing");
    }

    this.fishingRod = new FishingRod(this.player.model_148.modelParts_00[this.player.getWeaponModelPart()].coord2_04);
    this.menuStack.pushScreen(new BaitSelectionScreen(this.meta, this::handleBaitSelected));
    this.state = FishingState.IDLE;
  }

  @EventListener
  public void renderLoop(final RenderEvent event) {
    if(this.state != FishingState.NOT_FISHING) {
      this.renderFishing();
    }

    this.menuStack.render();
  }

  private void renderFishing() {
    if(this.loadingAnimIndex != -1) {
      // Wait for animation to finish loading and then load it into the player
      final TmdAnimationFile asset = battleState_8006e398.getAnimationGlobalAsset(this.player.combatant_144, this.loadingAnimIndex);

      if(asset != null) {
        asset.loadIntoModel(this.player.model_148);
        this.animationFrames = asset.totalFrames_0e;
        this.loadingAnimIndex = -1;
      }
    } else {
      switch(this.state) {
        case CASTING -> {
          this.castingTicks++;

          if(this.castingTicks > this.animationFrames) {
            final TmdAnimationFile asset = battleState_8006e398.getAnimationGlobalAsset(this.player.combatant_144, 0);
            asset.loadIntoModel(this.player.model_148);
          }

          if(this.castingTicks < 18) {
            this.fishingRod.bobberCoord2.set(this.player.model_148.modelParts_00[this.player.getRightHandModelPart()].coord2_04);
            this.bobberVerticalAcceleration = 100.0f;
          } else if(this.castingTicks < 32) {
            final MV lw = new MV();
            GsGetLw(this.player.model_148.coord2_14, lw);
            this.fishingRod.bobberCoord2.coord.transfer.add(new Vector3f(0.0f, -this.bobberVerticalAcceleration, -450.0f).mul(lw));
            this.fishingRod.bobberCoord2.flg = 0;
            this.bobberVerticalAcceleration -= 30.0f;
          }

          if(this.castingTicks > 45) {
            //TODO use fish
            final Fish fish = this.meta.getRandomFishForBait(this.currentCutFishingData, bait);
            this.menuStack.pushScreen(new WaitingBiteScreen(this::onFishNibbling, this::onFishHooked, this::onFishEscaped));
            this.state = FishingState.WAITING_FOR_BITE;
          }
        }

        case NIBBLING -> {
          final long time = System.nanoTime();

          if(this.nextBobTime < time && this.bobCount < BOB_COUNT) {
            this.nextBobTime = time + BOB_TIME;
            this.bobCount++;
          }

          if(this.nextBobInterval < time) {
            this.nextBobInterval = time + (long)(BOB_INTERVAL * (0.8f + this.rand.nextFloat() * 0.4f));
            this.bobCount = 0;
          }

          final float t = Math.clamp((this.nextBobTime - time) / (float)BOB_TIME, 0.0f, 1.0f);
          this.nibbleEndPos.lerp(this.nibbleStartPos, t, this.fishingRod.bobberCoord2.coord.transfer);
          this.fishingRod.bobberCoord2.flg = 0;
        }

        case REELING -> {
          this.additionTicks--;

          if(this.additionTicks <= 0) {
            this.player.model_148.animationState_9c = 2; // pause
            this.loadRandomAdditionHit();
          }
        }
      }
    }

    this.fishingRod.renderRod(this.player.model_148.zOffset_a0 * 4);

    if(this.state.ordinal() >= FishingState.CASTING.ordinal()) {
      this.fishingRod.renderBobber();
    }

    if(this.state.ordinal() > FishingState.IDLE.ordinal()) {
      this.fishingRod.processString(3);
      this.fishingRod.renderString();
    }
  }

  @EventListener
  public void inputReleased(final InputReleasedEvent event) {
    if(event.action == INPUT_ACTION_SMAP_INTERACT.get()
            && isOnFishingPrimitive(this.currentCutFishingData)
            && !gameState_800babc8.indicatorsDisabled_4e3) {

      this.isFishEncounter = true;
      this.fishListScreen.isFishListScreenDisabled = true;

      SBtld.startEncounter(new Encounter(1, 0, 0, 0, 0, 0, 0, 0, 133, 6, new Encounter.Monster(1, new Vector3f())), 13);
      ((SMap)currentEngineState_8004dd04).smapLoadingStage_800cb430 = SubmapState.TRANSITION_TO_COMBAT_19;
    }
  }

  private void loadAnimations(final int fileIndex) {
    loadDrgnDir(0, fileIndex, files -> {
      this.player.combatant_144.mrg_04 = null;
      this.battle.attackAnimationsLoaded(files, this.player.combatant_144, false, this.player.combatant_144.charSlot_19c);
      // Finish asset loading - some animations need to be decompressed
      this.battle.FUN_800c9e10(this.player.combatant_144, this.loadingAnimIndex);
    });
  }

  private void handleBaitSelected(final String bait, final Runnable unloadBaitSelectionScreen) {
    unloadBaitSelectionScreen.run();
    this.fishListScreen.isFishListScreenDisabled = true;

    if(bait == null) {
      postBattleAction_800bc974 = 5;
      return;
    }

    this.bait = bait;
    this.cast();
  }

  private void cast() {
    this.fishingRod.initString();
    playSound(0x0, 0x15, 0x10, 0);
    this.loadAnimations(4031 + this.player.charId_272 * 8);
    this.loadingAnimIndex = 7; // Throw attack item
    this.castingTicks = 0;
    this.state = FishingState.CASTING;
  }

  private void onFishNibbling() {
    this.nibbleEndPos.set(this.fishingRod.bobberCoord2.coord.transfer);
    this.nibbleStartPos.set(this.nibbleEndPos).add(0.0f, 100.0f, 0.0f);
    this.nextBobTime = 0; // Bob immediately
    this.nextBobInterval = System.nanoTime() + BOB_INTERVAL;
    this.bobCount = 0;
    this.state = FishingState.NIBBLING;
  }

  private void onFishHooked() {
    this.additionScreen = new AdditionOverlayScreen();
    this.menuStack.pushScreen(this.additionScreen);
    this.loadRandomAdditionHit();
    this.state = FishingState.REELING;
  }

  private void onFishEscaped() {
    this.menuStack.pushScreen(new BaitSelectionScreen(this.meta, this::handleBaitSelected));
    this.fishListScreen.isFishListScreenDisabled = false;
  }

  private void loadRandomAdditionHit() {
    final int charId = this.player.charId_272;
    final int additionCount = additionCounts_8004f5c0[charId];
    final int randomAddition = this.rand.nextInt(additionCount);
    final int fileIndex = 4031 + charId * 8 + randomAddition;

    final int oldAddition = gameState_800babc8.charData_32c[charId].selectedAddition_19;
    gameState_800babc8.charData_32c[charId].selectedAddition_19 = randomAddition;
    loadAdditions();
    gameState_800babc8.charData_32c[charId].selectedAddition_19 = oldAddition;

    this.activeAddition = battlePreloadedEntities_1f8003f4.additionHits_38[0];
    int hitCount = 0;

    for(int i = 0; i < this.activeAddition.hits_00.length; i++) {
      if(this.activeAddition.hits_00[i].flags_00 != 0) {
        hitCount++;
      }
    }

    this.loadingAnimIndex = 16 + this.rand.nextInt(hitCount);
    this.additionTicks = this.activeAddition.hits_00[this.loadingAnimIndex - 16].totalFrames_01;

    this.loadAnimations(fileIndex);
    this.additionScreen.addHit();
  }

  public static boolean isOnFishingPrimitive(final FishLocationData locationData) {
    try {
      return ((SubmapObject210)scriptStatePtrArr_800bc1c0[10].innerStruct_00).collidedPrimitiveIndex_16c == locationData.collisionPrimitive();
    }catch(final Exception e) {
      return false;
    }
  }

  //TODO use this
  private void stopFishing() {
    this.fishingRod.delete();
    this.fishingRod = null;

    this.battle = null;
    this.player = null;

    this.additionScreen = null;

    this.state = FishingState.NOT_FISHING;
  }
}
