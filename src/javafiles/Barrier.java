package javafiles;

import edu.princeton.cs.introcs.StdDraw;
import java.util.LinkedList;

// Calculate centroid which, for convex polygons, is on the correct side of each line formed
// by the sides of the polygon.  Compare which side the centroid is on to which side the point
// being checked is on, and if any side differs, point is not inside the polygon.

public class Barrier{

    public static LinkedList<Barrier> barriers = new LinkedList<Barrier>();
    public static LevelBarriers levelBarriers = new LevelBarriers();
    public static Barrier overlappingBarrier = null;
    public double[] xCords;
    public double[] yCords;
    public double[] centroid;
    public boolean touched = false;
    public static int barrierRed = 13;
    public static int barrierGreen = 13;
    public static int barrierBlue = 13;
    public static int barrierTouchedRed = 60;
    public static int barrierTouchedGreen = 60;
    public static int barrierTouchedBlue = 60;
    
    //public int index;
    //public static int count = 0;

    public Barrier(double[] xCords, double[] yCords){
        this.xCords = xCords;
        this.yCords = yCords;
        double centroidX = 0;
        double centroidY = 0;
        for(int i = 0; i < xCords.length; ++i){
            centroidX += xCords[i];
            centroidY += yCords[i];
        }
        centroidX /= xCords.length;
        centroidY /= yCords.length;
        centroid = new double[]{centroidX, centroidY};
    }
    

    public static double[] overlaps(double[] ballCenter, double ballRadius){
        overlappingBarrier = null;
        double[][] norms = new double[][]{};
        double[][] closestPointsToBall = new double[][]{};
        for(Barrier barrier : barriers){
            if(overlappingBarrier != null){
                break;
            }
            norms = new double[barrier.xCords.length][2];
            closestPointsToBall = new double[barrier.xCords.length][2];
            double dy;
            double dx;
            double edgeSlope;
            double[] ballNorm = new double[2];
            for(int vertexNum = 0; vertexNum < barrier.xCords.length; ++vertexNum){
                dy = barrier.yCords[(vertexNum+1)%barrier.xCords.length]-barrier.yCords[vertexNum];
                dx = barrier.xCords[(vertexNum+1)%barrier.xCords.length]-barrier.xCords[vertexNum];
                edgeSlope = dy/dx;
                if(Math.abs(edgeSlope) == 0){
                    ballNorm[0] = 0;
                    ballNorm[1] = ballCenter[1]-barrier.yCords[vertexNum];
                    ballNorm[1] -= Math.signum(barrier.yCords[vertexNum]-barrier.centroid[1])*ballRadius;
                    closestPointsToBall[vertexNum][0] = ballCenter[0];
                    closestPointsToBall[vertexNum][1] = barrier.yCords[vertexNum];
                    if(Math.signum(ballNorm[1]) != Math.signum(barrier.centroid[1]-barrier.yCords[vertexNum])){
                        break;
                    }
                    else{
                        norms[vertexNum][0] = ballNorm[0];
                        norms[vertexNum][1] = ballNorm[1];
                    }
                }
                else if(Double.isInfinite(edgeSlope)){
                    ballNorm[0] = ballCenter[0]-barrier.xCords[vertexNum];
                    ballNorm[1] = 0;
                    ballNorm[0] -= Math.signum(barrier.xCords[vertexNum]-barrier.centroid[0])*ballRadius;
                    closestPointsToBall[vertexNum][0] = barrier.xCords[vertexNum];
                    closestPointsToBall[vertexNum][1] = ballCenter[1];
                    if(Math.signum(barrier.centroid[0]-barrier.xCords[vertexNum]) != Math.signum(ballNorm[0])){
                        break;
                    }
                    else{
                        norms[vertexNum][0] = ballNorm[0];
                        norms[vertexNum][1] = ballNorm[1];
                    }
                }
                else{
                    double hypCentroidY;
                    double hypPointY;
                    double edgeAngle;
                    double ballCenterProjLength;
                    double centroidProjLength;
                    double[] closestPointToCentroid = new double[2];
                    double[] centroidNorm = new double[2];
                    double ballNormAngle;
                    hypCentroidY = edgeSlope*(barrier.centroid[0]-barrier.xCords[vertexNum]) + barrier.yCords[vertexNum];
                    hypPointY = edgeSlope*(ballCenter[0]-barrier.xCords[vertexNum]) + barrier.yCords[vertexNum];
                    edgeAngle = Math.atan(edgeSlope);
                    ballCenterProjLength = 0;
                    centroidProjLength = 0;
                    ballCenterProjLength += Math.cos(edgeAngle)*(ballCenter[0]-barrier.xCords[vertexNum]);
                    ballCenterProjLength += Math.sin(edgeAngle)*(ballCenter[1]-barrier.yCords[vertexNum]);
                    centroidProjLength += Math.cos(edgeAngle)*(barrier.centroid[0]-barrier.xCords[vertexNum]);
                    centroidProjLength += Math.sin(edgeAngle)*(barrier.centroid[1]-barrier.yCords[vertexNum]);
                    closestPointsToBall[vertexNum][0] = barrier.xCords[vertexNum]+ballCenterProjLength*Math.cos(edgeAngle);
                    closestPointsToBall[vertexNum][1] = barrier.yCords[vertexNum]+ballCenterProjLength*Math.sin(edgeAngle);
                    closestPointToCentroid[0] = barrier.xCords[vertexNum]+centroidProjLength*Math.cos(edgeAngle);
                    closestPointToCentroid[1] = barrier.yCords[vertexNum]+centroidProjLength*Math.sin(edgeAngle);
                    ballNorm[0] = ballCenter[0]-closestPointsToBall[vertexNum][0];
                    ballNorm[1] = ballCenter[1]-closestPointsToBall[vertexNum][1];
                    centroidNorm[0] = barrier.centroid[0]-closestPointToCentroid[0];
                    centroidNorm[1] = barrier.centroid[1]-closestPointToCentroid[1];
                    ballNormAngle = Math.atan2(ballNorm[1], ballNorm[0]);
                    if(Math.signum(hypCentroidY-barrier.centroid[1]) == Math.signum(hypPointY-ballCenter[1])){
                        ballNorm[0] += Math.cos(ballNormAngle)*ballRadius;
                        ballNorm[1] += Math.sin(ballNormAngle)*ballRadius;
                    }
                    else{
                        ballNorm[0] -= Math.cos(ballNormAngle)*ballRadius;
                        ballNorm[1] -= Math.sin(ballNormAngle)*ballRadius;
                    }
                    if(Math.signum(Math.atan2(ballNorm[1], ballNorm[0])) != Math.signum(Math.atan2(centroidNorm[1], centroidNorm[0]))){
                        break;
                    }
                    else{
                        norms[vertexNum][0] = ballNorm[0];
                        norms[vertexNum][1] = ballNorm[1];
                    }
                }
                
                if(vertexNum == barrier.xCords.length-1){
                    overlappingBarrier = barrier;
                }
            }
        }
        if(overlappingBarrier != null){
            double minDistance = Double.MAX_VALUE;
            int indexToReturn = 0;
            for(int i = 0; i < norms.length; ++i){
            //for(double[] norm : norms){
                double distance = Math.hypot(norms[i][0], norms[i][1]);
                if(distance < minDistance){
                    minDistance = distance;
                    //normToReturn = norms[i];
                    indexToReturn = i;
                }
            }
            return norms[indexToReturn];
        }
        return new double[3];
    }
        
    public static boolean aliveBarrier(){
        for(Barrier barrier : barriers){
            if(!barrier.touched){
                return true;
            }
        }
        return false;
    }

    public static void getLevelBarriers(int level){
        barriers = LevelBarriers.levelBarriers.get(level);
    }



    public static void resetBarriers(){
        for(Barrier barrier : barriers){
            barrier.touched = false;
        }
    }

    public static void drawBarriers(){
        for(Barrier barrier : barriers){
            if(barrier.touched){
                StdDraw.setPenColor(barrierTouchedRed, barrierTouchedGreen, barrierTouchedBlue);
            }
            else{
                StdDraw.setPenColor(barrierRed, barrierGreen, barrierBlue);
            }
            StdDraw.filledPolygon(barrier.xCords, barrier.yCords);
        }
    }

    public static void main(String[] args) {

    }


}