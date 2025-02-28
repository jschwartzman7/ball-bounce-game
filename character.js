const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');

const gun = {
    x: 0,
    y: 0,
    radius: 13,
    aimDirection: 0,
    aimLength: 40,
    placed: false,
    gunScaleFactors: [1.2, 1.4, 1.6, 1.6, 1.5, 1.4, 1.3, 1.2, 1.1, 1.0],
    gunScaleIndex: 9,
    aimScaleFactors: [0.8, 0.7, 0.5, 0.4, 0.4, .5, .5, .6, .7, .8, .9, 1.0],
    aimScaleIndex: 11,

    updateScaleFactors(){
        if(gun.gunScaleIndex < gun.gunScaleFactors.length - 1){
            gun.gunScaleIndex++;
        }
        if(gun.aimScaleIndex < gun.aimScaleFactors.length - 1){
            gun.aimScaleIndex++;
        }
    },

    draw(){
        if(gun.placed){
            ctx.beginPath();
            ctx.strokeStyle = colorSchemes[colorScheme].aimColor;
            ctx.moveTo(gun.x, gun.y);
            let scaledAimLength = gun.aimLength * gun.aimScaleFactors[gun.aimScaleIndex];
            ctx.lineTo(gun.x + scaledAimLength * Math.cos(gun.aimDirection), gun.y + scaledAimLength * Math.sin(gun.aimDirection));
            ctx.stroke();
        }
        ctx.fillStyle = colorSchemes[colorScheme].gunColor;
        ctx.beginPath();
        ctx.arc(gun.x, gun.y, gun.radius*gun.gunScaleFactors[gun.gunScaleIndex], 0, 2 * Math.PI);
        ctx.fill();
    }
};

const ball = {
    x: 0,
    y: 0,
    xVelo: 0,
    yVelo: 0,
    radius: 10,
    speed: 6,
    fired: false,

    draw(){
        if(ball.fired){
            ctx.fillStyle = colorSchemes[colorScheme].ballColor;
            ctx.beginPath();
            ctx.arc(ball.x, ball.y, ball.radius, 0, 2 * Math.PI);
            ctx.fill();
        }
    }
};

canvas.addEventListener("mousemove", (event) => {
    if(gun.placed){
        gun.aimDirection = Math.atan2(event.clientY - canvas.offsetTop - gun.y, event.clientX - canvas.offsetLeft - gun.x);
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
        gun.gunScaleIndex = 0;
        gun.aimScaleIndex = 0;
        ball.x = gun.x + Math.cos(gun.aimDirection)*gun.aimLength;
        ball.y = gun.y + Math.sin(gun.aimDirection)*gun.aimLength;
        ball.xVelo = ball.speed * Math.cos(gun.aimDirection);
        ball.yVelo = ball.speed * Math.sin(gun.aimDirection);
    }
});

