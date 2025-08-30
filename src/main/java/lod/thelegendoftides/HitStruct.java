package lod.thelegendoftides;

import java.util.ArrayList;

public record HitStruct(float shadowColour, int frameBeginTime, int numSuccessFrames, ArrayList<BorderStruct> borders) {
}
