package renovation.core;

import renovation.models.Laminate;
import renovation.models.Tile;
import renovation.models.WoodType;

import java.util.*;
import java.util.stream.Collectors;

public class RenovationImpl implements Renovation {
    private final ArrayDeque<Tile> tiles;
    private double totalTileArea;
    private final ArrayDeque<Laminate> laminates;

    public RenovationImpl() {
        this.tiles = new ArrayDeque<>();
        this.laminates = new ArrayDeque<>();

        this.totalTileArea = 0;
    }

    @Override
    public void deliverTile(Tile tile) {
        if (totalTileArea + tile.width * tile.height > 30) {
            throw new IllegalArgumentException();
        }

        this.tiles.push(tile);

        this.totalTileArea += tile.width * tile.height;
    }

    @Override
    public void deliverFlooring(Laminate laminate) {
        this.laminates.push(laminate);
    }

    @Override
    public double getDeliveredTileArea() {
        return totalTileArea;
    }

    @Override
    public boolean isDelivered(Laminate laminate) {
        return this.laminates.contains(laminate);
    }

    @Override
    public void returnTile(Tile tile) {
        if (!this.tiles.contains(tile)) {
            throw new IllegalArgumentException();
        }

        this.tiles.remove(tile);
        this.totalTileArea -= tile.width * tile.height;
    }

    @Override
    public void returnLaminate(Laminate laminate) {
        boolean removed = this.laminates.remove(laminate);

        if (!removed) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Collection<Laminate> getAllByWoodType(WoodType wood) {
        return this.laminates
            .stream()
            .filter(l -> l.woodType.equals(wood))
            .collect(Collectors.toList());
    }

    @Override
    public Collection<Tile> getAllTilesFitting(double width, double height) {
        return this.tiles
                .stream()
                .filter(t -> t.height <= height && t.width <= width)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Tile> sortTilesBySize() {
        return this.tiles
            .stream()
            .sorted((l, r) -> {
                double lArea = l.width * l.height;
                double rArea = r.width * r.height;

                if (lArea == rArea) {
                    return Double.compare(l.depth, r.depth);
                }

                return Double.compare(lArea, rArea);
            })
            .collect(Collectors.toList());
    }

    @Override
    public Iterator<Laminate> layFlooring() {
        return this.laminates.iterator();
    }
}
