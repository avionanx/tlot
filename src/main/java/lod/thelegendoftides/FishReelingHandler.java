package lod.thelegendoftides;

public class FishReelingHandler {
    private float stamina;
    private final float MAX_STAMINA;
    private int chains;
    private final int MAX_CHAINS = 3;
    private final float CHAIN_BONUS = 1.0f;
    private final float ADDITION_FAIL_STAMINA = 2.0f;
    private final float ADDITION_SUCCESS_STAMINA = 2.0f;

    private final Runnable fishCaptured;
    private final Runnable fishLost;

    public FishReelingHandler(final Runnable fishCaptured, final Runnable fishLost) {
        this.MAX_STAMINA = 30.0f;
        this.stamina = this.MAX_STAMINA / 2.0f;
        this.fishCaptured = fishCaptured;
        this.fishLost = fishLost;
    }

    public void additionSuccessHandler() {
        this.stamina = Math.max(this.stamina - ADDITION_SUCCESS_STAMINA - this.chains * this.CHAIN_BONUS, 0.0f);
        this.chains = Math.min(this.chains + 1, this.MAX_CHAINS);

        if(this.stamina == 0.0f) {
            this.fishCaptured.run();
        }
    }

    public void additionFailCallback() {
        this.chains = 0;
        this.stamina = Math.min(this.stamina + ADDITION_FAIL_STAMINA, this.MAX_STAMINA);

        if(this.stamina == this.MAX_STAMINA) {
            this.fishLost.run();
        }
    }

    public float getStaminaFraction() {
      return Math.clamp(this.stamina / this.MAX_STAMINA, 0.0f, 1.0f);
    }
}
