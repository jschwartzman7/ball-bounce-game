const canvas = document.getElementById('gameCanvas');

const gun = {
    x: 0,
    y: 0,
    radius: 13,
    aimDirection: 0,
    aimLength: 40,
    aimWidth: 2,
    color: "black",
    aimColor: "grey",
    placed: false
};

const ball = {
    x: 0,
    y: 0,
    dx: 0,
    dy: 0,
    radius: 15,
    color: "red",
    speed: 4,
    fired: false
};

const barrierRectangle = {
    xCenter: 0,
    yCenter: 0,
    xHalfLength: 0,
    yHalfLength: 0,
    rotationFromCenter: 0,
    color: "black"
};

canvas.addEventListener("mousemove", (event) => {
    if(gun.placed){
        gun.aimDirection = Math.atan2(event.clientY - gun.y, event.clientX - gun.x);
    }
    else{
        gun.x = event.clientX - canvas.offsetLeft;
        gun.y = event.clientY - canvas.offsetTop;
    }
});
canvas.addEventListener("click", (event) => {
    gun.placed = !gun.placed;
});
document.addEventListener("keydown", (event) => {
    if(event.key === " " && gun.placed){
        ball.fired = true;
        ball.x = gun.x + Math.cos(gun.aimDirection)*gun.aimLength;
        ball.y = gun.y + Math.sin(gun.aimDirection)*gun.aimLength;
        ball.dx = ball.speed * Math.cos(gun.aimDirection);
        ball.dy = ball.speed * Math.sin(gun.aimDirection);
    }
});

