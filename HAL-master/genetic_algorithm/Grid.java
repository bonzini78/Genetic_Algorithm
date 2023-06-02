package genetic_algorithm;

import HAL.GridsAndAgents.AgentGrid2D;
import HAL.Gui.GridWindow;
import HAL.Util;

public class Grid extends AgentGrid2D<Agent> {

    int[] divHood;

    public Grid(int x, int y) {
        super(x, y, Agent.class, true, true);
        this.divHood = Util.MooreHood(false);
    }

    public void DrawModel(GridWindow win) {
        for (int i = 0; i < length; i++) {
            GetAgent(i).renderAgent();
            int colour = GetAgent(i).getColour();
            win.SetPix(i, colour);
        }
    }

    public int[] getDivHood() {
        return this.divHood;
    }
}
