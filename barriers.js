class PolygonBarrier{
    constructor(xCords, yCords){
        this.xCords = xCords;
        this.yCords = yCords;
        this.color = "black";
        this.touched = false;
        this.touchedColor = "grey";
    };

    draw(){
        ctx.fillStyle = this.touched ? this.touchedColor : this.color;
        ctx.beginPath();
        ctx.moveTo(this.xCords[0], this.yCords[0]);
        for(let i = 1; i < this.xCords.length; i++){
            ctx.lineTo(this.xCords[i], this.yCords[i]);
        }
        ctx.closePath();
        ctx.fill();
    }

    collidingWith(point, radius){
        /* m = (y2-y1)/(x2-x1)
        *  y2-y1 = m(x2-x1)
        *  let y2 = y, x2 = x
        *  y = mx - mx1 + y1
        *  b = -mx1 + y1
        *  point[1] = -point[0]/m + b2
        *  b2 = point[1] + point[0]/m
        * 
        *  -x/m + point[1] + point[0]/m = mx - mx1 + y1
        *  -x + mpoint[1] + point[0] = m^2x - m^2x1 + my1
        *  x(m^2 + 1) = m^2x1 - my1 + mpoint[1] + point[0]
        *  x = (m(mx1 - y1 + point[1]) + point[0])/(m^2 + 1);
        */
        for(let vertexIdx = 0; vertexIdx < this.xCords.length; vertexIdx++){
            let nextIndex = (vertexIdx + 1) % this.xCords.length;
            let x1 = this.xCords[vertexIdx];
            let y1 = this.yCords[vertexIdx];
            let x2 = this.xCords[nextIndex];
            let y2 = this.yCords[nextIndex];
            // infinite slope
            if(x1 == x2){
                let minY = Math.min(y1, y2);
                let maxY = Math.max(y1, y2);
                if(point[0]+radius < x1 || point[0]-radius > x1 || point[1]+radius < minY || point[1]-radius > maxY){
                    continue;
                }
                this.touched = true;
                return Number.MAX_VALUE;
            }
            // zero slope
            if(y1 == y2){
                let minX = Math.min(x1, x2);
                let maxX = Math.max(x1, x2);
                if(point[1]+radius < y1 || point[1]-radius > y1 || point[0]+radius < minX || point[0]-radius > maxX){
                    continue;
                }
                this.touched = true;
                return 0;
            }
            // nonzero noninfinite slope
            let minX = Math.min(x1, x2);
            let maxX = Math.max(x1, x2);
            let minY = Math.min(y1, y2);
            let maxY = Math.max(y1, y2);
            if(point[0]+radius < minX || point[0]-radius > maxX || point[1]+radius < minY || point[1]-radius > maxY){
                continue;
            }
            let m = (y2 - y1)/(x2 - x1);
            let b2 = point[1] + point[0]/m;
            //let xEdgeClosest = (m*(point[1]+x2-y2)+point[0])/(m*m+1); 
            let xEdgeClosest = (m*(m*x1 - y1 + point[1]) + point[0])/(m*m + 1);
            /*if(xEdgeClosest+radius < minX || xEdgeClosest-radius > maxX){
                continue;
            }*/
            let yEdgeClosest = -xEdgeClosest/m + b2;
            /*if(yEdgeClosest+radius < minY || yEdgeClosest-radius > maxY){
                continue;
            }*/
            if(Math.hypot(Math.abs(point[0]-xEdgeClosest), Math.abs(point[1]-yEdgeClosest)) <= radius){
                this.touched = true;
                return Math.atan(y2-y1, x2-x1);
            }
        }
        return null;
    }
}

function drawBarriers(currentStage){
    ctx.fillStyle = PolygonBarrier.color;
    for(let i = 0; i < stageBarriers[currentStage].length; i++){
        let barrier = stageBarriers[currentStage][i];
        barrier.draw();
    }
}

const stage1Barriers = []
stage1Barriers.push(new PolygonBarrier([250, 300, 400], [130, 200, 100]));
stage1Barriers.push(new PolygonBarrier([100, 100, 400, 400], [250, 400, 400, 250]));

const stage2Barriers = []
stage2Barriers.push(new PolygonBarrier([100, 100, 400, 400], [250, 400, 400, 250]));
stage2Barriers.push(new PolygonBarrier([250, 300, 320, 260], [130, 120, 300, 360]));

let currentStage = 1;

const stageBarriers = {
    1: stage1Barriers,
    2: stage2Barriers
}

document.getElementById("levelSelect").addEventListener("change", setCurrentLevel);
function setCurrentLevel(){
    currentStage = document.getElementById("levelSelect").value;
}
setCurrentLevel();