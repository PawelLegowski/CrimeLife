package pl.legowski.crimelife;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class MenuMap extends AppCompatActivity implements Runnable{
    private final static double DELTA_TIME = 0.03;
    public DrawMap drawerMap;
    public int dupcia = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerMap = new DrawMap(this);
        drawerMap.setMap(BitmapFactory.decodeResource(getResources(), R.mipmap.map));
        //drawerMap.setIcons(BitmapFactory.decodeResource(getResources(), R.mipmap.mapicons));
        //drawerMap.setAvatar(BitmapFactory.decodeResource(getResources(), R.mipmap.avatar));
        setContentView(drawerMap);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void run() {
        update(DELTA_TIME);
        drawerMap.drawMap();

    }

    private void update(double deltaTime) {

    }

    public class DrawMap extends SurfaceView {
        Thread thread;
        SurfaceHolder surfaceHolder;
        Bitmap map;
        Bitmap icons;
        Bitmap avatar;
        public DrawMap(Context context) {
            super(context);
            surfaceHolder = getHolder();
        }

        public void drawMap() {
        }

        public void setAvatar(Bitmap avatar) {
            this.avatar = avatar;
        }

        public void setIcons(Bitmap icons) {
            this.icons = icons;
        }

        public void setMap(Bitmap map) {
            this.map = map;
        }
    }
}
