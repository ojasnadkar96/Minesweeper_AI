package src;
import java.util.*;

import src.Action.ACTION;

public class MyAI extends AI {
	
	@SuppressWarnings("unchecked")

	public int rowDimension;//row dimension
	public int colDimension;//column dimension
	public int totalMines;//total number of mines

	public int presentX;//value of present X coordinate
	public int presentY;//value of present Y coordinate
    
	public int startFlag=0;
	public int listFlag=0;
	public double min=1000;
	public int minTile=1000;
	public int lastFlag=0;
	public int minusFlag=0;

	ArrayList<myData> data = new ArrayList<myData>();//list of explored tiles
	Queue<myData> neighbour = new LinkedList<myData>();//list of valid neighbors
	Queue<myData> safe = new LinkedList<myData>();
	Set<myData> flagged = new HashSet<myData>();//set of flagged values
	ArrayList<myData> tempNine = new ArrayList<myData>();//
	ArrayList<myData> nonZeros = new ArrayList<myData>();//arraylist of non-zero tiles
	Queue<myData> mineNeighbour = new LinkedList<myData>();//
	Set<myData> mines = new HashSet<myData>();
	Queue<myData> tempOne = new LinkedList<myData>();
	ArrayList<myData> pollQ = new ArrayList<myData>();
	ArrayList<myData> minQ = new ArrayList<myData>();
	
	myData [][] grid=new myData[30][30];// game board, type: myData

public MyAI(int rowDimension, int colDimension, int totalMines, int startX, int startY) 
{	
	this.rowDimension = colDimension;
	this.colDimension = rowDimension;
	this.totalMines = totalMines;	

	presentX = startX;
	presentY = startY;

	for(int i=0;i<this.rowDimension;i++)//initialize grid with objects
		{
			for(int j=0;j<this.colDimension;j++)
			{
				grid[i][j]=new myData(i+1,j+1,9,false,0);
			}
		}
}
	
public Action getAction(int number) 
{
	if(listFlag==1)
	{
		setTileNumber(presentX,presentY,number);
	}
	
	startSetup();//sets up zero and numbered queues
	
	while(!neighbour.isEmpty())//while neighbors queue is full
	{
	listFlag=1;	//can start getting the tile number
	presentX=neighbour.peek().exploredX;//first (X) element of the queue
	presentY=neighbour.peek().exploredY;//first (Y) element of the queue
	grid[presentX-1][presentY-1].exploredX=presentX;//add the tile to be explored to grid
	grid[presentX-1][presentY-1].exploredY=presentY;
	grid[presentX-1][presentY-1].explored=true;
	data.add(grid[presentX-1][presentY-1]);//add to explored queue
	neighbour.remove();//remove from neighbors queue
	return new Action(ACTION.UNCOVER, presentX, presentY);//uncover the tile
	}
	
	flagMines();
	
	while(!neighbour.isEmpty())
	 {
		presentX = neighbour.peek().exploredX;
		presentY = neighbour.peek().exploredY;
		grid[presentX-1][presentY-1].exploredX=presentX;
		grid[presentX-1][presentY-1].exploredY=presentY;
		grid[presentX-1][presentY-1].explored=true;
		data.add(grid[presentX-1][presentY-1]);
		neighbour.remove();
		return new Action(ACTION.UNCOVER, presentX, presentY);
	 }
	
	findpatterns();
	while(!neighbour.isEmpty())
	 {
		presentX = neighbour.peek().exploredX;
		presentY = neighbour.peek().exploredY;
		grid[presentX-1][presentY-1].exploredX=presentX;
		grid[presentX-1][presentY-1].exploredY=presentY;
		grid[presentX-1][presentY-1].explored=true;
		data.add(grid[presentX-1][presentY-1]);
		neighbour.remove();
		return new Action(ACTION.UNCOVER, presentX, presentY);
	 }
		
	while(!neighbour.isEmpty())
	 {
		presentX = neighbour.peek().exploredX;
		presentY = neighbour.peek().exploredY;
		grid[presentX-1][presentY-1].exploredX=presentX;
		grid[presentX-1][presentY-1].exploredY=presentY;
		grid[presentX-1][presentY-1].explored=true;
		data.add(grid[presentX-1][presentY-1]);
		neighbour.remove();
		return new Action(ACTION.UNCOVER, presentX, presentY);
	 }
	
	polling();
	
	while(!neighbour.isEmpty())
	 {
		presentX = neighbour.peek().exploredX;
		presentY = neighbour.peek().exploredY;
		grid[presentX-1][presentY-1].exploredX=presentX;
		grid[presentX-1][presentY-1].exploredY=presentY;
		grid[presentX-1][presentY-1].explored=true;
		data.add(grid[presentX-1][presentY-1]);
		neighbour.remove();
		return new Action(ACTION.UNCOVER, presentX, presentY);
	 }

	  randomRestart();
    	
	while(!neighbour.isEmpty())
	 {
		presentX = neighbour.peek().exploredX;
		presentY = neighbour.peek().exploredY;
		grid[presentX-1][presentY-1].exploredX=presentX;
		grid[presentX-1][presentY-1].exploredY=presentY;
		grid[presentX-1][presentY-1].explored=true;
		data.add(grid[presentX-1][presentY-1]);
		neighbour.remove();
		return new Action(ACTION.UNCOVER, presentX, presentY);
	 }
    
	return new Action(ACTION.LEAVE);//leave game if nothing satisfies 
}

public void findpatterns()
{
	for(int i=1;i<rowDimension-2;i++)
	{
		for(int j=1;j<colDimension-2;j++)
		{
				
			//pattern 11 to mark and flag 
			if (grid[i][j].tile == 1 && grid[i][j+1].tile== 1 && grid[i][j-1].explored == true && grid[i][j+2].explored==false && 
			   		grid[i-1][j-1].explored==true && grid[i-1][j].explored ==true && grid[i-1][j+1].explored == false && 
			   		grid[i-1][j+2].explored == false && grid[i+1][j-1].explored == true && grid[i+1][j].explored == false && 
			   		grid[i+1][j+1].explored == false && grid[i+1][j+2].explored ==false)
				   	   {
				              neighbour.add(grid[i-1][j+2]);
				              neighbour.add(grid[i][j+2]);
				              neighbour.add(grid[i+1][j+2]);
				       }
			//pattern 11 to mark and flag 
			if (grid[i][j].tile == 1 && grid[i][j+1].tile== 1 && grid[i][j-1].explored == true && grid[i][j+2].explored==false && 
			   		grid[i-1][j-1].explored==true && grid[i-1][j].explored ==true && grid[i-1][j+1].explored == true && 
			   		grid[i-1][j+2].explored == false && grid[i+1][j-1].explored == true && grid[i+1][j].explored == false && 
			   		grid[i+1][j+1].explored == false && grid[i+1][j+2].explored ==false)
				   	   {
				              neighbour.add(grid[i-1][j+2]);
				              neighbour.add(grid[i][j+2]);
				              neighbour.add(grid[i+1][j+2]);
				       }
			//pattern 11 to mark and flag 
			if (grid[i][j].tile == 1 && grid[i][j+1].tile== 1 && grid[i][j-1].explored == true && grid[i][j+2].explored==false && 
			   		grid[i-1][j-1].explored==true && grid[i-1][j].explored ==true && grid[i-1][j+1].explored == false && 
			   		grid[i-1][j+2].explored == false && grid[i+1][j-1].explored == true && grid[i+1][j].explored == false && 
			   		grid[i+1][j+1].explored == true && grid[i+1][j+2].explored ==false)
				   	   {
				              neighbour.add(grid[i-1][j+2]);
				              neighbour.add(grid[i][j+2]);
				              neighbour.add(grid[i+1][j+2]);
				       }
			//pattern 22 to mark and flag 
			if (grid[i][j].tile == 2 && grid[i][j+1].tile== 2 && grid[i][j-1].explored == true && grid[i][j+2].explored==false && 
			   		grid[i-1][j-1].explored==true && grid[i-1][j].explored ==true && grid[i-1][j+1].explored == false && 
			   		grid[i-1][j+2].explored == false && grid[i+1][j-1].explored == true && grid[i+1][j].explored == false && 
			   		grid[i+1][j+1].explored == false && grid[i+1][j+2].explored ==false)
				   	   {
				              neighbour.add(grid[i-1][j+2]);
				              neighbour.add(grid[i][j+2]);
				              neighbour.add(grid[i+1][j+2]);
				       }
			//pattern 22 to mark and flag 
			if (grid[i][j].tile == 2 && grid[i][j+1].tile== 2 && grid[i][j-1].explored == true && grid[i][j+2].explored==false && 
			   		grid[i-1][j-1].explored==true && grid[i-1][j].explored ==true && grid[i-1][j+1].explored == true && 
			   		grid[i-1][j+2].explored == false && grid[i+1][j-1].explored == true && grid[i+1][j].explored == false && 
			   		grid[i+1][j+1].explored == false && grid[i+1][j+2].explored ==false)
				   	   {
				              neighbour.add(grid[i-1][j+2]);
				              neighbour.add(grid[i][j+2]);
				              neighbour.add(grid[i+1][j+2]);
				       }
			//pattern 22 to mark and flag 
			if (grid[i][j].tile == 2 && grid[i][j+1].tile== 2 && grid[i][j-1].explored == true && grid[i][j+2].explored==false && 
			   		grid[i-1][j-1].explored==true && grid[i-1][j].explored ==true && grid[i-1][j+1].explored == false && 
			   		grid[i-1][j+2].explored == false && grid[i+1][j-1].explored == true && grid[i+1][j].explored == false && 
			   		grid[i+1][j+1].explored == true && grid[i+1][j+2].explored ==false)
				   	   {
				              neighbour.add(grid[i-1][j+2]);
				              neighbour.add(grid[i][j+2]);
				              neighbour.add(grid[i+1][j+2]);
				       }
			
			//pattern 112 to mark and flag below
			if (grid[i][j].tile == 1 && grid[i][j-1].tile==1 && grid[i][j+1].tile == 2 && grid[i][j+2].explored==false && 
			   		grid[i-1][j-1].explored==true && grid[i-1][j].explored ==true && grid[i-1][j+1].explored == true && 
			   		grid[i-1][j+2].explored == true && grid[i+1][j-1].explored == false && grid[i+1][j].explored == false && 
			   		grid[i+1][j+1].explored == false && grid[i+1][j+2].explored ==true)
				   	   {
				              flagPattern(i,j+2);
				              flagPattern(i+1,j);
				       }
			
			//pattern 11 above to mark safe
			if (grid[i][j].tile == 1 && grid[i][j-1].explored==true && grid[i][j+1].tile == 1 && 
			   		grid[i][j+2].explored==true && grid[i+1][j-1].explored ==true && grid[i+1][j].explored == true && 
			   		grid[i+1][j+1].explored == true && grid[i+1][j+2].explored == true && grid[i-1][j-1].explored == true && 
			   		grid[i-1][j].explored == false && grid[i-1][j+1].explored ==false && grid[i-1][j+2].explored == false)
				   	   {
				               	  	neighbour.add(grid[i-1][j+2]);
				       }
			
			//pattern 11 above to mark safe
			if (grid[i][j].tile == 1 && grid[i][j-1].explored==true && grid[i][j+1].tile == 1 && 
			   		grid[i][j+2].explored==true && grid[i+1][j-1].explored ==true && grid[i+1][j].explored == true && 
			   		grid[i+1][j+1].explored == true && grid[i+1][j+2].explored == true && grid[i-1][j-1].explored == false && 
			   		grid[i-1][j].explored == false && grid[i-1][j+1].explored ==false && grid[i-1][j+2].explored == true)
				   	   {
				               	  	neighbour.add(grid[i-1][j-1]);
				       }
			//pattern 11 below to mark safe
			if (grid[i][j].tile == 1 && grid[i][j-1].explored==true && grid[i][j+1].tile == 1 && 
			   		grid[i][j+2].explored==true && grid[i+1][j-1].explored==true && grid[i+1][j].explored == false && 
			   		grid[i+1][j+1].explored == false && grid[i+1][j+2].explored == false && grid[i-1][j-1].explored == true &&
			   		grid[i-1][j].explored == true && grid[i-1][j+1].explored ==true && grid[i-1][j+2].explored == true)
				   	   {
				               	  	neighbour.add(grid[i+1][j+2]);
				       }
			
			//pattern 11 below to mark safe
			if (grid[i][j].tile == 1 && grid[i][j-1].explored==true && grid[i][j+1].tile == 1 && 
			   		grid[i][j+2].explored==true && grid[i+1][j-1].explored==false && grid[i+1][j].explored == false && 
			   		grid[i+1][j+1].explored == false && grid[i+1][j+2].explored == true && grid[i-1][j-1].explored == true &&
			   		grid[i-1][j].explored == true && grid[i-1][j+1].explored ==true && grid[i-1][j+2].explored == true)
				   	   {
				               	  	neighbour.add(grid[i+1][j-1]);
				       }
			//pattern 11 right to mark safe
			if (grid[i][j].tile == 1 && grid[i-1][j].explored==true && grid[i+1][j].tile == 1 && 
			   		grid[i+2][j].explored==true && grid[i-1][j-1].explored ==true && grid[i][j-1].explored == true && 
			   		grid[i+1][j-1].explored == true && grid[i+2][j-1].explored == true && grid[i-1][j+1].explored==true &&
			   		grid[i][j+1].explored == false && grid[i+1][j+1].explored == false && grid[i+2][j+1].explored == false)
				   	   {
				               	  	neighbour.add(grid[i+2][j+1]);
				       }
			
			//pattern 11 right to mark safe
			if (grid[i][j].tile == 1 && grid[i-1][j].explored==true && grid[i+1][j].tile == 1 && 
			   		grid[i+2][j].explored==true && grid[i-1][j-1].explored ==true && grid[i][j-1].explored == true && 
			   		grid[i+1][j-1].explored == true && grid[i+2][j-1].explored == true && grid[i-1][j+1].explored==false &&
			   		grid[i][j+1].explored == false && grid[i+1][j+1].explored == false && grid[i+2][j+1].explored == true)
				   	   {
				               	  	neighbour.add(grid[i-1][j+1]);
				       }
			//pattern 11 left to mark safe
			if (grid[i][j].tile == 1 && grid[i-1][j].explored==true && grid[i+1][j].tile == 1 && 
			   		grid[i+2][j].explored==true && grid[i-1][j+1].explored ==true && grid[i][j+1].explored == true && 
			   		grid[i+1][j+1].explored ==true && grid[i+2][j+1].explored == true && grid[i-1][j-1].explored==true &&
			   		grid[i][j-1].explored == false && grid[i+1][j-1].explored ==false &&  grid[i+2][j-1].explored == false)
				   	   {
				               	  	neighbour.add(grid[i+2][j-1]);
				       }
			
			//pattern 11 left to mark safe
			if (grid[i][j].tile == 1 && grid[i-1][j].explored==true && grid[i+1][j].tile == 1 && 
			   		grid[i+2][j].explored==true && grid[i-1][j+1].explored ==true && grid[i][j+1].explored == true && 
			   		grid[i+1][j+1].explored ==true && grid[i+2][j+1].explored == true && grid[i-1][j-1].explored==false &&
			   		grid[i][j-1].explored == false && grid[i+1][j-1].explored ==false &&  grid[i+2][j-1].explored == true)
				   	   {
				               	  	neighbour.add(grid[i-1][j-1]);
				       }
			
			//11 pattern to mark 3 safe below
			if(grid[i][j].tile==1 && grid[i+1][j].tile==1 && grid[i-1][j].explored==true && grid[i+2][j].explored==false && grid[i-1][j+1].explored==true
			   && grid[i][j+1].explored==true && grid[i+1][j+1].explored==false && grid[i+2][j+1].explored==false && grid[i-1][j-1].explored==true
			   && grid[i][j-1].explored==true && grid[i+1][j-1].explored==false && grid[i+2][j-1].explored==false)
			{
				neighbour.add(grid[i+2][j-1]);
				neighbour.add(grid[i+2][j]);
				neighbour.add(grid[i+2][j+1]);
			}
			//11 pattern to mark 3 safe above
			if(grid[i][j].tile==1 && grid[i+1][j].tile==1 && grid[i-1][j].explored==false && grid[i+2][j].explored==true && grid[i-1][j+1].explored==false
			   && grid[i][j+1].explored==false && grid[i+1][j+1].explored==true && grid[i+2][j+1].explored==true && grid[i-1][j-1].explored==false
			   && grid[i][j-1].explored==false && grid[i+1][j-1].explored==true && grid[i+2][j-1].explored==true)
				{
					neighbour.add(grid[i-1][j-1]);
					neighbour.add(grid[i-1][j]);
					neighbour.add(grid[i-1][j+1]);
				}
			//11 pattern to mark 3 safe right
			if(grid[i][j].tile==1 && grid[i][j+1].tile==1 && grid[i][j+2].explored==false && grid[i][j-1].explored==true && grid[i-1][j-1].explored==true
			   && grid[i-1][j].explored==true && grid[i-1][j+1].explored==false && grid[i-1][j+2].explored==false && grid[i+1][j-1].explored==true
			   && grid[i+1][j].explored==true && grid[i+1][j+1].explored==false && grid[i+1][j+2].explored==false)
				{
					neighbour.add(grid[i-1][j+2]);
					neighbour.add(grid[i][j+2]);
					neighbour.add(grid[i+1][j+2]);
				}
			//11 pattern to mark 3 safe left
			if(grid[i][j].tile==1 && grid[i][j+1].tile==1 && grid[i][j+2].explored==true && grid[i][j-1].explored==false && grid[i-1][j-1].explored==false
			   && grid[i-1][j].explored==false && grid[i-1][j+1].explored==true && grid[i-1][j+2].explored==true && grid[i+1][j-1].explored==false
			   && grid[i+1][j].explored==false && grid[i+1][j+1].explored==true && grid[i+1][j+2].explored==true)
				{
					neighbour.add(grid[i-1][j-1]);
					neighbour.add(grid[i][j-1]);
					neighbour.add(grid[i+1][j-1]);
				}	
		}
	}
   
	for(int i=1;i<rowDimension-1;i++)
	{
		for(int j=1;j<colDimension-1;j++)
		{   
			 
			//pattern 231 with flagging below
			if (grid[i][j].tile == 3 && grid[i][j-1].tile == 2 && grid[i][j+1].tile == 1 
				&& grid[i-1][j].explored == true && grid[i-1][j+1].explored == true && grid[i-1][j-1].explored ==false && 
				grid[i+1][j-1].explored == false && grid[i+1][j].explored == false && grid[i+1][j+1].explored == false)
			   	   {
			     	  	flagPattern(i-1,j-1);
			     	  	flagPattern(i+1,j-1);
			     	  	flagPattern(i+1,j+1);
			       }
			
			//pattern 231 with flagging above
			if (grid[i][j].tile == 3 && grid[i][j-1].tile == 2 && grid[i][j+1].tile == 1 
				&& grid[i+1][j].explored ==true && grid[i+1][j+1].explored == true && grid[i+1][j-1].explored ==false && 
				grid[i-1][j-1].explored == false && grid[i-1][j].explored ==false && grid[i-1][j+1].explored == false)
			   	   {
			     	  	flagPattern(i+1,j-1);
			     	  	flagPattern(i-1,j-1);
			     	  	flagPattern(i-1,j+1);
			       }
			
			//pattern 231 with flagging on right
			if (grid[i][j].tile == 3 && grid[i-1][j].tile == 2 && grid[i+1][j].tile == 1 
				&& grid[i+1][j-1].explored ==true && grid[i][j-1].explored == true && grid[i+1][j-1].explored == false && 
				grid[i-1][j+1].explored == false && grid[i][j+1].explored ==false && grid[i+1][j+1].explored == false)
			   	   {
			     	  	flagPattern(i-1,j-1);
			     	  	flagPattern(i-1,j+1);
			     	  	flagPattern(i+1,j+1);
			       }
			
			//pattern 231 with flagging on left
			if (grid[i][j].tile == 3 && grid[i-1][j].tile == 2 && grid[i+1][j].tile == 1 
				&& grid[i+1][j+1].explored == true && grid[i][j+1].explored == true && grid[i-1][j+1].explored == false &&
				grid[i-1][j-1].explored == false && grid[i][j-1].explored ==false && grid[i+1][j-1].explored == false)
			   	   {
			     	  	flagPattern(i-1,j+1);
			     	  	flagPattern(i-1,j-1);
			     	  	flagPattern(i+1,j-1);
			       }
			       
			//pattern 232 with flagging above
			if (grid[i][j].tile == 3 && grid[i][j-1].tile == 2 && grid[i][j+1].tile == 2 && 
				grid[i+1][j].tile==-100 && grid[i+1][j-1].explored ==true && grid[i+1][j+1].explored == true
		    	&& grid[i-1][j-1].explored == false && grid[i-1][j].explored ==false && grid[i-1][j+1].explored==false)
			   	   {
			     	  	flagPattern(i-1,j-1);
			     	  	flagPattern(i-1,j+1);
			       }
			
			//pattern 232 with flagging below
			if (grid[i][j].tile == 3 && grid[i][j-1].tile == 2 && grid[i][j+1].tile == 2 && 
				grid[i-1][j].tile==-100 && grid[i-1][j-1].explored ==true && grid[i-1][j+1].explored == true 
		    	&& grid[i+1][j-1].explored == false && grid[i+1][j].explored == false && grid[i+1][j+1].explored == false )
			   	   {
			     	  	flagPattern(i+1,j-1);
			     	  	flagPattern(i+1,j+1);
			       }
			
			//pattern 232 with flagging on left
		   	if (grid[i][j].tile == 3 && grid[i-1][j].tile == 2 && grid[i+1][j].tile == 2 &&
		    	grid[i][j+1].tile==-100 && grid[i-1][j+1].explored ==true && grid[i+1][j+1].explored == true 
		    		&& grid[i-1][j-1].explored == false && grid[i][j-1].explored ==false && grid[i+1][j-1].explored == false )
			   	   {
			     	  	flagPattern(i-1,j-1);
			     	  	flagPattern(i+1,j-1);
			       }
		   	
		  //pattern 232 with flagging on right
		   	if (grid[i][j].tile == 3 && grid[i-1][j].tile == 2 && grid[i+1][j].tile == 2 &&
		    	grid[i][j-1].tile==-100 && grid[i-1][j-1].explored ==true && grid[i+1][j-1].explored == true 
		    		&& grid[i-1][j+1].explored == false && grid[i][j+1].explored ==false && grid[i+1][j+1].explored == false )
			   	   {
			     	  	flagPattern(i-1,j+1);
			     	  	flagPattern(i+1,j+1);
			       }		  
		   	
		    //pattern 121 with flagging above working good
		   	if (grid[i][j].tile == 2 && grid[i][j-1].tile == 1 && grid[i][j+1].tile == 1 &&
	    		grid[i-1][j-1].explored ==false && grid[i-1][j].explored ==false && grid[i-1][j+1].explored == false 
	    		&& grid[i+1][j-1].explored ==true && grid[i+1][j].explored ==true && grid[i+1][j+1].explored == true)
		   	   {
		     	  	flagPattern(i-1,j-1);
		     	  	flagPattern(i-1,j+1);
		       }
		   	//pattern 121 with flagging below
		   	if (grid[i][j].tile == 2 && grid[i][j-1].tile == 1 && grid[i][j+1].tile == 1 &&
		    	grid[i-1][j-1].explored == true && grid[i-1][j].explored ==true && grid[i-1][j+1].explored == true 
		    	&& grid[i+1][j-1].explored ==false && grid[i+1][j].explored == false && grid[i+1][j+1].explored == false)
			   	   {
			     	  	flagPattern(i+1,j-1);
			     	  	flagPattern(i+1,j+1);
			       }
		   	//pattern 121 with flagging on left
		   	if (grid[i][j].tile == 2 && grid[i-1][j].tile == 1 && grid[i+1][j].tile == 1 &&
		    	grid[i-1][j+1].explored == true && grid[i][j+1].explored ==true && grid[i+1][j+1].explored == true 
		    	&& grid[i-1][j-1].explored == false && grid[i][j-1].explored ==false && grid[i+1][j-1].explored == false )
			   	   {
			     	  	flagPattern(i-1,j-1);
			     	  	flagPattern(i+1,j-1);
			       }
		   
		  //pattern 121 with flagging on right
		   	if (grid[i][j].tile == 2 && grid[i-1][j].tile == 1 && grid[i+1][j].tile == 1 &&
		    	grid[i-1][j+1].explored ==false && grid[i][j+1].explored ==false && grid[i+1][j+1].explored == false 
		    		&& grid[i-1][j-1].explored == true && grid[i][j-1].explored == true && grid[i+1][j-1].explored == true)
			   	   {
			     	  	flagPattern(i-1,j+1);
			     	  	flagPattern(i+1,j+1);
			       }
		   	
		  //pattern 12 with flagging above working good
		   	if (grid[i][j].tile == 2 && grid[i][j-1].tile == 1 && grid[i][j+1].explored == true &&
		    	grid[i+1][j-1].explored ==true && grid[i+1][j].explored ==true && grid[i+1][j+1].explored == true 
		    	 && grid[i-1][j-1].explored == false && grid[i-1][j].explored == false && grid[i-1][j+1].explored == false )
			   	   {
			     	  	flagPattern(i-1,j+1);
			       }
		  
		  //pattern 12 with flagging above working good
		   	if (grid[i][j].tile == 2 && grid[i][j+1].tile == 1 && grid[i][j-1].explored == true &&
		    	grid[i+1][j-1].explored ==true && grid[i+1][j].explored ==true && grid[i+1][j+1].explored == true 
		    	 && grid[i-1][j-1].explored == false && grid[i-1][j].explored == false && grid[i-1][j+1].explored == false )
			   	   {
			     	  	flagPattern(i-1,j-1);
			       }
		   	
		  //pattern 12 with flagging below
		   	if (grid[i][j].tile == 2 && grid[i][j-1].tile == 1 && grid[i][j+1].explored == true &&
		    	grid[i+1][j-1].explored ==false && grid[i+1][j].explored == false && grid[i+1][j+1].explored == false 
		    	 && grid[i-1][j-1].explored ==true && grid[i-1][j].explored == true && grid[i-1][j+1].explored == true )
			   	   {
			     	  	flagPattern(i+1,j+1);
			       }
		  //pattern 12 with flagging below
		   	if (grid[i][j].tile == 2 && grid[i][j+1].tile == 1 && grid[i][j-1].explored == true &&
		    	grid[i+1][j-1].explored ==false && grid[i+1][j].explored == false && grid[i+1][j+1].explored == false 
		    	 && grid[i-1][j-1].explored ==true && grid[i-1][j].explored == true && grid[i-1][j+1].explored == true )
			   	   {
			     	  	flagPattern(i+1,j-1);
			       } 	
		  //pattern 12 with flagging right
		   	if (grid[i][j].tile == 2 && grid[i-1][j].tile == 1 && grid[i+1][j].explored == true &&
		    	grid[i-1][j-1].explored == true && grid[i][j-1].explored == true && grid[i+1][j-1].explored == true 
		    	 && grid[i-1][j+1].explored == false && grid[i][j+1].explored ==false && grid[i+1][j+1].explored == false )
			   	   {
			     	  	flagPattern(i+1,j+1);
			       }	
		  //pattern 12 with flagging right
		   	if (grid[i][j].tile == 2 && grid[i+1][j].tile == 1 && grid[i-1][j].explored == true &&
		    	grid[i-1][j-1].explored == true && grid[i][j-1].explored == true && grid[i+1][j-1].explored == true 
		    	 && grid[i-1][j+1].explored == false && grid[i][j+1].explored ==false && grid[i+1][j+1].explored == false )
			   	   {
			     	  	flagPattern(i-1,j+1);
			       }
		  //pattern 12 with flagging left
		   	if (grid[i][j].tile == 2 && grid[i-1][j].tile == 1 && grid[i+1][j].explored == true &&
		    	grid[i-1][j-1].explored ==false && grid[i][j-1].explored ==false && grid[i+1][j-1].explored == false 
		    	 && grid[i-1][j+1].explored == true && grid[i][j+1].explored == true && grid[i+1][j+1].explored == true )
			   	   {
			     	  	flagPattern(i+1,j-1);
			       }
		  //pattern 12 with flagging left
		   	if (grid[i][j].tile == 2 && grid[i+1][j].tile == 1 && grid[i-1][j].explored == true &&
		    	grid[i-1][j-1].explored ==false && grid[i][j-1].explored ==false && grid[i+1][j-1].explored == false 
		    	 && grid[i-1][j+1].explored == true && grid[i][j+1].explored == true && grid[i+1][j+1].explored == true )
			   	   {
			     	  	flagPattern(i-1,j-1);
			       }     
		}
	}

		
	for(int i=2;i<rowDimension-2;i++)
	{
		for(int j=2;j<colDimension-2;j++)
		{
			//pattern 1x1 with flagging above
		   	if (grid[i][j].tile == grid[i-2][j].tile && grid[i-1][j].explored == false && grid[i+1][j].explored == false && grid[i-1][j+1].explored == false
		   		&& grid[i][j+1].explored == false	&& grid[i+1][j+1].explored == false && grid[i-1][j-1].explored == true && grid[i][j-1].explored == true
		   		&& grid[i+1][j-1].explored == false && grid[i][j-2].explored == true && grid[i-1][j-2].explored == false && grid[i+1][j-2].explored == false)
				   	   {
				     	  	neighbour.add(grid[i][j+1]);
				     	  	neighbour.add(grid[i+1][j+1]);
				     	  	neighbour.add(grid[i+1][j]);
				     	  	neighbour.add(grid[i+1][j-1]);
				       }
		  //pattern 1x1 with flagging above
		   	if (grid[i][j].tile == grid[i-2][j].tile && grid[i-1][j].explored == false && grid[i-1][j-1].explored == false && grid[i][j-1].explored == false
		   		&& grid[i+1][j-1].explored == false	&& grid[i+1][j].explored == false && grid[i+1][j+1].explored == false && grid[i][j+1].explored == true
		   		&& grid[i-1][j+1].explored == true && grid[i][j+2].explored == true && grid[i-1][j+2].explored == false && grid[i+1][j+2].explored == false)
				   	   {
				     	  	neighbour.add(grid[i][j-1]);
				     	  	neighbour.add(grid[i+1][j-1]);
				     	  	neighbour.add(grid[i+1][j]);
				     	  	neighbour.add(grid[i+1][j+1]);
				       }	       

			//pattern 212 with flagging above
			if(grid[i][j].tile==1 && grid[i][j-1].tile==2 && grid[i][j+1].tile==2 && grid[i][j-2].explored == true && 
			   grid[i][j+2].explored == true && grid[i+1][j-2].explored ==true && grid[i+1][j-1].explored == true && 
			   grid[i+1][j].explored ==	true && grid[i+1][j+1].explored ==true && grid[i+1][j+2].explored == true && 
			   grid[i-1][j-2].explored == false && grid[i-1][j-1].explored == false && 
			   grid[i-1][j].explored ==	false && grid[i-1][j+1].explored == false && grid[i-1][j+2].explored == false)
			{
				flagPattern(i-1,j);
				flagPattern(i-1,j-2);
				flagPattern(i-1,j+2);
			}
			
			//pattern 212 with flagging below
			if(grid[i][j].tile==1 && grid[i][j-1].tile==2 && grid[i][j+1].tile==2 && grid[i][j-2].explored ==true && 
			   grid[i][j+2].explored == true && grid[i+1][j-2].explored ==false && grid[i+1][j-1].explored == false && 
			   grid[i+1][j].explored ==	false && grid[i+1][j+1].explored ==false && grid[i+1][j+2].explored == false && 
			   grid[i-1][j-2].explored == true && grid[i-1][j-1].explored == true && 
			   grid[i-1][j].explored ==	true && grid[i-1][j+1].explored ==true && grid[i-1][j+2].explored == true)
			{
				flagPattern(i+1,j);
				flagPattern(i+1,j-2);
				flagPattern(i+1,j+2);
			}
			//pattern 12 with flagging above
			if(grid[i][j].tile==2 && grid[i][j-1].tile==1 && grid[i][j-2].explored==false && grid[i][j+1].explored==true && grid[i][j+2].explored==true
			   && grid[i+1][j+1].explored==true && grid[i+1][j+2].explored==true && grid[i+1][j].explored==false && grid[i+1][j-1].explored==false	
			   && grid[i+1][j-2].explored==false && grid[i-1][j-2].explored==false && grid[i-1][j-1].explored==false && grid[i-1][j].explored==false
			   && grid[i-1][j+1].explored==false && grid[i-1][j+2].explored==false)
			{
				flagPattern(i-1,j+1);
			}
			
		}
	}
	
}

public void printBoard()
{
for(int i=0; i<rowDimension; i++)
{
	for(int j=0;j<colDimension;j++)
	{
		System.out.print(grid[i][j].tile);
		System.out.print(" ");
	}
	System.out.println(" ");
}
}

public void setTileNumber(int x, int y, int number) 
{
	if (grid[x-1][y-1].tile == 9) 
	{
		grid[x-1][y-1].tile = number;
	}
	else
		grid[x-1][y-1].tile+=number;
}

public void startSetup()
{
	if(startFlag==0)
	{
		grid[presentX-1][presentY-1].exploredX=presentX;
		grid[presentX-1][presentY-1].exploredY=presentY;
		grid[presentX-1][presentY-1].tile=0;
		grid[presentX-1][presentY-1].explored=true;
		data.add(grid[presentX-1][presentY-1]);//add start tile to explored queue
		getNeighbours(grid[presentX-1][presentY-1].exploredX, grid[presentX-1][presentY-1].exploredY);
		startFlag=1;
	}
	if(grid[presentX-1][presentY-1].tile==0 && listFlag==1)//get neighbors for all 0 tiles
	{
	getNeighbours(grid[presentX-1][presentY-1].exploredX, grid[presentX-1][presentY-1].exploredY);
	}
}

public void flagPattern(int x,int y)  //new method to flag the mines from pattern
{
	   grid[x][y].tile=-100;
	   totalMines--;
	   grid[x][y].explored=true;
	   data.add(grid[x][y]);
	   reduceCount(x+1,y+1);
	   while(!mineNeighbour.isEmpty())
	   {
		   	getNeighbours(mineNeighbour.peek().exploredX,mineNeighbour.peek().exploredY);
			mineNeighbour.remove();
	   }
}

public void flagMines()
{
	if(neighbour.isEmpty()) //This function flags the tile which is the only neighbor of 1
	{
		for(int i=0; i<rowDimension; i++)
		{
			for(int j=0; j<colDimension; j++)
			{
				if(grid[i][j].tile > 0 && grid[i][j].tile!=-100 && grid[i][j].tile!=9)
				{
					nonZeros.add(grid[i][j]);
				}
			}
		}
		
		for(myData m: nonZeros)
		{
			int count=getUnopened(m.exploredX, m.exploredY);
			
			if(m.tile == count)
			{
				for(myData t: tempNine)
				{
					flagged.add(t);
				}
				tempNine.removeAll(tempNine);		
			}
			else
			{
			tempNine.removeAll(tempNine);
			}
		}
		nonZeros.removeAll(nonZeros);
	}
	   for(myData q: flagged)
	   {	
			   grid[q.exploredX-1][q.exploredY-1].tile=-100;
			   totalMines--;
			   grid[q.exploredX-1][q.exploredY-1].explored=true;
			   data.add(grid[q.exploredX-1][q.exploredY-1]);
			   reduceCount(q.exploredX,q.exploredY);
			   
	   }
	   mines.addAll(flagged);
	   flagged.removeAll(flagged);
	   while(!mineNeighbour.isEmpty())
	   {
		   	getNeighbours(mineNeighbour.peek().exploredX,mineNeighbour.peek().exploredY);
			mineNeighbour.remove();
	   }
}

public void randomRestart()
{
	if(grid[0][0].explored!=true)
		neighbour.add(grid[0][0]);
	if(grid[rowDimension-1][0].explored!=true)
		neighbour.add(grid[rowDimension-1][0]);
	if(grid[0][colDimension-1].explored!=true)
		neighbour.add(grid[0][colDimension-1]);
	if(grid[rowDimension-1][colDimension-1].explored!=true)
		neighbour.add(grid[rowDimension-1][colDimension-1]);
}

public void polling()
{
	for(int i=1; i<=rowDimension; i++)
	{
		for(int j=1; j<=colDimension; j++)
		{
			if(grid[i-1][j-1].explored==false)
			{
				 getVote(i,j);
			}
		}
	}
	
	for(myData m:pollQ)
	{
		if(m.vote<min) 
			min=m.vote;
	}
	if(rowDimension==8)
	{
		for(myData m:pollQ)
		{
			if(m.vote==min)
			{
				neighbour.add(m);
				break;
			}
		}
	}
	else
	{
	for(myData m:pollQ)
	{
		if(m.vote==min)
		{
			minQ.add(m);
			//break;
		}
	}
	
	for(myData m:minQ)
	{
		if(m.tile<minTile) 
			minTile=m.tile;
	}
	for(myData m:minQ)
	{
		if(m.tile==minTile)
		{
			neighbour.add(m);
			break;
		}
	}
	}
	for(myData m:pollQ)
	{
	   m.vote=0;	
	}
	minQ.removeAll(minQ);
	pollQ.removeAll(pollQ);
	min = 1000;
	minTile = 1000;
}

public boolean isValid(int x, int y)//function to check if the tile is within valid range 
	{	
		if((x>0) && (x<=rowDimension) && (y>0) && (y<=colDimension))
			return true;
		
		else
			return false;
	}

public boolean inData(int x, int y)//function to check whether tile is in explored queue
{
	for(myData m: data)
	{
		if(m.exploredX==x && m.exploredY==y)
			return true;
	}
	return false;
}

public boolean inQueue(int x, int y)//function to check whether tile is in neighbor queue
	{
		for(myData m: neighbour)
		{
			if(m.exploredX==x && m.exploredY==y)
				return true;
		}
		return false;
	}
	
public void getNeighbours(int x, int y)//get all valid neighbors which are not explored
	{
		if(isValid(x-1,y-1))
		{
			int flag=0;
			if(inQueue(x-1,y-1)||inData(x-1,y-1))
				flag = 1;
			if(flag==0)
				neighbour.add(grid[x-2][y-2]);
		}
		if(isValid(x-1,y))
		{
			int flag=0;
			if(inQueue(x-1,y)||inData(x-1,y))
				flag = 1;
			if(flag==0)
				neighbour.add(grid[x-2][y-1]);
		}
		if(isValid(x-1,y+1))
		{
			int flag=0;
			if(inQueue(x-1,y+1)||inData(x-1,y+1))
				flag = 1;
			if(flag==0)
				neighbour.add(grid[x-2][y]);
		}
		if(isValid(x,y-1))
		{
			int flag=0;
			if(inQueue(x,y-1)||inData(x,y-1))
				flag = 1;
			if(flag==0)
				neighbour.add(grid[x-1][y-2]);
		}
		if(isValid(x,y+1))
		{
			int flag=0;
			if(inQueue(x,y+1)||inData(x,y+1))
				flag = 1;
			if(flag==0)
				neighbour.add(grid[x-1][y]);
		}
		if(isValid(x+1,y-1))
		{
			int flag=0;
			if(inQueue(x+1,y-1)||inData(x+1,y-1))
				flag = 1;
			if(flag==0)
				neighbour.add(grid[x][y-2]);
		}
		if(isValid(x+1,y))
		{
			int flag=0;
			if(inQueue(x+1,y)||inData(x+1,y))
				flag = 1;
			if(flag==0)
				neighbour.add(grid[x][y-1]);
		}
		if(isValid(x+1,y+1))
		{
			int flag=0;
			if(inQueue(x+1,y+1)||inData(x+1,y+1))
				flag = 1;
			if(flag==0)
				neighbour.add(grid[x][y]);
		}
	}

	public int getUnopened(int x, int y)
	{
		int count=0;
		if(isValid(x-1,y-1) && grid[x-2][y-2].explored==false && grid[x-2][y-2].tile!=-100)
		{
				count++;
				tempNine.add(grid[x-2][y-2]);
		}
		if(isValid(x-1,y) && grid[x-2][y-1].explored==false && grid[x-2][y-1].tile!=-100)
		{
				count++;
				tempNine.add(grid[x-2][y-1]);
		}
		if(isValid(x-1,y+1) && grid[x-2][y].explored==false && grid[x-2][y].tile!=-100)
		{
				count++;
				tempNine.add(grid[x-2][y]);
		}
		if(isValid(x,y-1) && grid[x-1][y-2].explored==false && grid[x-1][y-2].tile!=-100)
		{
				count++;
				tempNine.add(grid[x-1][y-2]);
		}
		if(isValid(x,y+1) && grid[x-1][y].explored==false && grid[x-1][y].tile!=-100)
		{
				count++;
				tempNine.add(grid[x-1][y]);
		}
		if(isValid(x+1,y-1) && grid[x][y-2].explored==false && grid[x][y-2].tile!=-100)
		{
				count++;
				tempNine.add(grid[x][y-2]);
		}
		if(isValid(x+1,y) && grid[x][y-1].explored==false && grid[x][y-1].tile!=-100)
		{
				count++;
				tempNine.add(grid[x][y-1]);
		}
		if(isValid(x+1,y+1) && grid[x][y].explored==false && grid[x][y].tile!=-100)
		{
				count++;
				tempNine.add(grid[x][y]);
		}
		return count;
	}

	public void reduceCount(int x, int y)
	{
		if(isValid(x-1,y-1) && grid[x-2][y-2].tile!=-100 && grid[x-2][y-2].tile!=0)
		{
				setTileNumber(x-1, y-1, -1);
				if(grid[x-2][y-2].tile==0)
			{
				mineNeighbour.add(grid[x-2][y-2]);
			}
		}
		if(isValid(x-1,y) && grid[x-2][y-1].tile!=-100 && grid[x-2][y-1].tile!=0)
		{
			setTileNumber(x-1, y, -1);
			if(grid[x-2][y-1].tile==0)
			{
				mineNeighbour.add(grid[x-2][y-1]);
			}
		}
		if(isValid(x-1,y+1) && grid[x-2][y].tile!=-100 && grid[x-2][y].tile!=0)
		{
			setTileNumber(x-1, y+1, -1);
			if(grid[x-2][y].tile==0)
			{
				mineNeighbour.add(grid[x-2][y]);
			}
			
		}
		if(isValid(x,y-1) && grid[x-1][y-2].tile!=-100 && grid[x-1][y-2].tile!=0)
		{
			setTileNumber(x, y-1, -1);
			if(grid[x-1][y-2].tile== 0)
			{
				mineNeighbour.add(grid[x-1][y-2]);
			}
		}
		if(isValid(x,y+1) && grid[x-1][y].tile!=-100 && grid[x-1][y].tile!=0)
		{
			setTileNumber(x, y+1, -1);
			if(grid[x-1][y].tile==0)
			{
				mineNeighbour.add(grid[x-1][y]);
			}
		}
		if(isValid(x+1,y-1) && grid[x][y-2].tile!=-100 && grid[x][y-2].tile!=0)
		{
			setTileNumber(x+1, y-1, -1);
			if(grid[x][y-2].tile==0)
			{
				mineNeighbour.add(grid[x][y-2]);
			}
		}
		if(isValid(x+1,y) && grid[x][y-1].tile!=-100 && grid[x][y-1].tile!=0)
		{
			setTileNumber(x+1, y, -1);
			if(grid[x][y-1].tile==0)
			{
				mineNeighbour.add(grid[x][y-1]);
			}
		}
		if(isValid(x+1,y+1) && grid[x][y].tile!=-100 && grid[x][y].tile!=0)
		{
			setTileNumber(x+1, y+1, -1);
			if(grid[x][y].tile==0)
			{
				mineNeighbour.add(grid[x][y]);
			}
		}
		
	}
	
	public boolean notMinus(int x, int y)
	{
		if(isValid(x-1,y-1) && grid[x-2][y-2].tile==-100)
		{
			return true;
		}
		if(isValid(x-1,y) && grid[x-2][y-1].tile==-100)
		{
			return true;
		}
		if(isValid(x-1,y+1) && grid[x-2][y].tile==-100)
		{
			return true;
		}
		if(isValid(x,y-1) && grid[x-1][y-2].tile==-100)
		{
			return true;
		}
		if(isValid(x,y+1) && grid[x-1][y].tile==-100)
		{
			return true;
		}
		if(isValid(x+1,y-1) && grid[x][y-2].tile==-100)
		{
			return true;
		}
		if(isValid(x+1,y) && grid[x][y-1].tile==-100)
		{
			return true;
		}
		if(isValid(x+1,y+1) && grid[x][y].tile==-100)
		{
				return true;
		}
		return false;
	}
	
	public void getVote(int x, int y)
	{
		if(isValid(x-1,y-1) && grid[x-2][y-2].tile!=-100 && grid[x-2][y-2].explored!=false)
		{
			grid[x-1][y-1].vote+=grid[x-2][y-2].tile;
		}
		if(isValid(x-1,y) && grid[x-2][y-1].tile!=-100 && grid[x-2][y-1].explored!=false)
		{
			grid[x-1][y-1].vote+=grid[x-2][y-1].tile;
		}
		if(isValid(x-1,y+1) && grid[x-2][y].tile!=-100 && grid[x-2][y].explored!=false)
		{
			grid[x-1][y-1].vote+=grid[x-2][y].tile;
		}
		if(isValid(x,y-1) && grid[x-1][y-2].tile!=-100 && grid[x-1][y-2].explored!=false)
		{
			grid[x-1][y-1].vote+=grid[x-1][y-2].tile;
		}
		if(isValid(x,y+1) && grid[x-1][y].tile!=-100 && grid[x-1][y].explored!=false)
		{
			grid[x-1][y-1].vote+=grid[x-1][y].tile;
		}
		if(isValid(x+1,y-1) && grid[x][y-2].tile!=-100 && grid[x][y-2].explored!=false)
		{
			grid[x-1][y-1].vote+=grid[x][y-2].tile;
		}
		if(isValid(x+1,y) && grid[x][y-1].tile!=-100 && grid[x][y-1].explored!=false)
		{
			grid[x-1][y-1].vote+=grid[x][y-1].tile;
		}
		if(isValid(x+1,y+1) && grid[x][y].tile!=-100 && grid[x][y].explored!=false)
		{
			grid[x-1][y-1].vote+=grid[x][y].tile;
		}
		if(grid[x-1][y-1].vote!=0)
		{
		pollQ.add(grid[x-1][y-1]);
		}
		return;
	}
	
public static class myData{
		
	public int exploredX, exploredY, tile;
	public boolean explored;
	public double vote;
	
	public myData(int exploredX, int exploredY, int tile, boolean explored, double vote)
	{
		this.exploredX = exploredX; 
		this.exploredY = exploredY;
		this.tile = tile;
		this.explored = explored;
		this.vote = vote;
	}	
	
	@Override
	public boolean equals(Object eq)
	{
		if(((myData)eq).exploredX==this.exploredX && ((myData)eq).exploredY==this.exploredY)
			return true;
		else 
			return false;
	}
}
}