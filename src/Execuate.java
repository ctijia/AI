
public class Execuate {
	private static BoardAndMove boardandmove = new BoardAndMove();
	public static void main(String[] args) { 
    	String[] row = boardandmove.getBoardString(1352);
    	int x=0,y=0;
    	for(int i=0;i<row.length;i++) {
    		System.out.println(row[i]);
    		if(row[i].contains("O")) {
    			y=row[i].indexOf("O");
    			break;
    		}  	
    		x++;
    	}          
    		if(x==5&&y==5)
    			boardandmove.postMakeMove(1352,5,6);
    		else
    			boardandmove.postMakeMove(1352,5,5);
		//System.out.println(boardandmove.getRecentMove(1352).getString("moveX"));
    }
}
