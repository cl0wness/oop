package model.gamefield;

import model.units.landscapes.ChangeableLandscape;
import model.units.SmartRobot;
import model.units.StupidRobot;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Класс - Игровое поле. Прямоугольное поле, состоящее из ячеек.
 */
public class GameField implements Iterable<Cell> {

    // ---------------------- Размеры -----------------------------

    private final int _width;
    private final int _height;

    public int width() {
        return _width;
    }

    public int height() {
        return _height;
    }


    // --------------------------- Ячейки ----------------------

    private final HashMap<Point, Cell> _cells = new HashMap<>();

    public Cell cell(Point pos) {
        return _cells.get( pos );
    }

    public Cell cell(int row, int col) {
        return cell(new Point(row, col));
    }

    @Override
    public Iterator<Cell> iterator() {
        return new GameFieldIterator( this );
    }

    // ---------------------- Роботы ---------------------------

    private SmartRobot _smartRobot;

    public SmartRobot smartRobot() {
        return _smartRobot;
    }

    private StupidRobot _stupidRobot;

    public StupidRobot stupidRobot() {
        return _stupidRobot;
    }

    // -------------------------- Время года ----------------------

    private Season _season = Season.SUMMER;

    public Season currentSeason() { return _season;}

    public void changeSeason() {
        _season = _season.next();

        for(Cell cell : _cells.values()) {
            if(!cell.isEmpty(0) && cell.getUnit(0) instanceof ChangeableLandscape)
                ((ChangeableLandscape) cell.getUnit(0)).changeConditionTo(currentSeason());
        }
    }

    // ---------------------------- Порождение ---------------------

    public GameField(int height, int width, SmartRobot smart, StupidRobot stupid) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }

        _width = width;
        _height = height;

        _smartRobot = smart;
        _stupidRobot = stupid;

        buildField();
    }

    private void buildField() {

        // Создаем ячейки
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                Point pos = new Point(row, col);
                _cells.put(pos, new Cell(this, pos));
            }
        }

        // Связываем ячейки
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {

                Cell cell = cell(row, col);

                if (height() > 1 && row < height() - 1) {
                    cell.setNeighbor(Direction.south(), cell(row + 1, col));
                }
                if (row > 0) {
                    cell.setNeighbor(Direction.north(), cell(row - 1, col));
                }
                if (width() > 1 && col < width() - 1) {
                    cell.setNeighbor(Direction.east(), cell(row, col + 1));
                }
                if (col > 0) {
                    cell.setNeighbor(Direction.west(), cell(row, col - 1));
                }
            }
        }
    }

    // --------------- Итератор по ячейкам ----------------

    private  class GameFieldIterator implements Iterator<Cell> {

        private Cell _cell = null;
        private final GameField _field;

        public GameFieldIterator(GameField field) {
            _field = field;
        }

        @Override
        public boolean hasNext() {

            return nextCell( _cell ) != null;
        }

        @Override
        public Cell next() {
            _cell = nextCell(_cell);
            return _cell;
        }

        private Cell nextCell(Cell cell) {
            Cell next_cell = null;

            if(cell == null) {
                next_cell = _field.cell(0, 0);
            } else {
                next_cell = cell.neighbor( Direction.east() );
                if( next_cell == null && cell.position().getX() < _field.height()-1 ) {
                    next_cell = _field.cell( (int)cell.position().getX() + 1 , 0 );
                }
            }

            return next_cell;
        }
    }
}