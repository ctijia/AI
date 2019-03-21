
public class Execuate implements Runnable{
	private static BoardAndMove boardandmove = new BoardAndMove();
	public static void main(String[] args) { 
		Execuate e = new Execuate();
    	Thread t = new Thread(e);
    	t.start();
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
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
	    	if(row.length!=12) {
	    		System.out.println("not fair");
	  
	    	}else {
//	    		if(x==5&&y==5)
//	    			boardandmove.postMakeMove(1352,5,6);
//	    		else
//	    			boardandmove.postMakeMove(1352,5,5);
	    	}
	    	try {
				Thread.sleep(120000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	}
}
