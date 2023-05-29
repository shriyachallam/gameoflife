package gameoflife;

import gameoflife.Model.Cell;
import java.util.Iterator;

public class TwoDArrayIterator implements Iterator<Cell>{
  private final Cell[][] cellMatrix;
  private int row = 0;
  private int col = 0;

  private TwoDArrayIterator(Cell[][] cellMatrix){
    this.cellMatrix = cellMatrix;
  }

  @Override
  public boolean hasNext(){
    return row < cellMatrix.length && col < cellMatrix[0].length;
  }

  public Cell next(){
    Cell next = cellMatrix[row][col];

    col++;
    if(col >= cellMatrix[0].length){
      col = 0;
      row ++;
    }

    return next;
  }
}
