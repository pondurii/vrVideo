/**
 * All you need to know about this class is that, when you give it the projectPos, pos and the context,
 * it will render that particular jpg in 3D split view for viewing.
 * <p/>
 * This class is part of the Rajawali Library, with small additions to it as per our need.
 */
package com.samskrut.vrvideo;

import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import org.rajawali3d.cardboard.RajawaliCardboardRenderer;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class MyRenderer extends RajawaliCardboardRenderer {


    public ContextWrapper contextWrapper;


    public MyRenderer(ContextWrapper c) {
        super(c);
        contextWrapper = c;

    }

    private static Sphere createPhotoSphereWithTexture(ATexture texture) {

        Material material = new Material();
        material.setColor(0);

        try {
            material.addTexture(texture);
        } catch (ATexture.TextureException e) {
            throw new RuntimeException(e);
        }

        Sphere sphere = new Sphere(50, 64, 32);
        sphere.setScaleX(-1);
        sphere.setMaterial(material);

        return sphere;
    }

    @Override
    protected void initScene() {
        InputStream fis = null;
        try {
            FileInputStream fiss = new FileInputStream(Environment.getExternalStorageDirectory() + File.separator + "ss.jpg");

            fis = new BufferedInputStream(fiss);

        } catch (Exception e) {

            Log.d("bis", "my render image e=" + e.getLocalizedMessage());
        }


        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        // Bitmap bitmap = BitmapFactory.decodeResource(contextWrapper.getResources(), R.raw.ss);
        Log.d("bis", "my render image bitmap" + bitmap.toString());

        Sphere sphere = createPhotoSphereWithTexture(new Texture("photo", bitmap));

        ///////////////////////////////////////////////////////////////////////////

        getCurrentScene().addChild(sphere);
        getCurrentCamera().setPosition(Vector3.ZERO);
        getCurrentCamera().setFieldOfView(75);
    }
}