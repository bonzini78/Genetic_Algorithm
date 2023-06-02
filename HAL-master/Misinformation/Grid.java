package Misinformation;


import HAL.GridsAndAgents.AgentGrid2D;
import HAL.GridsAndAgents.AgentSQ2Dunstackable;
import HAL.Gui.GridWindow;
import HAL.Rand;
import HAL.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

class Agents extends AgentSQ2Dunstackable<Grid> {

    int score;
    int color;
    String type;

    public void createAgent (String type){
       this.score = 0;
        switch (type){
           case "T": this.type = "T"; this.color = Util.WHITE;
           break;
           case "U": this.type = "U"; this.color = Util.BLACK;
           break;
           case "G": this.type = "G"; this.color = Util.GREEN;
           break;
           case "R": this.type = "R"; this.color = Util.RED;
           break;
       }
    }

    public void PlayGame(int scoreA, int scoreB, int scoreC, int scoreD) {

        int x1 = this.Xsq() - 1;
        int x2 = this.Xsq() + 1;
        int y1 = this.Ysq() - 1;
        int y2 = this.Ysq() + 1;

        if (x1 == -1) {
            x1 = 99;
        }
        if (x2 == 100) {
            x2 = 0;
        }
        if (y1 == -1) {
            y1 = 99;
        }
        if (y2 == 100) {
            y2 = 0;
        }

        Agents left = G.GetAgent(x1, this.Ysq());
        Agents right = G.GetAgent(x2, this.Ysq());
        Agents up = G.GetAgent(Xsq(), y2);
        Agents down = G.GetAgent(Xsq(), y1);

        Agents[] Neighbours = new Agents[4];
        Neighbours[0] = left;
        Neighbours[1] = right;
        Neighbours[2] = up;
        Neighbours[3] = down;

        for (int i = 0; i < Neighbours.length; i++) {
            Agents agent = Neighbours[i];
            if (this.type == "T") {
                if (agent.type == "T" || agent.type == "G") {
                    this.score =+ scoreB;
                    agent.score =+ scoreB;
                }
                if (agent.type == "U") {
                    this.score =+ scoreD;
                    agent.score =+ scoreA;
                }
                if (agent.type == "R") {
                    if (G.rng.Double() < 0.5){
                        this.score =+ scoreD;
                        agent.score =+ scoreA;
                    }
                    else{
                        this.score =+ scoreB;
                        agent.score =+ scoreB;
                    }
                }
            }
            if (this.type == "U") {
                if (agent.type == "T") {
                    this.score =+ scoreA;
                    agent.score =+ scoreD;
                }
                if (agent.type == "U" || agent.type == "G") {
                    this.score =+ scoreC;
                    agent.score =+ scoreC;
                }
                if (agent.type == "R") {
                    if (G.rng.Double() < 0.5){
                        this.score =+ scoreC;
                        agent.score =+ scoreC;
                    }
                    else{

                        this.score =+ scoreA;
                        agent.score =+ scoreD;
                    }
                }
            }
            if (this.type == "G") {
                if (agent.type == "T") {
                    this.score =+ scoreB;
                    agent.score =+ scoreB;
                }
                if (agent.type == "U") {
                    this.score =+ scoreC;
                    agent.score =+ scoreC;
                }
                if (agent.type == "R" || agent.type == "G") {
                    if (G.rng.Double() < 0.5){
                        this.score =+ scoreC;
                        agent.score =+ scoreC;
                    }
                    else{
                        this.score =+ scoreB;
                        agent.score =+ scoreB;
                    }
                }
            }
            if (this.type == "R") {
                double p = G.rng.Double();
                if (agent.type == "T" && p < 0.5) {
                    this.score =+ scoreA;
                    agent.score =+ scoreD;
                }
                if (agent.type == "U" || agent.type == "G" && p < 0.5) {
                    this.score =+ scoreC;
                    agent.score =+ scoreC;
                }
                if (agent.type == "R" && p < 0.5) {
                    if (G.rng.Double() < 0.5){
                        this.score =+ scoreC;
                        agent.score =+ scoreC;
                    }
                    else{
                        this.score =+ scoreA;
                        agent.score =+ scoreD;
                    }
                }
                if (agent.type == "T" || agent.type == "G" && p > 0.5) {
                    this.score =+ scoreB;
                    agent.score =+ scoreB;
                }
                if (agent.type == "U" && p > 0.5) {
                    this.score =+ scoreD;
                    agent.score =+ scoreA;
                }
                if (agent.type == "R" && p > 0.5) {
                    if (G.rng.Double() < 0.5){
                        this.score =+ scoreD;
                        agent.score =+ scoreA;
                    }
                    else{
                        this.score =+ scoreB;
                        agent.score =+ scoreB;
                    }
                }
            }
        }

        //Update strategy of agent
        if (this.type == "G" || this.type == "R") {
            int position = 0;
            Agents highest = this;
            for (int i = 0; i < Neighbours.length; i++) {
                Agents agent = Neighbours[i];
                if (this.score < agent.score) {
                    position++;
                    if (highest.score < agent.score) {
                        highest = agent;
                    }
                }
            }
            this.type = highest.type;
            this.color = highest.color;
            if (position == 1 && G.rng.Double() < 0.25) {
                this.type = highest.type;
                this.color = highest.color;
            }
            if (position == 2 && G.rng.Double() < 0.50) {
                this.type = highest.type;
                this.color = highest.color;
            }
            if (position == 3 && G.rng.Double() < 0.75) {
                this.type = highest.type;
                this.color = highest.color;
            }
            if (position == 4) {
                this.type = highest.type;
                this.color = highest.color;
            }

        }
    }
}

public class Grid extends AgentGrid2D<Agents> {

    Rand rng = new Rand();
    int[] divHood = Util.VonNeumannHood(false);

    public Grid(int x, int y) {
        super(x, y, Agents.class, true, true);
    }

    public void Game(int scoreA, int scoreB, int scoreC, int scoreD) {
        for (Agents agent : this) {
            agent.PlayGame(scoreA, scoreB, scoreC, scoreD);
        }
    }

    public void DrawModel(GridWindow win) {
        for (int i = 0; i < length; i++) {
            int color = GetAgent(i).color;
            win.SetPix(i, color);
        }
    }

    public void checkCount() {
        int countT = 0;
        int countU = 0;
        int countG = 0;
        int countR = 0;
        for (Agents agent : this) {
            if (agent.color == Util.WHITE) {
                countT++;
            }
            if (agent.color == Util.BLACK) {
                countU++;
            }
            if (agent.color == Util.RED) {
                countR++;
            }
            if (agent.color == Util.GREEN) {
                countG++;
            }
        }

        try {
            File fileName = new File("C:\\Users\\tamar\\Documents\\Trustworthy.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.append(Integer.toString(countT));
            writer.newLine();
            writer.close();
        }
            catch(IOException e) {
                System.out.println("Error");
                e.printStackTrace();
            }

        try {
            File fileName = new File("C:\\Users\\tamar\\Documents\\Untrustworthy.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.append(Integer.toString(countU));
            writer.newLine();
            writer.close();
        }
        catch(IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        try {
            File fileName = new File("C:\\Users\\tamar\\Documents\\Gullible.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.append(Integer.toString(countG));
            writer.newLine();
            writer.close();
        }
        catch(IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        try {
            File fileName = new File("C:\\Users\\tamar\\Documents\\Random.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.append(Integer.toString(countR));
            writer.newLine();
            writer.close();
        }
        catch(IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        //THESE ARE THE ADJUSTABLE VARIABLES
        int numT = 50;
        int numU = 50;
        int numG = 5000;
        int numR = 4900;
        int scoreA = 0;
        int scoreB = 10;
        int scoreC = -10;
        int scoreD = 5;
        int x = 100;
        int y = 100;
        int timesteps = 100;

        GridWindow win = new GridWindow(x, y, 10);
        Grid model = new Grid(x, y);

        int [] cells = new int[10000];
        for (int i = 0; i < 10000 ; i++) {
            cells[i] = i;
        }

        Random rand = new Random();
        for (int i = 0; i < cells.length; i++) {
            int toSwap = rand.nextInt(cells.length);
            int temp = cells[toSwap];
            cells[toSwap] = cells[i];
            cells[i] = temp;
        }

        int j = 0;
        while (j < numT) {
            model.NewAgentSQ(cells[j]).createAgent("T");
            j++;
        }

        while (j < numT+numU) {
            model.NewAgentSQ(cells[j]).createAgent("U");
            j++;
        }

        while (j < numT+numU+numG) {
            model.NewAgentSQ(cells[j]).createAgent("G");
            j++;
        }

        while (j < numT+numU+numG+numR) {
            model.NewAgentSQ(cells[j]).createAgent("R");
            j++;
        }


            for (int i = 0; i < timesteps; i++) {

                win.TickPause(100);
                System.out.println(i);
                model.DrawModel(win);
                model.checkCount();
                model.Game(scoreA, scoreB, scoreC, scoreD);

            }



    }
}
