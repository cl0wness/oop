package model.gamefield;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Класс - Навигатор, основанный на волновом алгоритме поиска пути. Не принимает во внимание ландшафт поля.
 */
public class WaveNavigator extends Navigator{

    public WaveNavigator(GameField field) {
        super(field);
    }
    @Override
    public ArrayList<Cell> findPath(Cell from, Cell to) {
        HashMap<Cell, Integer> cellMarks = waveProrogation(from, to);

        ArrayList<Cell> reversPath = pathRecovery(from, to, cellMarks);
        ArrayList<Cell> path = new ArrayList<>();
        for(int i = reversPath.size()-1; i >= 0; i--)
            path.add(reversPath.get(i));
        return path;
    }

    // Этап распространения волны по полю. Расставляются метки.
    private HashMap<Cell, Integer> waveProrogation(Cell from, Cell to) {
        HashMap<Cell, Integer> cellMarks = new HashMap<>();

        int mark = 1;
        for (Cell cell : _field) {
            cellMarks.put(cell, 0);
        }
        cellMarks.replace(from, mark);

        while(cellMarks.get(to) == 0 && mark <= cellMarks.size()) {
            for (Cell cell : _field) {
                if (cellMarks.get(cell) == mark) {
                    Object[] neighborsDir = cell.neighbors().keySet().toArray();
                    for (Object obj : neighborsDir) {
                        Direction dir = (Direction) obj;
                        if (cellMarks.get(cell.neighbor(dir)) == 0 && cell.wall(dir) == null)
                            cellMarks.replace(cell.neighbor(dir), mark + 1);
                    }
                }
            }
            mark++;
        }
        return cellMarks;
    }

    // Восстановление пути
    private ArrayList<Cell> pathRecovery(Cell from, Cell to, HashMap<Cell, Integer> cellMarks) {
        ArrayList<Cell> path = new ArrayList<>();
        if(cellMarks.get(to) != 0) {
            path.add(to);
            Cell curCell = to;
            while (curCell != from) {
                Object[] neighborsDir = curCell.neighbors().keySet().toArray();
                for (int i = 0; i < neighborsDir.length; i++) {
                    Direction dir = (Direction) neighborsDir[i];
                    if (cellMarks.get(curCell.neighbor(dir)) == cellMarks.get(curCell) - 1) {
                        curCell = curCell.neighbor(dir);
                        path.add(curCell);
                        i = neighborsDir.length;
                    }
                }
            }
        }
        return path;
    }
}
