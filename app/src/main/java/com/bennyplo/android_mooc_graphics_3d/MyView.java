package com.bennyplo.android_mooc_graphics_3d;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.Timer;
import java.util.TimerTask;

public class MyView extends View {

    private Paint red, blue, cyan, green, magenta;
    private int w, h;

    // siempre pensar en que se esta construyendo el punto del centro del cubo


    private Cube head = new Cube(); // cabeza
    private Cube neck = new Cube(); // cuello
    private Cube body = new Cube(); // torso
    private Cube rShoulder = new Cube(); // hombro derecho
    private Cube lShoulder = new Cube(); // hombro izquierdo
    private Cube rArm = new Cube();// brazo derecho
    private Cube lArm = new Cube(); // brazo izquierdo
    private Cube rHand = new Cube(); // mano derecha
    private Cube lHand = new Cube(); // mano izquierda
    private Cube hip = new Cube(); // cadera
    private Cube rThigh = new Cube(); // muslo derecho
    private Cube lThigh = new Cube(); // muslo izquierdo
    private Cube rLeg = new Cube(); // pierna derecha
    private Cube lLeg = new Cube(); // pierna izquierda
    private Cube rFoot = new Cube(); // pie derecho
    private Cube lFoot = new Cube(); // pie izquierdo

    private enum Coordinates { X, Y, Z};

    /*
     [0] -> tx
     [1] -> ty
     [2] -> tz
     */
    private float[] traslateHead;
    private float[] traslateNeck ;
    private float[] traslateBody;
    private float[] traslateRightSchoulder;
    private float[] traslateLeftSchoulder;
    private float[] traslateRightArm;
    private float[] traslateLeftArm;
    private float[] traslateRightHand;
    private float[] traslateLeftHand;
    private float[] traslateHip;
    private float[] traslateRightThigh;
    private float[] traslateLeftThigh;
    private float[] traslateRightLeg;
    private float[] traslateLeftLeg;
    private float[] traslateRightFoot;
    private float[] traslateLeftFoot;

    /*
     [0] -> sx
     [1] -> sy
     [2] -> sz
     */
    private float[] scaleHead      = { 80  , 80   , 80  };
    private float[] scaleNeck      = { 25  , 25   , 25  };
    private float[] scaleBody      = { 180 , 200  , 80  };
    private float[] scaleSchoulder = { 67  , 75   , 80  };
    private float[] scaleArm       = { 67  , 100  , 80  };
    private float[] scaleHand      = { 67  , 40   , 120 };
    private float[] scaleHip       = { 180 , 50   , 80  };
    private float[] scaleThigh     = { 67  , 100  , 80  };
    private float[] scaleLeg       = { 67  , 125  , 80  };
    private float[] scaleFoot      = { 67  , 30   , 120 };



    public MyView(Context context, int w, int h) {

        super(context, null);


        red = newColor(Color.RED);
        blue = newColor(Color.BLUE);
        cyan = newColor(Color.CYAN);
        green = newColor(Color.GREEN);
        magenta = newColor(Color.MAGENTA);


        traslateHead = new float[]{calculateTraslation(w/2, scaleHead[coordinate("X")]), calculateTraslation(h/8, scaleHead[coordinate("Y")]), calculateTraslation(800, scaleHead[coordinate("Z")])};
        traslateNeck = new float[]{calculateTraslation(w/2, scaleNeck[coordinate("X")] ), calculateTraslation(h/8 + 105, scaleNeck[coordinate("Y")]), calculateTraslation(800, scaleNeck[coordinate("Z")])};
        traslateBody = new float[]{calculateTraslation(w/2, scaleBody[coordinate("X")]), calculateTraslation(h/4 + 105, scaleBody[coordinate("Y")]), calculateTraslation(800, scaleBody[coordinate("Z")])};
        traslateHip = new float[]{calculateTraslation(w/2, scaleHip[coordinate("X")]), calculateTraslation(h/2 - 90, scaleHip[coordinate("Y")]), calculateTraslation(800, scaleHip[coordinate("Z")])};

        traslateLeftSchoulder = new float[]{calculateTraslation(w/6 + 5*w/48, scaleSchoulder[coordinate("X")]), calculateTraslation(h/8 + 205, scaleSchoulder[coordinate("Y")]), calculateTraslation(800, scaleSchoulder[coordinate("Z")])};
        traslateLeftArm = new float[]{calculateTraslation(w/6 + 5*w/48, scaleArm[coordinate("X")]), calculateTraslation(h/8 + 380, scaleArm[coordinate("Y")]), calculateTraslation(800, scaleArm[coordinate("Z")])};
        traslateLeftHand = new float[]{calculateTraslation(w/6 + 5*w/48 , scaleHand[coordinate("X")]), calculateTraslation(h/8 + 520, scaleHand[coordinate("Y")]), calculateTraslation(760, scaleHand[coordinate("Z")])};

        traslateRightSchoulder = new float[]{calculateTraslation(w/2 + w/6 + 3*w/48, scaleSchoulder[coordinate("X")]), calculateTraslation(h/8 + 205, scaleSchoulder[coordinate("Y")]), calculateTraslation(800, scaleSchoulder[coordinate("Z")])};
        traslateRightArm = new float[]{calculateTraslation(w/2 + w/6 + 3*w/48, scaleArm[coordinate("X")]), calculateTraslation(h/8 + 380, scaleArm[coordinate("Y")]), calculateTraslation(800, scaleArm[coordinate("Z")])};
        traslateRightHand = new float[]{calculateTraslation(w/2 + w/6 + 3*w/48, scaleHand[coordinate("X")]), calculateTraslation(h/8 + 520, scaleHand[coordinate("Y")]), calculateTraslation(760, scaleHand[coordinate("Z")])};

        traslateLeftThigh = new float[]{calculateTraslation(w/3 + w/24 + w/48, scaleThigh[coordinate("X")]), calculateTraslation(h/2 + 60, scaleThigh[coordinate("Y")]), calculateTraslation(800, scaleThigh[coordinate("Z")])};
        traslateLeftLeg = new float[]{calculateTraslation(w/3 + w/24 + w/48, scaleLeg[coordinate("X")]), calculateTraslation(h/2 + 285,scaleLeg[coordinate("Y")]), calculateTraslation(800, scaleLeg[coordinate("Z")])};
        traslateLeftFoot = new float[]{calculateTraslation(w/3 + w/24 + w/48, scaleFoot[coordinate("X")]), calculateTraslation(h/2 + 440, scaleFoot[coordinate("Y")]), calculateTraslation(760, scaleFoot[coordinate("Z")])};

        traslateRightThigh = new float[]{calculateTraslation(2*w/3 - w/24 - w/48, scaleThigh[coordinate("X")]), calculateTraslation(h/2 + 60, scaleThigh[coordinate("Y")]), calculateTraslation(800, scaleThigh[coordinate("Z")])};
        traslateRightLeg = new float[]{calculateTraslation(2*w/3 - w/24 - w/48, scaleLeg[coordinate("X")]), calculateTraslation(h/2 + 285, scaleLeg[coordinate("Y")]), calculateTraslation(800, scaleLeg[coordinate("Z")])};
        traslateRightFoot = new float[]{calculateTraslation(2*w/3 - w/24 - w/48, scaleFoot[coordinate("X")]), calculateTraslation(h/2 + 440, scaleFoot[coordinate("Y")]), calculateTraslation(760, scaleFoot[coordinate("Z")])};

        this.w = w;
        this.h = h-70;

        System.out.println("ACCIONES TRASLADAR Y ESCALAR");


        head.translate(traslateHead[coordinate("X")], traslateHead[coordinate("Y")], traslateHead[coordinate("Z")]);
        head.scale(scaleHead[coordinate("X")], scaleHead[coordinate("Y")],scaleHead[coordinate("Z")]);

        neck.translate(traslateNeck[coordinate("X")], traslateNeck[coordinate("Y")], traslateNeck[coordinate("Z")]);
        neck.scale(scaleNeck[coordinate("X")], scaleNeck[coordinate("Y")],scaleNeck[coordinate("Z")]);

        body.translate(traslateBody[coordinate("X")],traslateBody[coordinate("Y")],traslateBody[coordinate("Z")]);
        body.scale(scaleBody[coordinate("X")], scaleBody[coordinate("Y")],scaleBody[coordinate("Z")]);

        hip.translate(traslateHip[coordinate("X")],traslateHip[coordinate("Y")],traslateHip[coordinate("Z")]);
        hip.scale(scaleHip[coordinate("X")], scaleHip[coordinate("Y")],scaleHip[coordinate("Z")]);

        rShoulder.translate(traslateRightSchoulder[coordinate("X")],traslateRightSchoulder[coordinate("Y")],traslateRightSchoulder[coordinate("Z")]);
        rShoulder.scale(scaleSchoulder[coordinate("X")], scaleSchoulder[coordinate("Y")],scaleSchoulder[coordinate("Z")]);

        rArm.translate(traslateRightArm[coordinate("X")],traslateRightArm[coordinate("Y")],traslateRightArm[coordinate("Z")]);
        rArm.scale(scaleArm[coordinate("X")], scaleArm[coordinate("Y")],scaleArm[coordinate("Z")]);

        rHand.translate(traslateRightHand[coordinate("X")],traslateRightHand[coordinate("Y")],traslateRightHand[coordinate("Z")]);
        rHand.scale(scaleHand[coordinate("X")], scaleHand[coordinate("Y")],scaleHand[coordinate("Z")]);

        lShoulder.translate(traslateLeftSchoulder[coordinate("X")],traslateLeftSchoulder[coordinate("Y")],traslateLeftSchoulder[coordinate("Z")]);
        lShoulder.scale(scaleSchoulder[coordinate("X")], scaleSchoulder[coordinate("Y")],scaleSchoulder[coordinate("Z")]);

        lArm.translate(traslateLeftArm[coordinate("X")],traslateLeftArm[coordinate("Y")],traslateLeftArm[coordinate("Z")]);
        lArm.scale(scaleArm[coordinate("X")], scaleArm[coordinate("Y")],scaleArm[coordinate("Z")]);

        lHand.translate(traslateLeftHand[coordinate("X")],traslateLeftHand[coordinate("Y")],traslateLeftHand[coordinate("Z")]);
        lHand.scale(scaleHand[coordinate("X")], scaleHand[coordinate("Y")],scaleHand[coordinate("Z")]);

        rThigh.translate(traslateRightThigh[coordinate("X")],traslateRightThigh[coordinate("Y")],traslateRightThigh[coordinate("Z")]);
        rThigh.scale(scaleThigh[coordinate("X")], scaleThigh[coordinate("Y")],scaleThigh[coordinate("Z")]);

        rLeg.translate(traslateRightLeg[coordinate("X")],traslateRightLeg[coordinate("Y")],traslateRightLeg[coordinate("Z")]);
        rLeg.scale(scaleLeg[coordinate("X")], scaleLeg[coordinate("Y")],scaleLeg[coordinate("Z")]);

        rFoot.translate(traslateRightFoot[coordinate("X")],traslateRightFoot[coordinate("Y")],traslateRightFoot[coordinate("Z")]);
        rFoot.scale(scaleFoot[coordinate("X")], scaleFoot[coordinate("Y")],scaleFoot[coordinate("Z")]);

        lThigh.translate(traslateLeftThigh[coordinate("X")],traslateLeftThigh[coordinate("Y")],traslateLeftThigh[coordinate("Z")]);
        lThigh.scale(scaleThigh[coordinate("X")], scaleThigh[coordinate("Y")],scaleThigh[coordinate("Z")]);

        lLeg.translate(traslateLeftLeg[coordinate("X")],traslateLeftLeg[coordinate("Y")],traslateLeftLeg[coordinate("Z")]);
        lLeg.scale(scaleLeg[coordinate("X")], scaleLeg[coordinate("Y")],scaleLeg[coordinate("Z")]);

        lFoot.translate(traslateLeftFoot[coordinate("X")],traslateLeftFoot[coordinate("Y")],traslateLeftFoot[coordinate("Z")]);
        lFoot.scale(scaleFoot[coordinate("X")], scaleFoot[coordinate("Y")],scaleFoot[coordinate("Z")]);


        //RotateRobot(0,0,0);

        updateView();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            float position = 1;
            boolean dir = true;

            @Override
            public void run() {
               if(position == 0)
                    dir = true;
                else if(position > 10)
                    dir = false;

                if(dir)
                {
                    position++;
                    RotateRobot(0 , 1, 0);
                }
                else
                {
                    position--;
                    RotateRobot(0, -1, 0);
                }

                System.out.println("dir " + dir + " position " + position);
                updateView();
            }
        };
        timer.scheduleAtFixedRate(task,100,100);

    }


    public void RotateRobot(double thetaX, double thetaY, double thetaZ)
    {
       // System.out.println("ROTANDO CABEZA");

        head.QuaternionRotate(thetaX, 1,0,0);
        head.QuaternionRotate(thetaY, 0,1,0);
        head.QuaternionRotate(thetaZ,0,0,1);

       // System.out.println("ROTANDO CUELLO");

        neck.QuaternionRotate(thetaX, 1,0,0);
        neck.QuaternionRotate(thetaY, 0,1,0);
        neck.QuaternionRotate(thetaZ, 0,0,1);

       // System.out.println("ROTANDO CUERPO");

        body.QuaternionRotate(thetaX, 1,0,0);
        body.QuaternionRotate(thetaY, 0,1,0);
        body.QuaternionRotate(thetaZ, 0,0,1);

     //   System.out.println("ROTANDO HOMBRO DER");

        rShoulder.QuaternionRotate(thetaX, 1,0,0);
        rShoulder.QuaternionRotate(thetaY, 0,1,0);
        rShoulder.QuaternionRotate(thetaZ, 0,0,1);

      //  System.out.println("ROTANDO BRAZO DER");

        rArm.QuaternionRotate(thetaX, 1,0,0);
        rArm.QuaternionRotate(thetaY, 0,1,0);
        rArm.QuaternionRotate(thetaZ, 0,0,1);

      //  System.out.println("ROTANDO MANO DER");

        rHand.QuaternionRotate(thetaX, 1,0,0);
        rHand.QuaternionRotate(thetaY, 0,1,0);
        rHand.QuaternionRotate(thetaZ, 0,0,1);

     //   System.out.println("ROTANDO HOMBRO IZQ");

        lShoulder.QuaternionRotate(thetaX, 1,0,0);
        lShoulder.QuaternionRotate(thetaY, 0,1,0);
        lShoulder.QuaternionRotate(thetaZ, 0,0,1);

    //    System.out.println("ROTANDO BRAZO IZQ");

        lArm.QuaternionRotate(thetaX, 1,0,0);
        lArm.QuaternionRotate(thetaY, 0,1,0);
        lArm.QuaternionRotate(thetaZ, 0,0,1);

    //    System.out.println("ROTANDO MANO IZQ");

        lHand.QuaternionRotate(thetaX, 1,0,0);
        lHand.QuaternionRotate(thetaY, 0,1,0);
        lHand.QuaternionRotate(thetaZ, 0,0,1);

     //   System.out.println("ROTANDO CADERA");

        hip.QuaternionRotate(thetaX, 1,0,0);
        hip.QuaternionRotate(thetaY, 0,1,0);
        hip.QuaternionRotate(thetaZ, 0,0,1);

   //     System.out.println("ROTANDO MUSLO DER");

        rThigh.QuaternionRotate(thetaX, 1,0,0);
        rThigh.QuaternionRotate(thetaY, 0,1,0);
        rThigh.QuaternionRotate(thetaZ, 0,0,1);

    //    System.out.println("ROTANDO PIERNA DER");

        rLeg.QuaternionRotate(thetaX, 1,0,0);
        rLeg.QuaternionRotate(thetaY, 0,1,0);
        rLeg.QuaternionRotate(thetaZ, 0,0,1);

     //   System.out.println("ROTANDO PIE DER");

        rFoot.QuaternionRotate(thetaX, 1,0,0);
        rFoot.QuaternionRotate(thetaY, 0,1,0);
        rFoot.QuaternionRotate(thetaZ, 0,0,1);

     //   System.out.println("ROTANDO MUSLO IZQ");

        lThigh.QuaternionRotate(thetaX, 1,0,0);
        lThigh.QuaternionRotate(thetaY, 0,1,0);
        lThigh.QuaternionRotate(thetaZ, 0,0,1);

       // System.out.println("ROTANDO PIERNA IZQ");

        lLeg.QuaternionRotate(thetaX, 1,0,0);
        lLeg.QuaternionRotate(thetaY, 0,1,0);
        lLeg.QuaternionRotate(thetaZ, 0,0,1);

      //  System.out.println("ROTANDO PIE IZQ");

        lFoot.QuaternionRotate(thetaX, 1,0,0);
        lFoot.QuaternionRotate(thetaY, 0,1,0);
        lFoot.QuaternionRotate(thetaZ, 0,0,1);
    }



    public int coordinate(String s){
        switch (s){
            case "X":
                return Coordinates.X.ordinal();

            case "Y":
                return Coordinates.Y.ordinal();

            case "Z":
                return Coordinates.Z.ordinal();
        }

        return 0;
    }

    public static Paint newColor(int color){

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStrokeWidth(2);
        p.setColor(color);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setAntiAlias(true);

        return p;
    }

    private void updateView(){
        this.invalidate();
    }

    public static float calculateTraslation(float number, float scale){
        return number/scale;
    }


    private void LinesDesign(Canvas canvas){

        // lineas exteriores
        canvas.drawLine(0, 50, w, 50, magenta);
        canvas.drawLine(0, h-50, w, h-50, magenta);
        canvas.drawLine(50, 0, 50, h, magenta);
        canvas.drawLine(w-50, 0, w-50, h, magenta);


        // lineas de medida horizontales
        canvas.drawLine(0, h/8, w, h/8, green);
        canvas.drawLine(0, h/4, w, h/4, green);
        canvas.drawLine(0, h/2, w, h/2, green);
        canvas.drawLine(0, h/2 + h/4, w, h/2 + h/4, green);
        canvas.drawLine(0, h/2 + h/4 + h/8, w, h/2 + h/4 + h/8, green);

        // lineas de medida verticales
        canvas.drawLine(w/2, 0, w/2, h, blue);
        canvas.drawLine(w/3, 0, w/3, h, blue);
        canvas.drawLine(w/6, 0, w/6, h, blue);
        canvas.drawLine(w/2 + w/6, 0, w/2 + w/6, h, blue);
        canvas.drawLine(w/2 + w/3, 0, w/2 + w/3, h, blue);

        canvas.drawLine(w/6 + 5*w/48, 0, w/6 + 5*w/48, h, red);
        canvas.drawLine(w/2 + w/6 + 3*w/48, 0, w/2 + w/6 + 3*w/48, h, red);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw objects on the screen
        super.onDraw(canvas);

        head.draw(canvas, blue);
        neck.draw(canvas, magenta);
        body.draw(canvas, red);
        hip.draw(canvas, magenta);

        rShoulder.draw(canvas, blue);
        rArm.draw(canvas, green);
        rHand.draw(canvas, cyan);

        lShoulder.draw(canvas, blue);
        lArm.draw(canvas, green);
        lHand.draw(canvas, cyan);

        rThigh.draw(canvas, blue);
        rLeg.draw(canvas, green);
        rFoot.draw(canvas, red);

        lThigh.draw(canvas, blue);
        lLeg.draw(canvas, green);
        lFoot.draw(canvas, red);

       // LinesDesign(canvas);
    }
}