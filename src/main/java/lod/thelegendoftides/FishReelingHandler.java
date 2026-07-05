package lod.thelegendoftides;

public class FishReelingHandler {
  private float stamina;
  private int chains;
  private final int MAX_CHAINS = TlotLevelHelpers.TLOT_GET_MAX_CHAINS();
  private final float CHAIN_BONUS = 1.0f;
  private final float ADDITION_FAIL_STAMINA = TlotLevelHelpers.TLOT_GET_PERCENTAGE_LOSS();
  private final float ADDITION_SUCCESS_STAMINA = TlotLevelHelpers.TLOT_GET_PROGRESS_INCREMENT();

  private final Fish fish;
  private final Runnable fishCaptured;
  private final Runnable fishLost;

  public FishReelingHandler(final Fish fish, final Runnable fishCaptured, final Runnable fishLost) {
    this.fish = fish;

    this.stamina = this.fish.stamina * (1 - TlotLevelHelpers.TLOT_GET_INITIAL_PROGRESS());
    this.fishCaptured = fishCaptured;
    this.fishLost = fishLost;
  }

  public void tick() {
    this.stamina = Math.min(this.fish.stamina, this.stamina + this.fish.strength / 10.0f * TlotLevelHelpers.TLOT_GET_FADE());
  }

  public void additionSuccessHandler() {
    this.stamina = Math.max(this.stamina - this.ADDITION_SUCCESS_STAMINA - this.chains * this.CHAIN_BONUS, 0.0f);
    this.chains = Math.min(this.chains + 1, this.MAX_CHAINS);

    if(this.stamina == 0.0f) {
      this.fishCaptured.run();
    }
  }

  public void additionFailCallback() {
    this.chains = 0;
    this.stamina = Math.min(this.stamina + this.fish.stamina * this.ADDITION_FAIL_STAMINA, this.fish.stamina);

    if(this.stamina >= this.fish.stamina) {
      this.fishLost.run();
    }
  }

  public float getStaminaFraction() {
    return Math.clamp(this.stamina / this.fish.stamina, 0.0f, 1.0f);
  }
}
