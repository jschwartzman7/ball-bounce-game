let score = 0;

function updateBallInCanvas(){
    let nextX = ball.x + ball.xVelo;
    let nextY = ball.y + ball.yVelo;
    if(nextX - ball.radius < 0 || nextX + ball.radius > canvas.width){
        ball.xVelo *= -1;
        score++;
        collisions.push(new Collision(ball.x, ball.y));
    }
    if(nextY - ball.radius < 0 || nextY + ball.radius > canvas.height){
        ball.yVelo *= -1;
        score++;
        collisions.push(new Collision(ball.x, ball.y));
    }
    ball.x += ball.xVelo;
    ball.y += ball.yVelo;
}

function checkBarrierCollisions(){
    for(let i = 0; i < stageBarriers[currentStage].length; i++){
        let currentBarrier = stageBarriers[currentStage][i];
        let collisionStatus = currentBarrier.collidingWith([ball.x, ball.y], ball.radius);
        if(collisionStatus !== null){
            score++;
            collisions.push(new Collision(ball.x, ball.y));
            if(collisionStatus == Math.PI/2){
                ball.xVelo *= -1;
            }
            else if(collisionStatus == 0){
                ball.yVelo *= -1;
            }
            else{
                let angleOfIncidence = collisionStatus - Math.atan2(ball.yVelo, ball.xVelo);
                let newAngle = Math.atan2(ball.yVelo, ball.xVelo) + Math.PI - 2*angleOfIncidence;
                ball.xVelo = ball.speed * Math.cos(newAngle);
                ball.yVelo = ball.speed * Math.sin(newAngle);
            }
        }
    }
}

function checkLevelStatus(){
    for(let i = 0; i < stageBarriers[currentStage].length; i++){
        let currentBarrier = stageBarriers[currentStage][i];
        if(currentBarrier.touched == false){
            return false
        }
    }
    currentStage++;
    return true;
}

function drawCollisions(){
    for(let i = 0; i < collisions.length; i++){
        collisions[i].draw();
        collisions[i].frameIndex++;
        if(collisions[i].frameIndex >= collisions[i].collisionFrames.length){
            collisions.splice(i, 1);
        }
    }
}

function updateGame() {
    updateBallInCanvas();
    gun.updateScaleFactors();
    checkBarrierCollisions();
    if(resetLevel){
        resetBarriers(currentStage);
    }
    checkLevelStatus();
}

function drawGame() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    gun.draw();
    ball.draw();
    ctx.fillStyle = "white";
    ctx.font = "20px Arial";
    ctx.fillText("Score: " + score, 10, 20);
    ctx.fillText("Level: " + currentStage, 10, 40);
    drawBarriers(currentStage);
    drawCollisions();
}

function gameLoop() {
    updateGame();
    drawGame();
    requestAnimationFrame(gameLoop);
}

let collisions = []

gameLoop();