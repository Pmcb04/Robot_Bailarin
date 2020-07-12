package com.bennyplo.android_mooc_graphics_3d;

import android.graphics.Canvas;
import android.graphics.Paint;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Cube {

    private Coordinate[]cube_vertices;//the vertices of a 3D cube
    private Coordinate[]draw_cube_vertices;//the vertices for drawing a 3D cube

    private int[] start = {0,1,3,2,4,5,7,6,0,1,2,3};
    private int[] end   = {1,3,2,0,5,7,6,4,4,5,6,7};

    public Cube(){

        cube_vertices = new Coordinate[8];
        cube_vertices[0] = new Coordinate(-1, -1, -1, 1);
        cube_vertices[1] = new Coordinate(-1, -1, 1, 1);
        cube_vertices[2] = new Coordinate(-1, 1, -1, 1);
        cube_vertices[3] = new Coordinate(-1, 1, 1, 1);
        cube_vertices[4] = new Coordinate(1, -1, -1, 1);
        cube_vertices[5] = new Coordinate(1, -1, 1, 1);
        cube_vertices[6] = new Coordinate(1, 1, -1, 1);
        cube_vertices[7] = new Coordinate(1, 1, 1, 1);

    }

    //*********************************
    //matrix and transformation functions
    public double []GetIdentityMatrix()
    {//return an 4x4 identity matrix
        double []matrix=new double[16];
        matrix[0]=1;matrix[1]=0;matrix[2]=0;matrix[3]=0;
        matrix[4]=0;matrix[5]=1;matrix[6]=0;matrix[7]=0;
        matrix[8]=0;matrix[9]=0;matrix[10]=1;matrix[11]=0;
        matrix[12]=0;matrix[13]=0;matrix[14]=0;matrix[15]=1;
        return matrix;
    }

    public Coordinate Transformation(Coordinate vertex,double []matrix)
    {//affine transformation with homogeneous coordinates
        //i.e. a vector (vertex) multiply with the transformation matrix
        // vertex - vector in 3D
        // matrix - transformation matrix
        Coordinate result=new Coordinate();
        result.x=matrix[0]*vertex.x+matrix[1]*vertex.y+matrix[2]*vertex.z+matrix[3];
        result.y=matrix[4]*vertex.x+matrix[5]*vertex.y+matrix[6]*vertex.z+matrix[7];
        result.z=matrix[8]*vertex.x+matrix[9]*vertex.y+matrix[10]*vertex.z+matrix[11];
        result.w=matrix[12]*vertex.x+matrix[13]*vertex.y+matrix[14]*vertex.z+matrix[15];
        return result;
    }
    public Coordinate[]Transformation(Coordinate []vertices,double []matrix)
    {   //Affine transform a 3D object with vertices
        // vertices - vertices of the 3D object.
        // matrix - transformation matrix
        Coordinate []result=new Coordinate[vertices.length];
        for (int i=0;i<vertices.length;i++)
        {
            result[i]=Transformation(vertices[i],matrix);
            result[i].Normalise();
        }
        return result;
    }


    //***********************************************************
    //Affine transformation
    public void translate(float tx,float ty,float tz)
    {
        double []matrix=GetIdentityMatrix();
        matrix[3]=tx;
        matrix[7]=ty;
        matrix[11]=tz;
        draw_cube_vertices = Transformation(cube_vertices,matrix);
    }
    public void scale(double sx,double sy,double sz)
    {
        double []matrix=GetIdentityMatrix();
        matrix[0]=sx;
        matrix[5]=sy;
        matrix[10]=sz;
        draw_cube_vertices = Transformation(draw_cube_vertices,matrix);
    }

    private  void DrawLinePairs(Canvas canvas, Coordinate[] vertices, int start, int end, Paint paint)
    {//draw a line connecting 2 points
        //canvas - canvas of the view
        //points - array of points
        //start - index of the starting point
        //end - index of the ending point
        //paint - the paint of the line
        canvas.drawLine((int)vertices[start].x,(int)vertices[start].y,(int)vertices[end].x,(int)vertices[end].y,paint);
    }

    public void rotateX(double alpha){
        double []matrix=GetIdentityMatrix();
        matrix[5]=Math.cos(alpha);
        matrix[6]=-Math.sin(alpha);
        matrix[9]=Math.sin(alpha);
        matrix[10]=Math.cos(alpha);
        draw_cube_vertices = Transformation(draw_cube_vertices,matrix);
    }

    public void rotateY(double alpha){
        double []matrix=GetIdentityMatrix();
        matrix[0]=Math.cos(alpha);
        matrix[2]=Math.sin(alpha);
        matrix[5] = 1;
        matrix[8]=-Math.sin(alpha);
        matrix[10]=Math.cos(alpha);
        matrix[15] = 1;
        cube_vertices = Transformation(draw_cube_vertices,matrix);
    }



    public void rotateZ(double alpha){
        double []matrix=GetIdentityMatrix();
        matrix[0]=Math.cos(alpha);
        matrix[1]=-Math.sin(alpha);
        matrix[4]=Math.sin(alpha);
        matrix[5]=Math.cos(alpha);
        draw_cube_vertices = Transformation(draw_cube_vertices,matrix);
    }

    public void draw(Canvas canvas, Paint paint)
    {//draw a cube on the screen

        int tam = start.length;
        int i = 0;

        while(i < tam){
            DrawLinePairs(canvas, draw_cube_vertices, start[i], end[i], paint);
            i++;
        }

    }

    public void QuaternionRotate(double theta, double abtX, double abtY, double abtZ)
    {
        double w = cos(Math.toRadians(theta/2));
        double x = sin(Math.toRadians(theta/2)) * abtX;
        double y = sin(Math.toRadians(theta/2)) * abtY ;
        double z = sin(Math.toRadians(theta/2)) * abtZ;

        double []matrix = GetIdentityMatrix();
        matrix[0] = w*w + x*x - y*y - z*z; // cos2 + sinX2 -sinY2 - sinZ2
        matrix[1] = 2*x*y - 2*w*z; // 2*sinX*siny - 2*cos*sinz
        matrix[2] = 2*x*z + 2*w*y;
        matrix[3] = 0;
        matrix[4] = 2*x*y + 2*w*z;
        matrix[5] = w*w + y*y - x*x - z*z;
        matrix[6] = 2*y*z - 2*w*x;
        matrix[7] = 0;
        matrix[8] = 2*x*z -2*w*y;
        matrix[9] = 2*y*z + 2*w*x;
        matrix[10] = w*w + z*z - x*x - y*y;
        matrix[11] = 0;
        matrix[12] = 0;
        matrix[13] = 0;
        matrix[14] = 0;
        matrix[15] = 1;

        printMatrix(matrix);

        draw_cube_vertices =  Transformation(draw_cube_vertices, matrix);



    }

    public void printMatrix(double[] matrix){
        for (int i = 0; i < matrix.length; i++){
            System.out.println(matrix[i] + " ");
            if(i%4 == 0) System.out.println();
        }
    }

}
