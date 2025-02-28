document.getElementById("selectLevel").addEventListener("click", function(){
    if(document.getElementById("levelsMenu").style.display == "block"){
        document.getElementById("levelsMenu").style.display = "none";
    }
    else{
        document.getElementById("levelsMenu").style.display = "block";
    }

});
document.getElementById("settings").addEventListener("click", function(){
    if(document.getElementById("settingsMenu").style.display == "block"){
        document.getElementById("settingsMenu").style.display = "none";
    }
    else{
        document.getElementById("settingsMenu").style.display = "block";
    }
});
document.getElementById("helpPage").addEventListener("click", function(){
    if(document.getElementById("helpMenu").style.display == "block"){
        document.getElementById("helpMenu").style.display = "none";
    }
    else{
        document.getElementById("helpMenu").style.display = "block";
    }
});


function updateBallInCanvas(){
    let nextX = ball.x + ball.xVelo;
    let nextY = ball.y + ball.yVelo;
    if(nextX - ball.radius < 0 || nextX + ball.radius > canvas.width){
        ball.xVelo *= -1;
    }
    if(nextY - ball.radius < 0 || nextY + ball.radius > canvas.height){
        ball.yVelo *= -1;
    }
    ball.x += ball.xVelo;
    ball.y += ball.yVelo;
}

function checkBarrierCollisions(){
    for(let i = 0; i < stageBarriers[currentStage].length; i++){
        let currentBarrier = stageBarriers[currentStage][i];
        let collisionStatus = currentBarrier.collidingWith([ball.x, ball.y], ball.radius);
        if(collisionStatus == Number.MAX_VALUE){
            ball.xVelo *= -1;
        }
        else if(collisionStatus == 0){
            ball.yVelo *= -1;
        }
        else{
            console.log("collision", collisionStatus)
            let angleOfIncidence = Math.atan2(ball.yVelo, ball.xVelo) - collisionStatus;
            let newAngle = Math.atan2(ball.yVelo, ball.xVelo) + Math.PI - 2*angleOfIncidence;
            ball.xVelo = ball.speed * Math.cos(angleOfIncidence);
            ball.yVelo = ball.speed * Math.sin(angleOfIncidence);
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
    return true;
}


function updateGame() {
    updateBallInCanvas();
    gun.updateScaleFactors();
    checkBarrierCollisions();
}

function drawGame() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    gun.draw();
    ball.draw();
    drawBarriers(currentStage);
}

function gameLoop() {
    updateGame();
    drawGame();
    requestAnimationFrame(gameLoop);
}

gameLoop();