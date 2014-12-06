package com.jimulabs.googlemusicmock;

/**
 * Created by lintonye on 14-12-01.
 */
public class AnimatorScripts {
    public final static AnimatorScript_AlbumDetails albumDetails = new AnimatorScript_AlbumDetails();

    public static void deinit() {
        albumDetails.deinit();
    }
}
