package ru.hawoline.alonar.model.map;

public class LandscapeMap extends Map {
    public static final int GRASS = 0;
    public static int MOUNTAIN = 1;

    public LandscapeMap(int size) {
        super(size);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mMap[i][j] = GRASS;
            }
        }

        for (int i = 0; i < mMap.length; i++) {
            mMap[i][0] = MOUNTAIN;
        }
        for (int i = 0; i < mMap.length; i++) {
            mMap[0][i] = MOUNTAIN;
        }
        for (int i = 0; i < mMap.length; i++) {
            mMap[mMap.length - 1][i] = MOUNTAIN;
        }
        for (int i = 0; i < mMap.length; i++) {
            mMap[i][mMap.length - 1] = MOUNTAIN;
        }
    }
}
