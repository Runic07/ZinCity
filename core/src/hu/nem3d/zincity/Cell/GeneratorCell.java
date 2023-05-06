package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.BuildingEffect;
import hu.nem3d.zincity.Misc.CellException;
import hu.nem3d.zincity.Misc.Direction;

import java.util.Arrays;
import java.util.LinkedList;

public class GeneratorCell extends BuildingCell {

    public GeneratorCell(int x, int y, TiledMapTileLayer tileLayer, BuildingPart part) throws CellException {
        super(x, y, tileLayer, part);
        this.name = "Generator";
        this.price = 20;
        this.range = 0;
        this.upkeepCost = 7.5;

        spreadEffect();
    }

    @Override
    public BuildingEffect getMyEffect() {
        return null;
    }

    @Override
    protected void spreadEffect() {
        boolean[][] visited = new boolean[tileLayer.getWidth()][tileLayer.getHeight()];

        visited[x][y] = true;
        LinkedList<CityCell> queue = new LinkedList<>();
        queue.add(this);

        while(!queue.isEmpty()) {
            CityCell current = queue.remove(0);
            if(!current.isElectrified() && current.setElectrified(true)) {
                //System.out.println("Electrified: " + current.getX() + " " + current.getY());
                Arrays.stream(Direction.values())
                        .map(current::getNeighbor)
                        .filter((neighbor) -> (neighbor != null && !visited[neighbor.getX()][neighbor.getY()]))
                        .forEach((cell) -> {
                            visited[cell.getX()][cell.getY()] = true;
                            queue.add(cell);
                        });
            }
        }
    }
}
