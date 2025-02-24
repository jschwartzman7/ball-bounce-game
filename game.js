const ctx = canvas.getContext('2d');

function update() {
    updateBallInCanvas();
}

function updateBallInCanvas(){
    let nextX = ball.x + ball.dx;
    let nextY = ball.y + ball.dy;
    if(nextX - ball.radius < 0 || nextX + ball.radius > canvas.width){
        ball.dx *= -1;
    }
    if(nextY - ball.radius < 0 || nextY + ball.radius > canvas.height){
        ball.dy *= -1;
    }
    ball.x += ball.dx;
    ball.y += ball.dy;
}

function drawGun(){
    ctx.fillStyle = gun.color;
    ctx.beginPath();
    ctx.arc(gun.x, gun.y, gun.radius, 0, 2 * Math.PI);
    ctx.fill();
    if(gun.placed){
        ctx.beginPath();
        ctx.moveTo(gun.x, gun.y);
        ctx.lineTo(gun.x + gun.aimLength * Math.cos(gun.aimDirection), gun.y + gun.aimLength * Math.sin(gun.aimDirection));
        ctx.stroke();
    }
}
function drawBall(){
    if(ball.fired){
        console.log("draw ball");
        ctx.fillStyle = ball.color;
        ctx.beginPath();
        ctx.arc(ball.x, ball.y, ball.radius, 0, 2 * Math.PI);
        ctx.fill();
    }
}

function draw() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    drawGun();
    drawBall();
}

function gameLoop() {
    update();
    draw();
    requestAnimationFrame(gameLoop);
}

gameLoop();