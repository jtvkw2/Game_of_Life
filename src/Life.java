import java.util.Random;

/**
 * Life class for cellular automation in a grid
 */
public class Life {
    int[][] lifeBoard;

    /**
     * Life constructor gets user input sets the size of board and seed and creates initial board
     */
    public Life(int boardSize){
        lifeBoard = new int[boardSize][boardSize];
    }

    public int[][] getLifeBoard() {
        return lifeBoard;
    }

    /**
     * Method creates random board based on seed
     */
    public int[][] setUp(int boardSize, int seed){
        Random generator = new Random();
        generator.setSeed(seed);
        for(int i= 0; i < boardSize; i++){
            for(int j =0; j < boardSize; j++){
                int newRand = generator.nextInt(boardSize);
                if(newRand == i || newRand == j){
                    lifeBoard[i][j] = 1;
                }
                else {
                    lifeBoard[i][j] = 0;
                }
            }
        }
        return lifeBoard;
    }

    public int[][]customSetUp(int boardSize, int[][] custom){
        for(int i = 0; i < boardSize; i++){
            System.arraycopy(custom[i], 0, lifeBoard[i], 0, boardSize);
        }
        return lifeBoard;
    }

    /**
     * Takes the old generation and runs through new generation procedure
     *
     * @return new 2d array after new generation
     */
    public int[][] gen(int boardSize){
        for(int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                    int count;
                    boolean isO = false;

                    if(lifeBoard[i][j] == 1 || lifeBoard[i][j] == 3){
                        count = -1;
                        isO = true;
                    }else{
                        count = 0;
                    }

                    for(int x = -1; x < 2; x++){
                        for(int y = -1; y < 2; y++){
                            if((i == 0 && x == -1)||(j == 0 && y == -1)|| (i == boardSize-1 && x == 1)||(j == boardSize-1 && y == 1)){
                                continue;
                            }
                            else{
                                if (lifeBoard[i + x][j + y] == 1 || lifeBoard[i+x][j + y]== 3)
                                    count = count + 1;
                            }
                        }
                    }

                    if(count == 3 && !isO){
                        lifeBoard[i][j] = 3;
                    }
                    else if(isO && count == 2 || count == 3){
                        lifeBoard[i][j] = 1;
                    }
                    else if(count < 2 && isO){
                        lifeBoard[i][j] = 2;
                    }
                    else if(count >=4 && isO){
                        lifeBoard[i][j] = 2;
                    }
                }
            }
        return lifeBoard;
    }
}
