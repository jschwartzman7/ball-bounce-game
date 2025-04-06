class ColorScheme {
  constructor(bodyColor, backgroundColor, gunColor, aimColor, ballColor) {
    this.bodyColor = bodyColor;
    this.backgroundColor = backgroundColor;
    this.gunColor = gunColor;
    this.aimColor = aimColor;
    this.ballColor = ballColor;
  }
}

let colorSchemes = {}
colorSchemes["Default"] = new ColorScheme("#4E4E4E", "#395A7D", "black", "blue", "red");
colorSchemes["Dark"] = new ColorScheme("#2E2E2E", "#42365d", "#D5D5D5", "red", "red");
colorSchemes["Blue"] = new ColorScheme("black", "blue", "white", "red", "red");

let colorScheme = "Default";

function setColorScheme(key){
    document.body.style.backgroundColor = colorSchemes[key].bodyColor;
    document.getElementById("gameCanvas").style.backgroundColor = colorSchemes[key].backgroundColor;
}
setColorScheme("Default");