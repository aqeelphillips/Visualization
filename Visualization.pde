//need to use proper getter and setter methods

import java.util.ArrayList;

int width;
int height;
int counter;
int days;
int speed;

Puff[] puffs;
Initializer in;
UI ui;

void setup() {
  width = 800;
  height = 600;
  size(width, height);
  
  counter = 0;
  days = 1;
  speed = 20;
  
  in = new Initializer("Desert", 100);
  ui = new UI();
  
  puffs = new Puff[6];
  puffs[0] = new Puff((int)(in.f("C")*2000), "F(C)", "C", 150, height/3);
  puffs[1] = new Puff((int)(in.f("c")*2000), "F(c)", "c", 150, 2*height/3);
  puffs[2] = new Puff((int)(in.f("H")*2000), "F(H)", "H", 400, height/3);
  puffs[3] = new Puff((int)(in.f("h")*2000), "F(h)", "h", 400, 2*height/3);
  puffs[4] = new Puff((int)(in.f("W")*2000), "F(W)", "W", 650, height/3);
  puffs[5] = new Puff((int)(in.f("w")*2000), "F(w)", "w", 650, 2*height/3);
  
  frameRate(30);
}

void draw() {
  background(0);
  noStroke();
  
  if (mousePressed) {
     checkButtons(mouseX, mouseY); 
  }

  paintButtons();
  paintText();

  for (int i = 0; i < puffs.length; i++) {
    Puff p = getPuffs()[i];
    //Update the size of the puff based on the new frequency of its attached gene
    p.updateSize(getIn().f(p.getGene()));
    p.move();
    for (int q = 0; q < p.getFrequencies().size(); q++) {
      //Paint each node of the puff
      float rad = p.getCellRadius().get(q);
      float px = p.getPx().get(q);
      float py = p.getPy().get(q);
      float headX = p.getHeadX();
      float headY = p.getHeadY();
      noStroke();
      fill(p.getRed(), p.getGreen(), p.getBlue(), p.getOpactiy());      
      ellipse(px, py, rad, rad);
    }
    p.setSpeed();
    
    //Labels for each puff:
    textSize(32);
    if (getIn().f(p.getGene()) == 1) {
      //Golden 100% ratio:
      fill(255, 188, 71, 255);
    } else {
      fill(255, 255, 255, 255);
    }
    text(p.getLabel(), p.getHeadX(), p.getHeadY());
    textSize(20);
    String f = "" + getIn().f(p.getGene());
    text(f, p.getHeadX(), p.getHeadY() - 50);
  }
  if (getCounter() >= getSpeed()) {
      //Counter controls how often days pass
      increaseDays();
      getIn().passDay();
      setCounter(0);
  }
  counter++;
}

void paintButtons() {
   for (int i = 0; i < ui.getEnvOptions().length; i++) {
      Button b = ui.getEnvOptions()[i];
      fill(100, 100, 100, 255);
      if (b.isOver(mouseX, mouseY)) {
          fill(255, 255, 255, 255);
      }
      rect(b.getX(), b.getY(), b.getW(), b.getH());
      
      fill(255, 255, 255, 255);
      textSize(20);
      text(b.getLabel(), b.getX() + 25, b.getY() + b.getH());
  }
  //Kind of useless loop:
  for (int i = 0; i < ui.getSpeedOptions().length; i++) {
      Button b = ui.getSpeedOptions()[i];
      fill(100, 100, 100, 255);
      if (b.isOver(mouseX, mouseY)) {
          fill(255, 255, 255, 255);
      }
      rect(b.getX(), b.getY(), b.getW(), b.getH());
     
      fill(255, 255, 255, 255);
      textSize(20);
      text(b.getLabel(), b.getX() + 25, b.getY() + b.getH());
  }
}
  
void checkButtons(int x, int y) {
  //Checks to see if a button was pressed, and performs its associated action if so
  Button[] env = getUi().getEnvOptions();
  for (int i = 0; i < env.length; i++) {
       if (env[i].isOver(x, y)) {
          setIn(new Initializer(env[i].getLabel(), 100));
          puffs[0] = new Puff((int)(getIn().f("C")*2000), "F(C)", "C", 150, height/3);
          puffs[1] = new Puff((int)(getIn().f("c")*2000), "F(c)", "c", 150, 2*height/3);
          puffs[2] = new Puff((int)(getIn().f("H")*2000), "F(H)", "H", 400, height/3);
          puffs[3] = new Puff((int)(getIn().f("h")*2000), "F(h)", "h", 400, 2*height/3);
          puffs[4] = new Puff((int)(getIn().f("W")*2000), "F(W)", "W", 650, height/3);
          puffs[5] = new Puff((int)(getIn().f("w")*2000), "F(w)", "w", 650, 2*height/3);
          setDays(1);
          setCounter(0);
          //Cancels unneeded iteration:
          return;
       }
    }
    Button[] spe = getUi().getSpeedOptions();
    for (int i = 0; i < spe.length; i++) {
       if (spe[i].isOver(x, y)) {
          if (spe[i].getLabel() == "Faster" && speed > 1) {
             speed /= 2; 
          } else if (spe[i].getLabel() == "Slower" && speed < 20) {
             speed *= 2;
          }
       }
       return;
   } 
}  

void paintText() {
    //A few surrounding HUD-like elements
    textSize(32);
    fill(255, 255, 255, 255);
    text("DAY "+getDays(), 60, 60);
    text(getIn().getEnv().getConditions(), width - 150, 60);
    textSize(20);
    text("Current num of organisms: "+getIn().getOrgs().size(), 450, 90);
}

Puff[] getPuffs() {
   return puffs; 
}

int getCounter() {
  return counter;
}
void setCounter(int n) {
  counter = n;
}

void increaseDays() {
   days++; 
}
void setDays(int n) {
   days = n; 
}
int getDays() {
   return days; 
}

int getSpeed() {
  return speed; 
}
void setSpeed(int n) {
   speed = n; 
}

UI getUi() {
   return ui; 
}

Initializer getIn() {
   return in; 
}
void setIn(Initializer i) {
   in = i; 
}


public class Puff {
  int deltaBlue;
  int red;
  int blue;
  int green;
  int opacity;
  
  String gene;
  String label;
  float headX;
  float headY;
  float speedX;
  float speedY;
  int cells;
  
  ArrayList<Float> px;
  ArrayList<Float> py;
  ArrayList<Float> radiiX;
  ArrayList<Float> radiiY;
  ArrayList<Float> angle;
  ArrayList<Float> frequency;
  ArrayList<Float> cellRadius;
  
  int cellsToAdd = 0;
  int cellsToRemove = 0;
  
  Puff(int c, String lab, String g, int w, int h){
    red = 150;
    blue = 130;
    green = 0;
    opacity = 5;
    
    label = lab;
    cells = c;
    speedX = .7;
    speedY = .9;
    gene = g;
    
    //I considered simplifying this down into a Node class but it was calling too slowly to present a smooth visualization
    px= new ArrayList<Float>(cells);
    py= new ArrayList<Float>(cells);
    radiiX = new ArrayList<Float>(cells);
    radiiY = new ArrayList<Float>(cells);
    angle = new ArrayList<Float>(cells);
    frequency = new ArrayList<Float>(cells);
    cellRadius = new ArrayList<Float>(cells);
    
    headX = w;
    headY = h;
  
    // Fill body arrays
    for (int i = 0; i < cells; i++){
      addCell();
    }
    frameRate(30);
  }
  
  void updateSize(double d) {
     //Instead of removing them all at once, cells are removed one at a time in the main Move loop
     //This makes the animation smoother, and caused them to "wriggle" when dying off 
     int size = (int)(d * 2000);
     if (px.size() > size) {
        cellsToRemove = px.size() - size;
        cellsToAdd = 0;
     } else if (px.size() < size) {
        cellsToAdd = px.size() - size;
        cellsToRemove = 0;
     }
     int newBlue = (int)(d * 255);
     deltaBlue = (newBlue - blue);
  }
  
  void addCell() {
     radiiX.add(random(-7, 7)); 
     radiiY.add(random(-4, 4));
     frequency.add(random(-5, 5));
     cellRadius.add(random(16, 30));
     px.add(null);
     py.add(null);
     angle.add((float)0); 
  }
  
  void removeCell() {
     if (radiiX.size() >= 1) {
       radiiX.remove(radiiX.size() - 1); 
       radiiY.remove(radiiY.size() - 1);
       frequency.remove(frequency.size() - 1);
       cellRadius.remove(cellRadius.size() - 1);
       px.remove(px.size() - 1);
       py.remove(py.size() - 1);
       angle.remove(angle.size() - 1);
     } 
  }
  
  void move(){
    int lim = 100 / getSpeed();
    //Removes or adds cells, if necessary
    if (cellsToRemove >= lim) {
       for (int i = 0; i < lim; i++) {
         removeCell();
       }
       cellsToRemove -= lim; 
    } else if (cellsToAdd >= lim) {
       for (int i = 0; i < lim; i++) {
         addCell();
       } 
       cellsToAdd -= lim;
    }
    
    //Similarly changes the color, if necessary
    if (deltaBlue > 0) {
       blue++;
       deltaBlue--;
    } else if (deltaBlue < 0) {
       blue--;
       deltaBlue++;
    }
    
    int size = px.size();
    // Follow the leader
    for (int i = 0; i < px.size(); i++){
      if (i == 0 || i == size / 2){
        //Needs to continually be established due to the "speed" componant 
        px.set(i, headX);
        py.set(i, headY);
      } 
      else{
        px.set(i, px.get(i-1) + (cos(radians(angle.get(i)))*radiiX.get(i))/2);
        py.set(i, py.get(i-1) + (sin(radians(angle.get(i)))*radiiY.get(i))/2);
      }
    }
  }
    
   void setSpeed() {
     for (int i = 0; i < px.size(); i++){
       // Set speed of body
       angle.set(i, angle.get(i) + frequency.get(i));
     }  
   }
   
   //Getter and setter
   ArrayList<Float> getFrequencies() {
      return frequency; 
   }
   
   String getLabel() {
      return label; 
   }
   
   float getHeadX() {
      return headX; 
   }
   
   float getHeadY() {
      return headY; 
   }
   
   ArrayList<Float> getPx() {
      return px; 
   }
   
   ArrayList<Float> getPy() {
      return py; 
   }
   
   ArrayList<Float> getCellRadius() {
      return cellRadius; 
   }
   
   int getOpactiy() {
      return opacity; 
   }
   
   String getGene() {
      return gene; 
   }
   
   int getRed() {
      return red; 
   }
   
   int getGreen() {
      return green; 
   }
   
   int getBlue() {
      return blue;
   } 
}

public class UI {
   Button[] envOptions;
   Button[] speedOptions;
  
   UI() {
      createEnvOptions(width, height);
      createSpeedOptions(width, height);
   }
   
   void createEnvOptions(int w, int h) {
      envOptions = new Button[6];
      envOptions[0] = new Button(20, height - 150, 15, 15, "Forest");
      envOptions[1] = new Button(20, height - 125, 15, 15, "Arctic");
      envOptions[2] = new Button(20, height - 100, 15, 15, "Desert");
      envOptions[3] = new Button(20, height - 75, 15, 15, "Mountain");
      envOptions[4] = new Button(20, height - 50, 15, 15, "Beach");
      envOptions[5] = new Button(20, height - 25, 15, 15, "Urban");
   }
   
   void createSpeedOptions(int w, int h) {
       speedOptions = new Button[2];
       speedOptions[0] = new Button(width - 100, height - 75, 15, 15, "Faster");
       speedOptions[1] = new Button(width - 100, height - 50, 15, 15, "Slower");
   }
   
   //Getter methods
   Button[] getEnvOptions() {
     return envOptions;
   }
   
   Button[] getSpeedOptions() {
      return speedOptions; 
   }
}

public class Button {
  int x;
  int y;
  int w;
  int h;
  String label;
  
  Button(int xx, int yy, int ww, int hh, String l) {
      x = xx;
      y = yy;
      w = ww;
      h = hh;
      label = l;
  }
  
  boolean isOver(int mx, int my) {
    if (mx >= x && mx <= (w+x) && my >= y && my <= (y+h)) {
       return true; 
    }
    return false;
  }
  
  //Getter methods
  int getX() {
     return x; 
  }
  
  int getY() {
     return y; 
  }
  
  int getW() {
     return w; 
  }
  
  int getH() {
     return h; 
  }
  
  String getLabel() {
     return label; 
  }
}
